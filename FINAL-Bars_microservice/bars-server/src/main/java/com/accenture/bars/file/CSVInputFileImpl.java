package com.accenture.bars.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.accenture.bars.domain.Request;
import com.accenture.bars.exception.BarsException;

/**
 * CSVInputFileImpl - class for reading text files
 *
 */
public class CSVInputFileImpl extends AbstractInputFile {

	@Override
	public List<Request> readFile() throws BarsException {
		Logger logger = Logger.getLogger(this.getClass());
		List<Request> requests = new ArrayList<>();
		Request request;
		File file = getFile();
		int errorLineCSV = 0;
		int row = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			int billingCycle;
			String startDate;
			String endDate;
			String line;

			while (br.ready()) {
				line = br.readLine();
				request = new Request();
				String[] lines = line.split(",");

				errorLineCSV = 1;
				billingCycle = Integer.parseInt(lines[0]);
				if (billingCycle > 0 && billingCycle < 13) {
					request.setBillingCycle(billingCycle);

				} else {
					requests.clear();
					br.close();
					throw new BarsException(
							BarsException.BILLING_CYCLE_NOT_ON_RANGE
									+ (row + 1));
				}

				errorLineCSV++;
				SimpleDateFormat format =  new SimpleDateFormat("MM/dd/yyyy");	
				startDate = lines[1];
				java.util.Date date = format.parse(startDate);
				request.setStartDate(new Date(date.getTime()));

				errorLineCSV++;
				endDate = lines[2];
				date = format.parse(endDate);
				request.setEndDate(new Date(date.getTime()));
				requests.add(request);

				row++;

			}

			br.close();

		} catch (Exception e) {
			logger.info(e);
			String messageErrorCSV = "";
			switch (errorLineCSV) {
			case 1:
				messageErrorCSV += BarsException.BILLING_CYCLE_NOT_ON_RANGE
						+ (row + 1);
				break;
			case 2:
				messageErrorCSV += BarsException.INVALID_START_DATE_FORMAT
						+ (row + 1);
				break;
			case 3:
				messageErrorCSV += BarsException.INVALID_END_DATE_FORMAT
						+ (row + 1);
				break;
			default:
				messageErrorCSV += "Exception";
			}
			throw new BarsException(messageErrorCSV);
		}

		return requests;
	}

}
