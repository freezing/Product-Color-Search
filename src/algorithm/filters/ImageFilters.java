package algorithm.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;

import constants.Constants;

public class ImageFilters {
	public static Mat kmeansClustering(Mat image, int clusterCount, int attempts) {
		Mat labels = new Mat();
		Mat centers = new Mat();
		
		Mat samples = image.reshape(1, image.cols() * image.rows());
		Mat samples32f = new Mat();
		samples.convertTo(samples32f, CvType.CV_32F, 1.0 / 255.0);
		
		Core.kmeans(samples32f, clusterCount, labels, new TermCriteria(TermCriteria.EPS + TermCriteria.COUNT, Constants.NUMBER_OF_ITERATIONS, Constants.EPS),
				attempts, Core.KMEANS_RANDOM_CENTERS, centers);
		
		return cluster(image, labels, centers);
	}
	
	private static Mat cluster(Mat image, Mat labels, Mat centers) {
		centers.convertTo(centers, CvType.CV_8UC1, 255.0);
		centers.reshape(3);
		
		Mat clustered = image.clone();
		
		int rows = 0;
		for (int i = 0; i < image.height(); i++) {
			for (int j = 0; j < image.width(); j++) {
				int label = (int)labels.get(rows, 0)[0];
				
				int L = (int)centers.get(label, 0)[0];
				int a = (int)centers.get(label, 1)[0];
				int b = (int)centers.get(label, 2)[0];
				
				clustered.put(i, j, L, a, b);
				rows++;
			}
		}
		
		return clustered;
	}

	private static List<Mat> showClusters (Mat cutout, Mat labels, Mat centers) {
		centers.convertTo(centers, CvType.CV_8UC1, 255.0);
		centers.reshape(3);
		
		List<Mat> clusters = new ArrayList<Mat>();
		for(int i = 0; i < centers.rows(); i++) {
			clusters.add(Mat.zeros(cutout.size(), cutout.type()));
		}
		
		Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for(int i = 0; i < centers.rows(); i++) counts.put(i, 0);
		
		int rows = 0;
		for(int y = 0; y < cutout.rows(); y++) {
			for(int x = 0; x < cutout.cols(); x++) {
				int label = (int)labels.get(rows, 0)[0];
				int r = (int)centers.get(label, 2)[0];
				int g = (int)centers.get(label, 1)[0];
				int b = (int)centers.get(label, 0)[0];
				counts.put(label, counts.get(label) + 1);
				clusters.get(label).put(y, x, b, g, r);
				rows++;
			}
		}
		System.out.println(counts);
		return clusters;
	}
	
}
