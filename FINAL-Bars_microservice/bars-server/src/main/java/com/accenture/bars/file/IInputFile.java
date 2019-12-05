package com.accenture.bars.file;

import java.io.File;
import java.util.List;

import com.accenture.bars.domain.Request;
import com.accenture.bars.exception.BarsException;

/**
 * IInputFile Interface
 *
 */
public interface IInputFile {

	public List<Request> readFile() throws BarsException;

	public void setFile(File file);

	public File getFile();

}
