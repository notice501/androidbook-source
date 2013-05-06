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

package com.android.internal.telephony;

import android.telephony.SmsCbMessage;
import android.test.AndroidTestCase;

/**
 * Test cases for basic SmsCbMessage operations
 */
public class GsmSmsCbTest extends AndroidTestCase {

    private void doTestGeographicalScopeValue(byte[] pdu, byte b, int expectedGs) {
        pdu[0] = b;
        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals("Unexpected geographical scope decoded", expectedGs, msg
                .getGeographicalScope());
    }

    public void testCreateNullPdu() {
        SmsCbMessage msg = SmsCbMessage.createFromPdu(null);

        assertNull("createFromPdu(byte[] with null pdu should return null", msg);
    }

    public void testCreateTooShortPdu() {
        byte[] pdu = new byte[4];
        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertNull("createFromPdu(byte[] with too short pdu should return null", msg);
    }

    public void testGetGeographicalScope() {
        byte[] pdu = {
                (byte)0xC0, (byte)0x00, (byte)0x00, (byte)0x32, (byte)0x40, (byte)0x11, (byte)0x41,
                (byte)0xD0, (byte)0x71, (byte)0xDA, (byte)0x04, (byte)0x91, (byte)0xCB, (byte)0xE6,
                (byte)0x70, (byte)0x9D, (byte)0x4D, (byte)0x07, (byte)0x85, (byte)0xD9, (byte)0x70,
                (byte)0x74, (byte)0x58, (byte)0x5C, (byte)0xA6, (byte)0x83, (byte)0xDA, (byte)0xE5,
                (byte)0xF9, (byte)0x3C, (byte)0x7C, (byte)0x2E, (byte)0x83, (byte)0xEE, (byte)0x69,
                (byte)0x3A, (byte)0x1A, (byte)0x34, (byte)0x0E, (byte)0xCB, (byte)0xE5, (byte)0xE9,
                (byte)0xF0, (byte)0xB9, (byte)0x0C, (byte)0x92, (byte)0x97, (byte)0xE9, (byte)0x75,
                (byte)0xB9, (byte)0x1B, (byte)0x04, (byte)0x0F, (byte)0x93, (byte)0xC9, (byte)0x69,
                (byte)0xF7, (byte)0xB9, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x00
        };

        doTestGeographicalScopeValue(pdu, (byte)0x00,
                SmsCbMessage.GEOGRAPHICAL_SCOPE_CELL_WIDE_IMMEDIATE);
        doTestGeographicalScopeValue(pdu, (byte)0x40, SmsCbMessage.GEOGRAPHICAL_SCOPE_PLMN_WIDE);
        doTestGeographicalScopeValue(pdu, (byte)0x80, SmsCbMessage.GEOGRAPHICAL_SCOPE_LA_WIDE);
        doTestGeographicalScopeValue(pdu, (byte)0xC0, SmsCbMessage.GEOGRAPHICAL_SCOPE_CELL_WIDE);
    }

    public void testGetMessageBody7Bit() {
        byte[] pdu = {
                (byte)0xC0, (byte)0x00, (byte)0x00, (byte)0x32, (byte)0x40, (byte)0x11, (byte)0x41,
                (byte)0xD0, (byte)0x71, (byte)0xDA, (byte)0x04, (byte)0x91, (byte)0xCB, (byte)0xE6,
                (byte)0x70, (byte)0x9D, (byte)0x4D, (byte)0x07, (byte)0x85, (byte)0xD9, (byte)0x70,
                (byte)0x74, (byte)0x58, (byte)0x5C, (byte)0xA6, (byte)0x83, (byte)0xDA, (byte)0xE5,
                (byte)0xF9, (byte)0x3C, (byte)0x7C, (byte)0x2E, (byte)0x83, (byte)0xEE, (byte)0x69,
                (byte)0x3A, (byte)0x1A, (byte)0x34, (byte)0x0E, (byte)0xCB, (byte)0xE5, (byte)0xE9,
                (byte)0xF0, (byte)0xB9, (byte)0x0C, (byte)0x92, (byte)0x97, (byte)0xE9, (byte)0x75,
                (byte)0xB9, (byte)0x1B, (byte)0x04, (byte)0x0F, (byte)0x93, (byte)0xC9, (byte)0x69,
                (byte)0xF7, (byte)0xB9, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x00
        };
        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals("Unexpected 7-bit string decoded",
                "A GSM default alphabet message with carriage return padding",
                msg.getMessageBody());
    }

