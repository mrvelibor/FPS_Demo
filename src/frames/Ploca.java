package frames;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Transparency;

import javax.swing.JOptionPane;

public class Ploca extends Panel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2639798130792910996L;
	
	private static final Color[] boje = {
										Color.white,
										Color.lightGray,
										Color.gray,
										Color.darkGray,
										Color.black,
										Color.blue,
										Color.cyan,
										Color.green,
										Color.magenta,
										Color.red,
										Color.pink,
										Color.yellow
										};
	
	private Thread nit = new Thread(this);
	private boolean radi = false;

	private boolean gore;
	
	private Prikaz[] prik;
	private Simbol sim;
	private Merac mer;
	
	
	public class Merac {
		public int frejm;
		public long pocetak;
		public double trajanje, fps;
	}
	
	
	private class Prikaz extends Canvas {
		/**
		 * 
		 */
		private static final long serialVersionUID = 805859678636339207L;
		
		private Graphics bafer;
		private Image slika;
		
		public Prikaz() {
			podesi();
			setBackground(Color.lightGray);
		}
		
		private void podesi() {
			Dimension dim = new Dimension(sim.getSize()+10, sim.getSize()*3);
			setPreferredSize(dim);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gs = ge.getDefaultScreenDevice(); 
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			slika = gc.createCompatibleImage(dim.width, dim.height, Transparency.OPAQUE);
			bafer = slika.getGraphics();
		}
		
		public void postaviVel(int width, int height) {
			sim.setSize(width-10);
			podesi();
		}
		
		@Override
		public void paint(Graphics g) {
			bafer.setColor(getBackground());
			bafer.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
			sim.crtaj(bafer);
			g.drawImage(slika, 0, 0, this);
		}
		
		@Override
		public void update(Graphics g)
		{ paint(g); }
	}
	
	
	private void popuniPl() {
		podesi();
		setLayout(new GridLayout(1, 3));
		for(Prikaz p : prik)
			add(p);
	}
	
	private void podesi()
	{ setPreferredSize(new Dimension(prik[0].getPreferredSize().width*3, prik[0].getPreferredSize().height)); }
	
	public void postaviVel(int width, int heigth) {
		for(Prikaz p : prik)
			p.postaviVel(width/3, heigth);
		podesi();
	}
	
	public Ploca(Simbol s) {
		mer = new Merac();
		sim = s;
		prik = new Prikaz[3];
		for(int i = 0; i < 3; i++)
			prik[i] = new Prikaz();
		popuniPl();
		nit.start();
	}
	
	
	public void obojPozadinu(int i) {
		for(Prikaz p : prik)
			p.setBackground(boje[i%boje.length]);
		if(!radi) {
			prik[0].repaint();
			prik[1].repaint();
			prik[2].repaint();
		}
	}
	
	public void obojKuglicu(int i) {
		sim.setColor(boje[i%boje.length]);
		if(!radi) {
			prik[0].repaint();
			prik[1].repaint();
			prik[2].repaint();
		}
	}
	
	public void brze()
	{ sim.incStep(); }
	
	public void sporije()
	{ sim.decStep(); }

	
	private void pomeri() {
		if(gore) {
			sim.goUp();
			if(sim.getHeight() < 0)
				gore = !gore;
		}
		else {
			sim.goDown();
			if(sim.getHeight()+sim.getSize() > getSize().height)
				gore = !gore;
		}
		
	}
	
	
	public void pokaziInfo() {
		int frejm = mer.frejm;
		double trajanje, fps;
		if(radi)
			trajanje = (double)((System.nanoTime()-mer.pocetak))/1000000000;
		else trajanje = mer.trajanje;
		fps = (double)(Math.round(frejm/trajanje*100)) / 100;
		JOptionPane.showMessageDialog(null, "Trajanje: " + trajanje + 
				   							" s\nBroj Frejmova: " + frejm +
				   							"\nFPS: " + fps,
				   					  "Info", JOptionPane.PLAIN_MESSAGE);
	}
	
	
	public synchronized void kreni() {
		radi = true;
		mer.frejm = 0;
		mer.pocetak = System.nanoTime();
		notify();
	}
	
	public synchronized void stani() {
		radi = false;
		mer.trajanje = (double)((System.nanoTime()-mer.pocetak))/1000000000;
		mer.fps = mer.frejm/mer.trajanje;
	}

	public void zavrsi()
	{ nit.interrupt(); }

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized(this) {
					if(!radi) wait();
				}
				switch(mer.frejm++ % 4)
				{
				case 0:
					prik[0].repaint();
				case 2:
					prik[1].repaint();
				default:
					prik[2].repaint();
				}
				pomeri();
				Thread.sleep(16);
			}
		} catch (InterruptedException e) {}
	}
}
