
public class Reguly {
	String regula;
	boolean alive[];
	boolean born[];
	
	public Reguly() {
		regula = "23/3";
		alive = new boolean[] {false, false, true, true, false, false, false, false, false}; 
		born = new boolean[] {false, false, false, true, false, false, false, false, false}; 
}
	
	public Reguly(String str) {
		regula = str;
		boolean terazborn = false;
		
		alive = new boolean[9];
		born = new boolean[9];
		for (int i=0; i<str.length(); i++)
		{
			char ch = str.charAt(i);
			if (ch == '/')
				terazborn = true;
			else if (ch >= '0' && ch <= '9')
				if (terazborn)
					born[ch-'0'] = true;
				else
					alive[ch-'0'] = true;
		}
	}
	
	public int nextstep(int prev, int neighbours) {
		if (prev != 0)
			return alive[neighbours] ? 1:0;
		else
			return born[neighbours] ? 1:0;
	}

}
