package model;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity("palettes")
public class ColorPaletteModel extends AbstractModel {
	private String name;
	private List<Color> colors;
	
	public ColorPaletteModel() {
		
	}
	
	public List<Color> getColors() {
		return colors;
	}
	
	public ColorPaletteModel setColors(List<Color> colors) {
		this.colors = colors;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public ColorPaletteModel setName(String name) {
		this.name = name;
		return this;
	}
}
