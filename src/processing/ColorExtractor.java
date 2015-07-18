package processing;

import java.util.List;

import org.opencv.core.Mat;

import model.Color;

public interface ColorExtractor {
	public List<Color> run(Mat image);
}
