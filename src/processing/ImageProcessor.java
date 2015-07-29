package processing;

import java.util.List;
import java.util.concurrent.Semaphore;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import drivers.ProcessedImageDriver;
import model.Color;
import model.ProcessedImage;
import model.Url;
import statistics.Statistics;

public class ImageProcessor implements Runnable {
	
	private Url url;
	private Semaphore semaphore;
	
	private ColorExtractor colorExtractor;

	public ImageProcessor(Url url, Semaphore semaphore) {
		this.url = url;
		this.semaphore = semaphore;
		
		init();
	}
	
	private void init() {
		colorExtractor = new ColorExtractorImpl();
	}

	@Override
	public void run() {
		try {
			semaphore.acquire();
			long t0 = System.currentTimeMillis();
			process();
			long totalProcessingTime = System.currentTimeMillis() - t0;
			Statistics.getInstance().addImageProcessingTime(totalProcessingTime);
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void process() {
		System.out.println("Processing: " + url.getValue());
		// Read image
		Mat image = Imgcodecs.imread(url.getValue());
		
		// Extract colors
		List<Color> extractedColors = colorExtractor.run(image);
		
		// Create Image model
		ProcessedImage processedImage = new ProcessedImage()
				.setColors(extractedColors)
				.setUrl(url.getValue());
		
		// Insert in Mongo database
		ProcessedImageDriver.getInstance().save(processedImage);
		System.out.println("Sucessfully processed: " + url.getValue());
	}
}
