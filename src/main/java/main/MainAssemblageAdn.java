package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import alignementEtConsensus.AlignementMultiple;
import alignementEtConsensus.CoupleFragments;
import alignementEtConsensus.ReconstitutionAlignementOpt;

import ordonnerLesFragments.HamiltonianPath;
import rechercheDesChevauchements.FragmentADN;

//import ordonnerLesFragments.HamiltonianPath;
//import rechercheDesChevauchements.FragmentADN;
//import alignementEtConsensus.AlignementMultiple;
//import alignementEtConsensus.CoupleFragments;
//import alignementEtConsensus.ReconstitutionAlignementOpt;

public class MainAssemblageAdn {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();

		System.out.println("Début de la construction du chemin Hameltonien");
		HamiltonianPath ga = new HamiltonianPath(args[0]);
		ga.hameltonienPath();
		Integer[] finalPath = ga.constructFinalPath();
		ArrayList<Integer> cheminHameltonienFinale = new ArrayList<Integer>();

		long endTime = System.nanoTime();

		System.out.println("Fin de la construction du chemin Hameltonien");
		System.out.println("Durée: " + ((endTime - startTime)/1000000)+"ms");
		for (Integer a : finalPath) {
			if (a != null) {
				cheminHameltonienFinale.add(a);
				System.out.print(a + " ->");
			}
		}
		System.out.println("");

		System.out.println("Début de la construction des alignements optimaux ...");
		ArrayList<FragmentADN> listeTousFragEtCompInv = new ArrayList<FragmentADN>();
		listeTousFragEtCompInv = ga.listeTousFragEtCompInv;

		Map<CoupleFragments, char[][]> mapTousLesAlignementsOptimaux = new HashMap<CoupleFragments, char[][]>();
		ReconstitutionAlignementOpt reconstituerAlgnement = new ReconstitutionAlignementOpt(cheminHameltonienFinale,
				listeTousFragEtCompInv);

		mapTousLesAlignementsOptimaux = reconstituerAlgnement.reconstitutionAlignementOpt();

		System.out.println("Fin de la construction des alignements optimaux ...");
		System.out.println("Durée: " + (System.nanoTime() - startTime));

		int sizeChemin = cheminHameltonienFinale.size();
		ArrayList<char[][]> listAlignement = new ArrayList<char[][]>();

		System.out.println("Ordonnoncement des alignement suivant le chemin hameltonien");
		for (int i = 0; i < sizeChemin - 1; i = i + 1) {
			// loop a Map
			for (Map.Entry<CoupleFragments, char[][]> entry : mapTousLesAlignementsOptimaux.entrySet()) {
				if (entry.getKey().getIdS() == cheminHameltonienFinale.get(i)
						&& entry.getKey().getIdT() == cheminHameltonienFinale.get(i + 1)) {
					// listAlignement.add(entry.getValue());
					char[][] alig = new char[entry.getValue().length][entry.getValue()[0].length];
					alig = entry.getValue();
					listAlignement.add(alig);
				}
			}
		}
		System.out.println("Fin  Ordonnoncement des alignement suivant le chemin hameltonien");
		System.out.println("Durée: " + (System.nanoTime() - startTime));

		System.out.println("Debut de la calcul de la matrice consensus");
		char[][] matrix = AlignementMultiple.consensus(listAlignement);
		System.out.println("Fin de la calcul de la matrice consensus");
		System.out.println("Durée: " + (System.nanoTime() - startTime));
		System.out.println("Debut du vote ");
		String str = String.valueOf(AlignementMultiple.voteMajorite(matrix));
		System.out.println("Fint du vote ");
		File file = new File("/home/bellafkih/Hello1.txt");
	      
	      // creates the file
	      file.createNewFile();
	      
	      // creates a FileWriter Object
	      FileWriter writer = new FileWriter(file); 
	      
	      // Writes the content to the file
	      writer.write(str); 
	      writer.flush();
	      writer.close();


		System.out.println("Durée: " + (System.nanoTime() - startTime));

	}
}