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
 * TextInputFileImpl - class for reading text files
 *
 */
public class TextInputFileImpl extends AbstractInputFile {

	
	@Override
	public List<Request> readFile() throws BarsException {
		Logger logger = Logger.getLogger(this.getClass());
		List<Request> requests = new ArrayList<>();
		BufferedReader br = null;
		Request request;
		int row = 0;
		int errorLine = 0;
		File file = getFile();
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			row = 0;
			logger.info("eme!" + getFile());
			while (br.ready()) {
				line = br.readLine();
				
				request = new Request();
				
				errorLine = 1;
				int billingCycle = Integer.parseInt(line.substring(0, 2));
				
				if (billingCycle > 0 && billingCycle < 13) {
					request.setBillingCycle(billingCycle);
				} else {
					requests.clear();
					br.close();
					throw new BarsException(
							BarsException.BILLING_CYCLE_NOT_ON_RANGE
									+ (row + 1));
				}
				
				errorLine++;
				SimpleDateFormat format =  new SimpleDateFormat("MMddyyyy");
				java.util.Date date= format.parse(line.substring(2, 10));
				request.setStartDate(new Date(date.getTime()));

				errorLine++;
				date= format.parse(line.substring(10, 18));
				request.setEndDate(new Date(date.getTime()));
				requests.add(request);
				row++;
			}

			br.close();

		} catch (Exception e) {
			String messageErrorText = "";
			logger.info(e);
			requests.clear();
			switch (errorLine) {
			case 1:
				messageErrorText += BarsException.BILLING_CYCLE_NOT_ON_RANGE
						+ (row + 1);
				break;
			case 2:
				messageErrorText += BarsException.INVALID_START_DATE_FORMAT
						+ (row + 1);
				break;
			case 3:
				messageErrorText += BarsException.INVALID_END_DATE_FORMAT
						+ (row + 1);
				break;
			default:
				messageErrorText += "Exception!!!";
			}

			throw new BarsException(messageErrorText);
		}

		return requests;
	}

}
