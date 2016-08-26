package shreyas;

import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ShutdownHibernate extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public JComboBox opt,hr,min;
	public Thread t1,t2;
	public SystemTray tray;
	public TrayIcon tcon;
	private long sl;
	public boolean isChange=false;
	public String option;
	private JLabel jl1,jl2,jl3;
	private JFrame parent;
	
	public void run()
	{
	
		
			if(Thread.currentThread().getName().equalsIgnoreCase("SHUTDOWN"))
			{
				
				try {
					System.out.println(sl);
					Thread.sleep(sl);
					
					if(option.equalsIgnoreCase("HIBERNATE")){
						Runtime.getRuntime().exec("shutdown -h");
					}
					else
					{
						Runtime.getRuntime().exec("shutdown -s");
		
					}
					
					System.exit(0);

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}
			
			if(Thread.currentThread().getName().equalsIgnoreCase("ALERT"))
			{
				try {
					System.out.println(sl -540000);
					Thread.sleep(sl - 480000);
					JOptionPane.showMessageDialog(parent,"Please Save Your Work.Your PC will be "+option.toLowerCase()+" After 8 Minuites");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		}
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		// TODO Auto-generated method stub

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new ShutdownHibernate().showUI();
	}

	public ShutdownHibernate()
	{
		tray = SystemTray.getSystemTray();
		this.parent = this;
		
	}
	public void showUI()
	{
		setLayout(new FlowLayout());
		opt = new JComboBox(new Object[]{"HIBERNATE","SHUTDOWN"});
		hr = new JComboBox(new Object[]{0,1,2,3,4,5});
		min = new JComboBox(new Object[]{10,20,30,40,50,60});
	
		t1 = new Thread(this);
		t1.setName("ALERT");
		t2 = new Thread(this);
		t2.setName("SHUTDOWN");
		
		
		jl1 =new JLabel("HRs");
		jl2 =new JLabel("MMs");
		jl3 =new JLabel("Options");
		PopupMenu pp = new PopupMenu();
		
		MenuItem m1 = new MenuItem("Open");
		m1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				// TODO Auto-generated method stub
				tray.remove(tcon);
				setExtendedState(JFrame.NORMAL);
				setVisible(true);
			}
		});
		
		MenuItem m2 = new MenuItem("Exit");
		
		m2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		ImageIcon ic = new ImageIcon("C:\\Users\\Shreyas\\Downloads\\Timer.png");
		pp.add(m1);
		pp.add(m2);
		tcon = new TrayIcon(ic.getImage(),"Terminator", pp);
		tcon.setImageAutoSize(true);
		opt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				// TODO Auto-generated method stub

			option = (String)opt.getSelectedItem();
			
			long tmhr = Long.parseLong(hr.getSelectedItem().toString());
			if(tmhr!=0)
				tmhr = tmhr*60;
			long th =  tmhr + Long.parseLong(min.getSelectedItem().toString());
			
				sl = th*60*1000;
				System.out.println(sl);
				//opt.setEnabled(false);
				
					if(!t1.isAlive())
						t1.start();
					if(!t2.isAlive())
						t2.start();
			
					setVisible(false);
					try {
						tray.add(tcon);
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					setExtendedState(JFrame.ICONIFIED);
					
				
			}
		});
		
		
		addWindowStateListener(new WindowStateListener() {
			
			public void windowStateChanged(WindowEvent w) {
				// TODO Auto-generated method stub
			System.out.println(w.getNewState());
				if(w.getNewState() == ICONIFIED){
					try {
						tray.add(tcon);
						setVisible(false);
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
					
			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w){
				
				try {
					tray.add(tcon);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		add(jl1);
		add(hr);
		add(jl2);
		add(min);
		add(jl3);
		add(opt);
		setTitle("ShutDown Timer");
		setIconImage(ic.getImage());
		setResizable(false);
		setLocation(500, 300);
		setVisible(true);
		pack();
	}
}
