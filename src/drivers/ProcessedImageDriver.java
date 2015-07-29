package drivers;

import model.ProcessedImage;

public class ProcessedImageDriver extends MorphiaAbstractModelDriver<ProcessedImage> {

	private static ProcessedImageDriver instance = null;

	public static ProcessedImageDriver getInstance() {
		if (instance == null) {
			instance = new ProcessedImageDriver();
		}
		return instance;
	}
	
	public ProcessedImageDriver() {
		super(ProcessedImage.class);
	}
}
