package statistics;

public class Statistics {
	private static Statistics instance = null;
	
	public static Statistics getInstance() {
		if (instance == null) {
			instance = new Statistics();
		}
		return instance;
	}
	
	private Statistics() {}
	
	private long totalImageTime = 0;
	private long totalImages = 0;
	
	public void addImageProcessingTime(long totalProcessingTime) {
		totalImages++;
		totalImageTime += totalProcessingTime;
	}
	
	public long getAverageProcessingTime() {
		return totalImageTime / totalImages;
	}
}
