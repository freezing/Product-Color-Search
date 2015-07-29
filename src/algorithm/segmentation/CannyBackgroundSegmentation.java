package algorithm.segmentation;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import constants.Constants;
import utils.Utils;

public class CannyBackgroundSegmentation implements BackgroundSegmentation {
	private static final boolean BACKGROUND_VALUE = false;
	private static final boolean FOREGROUND_VALUE = true;
	private static final byte EDGE_VALUE = -1;
	private static final byte NON_EDGE_VALUE = 0;

	@Override
	public boolean[][] run(Mat image) {
		// 1. Detect edges
		Mat edges = new Mat();
		Imgproc.Canny(image, edges, Constants.CANNY_THRESHOLD_LOW, Constants.CANNY_THRESHOLD_HIGH);
		
		// 2. Dilate once
		Mat dilated = new Mat();
		
		// Create dilation kernel
		int dilation_type = Imgproc.MORPH_RECT;
		int dilation_size = 2;
		Mat element = Imgproc.getStructuringElement( dilation_type,
                 new Size( 2*dilation_size + 1, 2*dilation_size+1 ),
                 new Point( dilation_size, dilation_size ) );
		
		Imgproc.dilate(edges, dilated, element);
		
		Utils.displayImage(dilated, false);
		
		// 3. Set foreground and background values
		return getForegroundAndBackground(dilated);
	}

	/**
	 * Run BFS from each black pixel. If BFS reaches edges it's background, otherwise it's foreground.
	 * @param edges
	 * @return 
	 */
	private boolean[][] getForegroundAndBackground(Mat edges) {
		byte[] buffEdges = new byte[(int) (edges.total() * edges.channels())];
		edges.get(0, 0, buffEdges);
		
		boolean[][] segmentationMask = new boolean[edges.height()][edges.width()];

		for (int i = 0; i < edges.height(); i++) {
			for (int j = 0; j < edges.width(); j++) {
				int idx = (i * edges.width() + j) * edges.channels();
				if (buffEdges[idx] == EDGE_VALUE) {
					segmentationMask[i][j] = true;
				}
			}
		}
		
		for (int i = 0; i < edges.height(); i++) {
			for (int j = 0; j < edges.width(); j++) {
				int idx = (i * edges.width() + j) * edges.channels();
				if (buffEdges[idx] == NON_EDGE_VALUE) {
					bfs(i, j, edges.height(), edges.width(), edges.channels(), buffEdges, segmentationMask);
				}
			}
		}
		return segmentationMask;
	}

	private void bfs(int i, int j, int height, int width, int channels, byte[] buffEdges, boolean[][] segmentationMask) {
		final int moves[][] = {
				{-1, 0}, {0, 1}, {1, 0}, {0, -1}	
		};
		
		List<Integer> qi = new ArrayList<>();
		List<Integer> qj = new ArrayList<>();
		
		int idx = getIdx(i, j, width, channels);
		buffEdges[idx] = EDGE_VALUE;
		
		qi.add(i);
		qj.add(j);
		
		int curr = 0;
		
		boolean isForeground = true;
		
		while (curr < qi.size()) {
			i = qi.get(curr);
			j = qj.get(curr++);
			
			for (int dir = 0; dir < moves.length; dir++) {
				int ti = i + moves[dir][0];
				int tj = j + moves[dir][1];
				
				if (isOut(ti, tj, height, width)) {
					isForeground = false;
					continue;
				}
				
				int tIdx = getIdx(ti, tj, width, channels);
				if (buffEdges[tIdx] == EDGE_VALUE) {
					continue;
				}
				
				buffEdges[tIdx] = EDGE_VALUE;
				qi.add(ti);
				qj.add(tj);
			}
		}
		
		for (int k = 0; k < qi.size(); k++) {
			int ti = qi.get(k);
			int tj = qj.get(k);
			
			if (isForeground) {
				segmentationMask[ti][tj] = FOREGROUND_VALUE;
			} else {
				segmentationMask[ti][tj] = BACKGROUND_VALUE;
			}
		}
	}

	private boolean isOut(int i, int j, int height, int width) {
		return i < 0 || j < 0 || i >= height || j >= width;
	}

	private int getIdx(int i, int j, int width, int channels) {
		return (i * width + j) * channels;
	}
}
