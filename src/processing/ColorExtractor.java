package processing;

import java.io.File;
import java.util.List;

import model.Color;

public interface ColorExtractor {
	public List<Color> extractColors(File imageFile);
}
