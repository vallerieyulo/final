package com.accenture.bars.sef.xml.impl;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.accenture.bars.sef.xml.interfce.BarsWriteXMLUtilsInterface;

/**
 */
public class BarsWriteXmlUtils implements BarsWriteXMLUtilsInterface {

	@Override
	public Document createXMLDocument() {
		Logger logger = Logger.getLogger(BarsWriteXmlUtils.class);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder;
		Document doc = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			logger.info(e);
		}

		return doc;
	}

	@Override
	public Element createDocumentElement(Document doc, String elementName) {
		Element rootElement = doc.createElement(elementName);
		doc.appendChild(rootElement);
		return rootElement;
	}

	@Override
	public Element createChildElement(Document doc, Element element,
			String elementName) {
		Element staff = doc.createElement(elementName);
		element.appendChild(staff);
		return staff;
	}

	@Override
	public Element createElementAttribute(Document doc, Element element,
			String attName, String attValue) {
		Attr attr = doc.createAttribute(attName);
		attr.setValue(attValue);
		element.setAttributeNode(attr);

		return element;
	}

	@Override
	public Element createElementTextNode(Document doc, Element element,
			String elementName, String nodeString) {
		Element el = doc.createElement(elementName);
		el.appendChild(doc.createTextNode(nodeString));
		element.appendChild(el);

		return element;
	}

	@Override
	public void transformToXML(Document doc, String filePath) {
		Logger logger = Logger.getLogger(BarsWriteXmlUtils.class);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			logger.info(e);
		}
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filePath));

		try {
			if (transformer != null) {
				transformer.transform(source, result);
			}
		} catch (TransformerException e) {
			logger.info(e);
		}

	}

}
