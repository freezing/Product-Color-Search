package opencv;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import io.ImagePathReader;

public class App {
	private static final String rootPath = "/home/nikola/storage/BigData/Images/Products/index_images/";
	
	static {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	}
	
    public static void main( String[] args ) {
    	ImagePathReader ipr = new ImagePathReader(rootPath);
    	
    	
    	
    }
    
    private static boolean isImage(File file) {
    	// TODO: implement better isImage algorithm
		return !file.getName().endsWith(".txt");
	}

	public static BufferedImage Mat2BufferedImage(Mat m){
    	// source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
    	// Fastest code
    	// The output can be assigned either to a BufferedImage or to an Image

	    int type = BufferedImage.TYPE_BYTE_GRAY;
	    if ( m.channels() > 1 ) {
	        type = BufferedImage.TYPE_3BYTE_BGR;
	    }
	    int bufferSize = m.channels()*m.cols()*m.rows();
	    byte [] b = new byte[bufferSize];
	    m.get(0,0,b); // get all the pixels
	    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
	    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    System.arraycopy(b, 0, targetPixels, 0, b.length);  
	    return image;

	}
    
    public static void displayImage(Image img2)
	{   
	    //BufferedImage img=ImageIO.read(new File("/HelloOpenCV/lena.png"));
	    ImageIcon icon=new ImageIcon(img2);
	    JFrame frame=new JFrame();
	    frame.setLayout(new FlowLayout());        
	    frame.setSize(img2.getWidth(null)+50, img2.getHeight(null)+50);     
	    JLabel lbl=new JLabel();
	    lbl.setIcon(icon);
	    frame.add(lbl);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}
}