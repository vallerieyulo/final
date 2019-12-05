package com.accenture.bars.file;

import java.io.File;

/**
 * AbstractInputFile Class
 *
 *
 */
public abstract class AbstractInputFile implements IInputFile {

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
