package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import alignementEtConsensus.AlignementMultipleArrayList;
import alignementEtConsensus.CoupleFragments;
import alignementEtConsensus.ReconstitutionAlignementOpt;
import main.utilitaires.Utilitaires;
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
		Utilitaires utils = new Utilitaires();
		System.out.println("Début de la construction du chemin Hameltonien");
		HamiltonianPath ga = new HamiltonianPath(args[0]);
		ga.hameltonienPath();
		Integer[] finalPath = ga.constructFinalPath();
		ArrayList<Integer> cheminHameltonienFinale = new ArrayList<Integer>();
		long endTime = System.nanoTime();

		System.out.println("Fin de la construction du chemin Hameltonien");
		System.out.println("Durée: " + (((endTime - startTime) / 1000000) / 60) + "minutes");
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

		ArrayList<ArrayList<Character>> matrix = AlignementMultipleArrayList.consensus(listAlignement);
		System.out.println("Fin de la calcul de la matrice consensus");
		System.out.println("Durée: " + (System.nanoTime() - startTime));
		System.out.println("Debut du vote ");

		// char[] consensus= AlignementMultiple.voteMajorite(matrix);
		// utils.generateCibleFileFast(args[1], consensus, args[0]);

		char[] consensus = AlignementMultipleArrayList.voteMajorite(matrix);
		utils.generateCibleFileFast(args[1], consensus, args[0]);

		System.out.println("Fint du vote ");
		Map<String, String> argsMap = new HashMap<String, String>();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("--out")) {
				argsMap.put("--out", args[i + 1]);
			}
			if (args[i].equals("--out-ic")) {
				argsMap.put("--out-ic", args[i + 1]);
			}
		}
		argsMap.put("fragmentsFasta", args[0]);
		utils.generateCibleFileFast(argsMap.get("--out"), consensus, argsMap.get("fragmentsFasta"));

		FragmentADN f = new FragmentADN();
		f.setAcides(consensus);
		FragmentADN fc = f.complementaire();
		utils.generateCibleFileFast(argsMap.get("--out-ic"), consensus, argsMap.get("fragmentsFasta"));

		System.out.println("Durée: " + (System.nanoTime() - startTime));

	}
}