package frames;

import java.awt.Color;
import java.awt.Graphics;

public class Krug extends Simbol {

	public Krug(Color c, int r)
	{ super(c, r); }

	
	@Override
	public void crtaj(Graphics g) {
		g.setColor(boja);
		g.fillOval(5, koraci*velicina/60, velicina, velicina);
	}
}
