package com.pms.data;

public class Message {
	/*****不重复ID****/
	public String key;
	/**分组ID**/
	public String group;
	/**0 扫描  1手录  2删除******/
	public int state;
	/*****条码内容****/
	public String codetext;
	/*****备注*****/
	public String remark;
}
