package blak3980;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.math.BigInteger;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Tyler
 *Graphs a polynomial function on a interval
 */
public class GraphWindow extends JPanel {

	/**
	 * @param args
	 */
	String myFunction;
	String myStart;
	String myEnd;
	String var;
	Double scale = 1.15;
	Boolean constant = false;
	double[] xcords;
	double[] ycords;
	double min;
	double max = 0;
	double maxmin;
	double maxplusmin;


	/**
	 * @param start starting x position
	 * @param end	ending x position
	 * @param Function polynomial function
	 * @param variable varibale on function
	 */
	public GraphWindow(String start, String end, String Function,String variable) {
		myFunction = Function;
		myStart = start;
		myEnd = end;
		var = variable;
		setBackground(Color.WHITE);
		
	}
	/**
	 * @param other
	 * Evaluates a given function over a interval at 1000 equal sub intervals
	 */
	public void evalfunc(Poly other){
		double xCord;
		double yCord;
		Rational xTemp;
		Rational evalTime;
	
		xcords = new double[1001];
		ycords = new double[1001];
	
		Poly polynomial = null;
		polynomial = new Poly(other);
		Rational start = null;
		try {
			start = new Rational(myStart);
		} catch (RationalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Rational end = null;
		try {
			end = new Rational(myEnd);
		} catch (RationalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Rational n = null;
		try {
			n = new Rational("1/1000");
		} catch (RationalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Rational h = new Rational((end.subtract(start).multiply(n)));
		min = Double.parseDouble(String.valueOf(polynomial.evalAt(start)));
		for (int i = 0; i < 1001; i++) {
			Rational goTime = new Rational(i);
			evalTime = polynomial.evalAt(start.add(h.multiply(goTime)));
			xTemp = start.add(h.multiply(goTime));
			double numy = Double.parseDouble(String.valueOf(evalTime.getNum()));
			double deny = Double.parseDouble(String.valueOf(evalTime.getDen()));
			double numx = Double.parseDouble(String.valueOf(xTemp.getNum()));
			double denx = Double.parseDouble(String.valueOf(xTemp.getDen()));

			yCord = numy / deny;
			xCord = numx / denx;

			xcords[i] = xCord;
			ycords[i] = yCord;

			if (ycords[i] > max) {
				max = ycords[i];
			}
			if (ycords[i] < min) {
				min = ycords[i];
			}
			
		}
		System.out.println(min);
		
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.LIGHT_GRAY);
		for(int i=0;i<getWidth();i+=getWidth()/25){
			g2.drawLine(i,0,i,getHeight());
		}
		for(int i = 0;i<getHeight();i+=getHeight()/25){
			g2.drawLine(0, i, getWidth(), i);
		}
		g2.setColor(Color.BLACK);
		Font theFont1 = new Font("SansSerif",Font.PLAIN,15);
		g2.setFont(theFont1);
		Poly p = null;
		try {
			p = new Poly(myFunction,var);
		} catch (PolyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (OperatorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			
			if(this.constant == true){
				this.evalfunc(new Poly(myFunction));
				if(myFunction.contains("-")){
					maxmin = -min;
					maxplusmin = min;
					
				}else{
					maxmin = max;
					maxplusmin = max;
				}
				
			}else{
				this.evalfunc(new Poly(myFunction,var));
				maxmin = max - min;
				maxplusmin = max + min;
			}
			
		} catch (PolyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Horizontal size of a pixel in new coords.
		double myStart1 = Double.parseDouble(myStart);
		double myEnd1 = Double.parseDouble(myEnd);
		g2.translate(getWidth() / 2, getHeight() / 2);
		//g2.translate(-(myStart1 + myEnd1) / 2, ((maxplusmin) / 2) - (maxplusmin)/20);
		for (double k = myStart1; k <= myEnd1; k = k + (myEnd1 - myStart1) / 8) {
//			   g2.setColor(Color.BLACK);
//			   g2.draw(new Line2D.Double(k, (maxmin) / 60, k, -(maxmin) / 60));
			   String labelx=String.valueOf(k);
			   Float xCo=Float.parseFloat(Double.toString(k));
			   
			   g2.drawString(labelx, (float) ((float)  (xCo-((myStart1 + myEnd1) / 2))*(getWidth() / (myEnd1 - myStart1)/scale)), (float) ((float) (((maxplusmin) / 2)-((maxmin)/55))*(getHeight() / (maxmin)/scale)));
			  }
		for (double f = min; f <= max; f += (maxmin)/8) {
			   g2.setColor(Color.BLACK);
			   if(f!=0){
			   String labely=String.valueOf(f);
			   Float xCo=Float.parseFloat(Double.toString(f));
		
			   g2.drawString(labely, (float) -(((myStart1 + myEnd1) / 2)*(getWidth() / (myEnd1 - myStart1)/scale)),(float) ((float) -(xCo-((maxplusmin)/2))*((getHeight() / (maxmin)/scale))));
			   }
			   //g2.draw(new Line2D.Double(-(myEnd1-myStart1)/60, -f, (myEnd1-myStart1)/60, -f));
			  }

		Font theFont = new Font("SansSerif",Font.PLAIN,1);
		theFont = theFont.deriveFont((float) 0.5);
		g2.setColor(Color.BLACK);
		

		g2.scale((getWidth() / (myEnd1 - myStart1)/scale), (getHeight() / (maxmin)/scale));
		g2.translate(-(myStart1 + myEnd1) / 2, ((maxplusmin) / 2) - (maxplusmin)/20);
		g2.setStroke(new BasicStroke(getWidth() / 1000));
		g2.draw(new Line2D.Double(myStart1 - 0.5, 0, myEnd1 + 0.5, 0));
		if (min < 0) {
			g2.draw(new Line2D.Double(0, -min * 100, 0, -max * 100));
			
		} else {
			g2.setColor(Color.BLACK);
			g2.draw(new Line2D.Double(0, min * 100, 0, -max * 100));
		
		}
		
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(getWidth() / 1000));
		for (int i = 0; i < xcords.length; i++) {
			if (i + 1 < xcords.length) {
				g2.draw(new Line2D.Double(xcords[i], -ycords[i], xcords[i + 1], -ycords[i + 1]));
			} else {
				break;
			}
		}
		try {
			Poly derivative = new Poly(myFunction,var);
			derivative = derivative.diff();
			
			this.evalfunc(derivative);
			g2.setColor(Color.MAGENTA);
			for (int i = 0; i < xcords.length; i++) {
				if (i + 1 < xcords.length) {
					g2.draw(new Line2D.Double(xcords[i], -ycords[i], xcords[i + 1], -ycords[i + 1]));
				} else {
					break;
				}
			}
			Poly derivative2 = derivative.diff();
			this.evalfunc(derivative2);
			g2.setColor(Color.BLUE);
			for (int i = 0; i < xcords.length; i++) {
				if (i + 1 < xcords.length) {
					g2.draw(new Line2D.Double(xcords[i], -ycords[i], xcords[i + 1], -ycords[i + 1]));
				} else {
					break;
				}
			}
		} catch (PolyException | OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.evalfunc(new Poly(myFunction,var));
		} catch (PolyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (double k = myStart1; k <= myEnd1; k = k + (myEnd1 - myStart1) / 8) {
			   g2.setColor(Color.BLACK);
			   g2.draw(new Line2D.Double(k, (maxmin) / 60, k, -(maxmin) / 60));
			  }
		g2.setColor(Color.LIGHT_GRAY);
		
		for (double f = min; f <= max; f += (maxmin)/8) {
			   g2.setColor(Color.BLACK);
			   g2.draw(new Line2D.Double(-(myEnd1-myStart1)/60, -f, (myEnd1-myStart1)/60, -f));
			  }
		

	}

}
