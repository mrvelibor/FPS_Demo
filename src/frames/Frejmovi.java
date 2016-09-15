package frames;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frejmovi extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4827166097174919367L;
	
	private Ploca pl;
	private Button kreni, stani, info,
				   kuglica, pozadina,
				   sporije, brze;
	
	
	private void popuniPr() {
		setLayout(new BorderLayout());
		pl = new Ploca(new Krug(Color.darkGray, 80));
		add(pl, BorderLayout.PAGE_START);
		
		Panel p1 = new Panel();
		p1.setLayout(new GridLayout(1, 3));
		p1.setPreferredSize(new Dimension(getPreferredSize().width/3, 20));
		p1.add(new Label("15 fps", Label.CENTER));
		p1.add(new Label("30 fps", Label.CENTER));
		p1.add(new Label("60 fps", Label.CENTER));
		add(p1, BorderLayout.CENTER);
		
		Panel p2 = new Panel();
		p2.add(kreni = new Button("Kreni"));
		kreni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pl.kreni();
				kreni.setEnabled(false); stani.setEnabled(true);
			}
		});
		p2.add(sporije = new Button("-"));
		sporije.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pl.sporije();
			}
		});
		p2.add(brze = new Button("+"));
		brze.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pl.brze();
			}
		});
		p2.add(info = new Button("i"));
		info.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pl.pokaziInfo();
			}
		});
		p2.add(kuglica = new Button("*"));
		kuglica.addActionListener(new ActionListener() {
			private int i = 0;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pl.obojKuglicu(i++);
			}
		});
		p2.add(pozadina = new Button("#"));
		pozadina.addActionListener(new ActionListener() {
			private int i = 0;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pl.obojPozadinu(i++);
			}
		});
		p2.add(stani = new Button("Stani"));
		stani.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pl.stani();
				stani.setEnabled(false); kreni.setEnabled(true);
			}
		});
		stani.setEnabled(false);
		add(p2, BorderLayout.PAGE_END);
	}
	
	public Frejmovi() {
		super("Frejmovi");
		setBounds(100, 100, 0, 0);
		popuniPr();
		podesi();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e)
			{ pl.postaviVel(getSize().width-20, getSize().height-110); }
		});
		addWindowListener(new WindowAdapter() {			
			@Override
			public void windowClosing(WindowEvent e)
			{ pl.zavrsi(); dispose(); }
		});
		setVisible(true);
	}
	
	public void podesi()
	{ setSize(pl.getPreferredSize().width+20, pl.getPreferredSize().height+110); }
	
	public static void main(String[] argc)
	{ new Frejmovi(); }
}
