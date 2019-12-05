package com.accenture.bars.sef.xml.interfce;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 */
public interface BarsReadXMLUtilsInterface {
	public NodeList retrieveXMLNodeList(String filePath, String tagName);

	public List<Element> retrieveNodeElement(NodeList nList);

	public String getElementNodeValue(Element element, String elementName);
}
