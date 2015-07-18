package io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagePathReader {

	private File root;

	public ImagePathReader(String rootPath) {
    	root = new File(rootPath);
	}

	public List<File> getImageFiles() {
		List<File> imageFiles = new ArrayList<>();
		for (File subFolder : root.listFiles()) {
			if (subFolder.isDirectory()) {
				for (File file : subFolder.listFiles()) {
					if (isImage(file)) {
						imageFiles.add(file);
						// Inserts only one image from a single folder
						break;
					}
				}
			}
		}
		return imageFiles;
	}

	private boolean isImage(File file) {
		return !file.getName().endsWith(".txt");
	}
}
