package drivers;

import model.ColorPaletteModel;

public class ColorPaletteModelDriver extends MorphiaAbstractModelDriver<ColorPaletteModel> {

	private static ColorPaletteModelDriver instance = null;
	
	public static ColorPaletteModelDriver getInstance() {
		if (instance == null) {
			instance = new ColorPaletteModelDriver();
		}
		return instance;
	}
	
	public ColorPaletteModelDriver() {
		super(ColorPaletteModel.class);
	}
	
	public ColorPaletteModel getByName(String name) {
		return createQuery().field("name").equal(name).get();		
	}
}