    public void testGetMessageBody7BitFull() {
        byte[] pdu = {
                (byte)0xC0, (byte)0x00, (byte)0x00, (byte)0x32, (byte)0x40, (byte)0x11, (byte)0x41,
                (byte)0xD0, (byte)0x71, (byte)0xDA, (byte)0x04, (byte)0x91, (byte)0xCB, (byte)0xE6,
                (byte)0x70, (byte)0x9D, (byte)0x4D, (byte)0x07, (byte)0x85, (byte)0xD9, (byte)0x70,
                (byte)0x74, (byte)0x58, (byte)0x5C, (byte)0xA6, (byte)0x83, (byte)0xDA, (byte)0xE5,
                (byte)0xF9, (byte)0x3C, (byte)0x7C, (byte)0x2E, (byte)0x83, (byte)0xC4, (byte)0xE5,
                (byte)0xB4, (byte)0xFB, (byte)0x0C, (byte)0x2A, (byte)0xE3, (byte)0xC3, (byte)0x63,
                (byte)0x3A, (byte)0x3B, (byte)0x0F, (byte)0xCA, (byte)0xCD, (byte)0x40, (byte)0x63,
                (byte)0x74, (byte)0x58, (byte)0x1E, (byte)0x1E, (byte)0xD3, (byte)0xCB, (byte)0xF2,
                (byte)0x39, (byte)0x88, (byte)0xFD, (byte)0x76, (byte)0x9F, (byte)0x59, (byte)0xA0,
                (byte)0x76, (byte)0x39, (byte)0xEC, (byte)0x4E, (byte)0xBB, (byte)0xCF, (byte)0x20,
                (byte)0x3A, (byte)0xBA, (byte)0x2C, (byte)0x2F, (byte)0x83, (byte)0xD2, (byte)0x73,
                (byte)0x90, (byte)0xFB, (byte)0x0D, (byte)0x82, (byte)0x87, (byte)0xC9, (byte)0xE4,
                (byte)0xB4, (byte)0xFB, (byte)0x1C, (byte)0x02
        };
        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals(
                "Unexpected 7-bit string decoded",
                "A GSM default alphabet message being exactly 93 characters long, " +
                "meaning there is no padding!",
                msg.getMessageBody());
    }

    public void testGetMessageBody7BitWithLanguage() {
        byte[] pdu = {
                (byte)0xC0, (byte)0x00, (byte)0x00, (byte)0x32, (byte)0x04, (byte)0x11, (byte)0x41,
                (byte)0xD0, (byte)0x71, (byte)0xDA, (byte)0x04, (byte)0x91, (byte)0xCB, (byte)0xE6,
                (byte)0x70, (byte)0x9D, (byte)0x4D, (byte)0x07, (byte)0x85, (byte)0xD9, (byte)0x70,
                (byte)0x74, (byte)0x58, (byte)0x5C, (byte)0xA6, (byte)0x83, (byte)0xDA, (byte)0xE5,
                (byte)0xF9, (byte)0x3C, (byte)0x7C, (byte)0x2E, (byte)0x83, (byte)0xEE, (byte)0x69,
                (byte)0x3A, (byte)0x1A, (byte)0x34, (byte)0x0E, (byte)0xCB, (byte)0xE5, (byte)0xE9,
                (byte)0xF0, (byte)0xB9, (byte)0x0C, (byte)0x92, (byte)0x97, (byte)0xE9, (byte)0x75,
                (byte)0xB9, (byte)0x1B, (byte)0x04, (byte)0x0F, (byte)0x93, (byte)0xC9, (byte)0x69,
                (byte)0xF7, (byte)0xB9, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x00
        };
        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals("Unexpected 7-bit string decoded",
                "A GSM default alphabet message with carriage return padding",
                msg.getMessageBody());

        assertEquals("Unexpected language indicator decoded", "es", msg.getLanguageCode());
    }

