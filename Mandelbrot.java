package com.nicholasjacquet.graphics;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

/**
  * @author Nicholas Jacquet
  * Notes:
  *
  *     The Mandelbrot Set is the visual representation 
  *     of an iterated function on the complex plane.
  * 
  *     ~Simple function example~
  * 
  *     INPUT -> FUNCTION -> OUTPUT
  *                f(x)
  *             f(INPUT) = OUTPUT       
  *          
  *     ~Iterated function example~
  *     
  * [Iterated] f(x) = x + 1
  *         f(2) = 2 + 1 = 3;   // from here on down the 
  *         f(3) = 3 + 1 = 4;   // OUTPUT of the function      
  *         f(4) = 4 + 1 = 5;   // is then used as INPUT
  *         f(5) = 5 + 1 = 6;   // of the next FUNCTION  
  *     
  * [Iterated] f(x) = x^2 + c             [say c is 2]
  *         f(   2) =       4 + 2 = 6
  *         f(   6) =      36 + 2 = 38
  *         f(  38) =    1444 + 2 = 1446
  *         f(1448) = 2090916 + 2 = 2090918
  *                  [OUTPUT DIVERGENCE]
  *
  * [Iterated] f(x) = x^2 + c             [say c is 0.1]
  *         f(      0.1) =               0.01 + 0.1 = 0.11
  *         f(     0.11) =             0.0121 + 0.1 = 0.1121
  *         f(   0.1121) =         0.01256641 + 0.1 = 0.11256641
  *         f(0.1256641) = 0.0126711966602881 + 0.1 = 0.112671196660288   
  *                  [OUTPUT CONVERGENCE]
  * 
  * 
  * The idea is that numbers between -2 and 0 behave differently!
  * 
  *     Negative * Negative = Positive
  *     (example:)
  *     
  *     -2 * -3 = 6
  *     -5 ^  2 = 25
  *     
  *     Adding a Negative = Subtracting
  *     (example:) 
  *     5 + -6 = 5 - 6
  * 
  * 
  * [Iterated] f(x) = x^2 + c             [say c is -1.5]
  *     f(       -1.5) = 2.25              - 1.5 =  0.75
  *     f(       0.75) = 0.5625            - 1.5 = -0.9375
  *     f(    -0.9375) = 0.87890625        - 1.5 = -0.62109375
  *     f(-0.62109375) = 0.385757446289063 - 1.5 = -1.11424255371094
  * 
  * References:
  * 
  *     ~The Amazing Mandelbrot Set~
  *     https://www.youtube.com/watch?v=0YaYmyfy9Z4
  * 
  *     -2 & -1 both behave very strangely when iterated thru:
  *     
  *     [say c is -2] ~the outputs when iterating will forever be 2
  *     [say c is -1] ~the outputs when iterating will flip flop back and forth as  0 & -1
  *     
  *     
  *     
  *     
  * ~Complex Numbers also cause strange things to happen when iterated thru:    
  *     
  *     All negative numbers have positive squares:
  *         
  *             2^2 = 4  &&  -2^2 = 4
  *        
  *     (-2 & 2 are both squareroots of 4)
  *     
  *     
  *     What is the squareroot of -4? (hint: The squareroot of any negative number is an imaginary number)
  * 
  * 
  * ~IMAGINARY NUMBER is the square root of a negative number
  * ~COMPLEX   NUMBER is the sum of an IMAGINARY NUMBER and REAL NUMBER
  *     
  *   examples: 
  *   
  *     (Math.Sqrt(-4)    )  ~equals a IMAGINARY NUMBER
  *     (Math.Sqrt(-4) + 3)  ~equals a COMPLEX   NUMBER
  *     
  *     
  * So what can I do with this?    
  *     
  *               -4  = 4 * -1
  *     Math.Sqrt(-4) = Math.Sqrt(4 * -1)
  *     Math.Sqrt(-4) = Math.Sqrt(4) * Math.Sqrt(-1)
  *     Math.Sqrt(-4) = 2 * Math.Sqrt(-1)
  *     
  *     
  *=====================================================================
  *     
  *     COMPLEX  = (REAL + IMAGINARY)   
  *  
  *       example: (3 + Math.Sqrt(-4))
  * standard form: (3 + (2*(Math.Sqrt(-1)))
  *            or: (3 + 2i)                      [i = Math.Sqrt(-1)]
  *            
  *       ~i is an IMAGINARY NUMBER     
  *       
  *=====================================================================       
  *       
  *      General form of a complex number: 
  *       
  *                 A + Bi
  *     (example:)  3 + 2i         
  *                 
  *            
  **/

public class MandelBrot extends JFrame
{
	
	static final int WIDTH  = 900;
	static final int HEIGHT = 900;
	
	Canvas canvas;
	BufferedImage fractalImage;
	
	//static final int MAX_ITER = 200;
	static final int MAX_ITER = 600;
	
