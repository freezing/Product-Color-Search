package model;

import org.mongodb.morphia.annotations.Entity;

@Entity("urls")
public class Url extends AbstractModel {
	private String value;
	private boolean isProcessed;
	
	public Url() {}
	
	public Url(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean getIsProcessed() {
		return isProcessed;
	}
	
	public Url setValue(String value) {
		this.value = value;
		return this;
	}
	
	public Url setIsProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
		return this;
	}
}
