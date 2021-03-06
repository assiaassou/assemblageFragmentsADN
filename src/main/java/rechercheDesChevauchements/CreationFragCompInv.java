package rechercheDesChevauchements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CreationFragCompInv {

	public ArrayList<FragmentADN> creationFragCompInv(String filepath)
			throws IOException {

		// lecture du fichier fasta
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String strLine, str = ""; // str:concaténation des lignes d'un fragment
		int id = 0;

		// Créer un un ArrayListe pour stocker les fragments et leurs
		// complémentaires inversés

		ArrayList<FragmentADN> listeTousFragEtCompInv = new ArrayList<FragmentADN>();

		strLine = br.readLine();
		while (strLine != null) {
			if (strLine.startsWith(">")) {
				str = "";
				strLine = br.readLine();
				while (strLine != null && (strLine.startsWith(">")) == false) {
					str = str + strLine;
					strLine = br.readLine();
				}
				id = id + 1;

				// les parametres de notre obj fragment
				char[] fr = str.toCharArray(); // creation tu tableau des
												// nucléotides
				FragmentADN fragment = new FragmentADN(id, fr); // le fragment
				FragmentADN fragCompInv = fragment.complementaire(); // le
																		// complémentaire
																		// inversé
				id = fragCompInv.getId();

				// Ajout du fragment et son complémentaire inversé dans le Map
				listeTousFragEtCompInv.add(fragment);
				listeTousFragEtCompInv.add(fragCompInv);
			}
		}
		// Close the input stream
		br.close();

		return listeTousFragEtCompInv;
	}
}
