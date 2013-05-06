package com.pms.xml;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.pms.bean.Person;

public class PullXmlService {
	public static ArrayList<Person> getPersonInfos(String queryString)
			throws Exception {
		//声明变量
		Person person = null;
		ArrayList<Person> personArr = null;
		XmlPullParser parser = Xml.newPullParser();
		//网络操作
		URL url = new URL(queryString);
		InputStream inputStream = url.openStream();
		parser.setInput(inputStream, "UTF-8");
		int event = parser.getEventType();
		//循环遍历Xml文档，获取对应值
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT://判断是否是文档开始事件
				personArr = new ArrayList<Person>();
				break;
			case XmlPullParser.START_TAG://判断是否是标签元素开始事件
				if ("person".equalsIgnoreCase(parser.getName())) {
					person = new Person();
				} else if (person != null) {
					if ("id".equalsIgnoreCase(parser.getName())) {
						person.setId(parser.nextText());
					} else if ("name".equalsIgnoreCase(parser.getName())) {
						person.setName(parser.nextText());
					} else if ("age".equalsIgnoreCase(parser.getName())) {
						person.setAge(parser.nextText());
					} else if ("address".equalsIgnoreCase(parser.getName())) {
						person.setAddress(parser.nextText());
					}
				}
				break;
			case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
				if ("person".equalsIgnoreCase(parser.getName())) {
					personArr.add(person);
				}
				break;
			}
			event = parser.next();// 进入下一个元素并触发相应事件
		}
		if (inputStream != null) {
			inputStream.close();
		}
		return personArr;
	}

}
