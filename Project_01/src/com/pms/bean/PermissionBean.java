package com.pms.bean;

/**
 * 权限实体Bean（解析permission.xml文件时配合使用）
 * 
 * @author machao
 * @mail zeusmc.163.com
 */
public class PermissionBean {
	private String name;
	private String zhExplain;
	private String enExplain;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZhExplain() {
		return zhExplain;
	}

	public void setZhExplain(String zhExplain) {
		this.zhExplain = zhExplain;
	}

	public String getEnExplain() {
		return enExplain;
	}

	public void setEnExplain(String enExplain) {
		this.enExplain = enExplain;
	}

}
