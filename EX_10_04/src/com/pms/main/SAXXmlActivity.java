package com.pms.main;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.pms.bean.Person;
import com.pms.xml.SAXXmlService;
/**
 * 演示SAX解析XML方法
 * @author machao
 *
 */
public class SAXXmlActivity extends Activity {
	public static ArrayList<Person> objs = null;// 所有人员信息
	static final String queryString = "http://192.168.1.7:8080/exa/xml";//注意路径中如果是本机ip要写局域网地址
	TextView textView=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		textView=(TextView)this.findViewById(R.id.textView);
		StringBuffer sb=new StringBuffer();
		try {
			//调用解析xml服务
			objs = SAXXmlService.getPersonInfos(queryString);
			if(objs!=null&&objs.size()>0){
				//循环并组装字符串
				for(Person person:objs){
					sb.append("编号："+person.getId()+"\n");
					sb.append("姓名："+person.getName()+"\n");
					sb.append("年龄："+person.getAge()+"\n");
					sb.append("地址："+person.getAddress()+"\n");
					sb.append("\n");
				}
			}
			//显示组装字符串到页面上
			textView.setText(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}