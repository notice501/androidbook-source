package com.pms.rights;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.pms.adapter.SearchAdapter;
import com.pms.bean.PermissionBean;
import com.pms.bean.TrustAppBean;
import com.pms.res.AXMLPrinter;
import com.pms.utils.DatabaseHelper;
import com.pms.utils.PermissionUtil;
import com.pms.utils.ProgressThread;
import com.pms.utils.UnzipManifest;

/**
 * Activity
 * 
 * @author machao
 * @mail zeusmc.163.com
 */
public class HomeActivity extends Activity {
	// 控件声明区域
	private LinearLayout lin_install, lin_appoint, lin_trust, lin_result;// 本机程序、APK扫描、信任程序、扫描结果
	private Button bt_handle;// 执行操作按钮
	private ListView lv_content;// 内容显示区域
	// Handler相关处理
	public static final int showProgressHandlerWhat = 100;// 显示滚动条.
	public static final int showToastHandlerWhat = 101;// 显示Toast

	public static final int installWhat = 1;// 安装程序列表
	public static final int appointWhat = 2;// Apk扫描
	public static final int trustWhat = 3;// 信任程序
	public static final int resultWhat = 4;// 扫描结果

	public static final int appPermissionWhat = 5;// 某个程序的权限页面处理
	public static final int trust_AppAddWhat = 6;// 添加某个程序到信任程序
	public static final int trust_AppDelWhat = 7;// 删除信任程序
	public static final int trust_AppPermissionWhat = 8;// 信任程序权限
	public static final int scanFileApkWhat = 9;// 添加某个程序到信任程序
	public static final int result_App_Warn = 10;// 扫描后警告程序列表

	// 提示框相关
	public static final int dialog_warn_app = 11; // 提示框，扫描出警告程序
	public static final int dialog_yes = 12;// 提示框，只有一个按钮
	public static final int dialog_scan_apk = 13;// 提示框，显示扫描本地APK文件提示
	public static final int dialog_prompt_apk = 14;// 提示框，如果本地文件格式是APk，则提示是否扫描
	// 解析Manifest.xml相关
	public static String tmpManifestPath = "tmp.xml";// 临时存储文件路径
	public static String assetsPermissionXml = "permission.xml";// 所有收集权限
	public static List<PermissionBean> permissionBeanList;// 读取permission.xml中所有收集权限
	// 数据库操作相关
	public DatabaseHelper mOpenHelper;
	// 其他变量
	public ProgressDialog mDialog;// 公用对话框
	public Intent mIntent;// 公用Intent
	public List<ResolveInfo> installApps = null;// 本机已经安装程序列表
	public List<ResolveInfo> warnApps = null;// 警告级别的已经安装程序列表

