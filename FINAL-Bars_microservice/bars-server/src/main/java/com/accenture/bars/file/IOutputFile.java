package com.accenture.bars.file;

import java.io.File;
import java.util.List;

import com.accenture.bars.domain.Record;

/**
 * IOutputFile Interface
 *
 */
public interface IOutputFile {

	public void writeFile(List<Record> records);

	public void setFile(File file);

	public File getFile();

}
