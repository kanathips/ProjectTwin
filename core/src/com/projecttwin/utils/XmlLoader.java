package com.projecttwin.utils;

import java.io.File;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlLoader {

	private String addr;
	private Document doc;
	private String tag;
	private NodeList nList;

	/**
	 * 
	 * @param addr
	 * 			location of xml file
	 * @param tag
	 * 			tag to read
	 */
	public XmlLoader(String addr, String tag) {
		this.addr = addr;
		this.tag = tag;
		init();
	}

	private void init() {
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
	
	/**
	 * get data of specific id by dataTag
	 * @param id
	 * @param dataTag
	 * @return data of specific id
	 */
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
	/**
	 * get data of specific id by dataTag array
	 * @param id
	 * @param dataTag
	 * @return Map of data
	 */
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
	
	public void setData(Integer id, String dataTag, String setData){
		for(int i = 0; i < nList.getLength(); i++){
			Node nNode = nList.item(i);
			Element eElement = (Element) nNode;
			if(nNode.getNodeType() ==  Node.ELEMENT_NODE && eElement.getAttribute("id").equals(id.toString())){
				NodeList nodeList = nNode.getChildNodes();
				for(int j = 0; j < nodeList.getLength(); j++){
					if(dataTag.equals(nodeList.item(j).getNodeName())){
						nodeList.item(j).setTextContent(setData);
					}
				}
			}
		}
	}
	
	public void saveData(){
		try {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(addr));
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