    public void testGetMessageBody7BitWithLanguageInBody() {
        byte[] pdu = {
                (byte)0xC0, (byte)0x00, (byte)0x00, (byte)0x32, (byte)0x10, (byte)0x11, (byte)0x73,
                (byte)0x7B, (byte)0x23, (byte)0x08, (byte)0x3A, (byte)0x4E, (byte)0x9B, (byte)0x20,
                (byte)0x72, (byte)0xD9, (byte)0x1C, (byte)0xAE, (byte)0xB3, (byte)0xE9, (byte)0xA0,
                (byte)0x30, (byte)0x1B, (byte)0x8E, (byte)0x0E, (byte)0x8B, (byte)0xCB, (byte)0x74,
                (byte)0x50, (byte)0xBB, (byte)0x3C, (byte)0x9F, (byte)0x87, (byte)0xCF, (byte)0x65,
                (byte)0xD0, (byte)0x3D, (byte)0x4D, (byte)0x47, (byte)0x83, (byte)0xC6, (byte)0x61,
                (byte)0xB9, (byte)0x3C, (byte)0x1D, (byte)0x3E, (byte)0x97, (byte)0x41, (byte)0xF2,
                (byte)0x32, (byte)0xBD, (byte)0x2E, (byte)0x77, (byte)0x83, (byte)0xE0, (byte)0x61,
                (byte)0x32, (byte)0x39, (byte)0xED, (byte)0x3E, (byte)0x37, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x00
        };
        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals("Unexpected 7-bit string decoded",
                "A GSM default alphabet message with carriage return padding",
                msg.getMessageBody());

        assertEquals("Unexpected language indicator decoded", "sv", msg.getLanguageCode());
    }

    public void testGetMessageBody8Bit() {
        byte[] pdu = {
                (byte)0xC0, (byte)0x00, (byte)0x00, (byte)0x32, (byte)0x44, (byte)0x11, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45, (byte)0x46, (byte)0x47, (byte)0x41,
                (byte)0x42, (byte)0x43, (byte)0x44, (byte)0x45
        };
        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals("8-bit message body should be empty", "", msg.getMessageBody());
    }

    public void testGetMessageBodyUcs2() {
        byte[] pdu = {
                (byte)0xC0, (byte)0x00, (byte)0x00, (byte)0x32, (byte)0x48, (byte)0x11, (byte)0x00,
                (byte)0x41, (byte)0x00, (byte)0x20, (byte)0x00, (byte)0x55, (byte)0x00, (byte)0x43,
                (byte)0x00, (byte)0x53, (byte)0x00, (byte)0x32, (byte)0x00, (byte)0x20, (byte)0x00,
                (byte)0x6D, (byte)0x00, (byte)0x65, (byte)0x00, (byte)0x73, (byte)0x00, (byte)0x73,
                (byte)0x00, (byte)0x61, (byte)0x00, (byte)0x67, (byte)0x00, (byte)0x65, (byte)0x00,
                (byte)0x20, (byte)0x00, (byte)0x63, (byte)0x00, (byte)0x6F, (byte)0x00, (byte)0x6E,
                (byte)0x00, (byte)0x74, (byte)0x00, (byte)0x61, (byte)0x00, (byte)0x69, (byte)0x00,
                (byte)0x6E, (byte)0x00, (byte)0x69, (byte)0x00, (byte)0x6E, (byte)0x00, (byte)0x67,
                (byte)0x00, (byte)0x20, (byte)0x00, (byte)0x61, (byte)0x00, (byte)0x20, (byte)0x04,
                (byte)0x34, (byte)0x00, (byte)0x20, (byte)0x00, (byte)0x63, (byte)0x00, (byte)0x68,
                (byte)0x00, (byte)0x61, (byte)0x00, (byte)0x72, (byte)0x00, (byte)0x61, (byte)0x00,
                (byte)0x63, (byte)0x00, (byte)0x74, (byte)0x00, (byte)0x65, (byte)0x00, (byte)0x72,
                (byte)0x00, (byte)0x0D, (byte)0x00, (byte)0x0D
        };
        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals("Unexpected 7-bit string decoded",
                "A UCS2 message containing a \u0434 character", msg.getMessageBody());
    }

