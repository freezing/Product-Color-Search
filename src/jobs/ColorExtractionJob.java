package jobs;

import java.util.concurrent.Semaphore;

import drivers.UrlDriver;
import model.Url;
import processing.ImageProcessor;
import statistics.Statistics;

public class ColorExtractionJob {
	private static final String NATIVE_LIBRARY_PATH = "/usr/local/share/OpenCV/java/libopencv_java300.so";
	
	static {
		System.load(NATIVE_LIBRARY_PATH);
	}
	
	private static final int NUMBER_OF_CORES = 1;
	private static final int MAX_ITERATIONS = 1000;
	private static final boolean REFRESH_ALL = true;

	private Semaphore semaphore;
	
	public ColorExtractionJob() {
		semaphore = new Semaphore(NUMBER_OF_CORES);
	}
	
	public void run() {
		if (REFRESH_ALL) {
			UrlDriver.getInstance().refreshAll();
		}
		
		int iter = 0;
		while (iter++ < MAX_ITERATIONS) {
			Url url = UrlDriver.getInstance().getNonQueued();
			
			if (url == null) {
				// No urls left
				System.out.println("All urls have been processed");
				return;
			}
			UrlDriver.getInstance().setIsInQueue(url.getId());
			new Thread(new ImageProcessor(url, semaphore)).start();
		}
		System.out.println("Max iterations reached");
		
		while (semaphore.getQueueLength() != 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Average processing time per image: " + Statistics.getInstance().getAverageProcessingTime());
	}
	
	public static void main(String[] args) {
		new ColorExtractionJob().run();
	}
}
