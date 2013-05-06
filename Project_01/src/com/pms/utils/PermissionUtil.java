package com.pms.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.pms.bean.PermissionBean;
/**
 * 解析permission.xml
 * @author machao
 *
 */
public class PermissionUtil {

	public static List<PermissionBean> getPermissionBeanList(
			InputStream inputStream) throws Exception {
		List<PermissionBean> list = new ArrayList<PermissionBean>();
		PermissionBean bean = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String name = parser.getName();
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				list = new ArrayList<PermissionBean>();
				break;
			case XmlPullParser.START_TAG:
				if (name.equalsIgnoreCase("permission")) {
					bean = new PermissionBean();
				} else if (bean != null) {
					if (name.equalsIgnoreCase("name")) {
						bean.setName(parser.nextText().trim());
					} else if (name.equalsIgnoreCase("zhExplain")) {
						bean.setZhExplain(parser.nextText().trim());
					} else if (name.equalsIgnoreCase("enExplain")) {
						bean.setEnExplain(parser.nextText().trim());
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if (name.equalsIgnoreCase("permission")) {
					list.add(bean);
				}
				break;
			}
			eventType = parser.next();
		}
		if (inputStream != null) {
			inputStream.close();
		}
		return list;
	}
}
