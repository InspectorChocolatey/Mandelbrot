package com.nicholasjacquet.graphics;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

/**
  * 
  * @author Nicholas
  *
  *	Notes:
  *
  *		What events are there within this program?
  *
  *		java.awt.image
  *			-
  *
  *
  **/

public class MandelBrot extends JFrame
{
	
	static final int WIDTH  = 900;
	static final int HEIGHT = 900;
	
	Canvas canvas;
	BufferedImage fractalImage;
	static final int MAX_ITER = 600;
	static final double DEFAULT_ZOOM       =  500.0;
	static final double DEFAULT_TOP_LEFT_X =  -2.25;                    // -2.0
	static final double DEFAULT_TOP_LEFT_Y =  1.1;                        // 1.5
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
		doIt();
	}
	// default Contructor	

	final Frame f = new Frame("DeviceIdentificationTest");

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
		
		/* AWT fullscreen */
		final Frame fsf = new Frame("FullScreen" + gd, gc) 
		{
			public void updateFractalAWT(Graphics g, int w, int h, BufferedImage bi) 
			{
				for (int x = 0; x < this.WIDTH ; x++ ) 
				{
					for (int y = 0; y < this.HEIGHT ; y++ ) 
					{
						double c_r = getXPos(x);
						double c_i = getYPos(y);
						
						int iterCount = computeIterations(c_r, c_i);
						int pixelColor = makeColor(iterCount);
						
						bi.setRGB(x, y, pixelColor);
					}
				}
				g.drawImage(bi , 0, 0, Color.black, null );
			}

			public void paint(Graphics g)
			{
				g.setColor(Color.black);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.gray );
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
			
				g.drawImage(fractalImage , 0, 0, Color.black, null );
				int i = 1;

				while(true)
				{
					try 
					{
						TimeUnit.SECONDS.sleep(1);
						//zoomFactor = zoomFactor + 50;
						
						//g.drawImage(fractalImage , 0, 0, Color.black, null );
						//updateFractalAWT(getGraphics(), getWidth(), getHeight(), fractalImage );
					} 
					catch (InterruptedException e) 
					{
						
					}
					++i;
				}
				
			}
		};

		fsf.setUndecorated(true);

		fsf.addMouseListener(
		
				//new MouseAdapter() 
				new MouseInputAdapter()
				{
					
					public void updateFractalAWT2(Graphics g, int w, int h, BufferedImage bi) 
					{
						for (int x = 0; x < bi.getWidth() ; x++ ) 
						{
							for (int y = 0; y < bi.getHeight() ; y++ ) 
							{
								double c_r = getXPos(x);
								double c_i = getYPos(y);
								
								int iterCount = computeIterations(c_r, c_i);
								int pixelColor = makeColor(iterCount);
								
								bi.setRGB(x, y, pixelColor);
							}
						}
						g.drawImage(bi , 0, 0, Color.black, null );
					}

					private void adjustZoom( double newX, double newY, double newZoomFactor, BufferedImage bi ) 
					{
						topLeftX += newX/zoomFactor;
						topLeftY -= newY/zoomFactor;
						zoomFactor = newZoomFactor;
						topLeftX -= ( WIDTH/2) / zoomFactor;
						topLeftY += (HEIGHT/2) / zoomFactor;
						updateFractalAWT2(getGraphics(), bi.getWidth(), bi.getHeight(), bi );
					}

					public void mouseClicked(MouseEvent e) 
					{
						gd.setFullScreenWindow(null);
						//fsf.dispose();
						double x = (double) e.getX();
						double y = (double) e.getY();
						
						switch( e.getButton() ) 
						{
							// Left
							case MouseEvent.BUTTON1:
								adjustZoom( x, y, zoomFactor*2, fractalImage  );
								break;
							
							// Right
							case MouseEvent.BUTTON3:
								adjustZoom( x, y, zoomFactor/2, fractalImage  );
								break;
						}
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
