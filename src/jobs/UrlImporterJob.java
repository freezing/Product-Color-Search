package jobs;

import java.io.File;

import drivers.UrlDriver;
import io.ImagePathReader;
import model.Url;

/**
 * Reads all image urls from the HDD and imports them in Mongo database.
 * @author nikola
 *
 */
public class UrlImporterJob {
	private static final String ROOT_PATH = "/home/nikola/storage/BigData/Images/Products/index_images/";

	public void run() {
		UrlDriver.getInstance().removeAll();
		
		ImagePathReader ipr = new ImagePathReader(ROOT_PATH);
    	
    	for (File imageFile : ipr.getImageFiles()) {
    		UrlDriver.getInstance().save(new Url(imageFile.getAbsolutePath()));
    	}
	}
	
	public static void main(String[] args) {
		new UrlImporterJob().run();
	}
}