	public List<Map<String, Object>> list1;// 具有短信权限的警告程序
	public List<Map<String, Object>> list2;// 具有网络权限的警告程序

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 初始化布局元素
		initMainView();
		// 默认加载"本机程序"列表
		new ProgressThread(HomeActivity.this, handler, installWhat).start();
		// 布局样式调整到"本机程序"
		changeButtonBackground(1);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mOpenHelper = new DatabaseHelper(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mOpenHelper != null)
			mOpenHelper.close();
	}

	/**
	 * 初始化布局
	 */
	private void initMainView() {
		bt_handle = (Button) this.findViewById(R.id.bt_handle);
		lv_content = (ListView) this.findViewById(R.id.lv_content);
		lin_install = (LinearLayout) findViewById(R.id.lin_install);
		lin_appoint = (LinearLayout) findViewById(R.id.lin_appoint);
		lin_trust = (LinearLayout) findViewById(R.id.lin_trust);
		lin_result = (LinearLayout) findViewById(R.id.lin_result);
		this.setOnClickListener(lin_install);
		this.setOnClickListener(lin_appoint);
		this.setOnClickListener(lin_trust);
		this.setOnClickListener(lin_result);
	}

	/**
	 * 处理控件点击事件
	 * 
	 * @param v
	 */
	private void setOnClickListener(View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v == lin_install) {
					System.out.println("-------本机安装程序--------");
					changeButtonBackground(1);
					new ProgressThread(HomeActivity.this, handler, installWhat)
							.start();
				} else if (v == lin_appoint) {
					changeButtonBackground(2);
					new ProgressThread(HomeActivity.this, handler, appointWhat)
							.start();
					System.out.println("-------Apk扫描--------");
				} else if (v == lin_trust) {
					changeButtonBackground(3);
					new ProgressThread(HomeActivity.this, handler, trustWhat)
							.start();
					System.out.println("-------信任程序--------");
				} else if (v == lin_result) {
					changeButtonBackground(4);
					new ProgressThread(HomeActivity.this, handler, resultWhat)
							.start();
					System.out.println("-------扫描结果--------");
				}
			}
		});
	}

	/**
	 * 更改对应背景图片，点击页面功能
	 * 
	 * @param type
	 *            1.本机安装程序 2.Apk扫描 3.信任程序 4.扫描结果
	 */
	private void changeButtonBackground(int type) {
		switch (type) {
		case 1:
			lin_install.setBackgroundResource(R.drawable.tab_two_highlight);
			lin_appoint.setBackgroundResource(R.drawable.tab_one_normal);
			lin_trust.setBackgroundResource(R.drawable.tab_one_normal);
			lin_result.setBackgroundResource(R.drawable.tab_one_normal);
			bt_handle = (Button) this.findViewById(R.id.bt_handle);
			bt_handle.setVisibility(View.VISIBLE);
			bt_handle.setText(R.string.s_bt_install);
			break;
		case 2:
			lin_install.setBackgroundResource(R.drawable.tab_one_normal);
			lin_appoint.setBackgroundResource(R.drawable.tab_two_highlight);
			lin_trust.setBackgroundResource(R.drawable.tab_one_normal);
			lin_result.setBackgroundResource(R.drawable.tab_one_normal);
			bt_handle = (Button) this.findViewById(R.id.bt_handle);
			bt_handle.setVisibility(View.GONE);// 隐藏按钮
			setTitle("APK扫描");
			break;
		case 3:
			lin_install.setBackgroundResource(R.drawable.tab_one_normal);
			lin_appoint.setBackgroundResource(R.drawable.tab_one_normal);
			lin_trust.setBackgroundResource(R.drawable.tab_two_highlight);
			lin_result.setBackgroundResource(R.drawable.tab_one_normal);
			bt_handle = (Button) this.findViewById(R.id.bt_handle);
			bt_handle.setVisibility(View.VISIBLE);
			bt_handle.setText(R.string.s_bt_trust);
			setTitle("信任程序");
			break;
		case 4:
			lin_install.setBackgroundResource(R.drawable.tab_one_normal);
			lin_appoint.setBackgroundResource(R.drawable.tab_one_normal);
			lin_trust.setBackgroundResource(R.drawable.tab_one_normal);
			lin_result.setBackgroundResource(R.drawable.tab_two_highlight);
			bt_handle = (Button) this.findViewById(R.id.bt_handle);
			bt_handle.setVisibility(View.VISIBLE);
			bt_handle.setText(R.string.s_bt_result);
			setTitle("扫描结果");
			break;
		default:
			break;
		}
	}

	/**
	 * Handler处理
	 */
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case installWhat:// 本机安装程序
					handleInstall();
					break;
				case appointWhat:// Apk扫描
					handleAppoint();
					break;
				case trustWhat:// 信任程序
					handleTrust();
					break;
				case resultWhat:// 扫描结果
					handleResult();
					break;
				case appPermissionWhat:// 某个程序的权限页面处理
					ResolveInfo resolveInfo = (ResolveInfo) msg.obj;
					String strPermission = getPermissionInfoByResolveInfo(resolveInfo);
					showMyDialog(appPermissionWhat, strPermission, resolveInfo);
					break;
				case trust_AppPermissionWhat:// 某个程序的权限页面处理
					String trustPermission = getPermissionInfoByResolveInfo((ResolveInfo) msg.obj);
					showMyDialog(trust_AppPermissionWhat, trustPermission,
							(ResolveInfo) msg.obj);
					break;
				case trust_AppAddWhat:// 添加某信任程序到信任列表
					if (msg.obj != null) {
						doAddTrustApp(msg.obj);
					} else {
						ArrayList<CharSequence> list = msg.getData()
								.getCharSequenceArrayList("trustPackageNames");
						doAddTrustApp(list);
					}
					handleTrust();
					changeButtonBackground(3);
					break;
				case trust_AppDelWhat:// 添加某信任程序到信任列表
					ArrayList<CharSequence> list = msg.getData()
							.getCharSequenceArrayList("delPackageNames");
					if (list != null && list.size() > 0) {
						for (CharSequence str : list) {
							mOpenHelper.deleteTrustAppBeanByPackageName(str
									.toString());
						}
					}
					handleTrust();
					break;
				case scanFileApkWhat:// 扫描指定apk文件
					String fPath = msg.getData().getString("filePath");// 文件路径
					String fName = msg.getData().getString("fileName");// 文件路径
					doScanFileApk(fPath, fName);
					break;
				case result_App_Warn:// 显示扫描结果
					showScanWarnAppPage();
					break;
				case dialog_yes:
					break;
				case dialog_warn_app:// 提示警告程序
					showMyDialog(dialog_warn_app, String.format(
							getString(R.string.s_info_warn), msg.arg1), msg.obj);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 处理本机安装程序
	 * 
	 * @throws Exception
	 */
	private void handleInstall() throws Exception {
		// 1.设置操作按钮可见(扫描已安装程序)
		bt_handle = (Button) this.findViewById(R.id.bt_handle);
		bt_handle.setVisibility(View.VISIBLE);
		bt_handle.setText(R.string.s_bt_install);
		// 3.得到本机安装应用程序
		if (installApps == null) {
			mIntent = new Intent(Intent.ACTION_MAIN, null);
			mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			installApps = getPackageManager().queryIntentActivities(mIntent, 0);
		}
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();// 将本机安装程序收集
		for (ResolveInfo info : installApps) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map
					.put("name", info.loadLabel(this.getPackageManager())
							.toString());
			map.put("packageName", info.activityInfo.packageName);
			map.put("img", info.loadIcon(this.getPackageManager()));
			list.add(map);
		}
		setTitle("共安装" + installApps.size() + "个程序");// 设置标题
		// 3.将程序显示到页面上
		lv_content = (ListView) this.findViewById(R.id.lv_content);
		SearchAdapter adapter = new SearchAdapter(this, list,
				R.layout.list_content_main, new String[] { "name",
						"packageName", "img" }, new int[] {
						R.id.activityInfo_name, R.id.activityInfo_packageName,
						R.id.activityInfo_img });// 自定义适配器
		lv_content.setAdapter(adapter);
		// 4.设置事件（点击ListView中元素触发，查看点击程序的权限信息）
		lv_content.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取本机安装程序列表
				if (installApps == null) {
					mIntent = new Intent(Intent.ACTION_MAIN, null);
					mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
					installApps = getPackageManager().queryIntentActivities(
							mIntent, 0);
				}
				// 当前点击程序包名称
				String packageName = ((TextView) view
						.findViewById(R.id.activityInfo_packageName)).getText()
						.toString().trim();
				ResolveInfo currInfo = null;
				for (ResolveInfo info : installApps) {
					if (info.activityInfo.applicationInfo.packageName
							.equals(packageName)) {
						currInfo = info;
						break;
					}
				}
				// 发送消息给Handler
				Message message = new Message();
				message.obj = currInfo;
				message.what = HomeActivity.appPermissionWhat;
				new ProgressThread(HomeActivity.this, handler, message,
						"正在扫描所选择程序，请稍等....").start();

			}
		});
		// 5.执行扫描事件(点击"扫描已安装程序"触发)
		bt_handle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 获取所有本机安装程序
				mIntent = new Intent(Intent.ACTION_MAIN, null);
				mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				HomeActivity.this.installApps = getPackageManager()
						.queryIntentActivities(mIntent, 0);
				mDialog = new ProgressDialog(HomeActivity.this);
				mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				mDialog.setTitle("提示");
				mDialog.setMessage("正在扫描已经安装程序...");
				mDialog.setMax(HomeActivity.this.installApps.size());
				mDialog.setIndeterminate(false);
				mDialog.setCancelable(true);
				mDialog.show();
				// 执行扫描
				new Thread() {
					public void run() {
						// 判断SD卡
						if (isSdState()) {
							doScanSofting();
						} else {
							showMyDialog(dialog_yes, "请插SD外部储存卡", null);
						}
						mDialog.cancel();
					}
				}.start();
			}
		});
		// 6.获取收集的权限
		if (permissionBeanList == null || permissionBeanList.size() <= 0)
			permissionBeanList = PermissionUtil
					.getPermissionBeanList(getResources().getAssets().open(
							assetsPermissionXml));
	}

	/**
	 * Apk扫描
	 */
	private void handleAppoint() {
		bt_handle = (Button) this.findViewById(R.id.bt_handle);
		bt_handle.setVisibility(View.GONE);
		// 读取SD卡根目录文件列表
		File sdFile = android.os.Environment.getExternalStorageDirectory();
		String rootPath = sdFile.getAbsolutePath() + File.separator;
		getFileDir(rootPath);
	}

	/**
	 * 信任程序
	 */
	private void handleTrust() {
		bt_handle = (Button) this.findViewById(R.id.bt_handle);
		bt_handle.setText(R.string.s_bt_trust);
		bt_handle.setVisibility(View.VISIBLE);
		showTrustAppPage();
	}

	/**
	 * 扫描结果
	 */
	private void handleResult() {
		showScanWarnAppPage();
	}

	/**
	 * 提示框
	 * 
	 * @param id
	 *            提示标识
	 * @param text
	 *            提示框内容
	 * @param obj
	 *            操作对象
	 */
	public void showMyDialog(int id, String text, final Object obj) {
		AlertDialog.Builder builder = null;
		ScrollView sv = null;
		TextView tv = null;
		switch (id) {
		case HomeActivity.appPermissionWhat:
			tv = new TextView(HomeActivity.this);
			sv = new ScrollView(HomeActivity.this);
			tv.setText(Html.fromHtml(text));
			builder = new AlertDialog.Builder(HomeActivity.this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(
					R.string.s_title_permission).setNegativeButton("关闭",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					}).setNeutralButton("信任",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							new ProgressThread(HomeActivity.this, handler,
									trust_AppAddWhat, "正在添加应用程序到信任...", obj)
									.start();
						}
					}).setPositiveButton("卸载",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// 通过程序的报名创建URI
							Uri packageURI = Uri
									.parse("package:"
											+ ((ResolveInfo) obj).activityInfo.processName);
							// 创建Intent意图
							Intent intent = new Intent(Intent.ACTION_DELETE,
									packageURI);
							// 执行卸载程序
							HomeActivity.this.startActivityForResult(intent, 1);
						}
					});
			sv.addView(tv);
			builder.setView(sv);
			builder.show();
			break;
		case dialog_yes:
			builder = new AlertDialog.Builder(HomeActivity.this).setIcon(
					R.drawable.alert_dialog_icon).setTitle("提示").setMessage(
					text).setNegativeButton("关闭",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					});
			builder.show();
			break;
		case dialog_warn_app:
			builder = new AlertDialog.Builder(HomeActivity.this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(
					R.string.s_title_warn).setMessage(text).setPositiveButton(
					"查看详情", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							new ProgressThread(HomeActivity.this, handler,
									result_App_Warn, "正在加载数据...", obj).start();
						}
					}).setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

						}
					});
			builder.show();
			break;
		case dialog_scan_apk:
			tv = new TextView(HomeActivity.this);
			sv = new ScrollView(HomeActivity.this);
			tv.setText(Html.fromHtml(text));
			builder = new AlertDialog.Builder(HomeActivity.this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(
					R.string.s_title_permission).setNegativeButton("关闭",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					}).setPositiveButton("删除",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							File delFile = new File((String) obj);
							delFile.delete();
							((ListView) HomeActivity.this
									.findViewById(R.id.lv_content))
									.setAdapter(null);
							new ProgressThread(HomeActivity.this, handler, 2)
									.start();
						}
					});
			sv.addView(tv);
			builder.setView(sv);
			builder.show();
			break;
		case trust_AppPermissionWhat:
			tv = new TextView(HomeActivity.this);
			sv = new ScrollView(HomeActivity.this);
			tv.setText(Html.fromHtml(text));
			builder = new AlertDialog.Builder(HomeActivity.this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(
					R.string.s_title_permission).setNegativeButton("关闭",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					}).setPositiveButton("卸载",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// 通过程序的报名创建URI
							Uri packageURI = Uri
									.parse("package:"
											+ ((ResolveInfo) obj).activityInfo.processName);
							// 创建Intent意图
							Intent intent = new Intent(Intent.ACTION_DELETE,
									packageURI);
							// 执行卸载程序
							HomeActivity.this.startActivityForResult(intent, 1);
						}
					});
			sv.addView(tv);
			builder.setView(sv);
			builder.show();
			break;
		case dialog_prompt_apk:
			new AlertDialog.Builder(HomeActivity.this).setIcon(
					R.drawable.alert_dialog_icon).setTitle(
					R.string.s_title_prompt).setMessage(text)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String[] objs = (String[]) obj;
									String execPath = objs[1] + objs[0];
									Message message = new Message();
									Bundle data = new Bundle();
									data.putString("filePath", execPath);
									data.putString("fileName", objs[0]);
									message.setData(data);
									message.what = scanFileApkWhat;
									new ProgressThread(HomeActivity.this,
											handler, message, "正在执行扫描...")
											.start();

								}
							}).setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

								}
							}).show();
			break;
		}
	}

	/**
	 * 获取某个应用程序的权限列表
	 * 
	 * @param info
	 * @return
	 */
	private String getPermissionInfoByResolveInfo(ResolveInfo info) {
		StringBuffer sb = new StringBuffer();
		sb.append("<font color='blue'>名称</font> "
				+ info.loadLabel(HomeActivity.this.getPackageManager())
				+ "<br>");
		sb.append("<font color='blue'>程序包 </font>"
				+ info.activityInfo.packageName + "<br>");
		sb.append("<font color='blue'>路径  </font>"
				+ info.activityInfo.applicationInfo.sourceDir + "<br>");
		sb.append("<br>");
		if (isSdState()) {
			List<String> list = getAppPermissionList(info.activityInfo.applicationInfo.sourceDir);
			if (list != null && HomeActivity.permissionBeanList != null) {
				for (PermissionBean bean : HomeActivity.permissionBeanList) {
					String name = bean.getName().trim();
					if (list.indexOf(name) != -1) {
						sb.append("<font color='blue'>权限名称 </font> "
								+ bean.getName() + "<br>");
						sb.append("<font color='blue'>权限描述  </font>"
								+ bean.getZhExplain() + "<br>");
						sb.append("<br>");
					}
				}
			}
		} else {
			showMyDialog(dialog_yes, "请插SD外部储存卡", null);
		}
		return sb.toString();
	}

	/**
	 * 得到某程序所有权限列表
	 * 
	 * @param sourceDir
	 *            文件路径
	 * @return
	 */
	public List<String> getAppPermissionList(String sourceDir) {
		File sdFile = android.os.Environment.getExternalStorageDirectory();
		String tmpManifestXmlPath = sdFile.getAbsolutePath() + File.separator
				+ tmpManifestPath;
		System.out.println("解压文件路径" + sourceDir);
		// 解压AndroidManifest.xml到临时存储路径上
		UnzipManifest.unzip(sourceDir, tmpManifestXmlPath);
		// 反编译AndroidManifest.xml得到权限列表
		List<String> list = AXMLPrinter.decrypt(tmpManifestXmlPath);
		return list;
	}

	/**
	 * 添加信任程序
	 */
	public void doAddTrustApp(Object obj) {
		// 如果信任一个程序
		if (obj instanceof ResolveInfo) {
			ResolveInfo resolveInfo = (ResolveInfo) obj;
			// 判断该信任程序是否已经存在
			boolean isExist = mOpenHelper
					.isExistTrustAppBeanByPackageName(resolveInfo.activityInfo.processName
							.trim());
			if (isExist) {
				showMyDialog(dialog_yes, "应用程序已经在信任列表中", null);
			} else {
				// 添加信任
				mOpenHelper.insertTrustAppBean(resolveInfo.loadLabel(
						HomeActivity.this.getPackageManager()).toString()
						.trim(), resolveInfo.activityInfo.processName.trim(),
						resolveInfo.activityInfo.applicationInfo.sourceDir
								.trim());
			}
		}
		// 如果信任多个程序
		else if (obj instanceof ArrayList) {
			ArrayList<CharSequence> list = (ArrayList<CharSequence>) obj;
			for (CharSequence str : list) {
				String tmp = str.toString();
				String str1 = tmp.substring(0, tmp.indexOf("|"));
				String str2 = tmp.substring(tmp.indexOf("|") + 1);
				// 添加到数据库中
				mOpenHelper.insertTrustAppBean(str1, str2, "");
				if (warnApps != null && warnApps.size() > 0) {
					Iterator<ResolveInfo> iter = warnApps.iterator();
					while (iter.hasNext()) {
						ResolveInfo info = iter.next();
						if (info.activityInfo.applicationInfo.packageName
								.equals(str2)) {
							iter.remove();
							break;
						}
					}
				}
				// 信任程序从短信警告程序中删除
				if (list1 != null && list1.size() > 0) {
					Iterator<Map<String, Object>> iter = list1.iterator();
					while (iter.hasNext()) {
						Map<String, Object> map = iter.next();
						if (map.get("packageName").toString().trim().equals(
								str2)) {
							iter.remove();
							break;
						}
					}
				}
				// 信任程序从网络权限警告程序中删除
				if (list2 != null && list2.size() > 0) {
					Iterator<Map<String, Object>> iter = list2.iterator();
					while (iter.hasNext()) {
						Map<String, Object> map = iter.next();
						if (map.get("packageName").toString().trim().equals(
								str2)) {
							iter.remove();
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * 开始扫描已经安装程序
	 */
	private void doScanSofting() {
		int warnNum = 0;
		int progressNum = 0;
		ResolveInfo info = null;
		boolean isScan = true;
		warnApps = new ArrayList<ResolveInfo>();
		// 得到信任程序列表
		List<TrustAppBean> trustList = mOpenHelper.loadTrustAppBeanAll();
		list1 = new ArrayList<Map<String, Object>>();// 具有短信权限的警告程序
		list2 = new ArrayList<Map<String, Object>>();// 具有网络权限的警告程序
		// 扫描时排除信任程序
		for (int i = 0; i < installApps.size(); i++) {
			isScan = true;
			progressNum++;
			mDialog.setProgress(progressNum);
			info = installApps.get(i);
			if (info.activityInfo.packageName.trim().contains("com.android")) {
				isScan = false;
			}
			if (isScan && trustList != null && trustList.size() > 0) {
				for (TrustAppBean app : trustList) {
					if (app.getPackageName().equalsIgnoreCase(
							info.activityInfo.packageName)) {
						isScan = false;
						break;
					}
				}
			}

			if (isScan) {
				Map<String, Object> map = null;
				List<String> list = getAppPermissionList(info.activityInfo.applicationInfo.sourceDir);
				// 过滤具有发短信、读短信、网络套接字权限的应用
				if ((list.indexOf("android.permission.SEND_SMS") != -1)
						|| (list.indexOf("android.permission.RECEIVE_SMS") != -1)
						|| (list.indexOf("android.permission.INTERNET") != -1)) {
					warnNum++;
					warnApps.add(info);
					map = new HashMap<String, Object>();
					map.put("name", info.loadLabel(
							HomeActivity.this.getPackageManager()).toString());
					map.put("packageName", info.activityInfo.packageName);
					map.put("img", info.loadIcon(HomeActivity.this
							.getPackageManager()));
					// 分类，将网络权限和短信权限的应用分开
					if ((list.indexOf("android.permission.SEND_SMS") != -1)
							|| (list.indexOf("android.permission.RECEIVE_SMS") != -1)) {
						list1.add(map);
					}
					if ((list.indexOf("android.permission.INTERNET") != -1)) {
						list2.add(map);
					}
				}
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (warnNum == 0) {
			Message msg = new Message();
			msg.obj = getString(R.string.s_info_prompt);
			msg.what = dialog_yes;
			handler.sendMessage(msg);
		} else {
			List<List<Map<String, Object>>> objs = new ArrayList<List<Map<String, Object>>>();
			objs.add(list1);
			objs.add(list2);
			Message msg = new Message();
			msg.obj = objs;
			msg.what = dialog_warn_app;
			msg.arg1 = warnNum;
			handler.sendMessage(msg);
		}
	}

	/**
	 * 扫描本地SD中APK文件
	 * 
	 * @param filePath
	 */
	private void getFileDir(final String filePath) {
		File sdFile = android.os.Environment.getExternalStorageDirectory();//获取SD卡
		String rootPath = sdFile.getAbsolutePath() + File.separator;
		List<String> items = new ArrayList<String>();//目录中所有文件名称
		final List<String> paths = new ArrayList<String>();//目录中所有文件路径（与文件名称对应坐标）
		File f = new File(filePath);
		File[] files = f.listFiles();//文件列表
		if (rootPath.indexOf(filePath) == -1) {
			items.add("Back to " + rootPath);//SD卡根目录
			paths.add(rootPath);
			items.add("Back to ../");//父目录
			paths.add(f.getParent());
		}
		for (int i = 0; i < files.length; i++) {//循环目录文件，分别设置名称和路径
			File file = files[i];
			items.add(file.getName());
			paths.add(file.getPath());
		}
		//获取布局ListView
		ListView listview = (ListView) this.findViewById(R.id.lv_content);
		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				R.layout.file_row, items);
		listview.setAdapter(fileList);
		//ListView中Item点击事件
		listview.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				File file = new File(paths.get(position));
				//如果是文件夹，则打开文件夹
				if (file.isDirectory()) {
					getFileDir(paths.get(position));
				} else {//如果是文件，则提示文件。
					final String fileName = file.getName().toUpperCase();
					if (fileName.indexOf(".APK") != -1
							&& fileName.substring(fileName.indexOf(".APK"))
									.length() == 4) {//如果是APK格式，则提示扫描
						showMyDialog(dialog_prompt_apk, "确定扫描[" + fileName
								+ "]文件吗？", new String[] { fileName, filePath });
					} else {//如果不是APK文件，则提示选择APK格式文件
						showMyDialog(dialog_yes, "请选择APK格式的文件", null);
					}
				}
			}

		});
	}

	/**
	 * 执行扫描本地APk文件
	 * 
	 * @param filePath
	 * @param fileName
	 */
	private void doScanFileApk(String filePath, String fileName) {
		List<String> list = getAppPermissionList(filePath);
		StringBuffer sb = new StringBuffer();
		sb.append("<font color='blue'>文件名称</font> " + fileName + "<br>");
		sb.append("<font color='blue'>文件路径  </font>" + filePath + "<br>");
		sb.append("<br>");
		if (list != null && HomeActivity.permissionBeanList != null)
			for (PermissionBean bean : HomeActivity.permissionBeanList) {
				String name = bean.getName();
				if (list.indexOf(name) != -1) {
					sb.append("<font color='blue'>权限名称 </font> "
							+ bean.getName() + "<br>");
					sb.append("<font color='blue'>权限描述  </font>"
							+ bean.getZhExplain() + "<br>");
					sb.append("<br>");
				}
			}
		showMyDialog(dialog_scan_apk, sb.toString(), filePath);
	}

	/**
	 * 获取信任程序集合
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getTrustAppData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		List<ResolveInfo> infoList = new ArrayList<ResolveInfo>();
		List<TrustAppBean> beanList = null;
		beanList = mOpenHelper.loadTrustAppBeanAll();
		if (installApps == null) {
			Intent mIntent = new Intent(Intent.ACTION_MAIN, null);
			mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			installApps = getPackageManager().queryIntentActivities(mIntent, 0);
		}
		if (beanList != null && beanList.size() > 0)
			for (ResolveInfo info : installApps) {
				for (TrustAppBean bean : beanList) {
					if (bean.getPackageName().trim().equalsIgnoreCase(
							info.activityInfo.applicationInfo.packageName
									.trim())) {
						infoList.add(info);
						break;
					}
				}
			}
		if (infoList != null && infoList.size() > 0) {
			for (ResolveInfo info : infoList) {
				map = new HashMap<String, Object>();
				map.put("name", info.loadLabel(
						HomeActivity.this.getPackageManager()).toString());
				map.put("packageName", info.activityInfo.packageName);
				map.put("img", info.loadIcon(HomeActivity.this
						.getPackageManager()));
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 显示信任程序Activity
	 */
	public void showTrustAppPage() {
		// 调整页面
		bt_handle.setText("删除已勾选程序");
		setTitle("信任程序");
		//点击"删除已勾选程序"
		bt_handle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ListView listview = (ListView) HomeActivity.this
						.findViewById(R.id.lv_content);
				ArrayList<CharSequence> delPackageNames = new ArrayList<CharSequence>();//需要删除的包名集合
				if (listview != null)
					for (int i = 0; i < listview.getChildCount(); i++) {
						CheckBox cb = (CheckBox) listview.getChildAt(i)
								.findViewById(R.id.cb_cancel_trust);
						if (cb.isChecked()) {//判断是否勾选复选框
							TextView tv = (TextView) listview
									.getChildAt(i)
									.findViewById(R.id.activityInfo_packageName);
							delPackageNames.add(tv.getText());
						}
					}
				if (delPackageNames != null && delPackageNames.size() > 0) {//执行删除
					Message message = new Message();
					Bundle data = new Bundle();
					data.putCharSequenceArrayList("delPackageNames",
							delPackageNames);
					message.setData(data);
					message.what = trust_AppDelWhat;
					new ProgressThread(HomeActivity.this, handler, message,
							"正在执行删除...").start();
				} else {
					showToast("请勾选要删除的程序");
				}
			}
		});
		//获取数据库中信任程序列表（数据库操作）
		List<Map<String, Object>> list = getTrustAppData();
		//将信任程序显示到ListView中
		if (list != null && list.size() > 0) {
			ListView listview = (ListView) this.findViewById(R.id.lv_content);
			SearchAdapter adapter = new SearchAdapter(this, list,
					R.layout.list_content, new String[] { "name",
							"packageName", "img" }, new int[] {
							R.id.activityInfo_name,
							R.id.activityInfo_packageName,
							R.id.activityInfo_img });
			listview.setAdapter(adapter);//设置ListView适配器
			//点击ListView中某一项，对其进行权限扫描
			listview.setOnItemClickListener(new ListView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (installApps == null) {//如果本机程序数据为空，则重新获取
						Intent mIntent = new Intent(Intent.ACTION_MAIN, null);
						mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
						installApps = getPackageManager()
								.queryIntentActivities(mIntent, 0);
					}
					//点击程序的包名
					String packageName = ((TextView) view
							.findViewById(R.id.activityInfo_packageName))
							.getText().toString().trim();
					ResolveInfo resolveInfo = null;
					for (ResolveInfo info : installApps) {//获取点击程序的ResolveInfo
						if (info.activityInfo.applicationInfo.packageName
								.equals(packageName)) {
							resolveInfo = info;
							break;
						}
					}
					new ProgressThread(HomeActivity.this, handler,
							HomeActivity.trust_AppPermissionWhat,
							"正在扫描所选择程序，请稍等....", resolveInfo).start();
				}
			});
		} else {//如果数据库没有，则提示
			ListView listview = (ListView) this.findViewById(R.id.lv_content);
			listview.setAdapter(null);
			showMyDialog(dialog_yes, "目前没有信任程序", null);
		}
	}

	/**
	 *构建扫描警告程序列表
	 */
	public void showScanWarnAppPage() {
		setTitle("扫描结果");
		bt_handle.setText("信任已勾选程序");
		changeButtonBackground(4);
		bt_handle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ListView listview = (ListView) HomeActivity.this
						.findViewById(R.id.lv_content);
				ArrayList<CharSequence> trustPackageNames = new ArrayList<CharSequence>();
				if (listview != null)
					for (int i = 0; i < listview.getChildCount(); i++) {
						CheckBox cb = (CheckBox) listview.getChildAt(i)
								.findViewById(R.id.cb_cancel_trust);
						if (cb.isChecked()) {
							TextView tv1 = (TextView) listview.getChildAt(i)
									.findViewById(R.id.activityInfo_name);
							TextView tv2 = (TextView) listview
									.getChildAt(i)
									.findViewById(R.id.activityInfo_packageName);

							trustPackageNames.add(tv1.getText() + "|"
									+ tv2.getText());
						}
					}
				if (trustPackageNames != null && trustPackageNames.size() > 0) {
					Message message = new Message();
					Bundle data = new Bundle();
					data.putCharSequenceArrayList("trustPackageNames",
							trustPackageNames);
					message.setData(data);
					message.what = trust_AppAddWhat;
					new ProgressThread(HomeActivity.this, handler, message,
							"正在执行添加...").start();
				} else {
					showToast("请勾选要信任的程序");
				}
			}
		});
		if (warnApps != null && warnApps.size() > 0) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// 合集
			Map<String, Object> map = null;
			map = new HashMap<String, Object>();
			map.put("name", Html.fromHtml("拥有短信权限的警告程序"));
			map.put("packageName", "");
			map.put("img", null);
			list.add(map);
			list.addAll(list1);
			map = new HashMap<String, Object>();
			map.put("name", Html.fromHtml("拥有网络权限的警告程序"));
			map.put("packageName", "");
			map.put("img", null);
			list.add(map);
			list.addAll(list2);

			ListView listview = (ListView) this.findViewById(R.id.lv_content);
			SearchAdapter adapter = new SearchAdapter(this, list,
					R.layout.list_content, new String[] { "name",
							"packageName", "img" }, new int[] {
							R.id.activityInfo_name,
							R.id.activityInfo_packageName,
							R.id.activityInfo_img });
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ListView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					TextView tv_pn = (TextView) view
							.findViewById(R.id.activityInfo_packageName);
					if (tv_pn.getText() == null
							|| tv_pn.getText().toString() == null
							|| tv_pn.getText().toString().equals("")) {
						return;
					}
					if (installApps == null) {
						Intent mIntent = new Intent(Intent.ACTION_MAIN, null);
						mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
						installApps = getPackageManager()
								.queryIntentActivities(mIntent, 0);
					}
					String packageName = ((TextView) view
							.findViewById(R.id.activityInfo_packageName))
							.getText().toString().trim();
					ResolveInfo resolveInfo = null;
					for (ResolveInfo info : installApps) {
						if (info.activityInfo.applicationInfo.packageName
								.equals(packageName)) {
							resolveInfo = info;
							break;
						}
					}
					new ProgressThread(HomeActivity.this, handler,
							appPermissionWhat, "正在扫描所选择程序，请稍等....", resolveInfo)
							.start();
				}
			});
		} else {
			ListView listview = (ListView) this.findViewById(R.id.lv_content);
			listview.setAdapter(null);
			showMyDialog(dialog_yes, "没有扫描结果数据", null);
		}
	}

	/**
	 * 判断SD外包储存卡
	 * 
	 * @return
	 */
	public boolean isSdState() {
		String sDStateString = android.os.Environment.getExternalStorageState();
		// 拥有可读可写权限
		if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Toast提示
	 * 
	 * @param text
	 */
	public void showToast(String text) {
		Toast.makeText(HomeActivity.this, text, Toast.LENGTH_SHORT).show();
	}
}