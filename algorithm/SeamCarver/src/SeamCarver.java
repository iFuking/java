import java.awt.Color;

public class SeamCarver {
	
	private Picture pic;
	private final int BORDER_VALUE = 255*255*3;
	
	public SeamCarver(Picture picture) {
		
		pic = picture;
	}
	
	public Picture picture() {
		return pic;
	}
	
	public int width() {
		return pic.width();
	}
	
	public int height() {
		return pic.height();
	}
	
	public double energy(int x, int y) {
		
		if (x<0 || x>=width() || y<0 || y>=height()) throw new IndexOutOfBoundsException();
		
		if (x==0 || x==width()-1 || y==0 || y==height()-1)
			return BORDER_VALUE;
		
		Color colorLeft = pic.get(x-1, y);
		Color colorRight = pic.get(x+1, y);
		double rx = colorLeft.getRed() - colorRight.getRed();
		double gx = colorLeft.getGreen() - colorRight.getGreen();
		double bx = colorLeft.getBlue() - colorRight.getBlue();
		double deltaX2 = rx*rx + gx*gx + bx*bx;
		
		Color colorUp = pic.get(x, y-1);
		Color colorDown = pic.get(x, y+1);
		double ry = colorUp.getRed() - colorDown.getRed();
		double gy = colorUp.getGreen() - colorDown.getGreen();
		double by = colorUp.getBlue() - colorDown.getBlue();
		double deltaY2 = ry*ry + gy*gy + by*by;
		
		return deltaX2 + deltaY2;
	}
	
	public int[] findHorizontalSeam() {
		
		Picture picTrans = new Picture(height(), width());
		Picture picTransBack = new Picture(width(), height());
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				picTrans.set(j, i, pic.get(i, j));
				picTransBack.set(i, j, pic.get(i, j));
			}
		}
		pic = picTrans;
		
		int[] horizontalSeam = findVerticalSeam();
		pic = picTransBack;
		
		return horizontalSeam;
	}
	
	public int[] findVerticalSeam() {
		
		double[][] energyFunc = new double[width()][height()];
		double[][] distTo = new double[width()][height()];
		Pixel[][] pixTo = new Pixel[width()][height()];
		
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				energyFunc[i][j] = energy(i, j);
				distTo[i][j] = Double.POSITIVE_INFINITY;
			}
		}
		
		for (int i = 0; i < width(); i++) {
			distTo[i][0] = 0;
			pixTo[i][0] = new Pixel();
		}
		Topological top = new Topological(width(), height());
		
		for (Pixel pixel : top.order()) {
			int x = pixel.getX(), y = pixel.getY();
			
			for (int i = 0; i < top.DIR.length; i++) {
				int xn = x+top.DIR[i][0], yn = y+top.DIR[i][1];
				
				if (xn>=0 && xn<width() && yn>=0 && yn<height()) {
					if (distTo[xn][yn] > distTo[x][y]+energyFunc[x][y]) {
						distTo[xn][yn] = distTo[x][y]+energyFunc[x][y];
						pixTo[xn][yn] = new Pixel(x, y);
					}
				}
			}
		}
		
		int bottomX = 0;
		double min = distTo[bottomX][height()-1];
		for (int i = 1; i < width(); i++) {
			if (distTo[i][height()-1] < min) {
				min = distTo[i][height()-1];
				bottomX = i;
			}
		}
		
		Stack<Integer> seamXRev = new Stack<Integer>();
		for (int i=bottomX, j=height()-1; j >= 0; i=pixTo[i][j].getX(), j--) {
			seamXRev.push(i);
		}
		
		int[] seamX = new int[seamXRev.size()];
		for (int i = 0; !seamXRev.isEmpty(); i++) {
			seamX[i] = seamXRev.pop();
		}
		
		return seamX;
	}
	
	public void removeHorizontalSeam(int[] seam) {
		
		Picture picTrans = new Picture(height(), width());
		Picture picTransBack = new Picture(width(), height()-1);
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				picTrans.set(j, i, pic.get(i, j));
			}
		}
		pic = picTrans;
		
		removeVerticalSeam(seam);
		
		for (int i = 0; i < width(); i++) {
			for (int j = 0; j < height(); j++) {
				picTransBack.set(j, i, pic.get(i, j));
			}
		}
		pic = picTransBack;
	}
	
	public void removeVerticalSeam(int[] seam) {
		
		if (seam == null) throw new NullPointerException();
		if (seam.length != height() || width() <= 1) throw new IllegalArgumentException();
		
		Picture removePic = new Picture(width()-1, height());
		
		for (int j = 0; j < height(); j++) {
			for (int i = 0; i < width()-1; i++) {
				if (i < seam[j]) {
					removePic.set(i, j, pic.get(i, j));
				} else {
					removePic.set(i, j, pic.get(i+1, j));
				}
			}
		}
		pic = removePic;
	}
	
}
