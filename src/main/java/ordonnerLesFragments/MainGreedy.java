package ordonnerLesFragments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.classes.Arc;
import main.utilitaires.Utilitaires;

/**
 * 
 * @author bellafkih
 * 
 */
public class MainGreedy {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException ,FileNotFoundException  {
		// TODO Auto-generated method stub
		List<Integer[]> selectedArcs=null;
		HamiltonianPath ga = new HamiltonianPath("");
		//System.out.println("aaaa   "+ga.listOfSelectedArcs.size());
		ga.hameltonienPath();
		Integer[] finalPath= ga.constructFinalPath();
		List<Integer> cheminHameltonienFinale= new ArrayList<Integer>();
		
		// suppression des valeurs null ( c'est prévu qu'on a N fragment (N/2 fragment + N/2 leurs complementaires), il faut éliminer les N/2 case nulle)
		for (Integer a: finalPath){
			if (a != null){
				cheminHameltonienFinale.add(a);
				System.out.println(a);
			}
			
		}
		

	}
}
