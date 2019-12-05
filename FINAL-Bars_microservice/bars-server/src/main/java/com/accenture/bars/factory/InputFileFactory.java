package com.accenture.bars.factory;

import java.io.File;

import com.accenture.bars.file.CSVInputFileImpl;
import com.accenture.bars.file.IInputFile;
import com.accenture.bars.file.TextInputFileImpl;

/**
 * InputFileFactory - class responsible for the creation of TextInputFileImpl
 * class and CSVInputFileImpl
 *
 */
public class InputFileFactory {

	private static InputFileFactory instance;

	private InputFileFactory() {

	}

	public static InputFileFactory getInstance() {
		if (instance == null) {
			instance = new InputFileFactory();
		}

		return instance;
	}

	public IInputFile getInputFile(File file) {
		IInputFile inputFile = null;
		String[] files = file.toString().split("\\.");
		String extension = files[files.length - 1];

		if ("txt".equals(extension)) {
			inputFile = new TextInputFileImpl();
			inputFile.setFile(file);
			return inputFile;
		} else if ("csv".equals(extension)) {
			inputFile = new CSVInputFileImpl();
			inputFile.setFile(file);
			return inputFile;
		}

		return inputFile;
	}

}
