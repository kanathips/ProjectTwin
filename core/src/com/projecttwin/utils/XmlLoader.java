package com.projecttwin.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.TreeMap;

public class XmlLoader {

	private String addr;
	private Document doc;
	private String tag;
	private NodeList nList;

	public XmlLoader(String addr, String tag) {
		this.addr = addr;
		this.tag = tag;
		init();
	}

	public void init() {
		try {
			File fXmlFile = new File(addr);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			nList = doc.getElementsByTagName(tag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getData(Integer id, String dataTag) {
		String data = null;
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			Element eElement = (Element) nNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE && eElement.getAttribute("id").equals(id.toString())) {
				data = eElement.getElementsByTagName(dataTag).item(0).getTextContent();
				break;
			}
		}
		return data;
	}

	public TreeMap<String, String> getData(Integer id, String[] dataTag) {
		TreeMap<String, String> data = new TreeMap<String, String>();
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			Element eElement = (Element) nNode;
			if (nNode.getNodeType() == Node.ELEMENT_NODE && eElement.getAttribute("id").equals(id.toString())) {
				for (String s : dataTag)
					data.put(s, eElement.getElementsByTagName(s).item(0).getTextContent());
				break;
			}
		}
		return data;
	}
}