	static final double DEFAULT_ZOOM       = 300.0;
	static final double DEFAULT_TOP_LEFT_X = -2.0;
	static final double DEFAULT_TOP_LEFT_Y = 1.5;
	
	
	double zoomFactor = DEFAULT_ZOOM;
	double topLeftX   = DEFAULT_TOP_LEFT_X;
	double topLeftY   = DEFAULT_TOP_LEFT_Y;
	
	
	
	
	
	
	
	
	
	
	// entry point
	public static void main(String[] args) 
	{
		new MandelBrot();
	}
	// entry point
	
	
	// default Contructor
	public MandelBrot() 
	{
		setInitialGUIProperties();
		addCanvas();
		canvas.addKeyStrokeEvents();
		
		//canvas.
		
		updateFractal();
		this.setVisible(true);
	}
	// default Contructor	
	
	
	
	/***
	   *  
	   ***/
	
	
	
	final Frame f = new Frame("DeviceIdentificationTest");
	
   		

    			

    /* ActionListener */

 
	

	/***
	    * 
	    ***/
	
	
	
	
	private void addCanvas() 
	{
		canvas = new Canvas();
		fractalImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		canvas.setVisible(true);
		this.add(canvas, BorderLayout.CENTER);
	}
	
	
	private void setInitialGUIProperties() 
	{
		this.setTitle("Fractal Explorer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		//this.setSize(900, 900);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	
	private double getXPos(double x) 
	{
		return x/zoomFactor + topLeftX;
	}
	
	
	private double getYPos(double y) 
	{
		return y/zoomFactor - topLeftY;
	}
	
	public void updateFractal() 
	{
		for (int x = 0; x < WIDTH; x++ ) 
		{
			for (int y = 0; y < HEIGHT; y++ ) 
			{
				double c_r = getXPos(x);
				double c_i = getYPos(y);
				
				int iterCount = computeIterations(c_r, c_i);
				int pixelColor = makeColor(iterCount);
				
				fractalImage.setRGB(x, y, pixelColor);
			}
		}
		canvas.repaint();
	}
	
	
	
	
	/** Returns a posterized color based off of the iteration count
    of a given point in the fractal **/
	private int makeColor( int iterCount ) 
	{
		int color = 0b011011100001100101101000; 
		int mask  = 0b000000000000010101110111; 
		int shiftMag = iterCount / 13;
	
		if (iterCount == MAX_ITER) 
		{	
			return Color.BLACK.getRGB();
		}
		return color | (mask << shiftMag);
	}
	
	

	private int computeIterations(double c_r, double c_i) 
	{
		
		/*
		
		Let c = c_r + c_i
		Let z = z_r + z_i
		
		z' = z*z + c
		   = (z_r + z_i)(z_r + z_i) + (c_r + c_i)
		   = z_r² + 2*z_r*z_i - z_i² + c_r + c_i

		     z_r' = z_r² - z_i² + c_r
		     z_i' = 2*z_i*z_r + c_i
		     
		*/

		double z_r = 0.0;
		double z_i = 0.0;
		
		int iterCount = 0;

		// Modulus (distance) formula:
		// sqrt(a² + b²) <= 2.0
		// a² + b² <= 4.0
		while ( z_r*z_r + z_i*z_i <= 4.0 ) 
		{
			double z_r_tmp = z_r;
			
			z_r = z_r*z_r - z_i*z_i + c_r;
			z_i = 2*z_i*z_r_tmp + c_i;
			
			// Point was inside the Mandelbrot set
			if (iterCount >= MAX_ITER) 
			{ 
				return MAX_ITER;
			}
			
			iterCount++;
			
		}
		
		// Complex point was outside Mandelbrot set
		return iterCount;
		
	}
	
	
	
	
	private void moveUp() 
	{
		double curHeight = HEIGHT / zoomFactor;
		topLeftY += curHeight / 6;
		updateFractal();
	}
	
	private void moveDown() 
	{
		double curHeight = HEIGHT / zoomFactor;
		topLeftY -= curHeight / 6;
		updateFractal();
	}
	
	
	
	private void moveLeft() 
	{
		double curWidth = WIDTH / zoomFactor;
		topLeftX -= curWidth / 6;
		updateFractal();
	} 
	
	
	
	private void moveRight() 
	{
		double curWidth = WIDTH / zoomFactor;
		topLeftX += curWidth / 6;
		updateFractal();
	}
	
	
	
	private void doIt() 
	{
		GraphicsConfiguration gc = f.getGraphicsConfiguration();
		final GraphicsDevice gd = gc.getDevice();
		
		//System.out.println("--- Creating FS Frame on Device ---");
		//System.out.println("Device  = " + gd);
		//System.out.println(" bounds = " + gd.getDefaultConfiguration().getBounds());

		/* AWT fullscreen */
		final Frame fsf = new Frame("FullScreen" + gd, gc) 
		{
			
			//addKeyListener();
			
			public void paint(Graphics g)
			{
				g.setColor(Color.black);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.gray );
				
				//fractalImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
				fractalImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
				
				for (int x = 0; x < getWidth(); x++ ) 
				{
					for (int y = 0; y < getHeight(); y++ ) 
					{
						double c_r = getXPos(x);
						double c_i = getYPos(y);
						
						int iterCount = computeIterations(c_r, c_i);
						int pixelColor = makeColor(iterCount);
						
						fractalImage.setRGB(x, y, pixelColor);
					}
				}
				
				
				//g.drawImage(img, x, y, bgcolor, observer)
				g.drawImage(fractalImage , 200, 200, Color.black, null );
				//updateFractal();
				
				//g.draw3DRect(425, 425,50, 40, true);
				
				//Font myFont = new Font("Serif", Font.BOLD, 24);
				
				//g.setFont(myFont);
				//g.drawString("FS on device: " + gd, 200, 200);
				//g.drawString("Click to exit Full-screen.", 200, 250);
				
				int i = 1;
				
				int yLoc = 300;
				
				while(i < 10) 
				{
					yLoc = yLoc + 25;
					
					//g.drawString("Lorum ipsum quia dolor sit amet, consectetur, adipisci velit: " + i, 200, yLoc);
					
					//try 
					//{
						//TimeUnit.SECONDS.sleep(1);
					//} 
					//catch (InterruptedException e) 
					//{
						//g.drawString(e.getMessage().toString(), 200, yLoc);
					//}
					++i;
				}
				//g.drawString("Input Command: ", 200, yLoc+25);
			}
		};

		fsf.setUndecorated(true);

		fsf.addMouseListener(
		
				new MouseAdapter() 
				{
					public void mouseClicked(MouseEvent e) 
					{
						gd.setFullScreenWindow(null);
						fsf.dispose();
					}
				}
			);
		
		gd.setFullScreenWindow(fsf);
		
	}
	
	
	
	
	// MouseListener
	private class Canvas extends JPanel implements MouseListener 
	{
		private void adjustZoom( double newX, double newY, double newZoomFactor ) 
		{
			topLeftX += newX/zoomFactor;
			topLeftY -= newY/zoomFactor;
			zoomFactor = newZoomFactor;
			topLeftX -= ( WIDTH/2) / zoomFactor;
			topLeftY += (HEIGHT/2) / zoomFactor;
			updateFractal();
		}
		// MouseListener		
		
		
		
		
		public Canvas() 
		{
			addMouseListener(this);
		} 
		
		@Override public Dimension getPreferredSize() 
		{
			return new Dimension(WIDTH, HEIGHT);
			//return new Dimension(900, 900);
		} // getPreferredSize
		
		@Override public void paintComponent(Graphics drawingObj) 
		{
			drawingObj.drawImage( fractalImage, 0, 0, null );
		} // paintComponent
		
		@Override public void mousePressed(MouseEvent mouse) 
		{
			double x = (double) mouse.getX();
			double y = (double) mouse.getY();
			
			switch( mouse.getButton() ) 
			{
				// Left
				case MouseEvent.BUTTON1:
					adjustZoom( x, y, zoomFactor*2 );
					break;
				
				// Right
				case MouseEvent.BUTTON3:
					adjustZoom( x, y, zoomFactor/2 );
					break;
				
			}
			
		} // mousePressed
		
		public void addKeyStrokeEvents() 
		{
			KeyStroke wKey = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0 );
			KeyStroke aKey = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0 );
			KeyStroke sKey = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0 );
			KeyStroke dKey = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0 );
			
			//
			KeyStroke xKey = KeyStroke.getKeyStroke(KeyEvent.VK_X, 0 );
			//
			
			
			Action wPressed = new AbstractAction() 
			{
				@Override public void actionPerformed(ActionEvent e) 
				{
					moveUp();
				}
			};
			
			Action aPressed = new AbstractAction() 
			{
				@Override public void actionPerformed(ActionEvent e) 
				{
					moveLeft();
				}
			};
			
			Action sPressed = new AbstractAction() 
			{
				@Override public void actionPerformed(ActionEvent e) 
				{
					moveDown();
				}
			};
			
			Action dPressed = new AbstractAction() 
			{
				@Override public void actionPerformed(ActionEvent e) 
				{
					moveRight();
				}
			}; 
			
			
			Action xPressed = new AbstractAction() 
			{
				@Override public void actionPerformed(ActionEvent e)
    			{
    				doIt();
    			}
			}; 
			
			
			this.getInputMap().put( wKey, "w_key" );
			this.getInputMap().put( aKey, "a_key" );
			this.getInputMap().put( sKey, "s_key" );
			this.getInputMap().put( dKey, "d_key" );
			this.getInputMap().put( xKey, "x_key" ) ;
			
			this.getActionMap().put( "w_key", wPressed );
			this.getActionMap().put( "a_key", aPressed );
			this.getActionMap().put( "s_key", sPressed );
			this.getActionMap().put( "d_key", dPressed );
			this.getActionMap().put( "x_key", xPressed );
			
		} // addKeyStrokeEvents
		
		@Override public void mouseReleased(MouseEvent mouse){ }
		@Override public void mouseClicked(MouseEvent mouse) { }
		@Override public void mouseEntered(MouseEvent mouse) { }
		@Override public void mouseExited (MouseEvent mouse) { }
		
	} // Canvas


}
