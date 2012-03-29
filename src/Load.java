import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Load {

	BufferedImage img;
	//Image img;
	public String ll;
	File[] files;
	ImageIcon imgIcon;
	int Counter=0;
	long End=0;
	long Start=0;
	long last=0;
	int x=0;
	
	Image ImgArr[];
	ImageIcon ImageIconArr[];
	
	CountDownLatch gate;
	
	Thread threadArr[];
	
	int numProc = Runtime.getRuntime().availableProcessors() + 1;
	
	public void FilesArray(String file) {
		files = new File(file).listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".jpg");
			}
		});
	}

	public void NormalLOAD(){
		
		ImgArr = new Image[files.length];
		ImageIconArr = new ImageIcon[files.length];
		
		for (int i = 0; i < files.length; i++) {
			try {
				ImgArr[i] = ImageIO.read(files[i]);
				ImageIconArr[i] = new ImageIcon(ImgArr[i]);
				
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("Loading Error : IO Exception");
			}catch (NullPointerException e){
				System.out.println("Loading Error : Pointer Exeption");
			}
		}
	}
	
	public Thread ParallelLOAD() {
		
		ImgArr = new Image[files.length];
		ImageIconArr = new ImageIcon[files.length];
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < files.length; i++) {
					try {
						ImgArr[i] = ImageIO.read(files[i]);
						ImageIconArr[i] = new ImageIcon(ImgArr[i]);
					} catch (NullPointerException e) {
						System.out.println("Loading Error : Pointer Exeption");
					} catch (IOException e) {
						//e.printStackTrace();
						System.out.println("Loading Error : IO Exception");
					}
				}
			}
		});
		return t;
	}
	
	public Thread[] ConceranteLOAD() {
		
		ImgArr = new Image[files.length];
		ImageIconArr = new ImageIcon[files.length];
		threadArr = new Thread[files.length];
		for (int i = 0; i < files.length; i++) {
			Counter = i;
			Thread e  =new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						ImgArr[Counter] = ImageIO.read(files[Counter]);
						ImageIconArr[Counter] = new ImageIcon(ImgArr[Counter]);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Loading Error : IO Exception");
					} catch (NullPointerException e){
						// TODO Auto-generated catch block
						System.out.println("Loading Error : Pointer Exeption");
					}
				}
			});
			threadArr[i]=e;
		}
		return threadArr;
	}

	public void newload(JPanel j){
		try {
			Graphics g = (Graphics)j.getGraphics();
			ImgArr[x] = ImageIO.read(files[x]);	//img = ImageIO.read(new File(files[x].toString()));
			g.drawImage(ImgArr[x], 20, 20, null);
			ImageIconArr[x]= new ImageIcon(ImgArr[x]);
			j.removeAll();
			j.repaint();
			j.add(new JLabel(ImageIconArr[x]));
		} catch (IOException e) {
			System.out.println("error");
		}		
	}
	
	public void KOKO(){
		
		/*ImgArr = new Image[files.length];
		ImageIconArr = new ImageIcon[files.length];
		
		for (int i = 0; i < files.length; i++) {
			Counter = i;
			Thread k = new Thread(new Worker(ImgArr,ImageIconArr,files,Counter));
			k.start();
		}*/
	}
	
	public void KOKOK(){
		Thread k = new Thread(new Worker1());
		k.start();
	}
	
	public void KOKOKO(){
		
		gate = new CountDownLatch(1);
		Thread k = new Thread(new WorkerGate(gate));
		k.start();
		try {
			System.out.println("berfore");
			gate.await(0, null);
			System.out.println("after await");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("With Gate");
		}
	}
	
	public Thread Return(){
		
		gate = new CountDownLatch(1);
		Thread k = new Thread(new WorkerGate(gate));
		return k;
	}
			
	public class Loader extends Thread{

		Thread t;
		
		public Loader(Thread h){t=h;}
		public Loader() {
			// TODO Auto-generated constructor stub
		}
		public void setRun(Thread h){t=h;}
		public void startWorking()
		{
			t.start();
			
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ImgArr = new Image[files.length];
			ImageIconArr = new ImageIcon[files.length];
			for (int i = 0; i < files.length; i++) {
				try {
					ImgArr[i] = ImageIO.read(files[i]);
					ImageIconArr[i] = new ImageIcon(ImgArr[i]);
				} catch (IOException e) {
					//e.printStackTrace();
					System.out.println("Loading Error : IO Exception");
				}catch (NullPointerException e){
					System.out.println("Loading Error : Pointer Exeption");
				}
			}
		}
		
		
		
	}
	
	class Worker implements Runnable{
		
		public Worker(Image[] imgArr1, ImageIcon[] imageIconArr1, File[] files1, int counter1) {
			ImgArr = imgArr1;
			ImageIconArr = imageIconArr1;
			files = files1;
			counter1 = Counter;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ImgArr = new Image[files.length];
			ImageIconArr = new ImageIcon[files.length];
			try {
				ImgArr[Counter] = ImageIO.read(files[Counter]);
				ImageIconArr[Counter] = new ImageIcon(ImgArr[Counter]);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Loading Error : IO Exception");
			}
			catch (NullPointerException e)
			{
				System.out.println("Loading Error : Pointer Exeption");
			}
			
		}
	}
	
	class Worker1 implements Runnable{

		public Worker1() {}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ImgArr = new Image[files.length];
			ImageIconArr = new ImageIcon[files.length];
			for (int i = 0; i < files.length; i++) {
				try {
					ImgArr[i] = ImageIO.read(files[i]);
					ImageIconArr[i] = new ImageIcon(ImgArr[i]);
					System.out.println("here");
				} catch (IOException e) {
					//e.printStackTrace();
					System.out.println("Loading Error : IO Exception");
				}catch (NullPointerException e){
					System.out.println("Loading Error : Pointer Exeption");
				}
			}
		}
	}
	
	class WorkerGate implements Runnable{
		
		CountDownLatch count;
		
		public WorkerGate(CountDownLatch d){
			count = d = new CountDownLatch(1);
			ImgArr = new Image[files.length];
			ImageIconArr = new ImageIcon[files.length];
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int i = 0; i < files.length; i++) {
				try {
					ImgArr[i] = ImageIO.read(files[i]);
					ImageIconArr[i] = new ImageIcon(ImgArr[i]);
					System.out.println("Here Gate");
				} catch (IOException e) {
					//e.printStackTrace();
					System.out.println("Loading Error : IO Exception");
				}catch (NullPointerException e){
					System.out.println("Loading Error : Pointer Exeption");
				}
			}
			count.countDown();
			System.out.println(count.toString());
		}
	}
	
	public void blurImage(int dimension, JPanel j){
		int n = dimension*dimension;
		float[] matrix = new float[n];
		for (int i = 0; i < matrix.length; i++)
			matrix[i] = 1.0f/(float)n;
		//long start = System.nanoTime();
		BufferedImageOp op = new ConvolveOp(new Kernel(dimension, dimension, matrix) );
		img = op.filter(img, null);
		//long end = System.nanoTime();
		//repaint();
		
		/*Graphics g = (Graphics)j.getGraphics();	//img = ImageIO.read(new File(files[x].toString()));
		g.drawImage(img, 20, 20, null);
		imgIcon= new ImageIcon(img);
		j.removeAll();
		j.repaint();
		j.add(new JLabel(imgIcon));*/
		newload(j);
	}

}
