package processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import algorithm.color_palette.ColorPalette;
import algorithm.color_palette.ColorPaletteImpl;
import algorithm.filters.ImageFilters;
import algorithm.segmentation.BackgroundSegmentation;
import algorithm.segmentation.CannyBackgroundSegmentation;
import constants.Constants;
import model.Color;
import utils.Utils;

public class ColorExtractorImpl implements ColorExtractor {
	private BackgroundSegmentation backgroundSegmentation;
	private ColorPalette palette;
	
	public ColorExtractorImpl() {
		backgroundSegmentation = new CannyBackgroundSegmentation();
		palette = ColorPaletteImpl.getInstance();
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
		
		// 5. Iterate through colors and find best match from Color Palette
		return extractColors(cropped, cropSegmentationMask(segmentationMask, boundingBox));
	}

	private List<Color> extractColors(Mat cropped, boolean[][] cropSegmentationMask) {
		showSegmentationMask(cropSegmentationMask);
		
		int totalForegroundPixels = 0;
		Map<Color, Integer> countsMap = new HashMap<>();
		for (int i = 0; i < cropped.height(); i++) {
			for (int j = 0; j < cropped.width(); j++) {
				if (cropSegmentationMask[i][j]) {
					Color color = Color.create(cropped.get(i, j));
					Color closest = palette.findClosest(color);
					putInMap(closest, countsMap);
					totalForegroundPixels++;
				}
			}
		}
		
		return makeList(countsMap, totalForegroundPixels);
	}

	private List<Color> makeList(Map<Color, Integer> countsMap, int total) {
		List<Color> colors = new ArrayList<>();
		
		for (Entry<Color, Integer> entry : countsMap.entrySet()) {
			Color color = entry.getKey();
			color.setPercentage(entry.getValue() * 100 / total);
		}
		
		return colors;
	}

	private void putInMap(Color closest, Map<Color, Integer> countsMap) {
		if (countsMap.containsKey(closest)) {
			int prev = countsMap.get(closest);
			countsMap.put(closest, prev + 1);
		} else {
			countsMap.put(closest, 1);
		}
	}

	private boolean[][] cropSegmentationMask(boolean[][] mask, Rect boundingBox) {
		boolean cropped[][] = new boolean[boundingBox.height][boundingBox.width];
		
		for (int i = boundingBox.y; i < boundingBox.y + boundingBox.height; i++) {
			for (int j = boundingBox.x; j < boundingBox.x + boundingBox.width; j++) {
				cropped[i - boundingBox.y][j - boundingBox.x] = mask[i][j];
			}
		}
		
		return cropped;
	}

	private void showSegmentationMask(boolean[][] segmentationMask) {
		Mat edges = new Mat(segmentationMask.length, segmentationMask[0].length, CvType.CV_8UC3);
		
		for (int i = 0; i < segmentationMask.length; i++) {
			for (int j = 0; j < segmentationMask[0].length; j++) {
				double val = 0.0;
				if (segmentationMask[i][j]) {
					val = 255.0;
				}
				edges.put(i, j, val, val, val);
			}
		}
		Utils.displayImage(edges, false);
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
