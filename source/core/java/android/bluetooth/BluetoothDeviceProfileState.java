/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Message;
import android.server.BluetoothA2dpService;
import android.server.BluetoothService;
import android.util.Log;

import com.android.internal.util.HierarchicalState;
import com.android.internal.util.HierarchicalStateMachine;

/**
 * This class is the Profile connection state machine associated with a remote
 * device. When the device bonds an instance of this class is created.
 * This tracks incoming and outgoing connections of all the profiles. Incoming
 * connections are preferred over outgoing connections and HFP preferred over
 * A2DP. When the device is unbonded, the instance is removed.
 *
 * States:
 * {@link BondedDevice}: This state represents a bonded device. When in this
 * state none of the profiles are in transition states.
 *
 * {@link OutgoingHandsfree}: Handsfree profile connection is in a transition
 * state because of a outgoing Connect or Disconnect.
 *
 * {@link IncomingHandsfree}: Handsfree profile connection is in a transition
 * state because of a incoming Connect or Disconnect.
 *
 * {@link IncomingA2dp}: A2dp profile connection is in a transition
 * state because of a incoming Connect or Disconnect.
 *
 * {@link OutgoingA2dp}: A2dp profile connection is in a transition
 * state because of a outgoing Connect or Disconnect.
 *
 * Todo(): Write tests for this class, when the Android Mock support is completed.
 * @hide
 */
public final class BluetoothDeviceProfileState extends HierarchicalStateMachine {
    private static final String TAG = "BluetoothDeviceProfileState";
    private static final boolean DBG = false;

    public static final int CONNECT_HFP_OUTGOING = 1;
    public static final int CONNECT_HFP_INCOMING = 2;
    public static final int CONNECT_A2DP_OUTGOING = 3;
    public static final int CONNECT_A2DP_INCOMING = 4;

    public static final int DISCONNECT_HFP_OUTGOING = 5;
    private static final int DISCONNECT_HFP_INCOMING = 6;
    public static final int DISCONNECT_A2DP_OUTGOING = 7;
    public static final int DISCONNECT_A2DP_INCOMING = 8;
    public static final int DISCONNECT_PBAP_OUTGOING = 9;

    public static final int UNPAIR = 100;
    public static final int AUTO_CONNECT_PROFILES = 101;
    public static final int TRANSITION_TO_STABLE = 102;
    public static final int CONNECT_OTHER_PROFILES = 103;

    private static final int AUTO_CONNECT_DELAY = 6000; // 6 secs
    private static final int CONNECT_OTHER_PROFILES_DELAY = 4000; // 4 secs

    private BondedDevice mBondedDevice = new BondedDevice();
    private OutgoingHandsfree mOutgoingHandsfree = new OutgoingHandsfree();
    private IncomingHandsfree mIncomingHandsfree = new IncomingHandsfree();
    private IncomingA2dp mIncomingA2dp = new IncomingA2dp();
    private OutgoingA2dp mOutgoingA2dp = new OutgoingA2dp();

    private Context mContext;
    private BluetoothService mService;
    private BluetoothA2dpService mA2dpService;
    private BluetoothHeadset  mHeadsetService;
    private BluetoothPbap     mPbapService;
    private boolean mHeadsetServiceConnected;
    private boolean mPbapServiceConnected;

    private BluetoothDevice mDevice;
    private int mHeadsetState;
    private int mA2dpState;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (!device.equals(mDevice)) return;

