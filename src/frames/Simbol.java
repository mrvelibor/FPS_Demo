package frames;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Simbol {
	
	protected Color boja;
	protected int velicina, koraci, korak;
	
	public Simbol(Color c, int v)
	{ boja = c; velicina = v; korak = 2; koraci = 0; }
	
	
	public int getSize()
	{ return velicina; }
	
	public void setSize(int s)
	{ velicina = s; }
	
	public int getHeight()
	{ return koraci*velicina/60; }
	
	public void setColor(Color c)
	{ boja = c; }
	
	public void incStep()
	{ if(++korak > 6) korak = 6; }
	
	public void decStep()
	{ if(--korak < 1) korak = 1; }
	
	public void goDown()
	{ koraci += korak; }
	
	public void goUp()
	{ koraci -= korak; }
	
	public abstract void crtaj(Graphics g);
}