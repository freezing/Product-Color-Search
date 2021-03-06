package utils;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import constants.Constants;

public class Utils {
	public static BufferedImage Mat2BufferedImage(Mat m) {
    	// source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
    	// Fastest code
    	// The output can be assigned either to a BufferedImage or to an Image

	    int type = BufferedImage.TYPE_BYTE_GRAY;
	    if ( m.channels() == 3 ) {
	        type = BufferedImage.TYPE_3BYTE_BGR;
	    } else if (m.channels() == 4) {
	    	type = BufferedImage.TYPE_4BYTE_ABGR;
	    }
	    int bufferSize = m.channels()*m.cols()*m.rows();
	    byte [] b = new byte[bufferSize];
	    m.get(0,0,b); // get all the pixels
	    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
	    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    System.arraycopy(b, 0, targetPixels, 0, b.length);  
	    return image;

	}
    
    public static void displayImage(Image img2) {
		if (!Constants.DEBUG) return; 
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

	public static void displayImage(Mat img, boolean isLab) {
		if (!Constants.DEBUG) return;
		Mat cnv = img;
		if (isLab) {
			cnv = new Mat();
			Imgproc.cvtColor(img, cnv, Imgproc.COLOR_Lab2BGR);
		}
		BufferedImage tmp = Utils.Mat2BufferedImage(cnv);
		Utils.displayImage(tmp);
	}
}