            if (action.equals(BluetoothHeadset.ACTION_STATE_CHANGED)) {
                int newState = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, 0);
                int oldState = intent.getIntExtra(BluetoothHeadset.EXTRA_PREVIOUS_STATE, 0);
                int initiator = intent.getIntExtra(
                    BluetoothHeadset.EXTRA_DISCONNECT_INITIATOR,
                    BluetoothHeadset.LOCAL_DISCONNECT);
                mHeadsetState = newState;
                if (newState == BluetoothHeadset.STATE_DISCONNECTED &&
                    initiator == BluetoothHeadset.REMOTE_DISCONNECT) {
                    sendMessage(DISCONNECT_HFP_INCOMING);
                }
                if (newState == BluetoothHeadset.STATE_CONNECTED ||
                    newState == BluetoothHeadset.STATE_DISCONNECTED) {
                    sendMessage(TRANSITION_TO_STABLE);
                }
            } else if (action.equals(BluetoothA2dp.ACTION_SINK_STATE_CHANGED)) {
                int newState = intent.getIntExtra(BluetoothA2dp.EXTRA_SINK_STATE, 0);
                int oldState = intent.getIntExtra(BluetoothA2dp.EXTRA_PREVIOUS_SINK_STATE, 0);
                mA2dpState = newState;
                if ((oldState == BluetoothA2dp.STATE_CONNECTED ||
                           oldState == BluetoothA2dp.STATE_PLAYING) &&
                           newState == BluetoothA2dp.STATE_DISCONNECTED) {
                    sendMessage(DISCONNECT_A2DP_INCOMING);
                }
                if (newState == BluetoothA2dp.STATE_CONNECTED ||
                    newState == BluetoothA2dp.STATE_DISCONNECTED) {
                    sendMessage(TRANSITION_TO_STABLE);
                }
            } else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                // This is technically not needed, but we can get stuck sometimes.
                // For example, if incoming A2DP fails, we are not informed by Bluez
                sendMessage(TRANSITION_TO_STABLE);
            }
      }
    };

    private boolean isPhoneDocked(BluetoothDevice autoConnectDevice) {
      // This works only because these broadcast intents are "sticky"
      Intent i = mContext.registerReceiver(null, new IntentFilter(Intent.ACTION_DOCK_EVENT));
      if (i != null) {
          int state = i.getIntExtra(Intent.EXTRA_DOCK_STATE, Intent.EXTRA_DOCK_STATE_UNDOCKED);
          if (state != Intent.EXTRA_DOCK_STATE_UNDOCKED) {
              BluetoothDevice device = i.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
              if (device != null && autoConnectDevice.equals(device)) {
                  return true;
              }
          }
      }
      return false;
  }

    public BluetoothDeviceProfileState(Context context, String address,
          BluetoothService service, BluetoothA2dpService a2dpService) {
        super(address);
        mContext = context;
        mDevice = new BluetoothDevice(address);
        mService = service;
        mA2dpService = a2dpService;

        addState(mBondedDevice);
        addState(mOutgoingHandsfree);
        addState(mIncomingHandsfree);
        addState(mIncomingA2dp);
        addState(mOutgoingA2dp);
        setInitialState(mBondedDevice);

        IntentFilter filter = new IntentFilter();
        // Fine-grained state broadcasts
        filter.addAction(BluetoothA2dp.ACTION_SINK_STATE_CHANGED);
        filter.addAction(BluetoothHeadset.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);

        mContext.registerReceiver(mBroadcastReceiver, filter);

        HeadsetServiceListener l = new HeadsetServiceListener();
        PbapServiceListener p = new PbapServiceListener();
    }

    private class HeadsetServiceListener implements BluetoothHeadset.ServiceListener {
        public HeadsetServiceListener() {
            mHeadsetService = new BluetoothHeadset(mContext, this);
        }
        public void onServiceConnected() {
            synchronized(BluetoothDeviceProfileState.this) {
                mHeadsetServiceConnected = true;
            }
        }
        public void onServiceDisconnected() {
            synchronized(BluetoothDeviceProfileState.this) {
                mHeadsetServiceConnected = false;
            }
        }
    }

    private class PbapServiceListener implements BluetoothPbap.ServiceListener {
        public PbapServiceListener() {
            mPbapService = new BluetoothPbap(mContext, this);
        }
        public void onServiceConnected() {
            synchronized(BluetoothDeviceProfileState.this) {
                mPbapServiceConnected = true;
            }
        }
        public void onServiceDisconnected() {
            synchronized(BluetoothDeviceProfileState.this) {
                mPbapServiceConnected = false;
            }
        }
    }

    private class BondedDevice extends HierarchicalState {
        @Override
        protected void enter() {
            Log.i(TAG, "Entering ACL Connected state with: " + getCurrentMessage().what);
            Message m = new Message();
            m.copyFrom(getCurrentMessage());
            sendMessageAtFrontOfQueue(m);
        }
        @Override
        protected boolean processMessage(Message message) {
            log("ACL Connected State -> Processing Message: " + message.what);
            switch(message.what) {
                case CONNECT_HFP_OUTGOING:
                case DISCONNECT_HFP_OUTGOING:
                    transitionTo(mOutgoingHandsfree);
                    break;
                case CONNECT_HFP_INCOMING:
                    transitionTo(mIncomingHandsfree);
                    break;
                case DISCONNECT_HFP_INCOMING:
                    transitionTo(mIncomingHandsfree);
                    break;
                case CONNECT_A2DP_OUTGOING:
                case DISCONNECT_A2DP_OUTGOING:
                    transitionTo(mOutgoingA2dp);
                    break;
                case CONNECT_A2DP_INCOMING:
                case DISCONNECT_A2DP_INCOMING:
                    transitionTo(mIncomingA2dp);
                    break;
                case DISCONNECT_PBAP_OUTGOING:
                    processCommand(DISCONNECT_PBAP_OUTGOING);
                    break;
                case UNPAIR:
                    if (mHeadsetState != BluetoothHeadset.STATE_DISCONNECTED) {
                        sendMessage(DISCONNECT_HFP_OUTGOING);
                        deferMessage(message);
                        break;
                    } else if (mA2dpState != BluetoothA2dp.STATE_DISCONNECTED) {
                        sendMessage(DISCONNECT_A2DP_OUTGOING);
                        deferMessage(message);
                        break;
                    }
                    processCommand(UNPAIR);
                    break;
                case AUTO_CONNECT_PROFILES:
                    if (isPhoneDocked(mDevice)) {
                        // Don't auto connect to docks.
                        break;
                    } else if (!mHeadsetServiceConnected) {
                        deferMessage(message);
                    } else {
                        if (mHeadsetService.getPriority(mDevice) ==
                              BluetoothHeadset.PRIORITY_AUTO_CONNECT &&
                              !mHeadsetService.isConnected(mDevice)) {
                            Log.i(TAG, "Headset:Auto Connect Profiles");
                            mHeadsetService.connectHeadset(mDevice);
                        }
                        if (mA2dpService != null &&
                              mA2dpService.getSinkPriority(mDevice) ==
                              BluetoothA2dp.PRIORITY_AUTO_CONNECT &&
                              mA2dpService.getConnectedSinks().length == 0) {
                            Log.i(TAG, "A2dp:Auto Connect Profiles");
                            mA2dpService.connectSink(mDevice);
                        }
                    }
                    break;
                case CONNECT_OTHER_PROFILES:
                    if (isPhoneDocked(mDevice)) {
                       break;
                    }
                    if (message.arg1 == CONNECT_A2DP_OUTGOING) {
                        if (mA2dpService != null &&
                            mA2dpService.getConnectedSinks().length == 0) {
                            Log.i(TAG, "A2dp:Connect Other Profiles");
                            mA2dpService.connectSink(mDevice);
                        }
                    } else if (message.arg1 == CONNECT_HFP_OUTGOING) {
                        if (!mHeadsetServiceConnected) {
                            deferMessage(message);
                        } else {
                            if (!mHeadsetService.isConnected(mDevice)) {
                                Log.i(TAG, "Headset:Connect Other Profiles");
                                mHeadsetService.connectHeadset(mDevice);
                            }
                        }
                    }
                    break;
                case TRANSITION_TO_STABLE:
                    // ignore.
                    break;
                default:
                    return NOT_HANDLED;
            }
            return HANDLED;
        }
    }

    private class OutgoingHandsfree extends HierarchicalState {
        private boolean mStatus = false;
        private int mCommand;

        @Override
        protected void enter() {
            Log.i(TAG, "Entering OutgoingHandsfree state with: " + getCurrentMessage().what);
            mCommand = getCurrentMessage().what;
            if (mCommand != CONNECT_HFP_OUTGOING &&
                mCommand != DISCONNECT_HFP_OUTGOING) {
                Log.e(TAG, "Error: OutgoingHandsfree state with command:" + mCommand);
            }
            mStatus = processCommand(mCommand);
            if (!mStatus) {
                sendMessage(TRANSITION_TO_STABLE);
                mService.sendProfileStateMessage(BluetoothProfileState.HFP,
                                                 BluetoothProfileState.TRANSITION_TO_STABLE);
            }
        }

        @Override
        protected boolean processMessage(Message message) {
            log("OutgoingHandsfree State -> Processing Message: " + message.what);
            Message deferMsg = new Message();
            int command = message.what;
            switch(command) {
                case CONNECT_HFP_OUTGOING:
                    if (command != mCommand) {
                        // Disconnect followed by a connect - defer
                        deferMessage(message);
                    }
                    break;
                case CONNECT_HFP_INCOMING:
                    if (mCommand == CONNECT_HFP_OUTGOING) {
                        // Cancel outgoing connect, accept incoming
                        cancelCommand(CONNECT_HFP_OUTGOING);
                        transitionTo(mIncomingHandsfree);
                    } else {
                        // We have done the disconnect but we are not
                        // sure which state we are in at this point.
                        deferMessage(message);
                    }
                    break;
                case CONNECT_A2DP_INCOMING:
                    // accept incoming A2DP, retry HFP_OUTGOING
                    transitionTo(mIncomingA2dp);

                    if (mStatus) {
                        deferMsg.what = mCommand;
                        deferMessage(deferMsg);
                    }
                    break;
                case CONNECT_A2DP_OUTGOING:
                    deferMessage(message);
                    break;
                case DISCONNECT_HFP_OUTGOING:
                    if (mCommand == CONNECT_HFP_OUTGOING) {
                        // Cancel outgoing connect
                        cancelCommand(CONNECT_HFP_OUTGOING);
                        processCommand(DISCONNECT_HFP_OUTGOING);
                    }
                    // else ignore
                    break;
                case DISCONNECT_HFP_INCOMING:
                    // When this happens the socket would be closed and the headset
                    // state moved to DISCONNECTED, cancel the outgoing thread.
                    // if it still is in CONNECTING state
                    cancelCommand(CONNECT_HFP_OUTGOING);
                    break;
                case DISCONNECT_A2DP_OUTGOING:
                    deferMessage(message);
                    break;
                case DISCONNECT_A2DP_INCOMING:
                    // Bluez will handle the disconnect. If because of this the outgoing
                    // handsfree connection has failed, then retry.
                    if (mStatus) {
                       deferMsg.what = mCommand;
                       deferMessage(deferMsg);
                    }
                    break;
                case DISCONNECT_PBAP_OUTGOING:
                case UNPAIR:
                case AUTO_CONNECT_PROFILES:
                case CONNECT_OTHER_PROFILES:
                    deferMessage(message);
                    break;
                case TRANSITION_TO_STABLE:
                    transitionTo(mBondedDevice);
                    break;
                default:
                    return NOT_HANDLED;
            }
            return HANDLED;
        }
    }

    private class IncomingHandsfree extends HierarchicalState {
        private boolean mStatus = false;
        private int mCommand;

        @Override
        protected void enter() {
            Log.i(TAG, "Entering IncomingHandsfree state with: " + getCurrentMessage().what);
            mCommand = getCurrentMessage().what;
            if (mCommand != CONNECT_HFP_INCOMING &&
                mCommand != DISCONNECT_HFP_INCOMING) {
                Log.e(TAG, "Error: IncomingHandsfree state with command:" + mCommand);
            }
            mStatus = processCommand(mCommand);
            if (!mStatus) {
                sendMessage(TRANSITION_TO_STABLE);
                mService.sendProfileStateMessage(BluetoothProfileState.HFP,
                                                 BluetoothProfileState.TRANSITION_TO_STABLE);
            }
        }

        @Override
        protected boolean processMessage(Message message) {
            log("IncomingHandsfree State -> Processing Message: " + message.what);
            switch(message.what) {
                case CONNECT_HFP_OUTGOING:
                    deferMessage(message);
                    break;
                case CONNECT_HFP_INCOMING:
                    // Ignore
                    Log.e(TAG, "Error: Incoming connection with a pending incoming connection");
                    break;
                case CONNECT_A2DP_INCOMING:
                    // Serialize the commands.
                    deferMessage(message);
                    break;
                case CONNECT_A2DP_OUTGOING:
                    deferMessage(message);
                    break;
                case DISCONNECT_HFP_OUTGOING:
                    // We don't know at what state we are in the incoming HFP connection state.
                    // We can be changing from DISCONNECTED to CONNECTING, or
                    // from CONNECTING to CONNECTED, so serializing this command is
                    // the safest option.
                    deferMessage(message);
                    break;
                case DISCONNECT_HFP_INCOMING:
                    // Nothing to do here, we will already be DISCONNECTED
                    // by this point.
                    break;
                case DISCONNECT_A2DP_OUTGOING:
                    deferMessage(message);
                    break;
                case DISCONNECT_A2DP_INCOMING:
                    // Bluez handles incoming A2DP disconnect.
                    // If this causes incoming HFP to fail, it is more of a headset problem
                    // since both connections are incoming ones.
                    break;
                case DISCONNECT_PBAP_OUTGOING:
                case UNPAIR:
                case AUTO_CONNECT_PROFILES:
                case CONNECT_OTHER_PROFILES:
                    deferMessage(message);
                    break;
                case TRANSITION_TO_STABLE:
                    transitionTo(mBondedDevice);
                    break;
                default:
                    return NOT_HANDLED;
            }
            return HANDLED;
        }
    }

    private class OutgoingA2dp extends HierarchicalState {
        private boolean mStatus = false;
        private int mCommand;

        @Override
        protected void enter() {
            Log.i(TAG, "Entering OutgoingA2dp state with: " + getCurrentMessage().what);
            mCommand = getCurrentMessage().what;
            if (mCommand != CONNECT_A2DP_OUTGOING &&
                mCommand != DISCONNECT_A2DP_OUTGOING) {
                Log.e(TAG, "Error: OutgoingA2DP state with command:" + mCommand);
            }
            mStatus = processCommand(mCommand);
            if (!mStatus) {
                sendMessage(TRANSITION_TO_STABLE);
                mService.sendProfileStateMessage(BluetoothProfileState.A2DP,
                                                 BluetoothProfileState.TRANSITION_TO_STABLE);
            }
        }

        @Override
        protected boolean processMessage(Message message) {
            log("OutgoingA2dp State->Processing Message: " + message.what);
            Message deferMsg = new Message();
            switch(message.what) {
                case CONNECT_HFP_OUTGOING:
                    processCommand(CONNECT_HFP_OUTGOING);

                    // Don't cancel A2DP outgoing as there is no guarantee it
                    // will get canceled.
                    // It might already be connected but we might not have got the
                    // A2DP_SINK_STATE_CHANGE. Hence, no point disconnecting here.
                    // The worst case, the connection will fail, retry.
                    // The same applies to Disconnecting an A2DP connection.
                    if (mStatus) {
                        deferMsg.what = mCommand;
                        deferMessage(deferMsg);
                    }
                    break;
                case CONNECT_HFP_INCOMING:
                    processCommand(CONNECT_HFP_INCOMING);

                    // Don't cancel A2DP outgoing as there is no guarantee
                    // it will get canceled.
                    // The worst case, the connection will fail, retry.
                    if (mStatus) {
                        deferMsg.what = mCommand;
                        deferMessage(deferMsg);
                    }
                    break;
                case CONNECT_A2DP_INCOMING:
                    // Bluez will take care of conflicts between incoming and outgoing
                    // connections.
                    transitionTo(mIncomingA2dp);
                    break;
                case CONNECT_A2DP_OUTGOING:
                    // Ignore
                    break;
                case DISCONNECT_HFP_OUTGOING:
                    deferMessage(message);
                    break;
                case DISCONNECT_HFP_INCOMING:
                    // At this point, we are already disconnected
                    // with HFP. Sometimes A2DP connection can
                    // fail due to the disconnection of HFP. So add a retry
                    // for the A2DP.
                    if (mStatus) {
                        deferMsg.what = mCommand;
                        deferMessage(deferMsg);
                    }
                    break;
                case DISCONNECT_A2DP_OUTGOING:
                    deferMessage(message);
                    break;
                case DISCONNECT_A2DP_INCOMING:
                    // Ignore, will be handled by Bluez
                    break;
                case DISCONNECT_PBAP_OUTGOING:
                case UNPAIR:
                case AUTO_CONNECT_PROFILES:
                case CONNECT_OTHER_PROFILES:
                    deferMessage(message);
                    break;
                case TRANSITION_TO_STABLE:
                    transitionTo(mBondedDevice);
                    break;
                default:
                    return NOT_HANDLED;
            }
            return HANDLED;
        }
    }

    private class IncomingA2dp extends HierarchicalState {
        private boolean mStatus = false;
        private int mCommand;

        @Override
        protected void enter() {
            Log.i(TAG, "Entering IncomingA2dp state with: " + getCurrentMessage().what);
            mCommand = getCurrentMessage().what;
            if (mCommand != CONNECT_A2DP_INCOMING &&
                mCommand != DISCONNECT_A2DP_INCOMING) {
                Log.e(TAG, "Error: IncomingA2DP state with command:" + mCommand);
            }
            mStatus = processCommand(mCommand);
            if (!mStatus) {
                sendMessage(TRANSITION_TO_STABLE);
                mService.sendProfileStateMessage(BluetoothProfileState.A2DP,
                                                 BluetoothProfileState.TRANSITION_TO_STABLE);
            }
        }

        @Override
        protected boolean processMessage(Message message) {
            log("IncomingA2dp State->Processing Message: " + message.what);
            switch(message.what) {
                case CONNECT_HFP_OUTGOING:
                    deferMessage(message);
                    break;
                case CONNECT_HFP_INCOMING:
                    // Shouldn't happen, but serialize the commands.
                    deferMessage(message);
                    break;
                case CONNECT_A2DP_INCOMING:
                    // ignore
                    break;
                case CONNECT_A2DP_OUTGOING:
                    // Defer message and retry
                    deferMessage(message);
                    break;
                case DISCONNECT_HFP_OUTGOING:
                    deferMessage(message);
                    break;
                case DISCONNECT_HFP_INCOMING:
                    // Shouldn't happen but if does, we can handle it.
                    // Depends if the headset can handle it.
                    // Incoming A2DP will be handled by Bluez, Disconnect HFP
                    // the socket would have already been closed.
                    // ignore
                    break;
                case DISCONNECT_A2DP_OUTGOING:
                    deferMessage(message);
                    break;
                case DISCONNECT_A2DP_INCOMING:
                    // Ignore, will be handled by Bluez
                    break;
                case DISCONNECT_PBAP_OUTGOING:
                case UNPAIR:
                case AUTO_CONNECT_PROFILES:
                case CONNECT_OTHER_PROFILES:
                    deferMessage(message);
                    break;
                case TRANSITION_TO_STABLE:
                    transitionTo(mBondedDevice);
                    break;
                default:
                    return NOT_HANDLED;
            }
            return HANDLED;
        }
    }



    synchronized void cancelCommand(int command) {
        if (command == CONNECT_HFP_OUTGOING ) {
            // Cancel the outgoing thread.
            if (mHeadsetServiceConnected) {
                mHeadsetService.cancelConnectThread();
            }
            // HeadsetService is down. Phone process most likely crashed.
            // The thread would have got killed.
        }
    }

    synchronized void deferProfileServiceMessage(int command) {
        Message msg = new Message();
        msg.what = command;
        deferMessage(msg);
    }

    synchronized boolean processCommand(int command) {
        Log.i(TAG, "Processing command:" + command);
        switch(command) {
            case  CONNECT_HFP_OUTGOING:
                if (mHeadsetService != null) {
                    return mHeadsetService.connectHeadsetInternal(mDevice);
                }
                break;
            case CONNECT_HFP_INCOMING:
                if (!mHeadsetServiceConnected) {
                    deferProfileServiceMessage(command);
                } else if (mHeadsetState == BluetoothHeadset.STATE_CONNECTING) {
                    return mHeadsetService.acceptIncomingConnect(mDevice);
                } else if (mHeadsetState == BluetoothHeadset.STATE_DISCONNECTED) {
                    handleConnectionOfOtherProfiles(command);
                    return mHeadsetService.createIncomingConnect(mDevice);
                }
                break;
            case CONNECT_A2DP_OUTGOING:
                if (mA2dpService != null) {
                    return mA2dpService.connectSinkInternal(mDevice);
                }
                break;
            case CONNECT_A2DP_INCOMING:
                handleConnectionOfOtherProfiles(command);
                // ignore, Bluez takes care
                return true;
            case DISCONNECT_HFP_OUTGOING:
                if (!mHeadsetServiceConnected) {
                    deferProfileServiceMessage(command);
                } else {
                    // Disconnect PBAP
                    // TODO(): Add PBAP to the state machine.
                    Message m = new Message();
                    m.what = DISCONNECT_PBAP_OUTGOING;
                    deferMessage(m);
                    if (mHeadsetService.getPriority(mDevice) ==
                        BluetoothHeadset.PRIORITY_AUTO_CONNECT) {
                        mHeadsetService.setPriority(mDevice, BluetoothHeadset.PRIORITY_ON);
                    }
                    return mHeadsetService.disconnectHeadsetInternal(mDevice);
                }
                break;
            case DISCONNECT_HFP_INCOMING:
                // ignore
                return true;
            case DISCONNECT_A2DP_INCOMING:
                // ignore
                return true;
            case DISCONNECT_A2DP_OUTGOING:
                if (mA2dpService != null) {
                    if (mA2dpService.getSinkPriority(mDevice) ==
                        BluetoothA2dp.PRIORITY_AUTO_CONNECT) {
                        mA2dpService.setSinkPriority(mDevice, BluetoothHeadset.PRIORITY_ON);
                    }
                    return mA2dpService.disconnectSinkInternal(mDevice);
                }
                break;
            case DISCONNECT_PBAP_OUTGOING:
                if (!mPbapServiceConnected) {
                    deferProfileServiceMessage(command);
                } else {
                    return mPbapService.disconnect();
                }
                break;
            case UNPAIR:
                return mService.removeBondInternal(mDevice.getAddress());
            default:
                Log.e(TAG, "Error: Unknown Command");
        }
        return false;
    }

    private void handleConnectionOfOtherProfiles(int command) {
        // The white paper recommendations mentions that when there is a
        // link loss, it is the responsibility of the remote device to connect.
        // Many connect only 1 profile - and they connect the second profile on
        // some user action (like play being pressed) and so we need this code.
        // Auto Connect code only connects to the last connected device - which
        // is useful in cases like when the phone reboots. But consider the
        // following case:
        // User is connected to the car's phone and  A2DP profile.
        // User comes to the desk  and places the phone in the dock
        // (or any speaker or music system or even another headset) and thus
        // gets connected to the A2DP profile.  User goes back to the car.
        // Ideally the car's system is supposed to send incoming connections
        // from both Handsfree and A2DP profile. But they don't. The Auto
        // connect code, will not work here because we only auto connect to the
        // last connected device for that profile which in this case is the dock.
        // Now suppose a user is using 2 headsets simultaneously, one for the
        // phone profile one for the A2DP profile. If this is the use case, we
        // expect the user to use the preference to turn off the A2DP profile in
        // the Settings screen for the first headset. Else, after link loss,
        // there can be an incoming connection from the first headset which
        // might result in the connection of the A2DP profile (if the second
        // headset is slower) and thus the A2DP profile on the second headset
        // will never get connected.
        //
        // TODO(): Handle other profiles here.
        switch (command) {
            case CONNECT_HFP_INCOMING:
                // Connect A2DP if there is no incoming connection
                // If the priority is OFF - don't auto connect.
                if (mA2dpService.getSinkPriority(mDevice) == BluetoothA2dp.PRIORITY_ON ||
                        mA2dpService.getSinkPriority(mDevice) ==
                            BluetoothA2dp.PRIORITY_AUTO_CONNECT) {
                    Message msg = new Message();
                    msg.what = CONNECT_OTHER_PROFILES;
                    msg.arg1 = CONNECT_A2DP_OUTGOING;
                    sendMessageDelayed(msg, CONNECT_OTHER_PROFILES_DELAY);
                }
                break;
            case CONNECT_A2DP_INCOMING:
                // This is again against spec. HFP incoming connections should be made
                // before A2DP, so we should not hit this case. But many devices
                // don't follow this.
                if (mHeadsetService.getPriority(mDevice) == BluetoothHeadset.PRIORITY_ON
                        || mHeadsetService.getPriority(mDevice) ==
                            BluetoothHeadset.PRIORITY_AUTO_CONNECT) {
                    Message msg = new Message();
                    msg.what = CONNECT_OTHER_PROFILES;
                    msg.arg1 = CONNECT_HFP_OUTGOING;
                    sendMessageDelayed(msg, CONNECT_OTHER_PROFILES_DELAY);
                }
                break;
            default:
                break;
        }

    }

    /*package*/ BluetoothDevice getDevice() {
        return mDevice;
    }

    private void log(String message) {
        if (DBG) {
            Log.i(TAG, "Device:" + mDevice + " Message:" + message);
        }
    }
}