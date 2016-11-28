package alignementEtConsensus;

public class CoupleFragments {
	private int idS;
	private int idT;
	
	public CoupleFragments() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public CoupleFragments(int idS, int idT) {
		super();
		this.idS = idS;
		this.idT = idT;
	}


	public int getIdS() {
		return idS;
	}
	@Override
	public String toString() {
		return "CoupleFragments [idS=" + idS + ", idT=" + idT + "]";
	}


	public void setIdS(int idS) {
		this.idS = idS;
	}
	
	public int getIdT() {
		return idT;
	}
	public void setIdT(int idT) {
		this.idT = idT;
	}
	
}