    public void testGetMessageBodyUcs2WithLanguageInBody() {
        byte[] pdu = {
                (byte)0xC0, (byte)0x00, (byte)0x00, (byte)0x32, (byte)0x11, (byte)0x11, (byte)0x78,
                (byte)0x3C, (byte)0x00, (byte)0x41, (byte)0x00, (byte)0x20, (byte)0x00, (byte)0x55,
                (byte)0x00, (byte)0x43, (byte)0x00, (byte)0x53, (byte)0x00, (byte)0x32, (byte)0x00,
                (byte)0x20, (byte)0x00, (byte)0x6D, (byte)0x00, (byte)0x65, (byte)0x00, (byte)0x73,
                (byte)0x00, (byte)0x73, (byte)0x00, (byte)0x61, (byte)0x00, (byte)0x67, (byte)0x00,
                (byte)0x65, (byte)0x00, (byte)0x20, (byte)0x00, (byte)0x63, (byte)0x00, (byte)0x6F,
                (byte)0x00, (byte)0x6E, (byte)0x00, (byte)0x74, (byte)0x00, (byte)0x61, (byte)0x00,
                (byte)0x69, (byte)0x00, (byte)0x6E, (byte)0x00, (byte)0x69, (byte)0x00, (byte)0x6E,
                (byte)0x00, (byte)0x67, (byte)0x00, (byte)0x20, (byte)0x00, (byte)0x61, (byte)0x00,
                (byte)0x20, (byte)0x04, (byte)0x34, (byte)0x00, (byte)0x20, (byte)0x00, (byte)0x63,
                (byte)0x00, (byte)0x68, (byte)0x00, (byte)0x61, (byte)0x00, (byte)0x72, (byte)0x00,
                (byte)0x61, (byte)0x00, (byte)0x63, (byte)0x00, (byte)0x74, (byte)0x00, (byte)0x65,
                (byte)0x00, (byte)0x72, (byte)0x00, (byte)0x0D
        };
        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals("Unexpected 7-bit string decoded",
                "A UCS2 message containing a \u0434 character", msg.getMessageBody());

        assertEquals("Unexpected language indicator decoded", "xx", msg.getLanguageCode());
    }

    public void testGetMessageIdentifier() {
        byte[] pdu = {
                (byte)0xC0, (byte)0x00, (byte)0x30, (byte)0x39, (byte)0x40, (byte)0x11, (byte)0x41,
                (byte)0xD0, (byte)0x71, (byte)0xDA, (byte)0x04, (byte)0x91, (byte)0xCB, (byte)0xE6,
                (byte)0x70, (byte)0x9D, (byte)0x4D, (byte)0x07, (byte)0x85, (byte)0xD9, (byte)0x70,
                (byte)0x74, (byte)0x58, (byte)0x5C, (byte)0xA6, (byte)0x83, (byte)0xDA, (byte)0xE5,
                (byte)0xF9, (byte)0x3C, (byte)0x7C, (byte)0x2E, (byte)0x83, (byte)0xEE, (byte)0x69,
                (byte)0x3A, (byte)0x1A, (byte)0x34, (byte)0x0E, (byte)0xCB, (byte)0xE5, (byte)0xE9,
                (byte)0xF0, (byte)0xB9, (byte)0x0C, (byte)0x92, (byte)0x97, (byte)0xE9, (byte)0x75,
                (byte)0xB9, (byte)0x1B, (byte)0x04, (byte)0x0F, (byte)0x93, (byte)0xC9, (byte)0x69,
                (byte)0xF7, (byte)0xB9, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x00
        };

        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals("Unexpected message identifier decoded", 12345, msg.getMessageIdentifier());
    }

