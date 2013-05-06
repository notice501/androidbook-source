package com.pms.xml;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.pms.bean.Person;

public class SAXXmlService extends DefaultHandler {
	// //定义一个Person引用
	Person person = null;
	// 此处将XML里的数据封装成Person类，personList用于装解析后的数据
	ArrayList<Person> personArr = null;
	// 定义一个标记变量，标记当前的Xml文件被解析到哪个标签
	private String currentTag = null;

	/**
	 * 执行解析
	 * 
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Person> getPersonInfos(String queryString)
			throws Exception {
		// 得到SAX解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 创建解析器
		SAXParser parser = factory.newSAXParser();
		XMLReader xmlreader = parser.getXMLReader();
		// 得到输入流
		URL url = new URL(queryString);
		InputSource is = new InputSource(url.openStream());
		// 得到SAX解析实现类
		SAXXmlService handler = new SAXXmlService();
		xmlreader.setContentHandler(handler);
		// 开始解析
		xmlreader.parse(is);
		return handler.personArr;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		// 判断当前标签是否有效
		if (currentTag != null) {
			// //读取标签里面的内容
			String value = new String(ch, start, length);
			// 如果是Id标签，则读取Id标签的内容设置到Person的ID值上
			if ("id".equalsIgnoreCase(currentTag)) {
				person.setId(value);
			} else if ("name".equalsIgnoreCase(currentTag)) {// name标签
				person.setName(value);
			} else if ("age".equalsIgnoreCase(currentTag)) {// age标签
				person.setAge(value);
			} else if ("address".equalsIgnoreCase(currentTag)) {// address标签
				person.setAddress(value);
			}
		}
	}

	/**
	 * 解析XML时，当读到结束一个元素标签时
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		// 将当前标签名制空
		currentTag = null;
		// 如果当前结束的标签名是person的话，代表一个person对象已经读取完毕。将其添加到list后制空
		if (localName.equals("person")) {
			personArr.add(person);
			person = null;
		}
	}

	/**
	 * 解析XML时，当开始读取Xml文档时
	 */
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		personArr = new ArrayList<Person>();
	}

	/**
	 * 解析XML时，当开始读到一个元素标签开始时
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (localName.equals("person")) {
			person = new Person();
		}
		currentTag = localName;
	}

}
