package algorithm.color_palette;

import java.util.HashMap;
import java.util.Map;

import drivers.ColorPaletteModelDriver;
import model.Color;
import model.ColorPaletteModel;

public class ColorPaletteImpl implements ColorPalette {
	private static final String PALETTE_NAME = "20 color palette";

	private static ColorPaletteImpl instance = null;
	
	public static ColorPaletteImpl getInstance() {
		if (instance == null) {
			instance = new ColorPaletteImpl();
		}
		return instance;
	}

	private ColorPaletteModel model;
	Map<Color, Color> cache;
	
	private ColorPaletteImpl() {
		model = ColorPaletteModelDriver.getInstance().getByName(PALETTE_NAME);
		cache = new HashMap<>();
	}
	
	@Override
	public Color findClosest(Color color) {
		if (cache.containsKey(color)) {
			return cache.get(color);
		}
		
		// Find the best
		Color best = null;
		int bestDistance = -1;
		for (Color potential : model.getColors()) {
			if (best == null) {
				best = potential;
				bestDistance = potential.distance(color);
			} else {
				int distance = potential.distance(color);
				if (distance < bestDistance) {
					bestDistance = distance;
					best = potential;
				}
			}
		}
		
		// Save in cache
		cache.put(color, best);
		return best;
	}
}
