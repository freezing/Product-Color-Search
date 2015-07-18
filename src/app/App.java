package app;

import java.io.File;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.opencv.core.Core;

import io.ImagePathReader;
import model.Color;
import processing.ColorExtractorImpl;

public class App {
	private static final String ROOT_PATH = "/home/nikola/storage/BigData/Images/Products/index_images/";
	private static final int NUMBER_OF_CORES = 8;
	
	static {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	}
	
    public static void main( String[] args ) {
    	ImagePathReader ipr = new ImagePathReader(ROOT_PATH);
    	Semaphore semaphore = new Semaphore(NUMBER_OF_CORES);
    	
    	for (File imageFile : ipr.getImageFiles()) {
    		try {
				semaphore.acquire();

				List<Color> colors = new ColorExtractorImpl().extractColors(imageFile);
	    		System.out.println(imageFile.getAbsolutePath());
	    		semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }
}