    public void testGetMessageCode() {
        byte[] pdu = {
                (byte)0x2A, (byte)0xA5, (byte)0x30, (byte)0x39, (byte)0x40, (byte)0x11, (byte)0x41,
                (byte)0xD0, (byte)0x71, (byte)0xDA, (byte)0x04, (byte)0x91, (byte)0xCB, (byte)0xE6,
                (byte)0x70, (byte)0x9D, (byte)0x4D, (byte)0x07, (byte)0x85, (byte)0xD9, (byte)0x70,
                (byte)0x74, (byte)0x58, (byte)0x5C, (byte)0xA6, (byte)0x83, (byte)0xDA, (byte)0xE5,
                (byte)0xF9, (byte)0x3C, (byte)0x7C, (byte)0x2E, (byte)0x83, (byte)0xEE, (byte)0x69,
                (byte)0x3A, (byte)0x1A, (byte)0x34, (byte)0x0E, (byte)0xCB, (byte)0xE5, (byte)0xE9,
                (byte)0xF0, (byte)0xB9, (byte)0x0C, (byte)0x92, (byte)0x97, (byte)0xE9, (byte)0x75,
                (byte)0xB9, (byte)0x1B, (byte)0x04, (byte)0x0F, (byte)0x93, (byte)0xC9, (byte)0x69,
                (byte)0xF7, (byte)0xB9, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x00
        };

        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals("Unexpected message code decoded", 682, msg.getMessageCode());
    }

    public void testGetUpdateNumber() {
        byte[] pdu = {
                (byte)0x2A, (byte)0xA5, (byte)0x30, (byte)0x39, (byte)0x40, (byte)0x11, (byte)0x41,
                (byte)0xD0, (byte)0x71, (byte)0xDA, (byte)0x04, (byte)0x91, (byte)0xCB, (byte)0xE6,
                (byte)0x70, (byte)0x9D, (byte)0x4D, (byte)0x07, (byte)0x85, (byte)0xD9, (byte)0x70,
                (byte)0x74, (byte)0x58, (byte)0x5C, (byte)0xA6, (byte)0x83, (byte)0xDA, (byte)0xE5,
                (byte)0xF9, (byte)0x3C, (byte)0x7C, (byte)0x2E, (byte)0x83, (byte)0xEE, (byte)0x69,
                (byte)0x3A, (byte)0x1A, (byte)0x34, (byte)0x0E, (byte)0xCB, (byte)0xE5, (byte)0xE9,
                (byte)0xF0, (byte)0xB9, (byte)0x0C, (byte)0x92, (byte)0x97, (byte)0xE9, (byte)0x75,
                (byte)0xB9, (byte)0x1B, (byte)0x04, (byte)0x0F, (byte)0x93, (byte)0xC9, (byte)0x69,
                (byte)0xF7, (byte)0xB9, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x68, (byte)0x34, (byte)0x1A, (byte)0x8D,
                (byte)0x46, (byte)0xA3, (byte)0xD1, (byte)0x00
        };

        SmsCbMessage msg = SmsCbMessage.createFromPdu(pdu);

        assertEquals("Unexpected update number decoded", 5, msg.getUpdateNumber());
    }
}
