package com.pms.bean;

/**
 * 信任程序实体Bean
 * 
 * @author machao
 * @mail zeusmc.163.com
 */
public class TrustAppBean {
	private String id;
	private String name;
	private String packageName;
	private String sourceDir;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

}
