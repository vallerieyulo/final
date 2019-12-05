package com.accenture.bars.sef.xml.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.accenture.bars.sef.xml.interfce.BarsReadXMLUtilsInterface;

/**
 */
public class BarsReadXMLUtils implements BarsReadXMLUtilsInterface {

	@Override
	public NodeList retrieveXMLNodeList(String filePath, String tagName) {
		Logger logger = Logger.getLogger(BarsReadXMLUtils.class);
		File fXmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.info(e);
		}
		if (doc != null) {
			doc.getDocumentElement().normalize();
			return doc.getElementsByTagName(tagName);
		}

		return null;
	}

	@Override
	public List<Element> retrieveNodeElement(NodeList nList) {
		List<Element> elementList = new ArrayList<>();
		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				elementList.add(eElement);

			}
		}

		return elementList;
	}

	@Override
	public String getElementNodeValue(Element element, String elementName) {
		return element.getElementsByTagName(elementName).item(0)
				.getChildNodes().item(0).getNodeValue();
	}

}
