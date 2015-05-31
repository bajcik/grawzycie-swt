/**
 * 
 */


/**
 * @author bajcik
 *
 */
public class Plansza {
	public int plansza[][];
	Reguly reguly;
	int w,h;
	int gens;
	
	/**
	 * 
	 */
	public Plansza(int w, int h) {
		// TODO Auto-generated constructor stub
		plansza = new int[w][h];
		reguly = new Reguly();
		this.w = w;
		this.h = h;
		this.gens = 0;
	}
	
	
	
	public void zeruj() {
		plansza = new int[w][h];
	}
	
	
	/**
	 * wypełnia losowo prostokąt na planszy
	 * @param x0 (x0,y0) - lewy górny róg prostokąta
	 * @param y0
	 * @param w (w,h) - wymiary prostokąta
	 * @param h
	 * @param gestosc gęsto (gestosc>1) lub rzadko (gestosc<1)
	 */
	public void losuj(int x0, int y0, int w, int h, double gestosc)
	{
		for (int x=x0; x<x0+w; x++)
			for (int y=y0; y<y0+h; y++)
				plansza[x][y] = 
					(java.lang.Math.random()*gestosc > 0.5) ? 1:0;
	}
	
	public void losuj(double gestosc)
	{
		losuj(0,0,w,h,gestosc);
	}
	
	
	public void NextGen(int n) {
		/* ile zadanych pokoleń */
		for (int gen=0; gen<n; gen++) {
			
			/* plansza zastepcza */
			int plansza2[][] = new int[w][h];
			
			/* wiersze i kolumny */
			for (int x=0; x<w; x++)
				for (int y=0; y<h; y++) {
					/* liczymy sasiadow */
					int nei = 0;
					if (x > 0 && y > 0) nei += plansza[x-1][y-1];
					if (x > 0          ) nei += plansza[x-1][y];
					if (x > 0 && y<h-1) nei += plansza[x-1][y+1];
					if (         y<h-1) nei += plansza[x][y+1];
					if (x<w-1 && y<h-1) nei += plansza[x+1][y+1];
					if (x<w-1         ) nei += plansza[x+1][y];
					if (x<w-1 && y > 0) nei += plansza[x+1][y-1];
					if (         y > 0) nei += plansza[x][y-1];
					
					plansza2[x][y] = reguly.nextstep(plansza[x][y], nei);
				}
			
			/* podmiana plansz */
			plansza = plansza2;
		}
		gens += n;
	}

}
