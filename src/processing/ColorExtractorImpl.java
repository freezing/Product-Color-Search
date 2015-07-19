package processing;

import java.awt.image.BufferedImage;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import model.Color;
import utils.Utils;

public class ColorExtractorImpl implements ColorExtractor {

	@Override
	public List<Color> run(Mat image) {
		// 1. Convert to LAB color space
		Mat labImage = new Mat();
		Imgproc.cvtColor(image, labImage, Imgproc.COLOR_BGR2Lab);
		
		Mat test = new Mat();
		Imgproc.cvtColor(labImage, test, Imgproc.COLOR_Lab2BGR);
		
		BufferedImage img = Utils.Mat2BufferedImage(test);
		Utils.displayImage(img);
		
		return null;
	}
}
