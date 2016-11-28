package rechercheDesChevauchements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ResultatProgDynam {

	
	public ResultatProgDynam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResultatProgDynam(
			int[][] a,
			Map<PositionScore, ArrayList<PositionScore>> mapPositionsCasesEtSources) {
		super();
		this.a = a;
		this.mapPositionsCasesEtSources = mapPositionsCasesEtSources;
	}

	//matrice de programmation dynamique 
	private int[][] a;

	// Map contient la position de chaque case ds la matrice de programmation dynamique et positions de ses sources
	private Map<PositionScore, ArrayList<PositionScore>  > mapPositionsCasesEtSources=new HashMap<PositionScore, ArrayList <PositionScore> >();

	public int[][] getA() {
		return a;
	}

	public void setA(int[][] a) {
		this.a = a;
	}

	public Map<PositionScore, ArrayList<PositionScore>> getMapPositionsCasesEtSources() {
		return mapPositionsCasesEtSources;
	}

	public void setMapPositionsCasesEtSources(
			Map<PositionScore, ArrayList<PositionScore>> mapPositionsCasesEtSources) {
		this.mapPositionsCasesEtSources = mapPositionsCasesEtSources;
	}

}
