package processing;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import algorithm.filters.ImageFilters;
import algorithm.segmentation.BackgroundSegmentation;
import algorithm.segmentation.CannyBackgroundSegmentation;
import constants.Constants;
import model.Color;
import utils.Utils;

public class ColorExtractorImpl implements ColorExtractor {
	private BackgroundSegmentation backgroundSegmentation;
	
	public ColorExtractorImpl() {
		backgroundSegmentation = new CannyBackgroundSegmentation();
	}
	
	@Override
	public List<Color> run(Mat image) {
		Utils.displayImage(image, false);
		// 1. Convert to LAB color space
		Mat labImage = new Mat();
		Imgproc.cvtColor(image, labImage, Imgproc.COLOR_BGR2Lab);
		
		Mat filtered = prefiltering(labImage);
		
		// 2. Detect foreground pixels
		boolean[][] segmentationMask = backgroundSegmentation.run(filtered);

		// 3. Find bounding box
		Rect boundingBox = findBoundingBox(segmentationMask);

		// 4. Crop image
		Mat cropped = filtered.submat(boundingBox);
		
		Utils.displayImage(cropped, true);
		
		return null;
	}

	private Mat prefiltering(Mat image) {
		// Apply bilateral filtering
		Mat filtered = applyBilateralFiltering(image);
		
		// Apply K-means clustering
		filtered = applyKMeansClustering(filtered);
		
		Utils.displayImage(filtered, true);
		return filtered;
	}

	private Mat applyBilateralFiltering(Mat image) {
		Mat filtered = new Mat();
		Imgproc.bilateralFilter(image, filtered , 5, 100, 150);
		return filtered;
	}

	private Mat applyKMeansClustering(Mat image) {
		return ImageFilters.kmeansClustering(image, Constants.CLUSTER_COUNT, Constants.ATTEMPTS);
	}

	private Rect findBoundingBox(boolean[][] segmentationMask) {		
		int top = segmentationMask.length;
		int right = 0;
		int bottom = 0;
		int left = segmentationMask[0].length;
		
		for (int i = 0; i < segmentationMask.length; i++) {
			for (int j = 0; j < segmentationMask[0].length; j++) {
				if (segmentationMask[i][j]) {
					top = Math.min(top, i);
					right = Math.max(right,  j);
					bottom = Math.max(bottom, i);
					left = Math.min(left, j);
				}
			}
		}
		return new Rect(left, top, right - left, bottom - top);
	}
}
