package blak3980;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Tyler Blakeley
 * 090603980	Blak3980@mylaurier.ca
 *
 */
@SuppressWarnings("serial")
public class GraphCalc extends JFrame {
	// declare buttons and fields
	private JButton Create;
	public JTextField Function;
	private JLabel FunctionLabel;
	public JTextField Var;
	private JLabel VarLabel;
	public JTextField StartPoint;
	private JLabel StartLabel;
	public JTextField EndPoint;
	private JLabel EndLabel;
	private JTextField FunctionText;
	private JTextField Function2Text;
	private JTextField Function3Text;
	private JTextField Error;
	private JButton Zoomout;
	private JButton Zoomin;
	private GraphWindow gw;
	private boolean constants;
	
	

	// constructor
	/**
	 * Builds GUI to take a functions, variable and start and end points and graphs them
	 */
	public GraphCalc() {
		super("Tyler's Grapher");
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(2, 4));
		p1.setBackground(Color.WHITE);
		p1.add(FunctionLabel = new JLabel("Function:", JLabel.LEFT));
		FunctionLabel.setLabelFor(Function);
		p1.add(VarLabel = new JLabel("Var:", JLabel.LEFT));
		VarLabel.setLabelFor(Var);
		p1.add(StartLabel = new JLabel("Start Point:"),
				Component.LEFT_ALIGNMENT);
		StartLabel.setLabelFor(StartPoint);
		p1.add(EndLabel = new JLabel("End Point:"), Component.LEFT_ALIGNMENT);
		EndLabel.setLabelFor(EndPoint);
		p1.add(Function = new JTextField(20));
		p1.add(Var = new JTextField(20));
		p1.add(StartPoint = new JTextField(20));
		p1.add(EndPoint = new JTextField(20));
		
	
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(p1, BorderLayout.CENTER);
		
		Error = new JTextField();
		p.add(Error,BorderLayout.SOUTH);
		Error.setBackground(Color.WHITE);
		p.add(Create = new JButton("Create"), BorderLayout.EAST);
		p.setBackground(Color.WHITE);
		Create.setBackground(Color.BLACK);
		Create.setForeground(Color.WHITE);
		Error.setEditable(false);
		add(p);
		// Add Listeners
		Create.addActionListener(new ListenToCreate());

	}

	/**
	 * @author Tyler
	 *Executes when create button is pushed creates graph of entered function on the interval entered
	 */
	class ListenToCreate implements ActionListener , Runnable{
		public void actionPerformed(ActionEvent e) {
			startThread();
		}
			public void run(){
			JFrame f = new JFrame("Graph Window");
			
			try{
				try{
					Poly p = new Poly(Function.getText());
					
					Float.parseFloat(p.toString());
					constants = true;
					
					
				}catch(Exception e1){
					
					if(!Function.getText().contains(Var.getText())){
						
						throw new Exception("ERROR: Variable entered must match variables in the function");
						
					}
				}
				if(Function.getText().equals("")||Var.getText().equals("")||EndPoint.getText().equals("")||StartPoint.getText().equals("")){
					throw new Exception("ERROR: Please make sure all fields are filled out");
				}
				
				if(Integer.parseInt(StartPoint.getText())>=Integer.parseInt(EndPoint.getText())){
					throw new Exception("ERROR: Start Point must be less than Endpoint");
				}
			Poly test = new Poly(Function.getText(),Var.getText());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gw = new GraphWindow(StartPoint.getText(),
					EndPoint.getText(), Function.getText(),Var.getText());
			if(constants == true){
				gw.constant = true;
			}
			JPanel p1 = new JPanel();
			p1.setLayout(new GridLayout(2, 3));
			p1.add(FunctionText = new JTextField());
			
			FunctionText.setEditable(false);
			FunctionText.setForeground(Color.RED);
			Poly p = null;
			try {
				p = new Poly(Function.getText(),Var.getText());
			} catch (PolyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (OperatorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Font theFont1 = new Font("SansSerif",Font.BOLD,2);
			p1.setFont(theFont1);
			FunctionText.setText("F("+Var.getText()+") = " + p.toString());
			p1.add(Function2Text = new JTextField());
			Function2Text.setForeground(Color.MAGENTA);
			Poly diff = new Poly(p.diff());
			diff.variable = Var.getText();
			Function2Text.setText("F'("+Var.getText()+") = " +diff.toString());
			Function2Text.setEditable(false);
			p1.add(Function3Text = new JTextField());
			Function3Text.setForeground(Color.BLUE);
			Poly diff2 = new Poly(diff.diff());
			diff2.variable = Var.getText();
			Function3Text.setText("F''("+Var.getText()+") = "+(diff2.toString()));
			Function3Text.setEditable(false);
			p1.add(Zoomout = new JButton("Zoom Out"));
			p1.add(Error = new JTextField());
			Error.setBackground(Color.WHITE);
			Error.setEditable(false);
			p1.add(Zoomin = new JButton("Zoom in"));
			Zoomin.setBackground(Color.BLACK);
			Zoomin.setForeground(Color.WHITE);
			Zoomout.setForeground(Color.WHITE);
			Zoomout.setBackground(Color.BLACK);
			Zoomout.addActionListener(new ListentoZoomout());
			Zoomin.addActionListener(new ListentoZoomin());
			f.add(gw);
			f.add(p1,BorderLayout.SOUTH);
			f.setSize(900, 700);
			f.setResizable(false);
			f.setVisible(true);
			}catch(Exception e1){
				Error.setText(e1.getMessage());
			}
			
		
		
		}
			public void startThread(){
				Thread thread = new Thread(this);
				thread.start();
			}
	}
	
	/**
	 * @author Tyler
	 *executes when the user clicks Zoomout button, view zooms outs
	 */
	class ListentoZoomout implements ActionListener, Runnable{
		public void actionPerformed(ActionEvent e) {
			startThread();
		}
			public void run(){
			gw.scale = gw.scale*1.25;
			gw.repaint();
		}
			public void startThread(){
				Thread thread = new Thread(this);
				thread.start();
			}
		}
	/**
	 * @author Tyler
	 *executes when the user clicks Zoomin button, view zooms in
	 */
	class ListentoZoomin implements ActionListener, Runnable{
		public void actionPerformed(ActionEvent e) {
			startThread();
			}
				public void run(){
				gw.scale = gw.scale/1.25;
				gw.repaint();
			}
				public void startThread(){
					Thread thread = new Thread(this);
					thread.start();
				}
	}

	public static void main(String[] args) throws Exception {
		GraphCalc calc = new GraphCalc();
		calc.pack();
		calc.setLocationRelativeTo(null);
		calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calc.setVisible(true);

	}

}