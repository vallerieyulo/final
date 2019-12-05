package com.accenture.bars.file;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * AbstractOutputFile Class
 *
 */
public abstract class AbstractOutputFile implements IOutputFile {

	protected Logger logger;
	private File file;

	@Override
	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public File getFile() {
		return file;
	}

}
