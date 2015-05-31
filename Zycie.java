import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */

/**
 * @author bajcik
 *
 */
class Rysownik implements PaintListener {
	Plansza plansza;
	
	
	public Rysownik(Plansza plansza) {
		this.plansza = plansza;
	}
	
	public void paintControl(PaintEvent e) {
		GC gc = e.gc;
		Color blue = e.display.getSystemColor(SWT.COLOR_BLUE);
		gc.setBackground(blue);
		for (int x=0; x<plansza.w; x++)
			for (int y=0; y<plansza.h; y++)
				if (plansza.plansza[x][y] == 1)
				{
					gc.fillRectangle(x*5, y*5, 3, 3);
					gc.drawRectangle(x*5, y*5, 3, 3);
				}
	}
}

class Zegareczek implements Runnable {
	Canvas canvas;
	Plansza plansza;
	int miliseconds;
	int gens;
	boolean goon;
	
	public Zegareczek(Canvas canvas, Plansza plansza, int miliseconds, int gens) {
		this.canvas = canvas;
		this.miliseconds = miliseconds;
		this.plansza = plansza;
		this.gens = gens;
		this.goon = true;
	}
	
	public void run()
	{
		if (!goon)
			return;
		plansza.NextGen(gens);
		canvas.redraw();
		canvas.getDisplay().timerExec(miliseconds, this);
	}
	
	public void stop() {
		goon = false;
	}
	
}


public class Zycie {
	public static void main(String[] args) {
		/* plansza */
		Plansza plansza = new Plansza(250,250);
		plansza.reguly = new Reguly("23/3");
		plansza.losuj(1.2);
		plansza.losuj(125,130,5,5, 1.2);
		//plansza.NextGen(2);
		
		/* grafika */
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Gra w życie");
		Canvas canvas = new Canvas(shell, SWT.BORDER);
		canvas.setBounds(5, 5, 700, 500);
		canvas.addPaintListener(new Rysownik(plansza));
		
		/* obsługa kroków */
		canvas.setData("plansza", plansza);
		canvas.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				((Plansza)e.widget.getData("plansza")).NextGen(1);
				((Canvas)e.widget).redraw();
				
			}
			public void mouseUp(MouseEvent e) {
				
			}
			public void mouseDoubleClick(MouseEvent e) {
				
			}
		});
		
		Zegareczek zegar = new Zegareczek(canvas, plansza, 100, 1);
		zegar.run();
		
		shell.open();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
				display.sleep();
		}
		zegar.stop();
		display.dispose();
	}
}
