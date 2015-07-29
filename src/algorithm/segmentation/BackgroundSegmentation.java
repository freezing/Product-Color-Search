package algorithm.segmentation;

import org.opencv.core.Mat;

/**
 * Segmentation algorithm that separates foreground from background.
 * @author nikola
 *
 */
public interface BackgroundSegmentation {
	/**
	 * 
	 * @param image
	 * @return True for foreground and false otherwise.
	 */
	public boolean[][] run(Mat image);
}
