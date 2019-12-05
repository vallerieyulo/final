package com.accenture.bars.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.bars.domain.Record;
import com.accenture.bars.domain.Request;
import com.accenture.bars.exception.BarsException;
import com.accenture.bars.factory.InputFileFactory;
import com.accenture.bars.file.IInputFile;
import com.accenture.bars.file.IOutputFile;
import com.accenture.bars.file.XMLOutputFileImpl;
import com.accenture.bars.repository.RequestRepository;

@Path("/bars")
public class BarsService {

	private static Logger log = LoggerFactory
			.getLogger(BarsService.class);

	@Autowired
	RequestRepository requestRepository;
	IInputFile inputFile;
	IOutputFile outputFile;

	// http://localhost:1998/bars/execute?file=FILE
	@GET
	@Path("/execute")
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@QueryParam("file") String file)
			throws JSONException {
		log.info("Access /execute");
		log.info("HELLO");
		JSONObject json = new JSONObject();
		log.info("HELLO!!");
		inputFile = InputFileFactory.getInstance().getInputFile(new File(file));

		if ("".equals(file)) {
			json.put("ERROR", BarsException.PATH_DOES_NOT_EXIST);
			return Response.status(405).type(MediaType.APPLICATION_JSON)
					.entity(json.toString()).build();
			
		} else if (null == inputFile) {
			json.put("ERROR", BarsException.NO_SUPPORT_FILE);
			return Response.status(405).type(MediaType.APPLICATION_JSON)
					.entity(json.toString()).build();
		}

		inputFile.setFile(new File(file));
		log.info("FILE SET!! " +file);
		try {
			List<Request> requests = inputFile.readFile();
			if (requests.isEmpty()) {
				json.put("ERROR", BarsException.NO_RECORDS_TO_READ);
				return Response.status(405).type(MediaType.APPLICATION_JSON)
						.entity(json.toString()).build();
			}

			for (Request request : requests) {
				requestRepository.save(request);
			}

			List<Record> records = fileProcessorRetrieveRecords();
			log.info("buset");
			
			if (records.isEmpty()) {
				json.put("ERROR", BarsException.NO_RECORDS_TO_WRITE);
				return Response.status(405).type(MediaType.APPLICATION_JSON)
						.entity(json.toString()).build();
			}

			writeOutput(records);

			return Response.status(200).build();

		} catch (BarsException e) {
			json.put("ERROR", e.getMessage());
			return Response.status(405).type(MediaType.APPLICATION_JSON)
					.entity(json.toString()).build();
		}

	}

	// http://localhost:1998/bars/getrecords
	@GET
	@Path("/getrecords")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Record> getRecords() {
		log.info("Access /getrecords");
		List<Record> records = fileProcessorRetrieveRecords();
		requestRepository.deleteAll();
		return records;
	}

	public List<Record> fileProcessorRetrieveRecords() {
		log.info("Access /fileProcessorRetrieveRecords");
		List<Object[]> objectRecords = requestRepository.findRecords();
		List<Record> records = new ArrayList<>();
		for (Object[] object : objectRecords) {
			records.add(new Record((int) object[0],
					(java.sql.Date) object[1], (java.sql.Date) object[2],
					(String) object[3], (String) object[4],
					(double) object[5]));
		}
		return records;
	}

	public void writeOutput(List<Record> records) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"MMddyyyy_HHmmss");
		String date = dateFormat.format(new Date());

		outputFile = new XMLOutputFileImpl();
		outputFile.setFile(new File("C:/BARS/Report/BARS_Report-" + date
				+ ".xml"));
		outputFile.writeFile(records);
		
	}

}
