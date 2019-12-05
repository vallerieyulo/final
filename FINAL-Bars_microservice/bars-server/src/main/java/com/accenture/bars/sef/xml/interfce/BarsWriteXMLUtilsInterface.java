package com.accenture.bars.sef.xml.interfce;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 */
public interface BarsWriteXMLUtilsInterface {
	public Document createXMLDocument();

	public Element createDocumentElement(Document doc, String elementName);

	public Element createChildElement(Document doc, Element element,
			String elementName);

	public Element createElementAttribute(Document doc, Element element,
			String attName, String attValue);

	public Element createElementTextNode(Document doc, Element element,
			String elementName, String nodeString);

	public void transformToXML(Document doc, String filePath);
}
