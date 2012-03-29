import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class LoadImageApp extends Component implements ActionListener{

	/**
	 * 
	 */
	Thread t;
	Thread[] arr;
	long StartTimer;
	long EndTimer;
	JFrame f;
	//JPanel main;
	Container main;
	static JPanel Pic;
	static JPanel top;
	JButton Next;
	JButton Prev;
	JButton Blur;
	JFileChooser filechoose;
	JLabel Lab;
	JMenuBar menuBar;
	JMenu Load;
	JMenu Load2;
	JMenu Selector;
	JMenuItem ParallelLoad2;
	JMenuItem ConcreteLoad2;
	JMenuItem NormalLoad;
	JMenuItem ParallelLoad;
	JMenuItem ConcreteLoad;
	JMenuItem Default;
	JMenuItem Open;
	JMenuItem Exit;
	
	Thread rr = null;
	
	private static final long serialVersionUID = 1L;

	static Load l = new Load();

	public LoadImageApp() {
		
		main =new JPanel();

		menuBar = new JMenuBar();
		
		Load = new JMenu("Load");
		Load2 = new JMenu("Worker Load");
		Selector =  new JMenu("File");
		
		
		Open = new JMenuItem("Open");
		Default = new JMenuItem("Default");
		NormalLoad = new JMenuItem("Normal Load");
		ParallelLoad = new JMenuItem("Parallel Load");
		ParallelLoad2 = new JMenuItem("Parallel Worker");
		ConcreteLoad = new JMenuItem("Concrete Load");
		ConcreteLoad2 = new JMenuItem("Concrete Worker");
		Exit = new JMenuItem("Exit");
		
		filechoose = new JFileChooser();
		
		Pic = new JPanel();
		top = new JPanel();

		Lab = new JLabel("");
		
		Next = new JButton("Next");
		Prev = new JButton("Prev");
		Blur = new JButton("Blur");
		
		f = new JFrame("Blur");
		
		main.setLayout(new BorderLayout());
		top.setLayout(new FlowLayout());
		
		top.add(Prev);
		top.add(new JSeparator(JSeparator.VERTICAL));
		top.add(new JSeparator(JSeparator.VERTICAL));
		top.add(Blur);
		top.add(new JSeparator(JSeparator.VERTICAL));
		top.add(new JSeparator(JSeparator.VERTICAL));
		top.add(Next);

		main.add(top, BorderLayout.NORTH);
		main.add(Pic, BorderLayout.CENTER);
		//top.add(new JSeparator(JSeparator.HORIZONTAL));
		main.add(Lab, BorderLayout.SOUTH);
		
		Next.addActionListener(this);
		Prev.addActionListener(this);
		Blur.addActionListener(this);
		NormalLoad.addActionListener(this);
		ParallelLoad.addActionListener(this);
		ParallelLoad2.addActionListener(this);
		ConcreteLoad.addActionListener(this);
		ConcreteLoad2.addActionListener(this);
		Open.addActionListener(this);
		Default.addActionListener(this);
		Exit.addActionListener(this);
		
		Load.add(NormalLoad);
		Load.add(ParallelLoad);
		Load.add(ConcreteLoad);

		
		Load2.add(ParallelLoad2);
		Load2.add(ConcreteLoad2);
		
		Selector.add(Open);
		Selector.add(Default);
		Selector.addSeparator();
		Selector.add(Exit);
		
		menuBar.add(Selector);
		menuBar.add(Load);
		menuBar.add(Load2);
		
		f.add(main);
		f.setJMenuBar(menuBar);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600,600);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == Next){
			try{
			//l.load(Pic);
			l.newload(Pic);
			Lab.setText(l.files[l.x].toString());
			l.x++;
			if(l.x == l.files.length)
				l.x=0;
			}catch(NullPointerException e){
				JOptionPane.showMessageDialog(null, "Load The Image First");				
			}
		}
		if(arg0.getSource() == Blur){
			try {
				l.blurImage(20, Pic);
			} catch (NullPointerException e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Load The Image First");
			} catch (Exception e){
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Load The Image First");
			}
			
		}
		
		if(arg0.getSource() == Open){
			int result = filechoose.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION)
			{
				String path = filechoose.getCurrentDirectory().toString();
				l.ll = path;
				l.FilesArray(l.ll);
				Pic.removeAll();
			}
		}
		
		if(arg0.getSource() == Default){
			l.FilesArray("C:\\Users\\SAMI\\Pictures\\try");
		}
		
		if(arg0.getSource() == Prev){
			try{
				l.newload(Pic);
				Lab.setText(l.files[l.x].toString());
				l.x--;
				if(l.x == 0)
					l.x=l.files.length;
			}catch(NullPointerException e){
				JOptionPane.showMessageDialog(null, "Load The Image First");
			}
		}
		
		if(arg0.getSource() == Exit){
		
			int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit", "File Submission",JOptionPane.YES_NO_OPTION);
			 if (answer == JOptionPane.YES_OPTION)
				 System.exit(0);
		}
		
		if(arg0.getSource() == NormalLoad){
			
			try {
				StartTimer = System.nanoTime();
				l.NormalLOAD();
				EndTimer = System.nanoTime();
				JOptionPane.showMessageDialog(null, "Time it take :" + (EndTimer-StartTimer)/1000000000);
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Load The Image First");
			}
		}
	
		if(arg0.getSource() == ParallelLoad){

			try {
				t = l.ParallelLOAD();
				Thread ll = new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							StartTimer = System.nanoTime();
							t.start();
							t.join();
							EndTimer = System.nanoTime();
							JOptionPane.showMessageDialog(null,
									"Time it take :" + (EndTimer - StartTimer)
											/ 1000000000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				ll.start();
			} catch (NullPointerException e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Load The Image First");
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Load The Image First");
			}
		}
	
		if(arg0.getSource() == ConcreteLoad){
			
			try {
				arr = l.ConceranteLOAD();
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						StartTimer = System.nanoTime();
						for (int i = 0; i < arr.length; i++) {
							try {
								arr[i].start();
								arr[i].join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						EndTimer = System.nanoTime();
						JOptionPane.showMessageDialog(null, "Time it take :"
								+ (EndTimer - StartTimer) / 1000000000);
					}
				});
				t.start();
			} catch (NullPointerException e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Load The Image First");
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Load The Image First");
			}
		}
		
		if(arg0.getSource() == ParallelLoad2){
			
			try {
				long startTime = System.nanoTime();
				l.KOKOKO();
				System.out.println("complete");
				long endTime = System.nanoTime();
				long wow = endTime - startTime;
				JOptionPane.showMessageDialog(null, "Time it take :" + wow);
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Load The Image First");
			}
		}
		
		if(arg0.getSource() == ConcreteLoad2){
			
			try {
				long startTime = System.nanoTime();
				//l.KOKOKO();
				Thread m= l.Return();
				m.start();
				l.gate.await();
				System.out.println("wenk");
				long endTime = System.nanoTime();
				long wow = endTime - startTime;
				JOptionPane.showMessageDialog(null, "Time it take :" + wow);
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "Load The Image First");
			}
		}
	}
	
	public static void main(String[] args){
		@SuppressWarnings("unused")
		LoadImageApp kk = new LoadImageApp();
	}
}