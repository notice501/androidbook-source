package com.pms.data;

public class AppContext {
	static AppContext instance;
	final static int SIGNLESTYLE=0;
	final static int MORESYTLE=1;
	/******单次          或者连续扫描*******/
	private int mode;
//	private String defaultgroup;
	private Group currGroup=new Group();
	public AppContext(){
//		setCurrGroup();
	}
//	public void setCurrGroup(){
//		currGroup.groupid="默认";
//		currGroup.maxNum="500";
//	}
	public static  AppContext getInstance(){
		if(instance==null){
			instance=new AppContext();
			
		}
		return instance;
	}
	public void setCodeMode(int mode){
		this.mode=mode;
	}
	public int getCodeMode(){
		return mode;
	}
	/*public void setDefaultGroup	(String group){
		defaultgroup=group;
	}
	public String getDefaultGroup(){
		return defaultgroup;
	}*/
	public void setCurrGroup(Group currgroup){
		this.currGroup=currgroup;
	}
	public Group getCurrGroup(){
		return currGroup;
	}
}
