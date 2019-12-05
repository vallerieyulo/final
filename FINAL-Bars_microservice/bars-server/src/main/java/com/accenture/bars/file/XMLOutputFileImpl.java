package com.accenture.bars.file;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.accenture.bars.domain.Record;
import com.accenture.bars.sef.xml.impl.BarsWriteXmlUtils;
import com.accenture.bars.sef.xml.interfce.BarsWriteXMLUtilsInterface;

/**
 * XMLOutputFileImpl - class to write xml file with existing records from
 * database
 *
 */
public class XMLOutputFileImpl extends AbstractOutputFile {

	@Override
	public void writeFile(List<Record> records) {

		BarsWriteXMLUtilsInterface xmlWriter = new BarsWriteXmlUtils();

		Document doc = xmlWriter.createXMLDocument();
		Element recordRoot = xmlWriter.createDocumentElement(doc, "records");
		Element recordElement;

		int index = 1;
		for (Record record : records) {
			recordElement = xmlWriter.createChildElement(doc, recordRoot,
					"BARS");
			xmlWriter.createElementAttribute(doc, recordElement, "request",
					String.valueOf(index++));
			xmlWriter.createElementTextNode(doc, recordElement,
					"billing_cycle", String.valueOf(record.getBillingCycle()));
			xmlWriter.createElementTextNode(doc, recordElement, "start_date",
					record.getStartDate().toString());
			xmlWriter.createElementTextNode(doc, recordElement, "end_date",
					record.getEndDate().toString());
			xmlWriter.createElementTextNode(doc, recordElement, "first_name",
					record.getCustomerFirstName());
			xmlWriter.createElementTextNode(doc, recordElement, "last_name",
					record.getCustomerLastName());
			xmlWriter.createElementTextNode(doc, recordElement, "amount",
					String.valueOf(record.getAmount()));
			xmlWriter.transformToXML(doc, getFile().toString());
		}

	}

}
