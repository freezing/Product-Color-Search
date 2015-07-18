package model;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

@Entity("images")
public class ProcessedImage extends AbstractModel {
	
	private String url;
	
	@Indexed(unique=false, sparse = true)
	private List<Color> colors;
	
	public String getUrl() {
		return url;
	}
	
	public List<Color> getColors() {
		return colors;
	}
	
	public ProcessedImage setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public ProcessedImage setColors(List<Color> colors) {
		this.colors = colors;
		return this;
	}
}
