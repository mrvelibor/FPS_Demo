package frames;

import java.awt.Color;
import java.awt.Graphics;

public class Kvadrat extends Simbol {

	public Kvadrat(Color c, int s)
	{ super(c, s); }
	
	@Override
	public void crtaj(Graphics g) {
		g.setColor(boja);
		g.fillRect(5, koraci*velicina/60, velicina, velicina);
	}
}
