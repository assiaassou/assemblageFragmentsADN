package alignementEtConsensus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import main.utilitaires.Utilitaires;

/**
 * ok ok ^_^
 * 
 * @author bellafkih
 *
 */
public class AlignementMultipleArrayList {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<char[][]> listAlignement = new ArrayList<char[][]>();
		// char[][][] alignements = { al4, al3 };
		Utilitaires u = new Utilitaires();
		char[][] all1 = { { 'a', 'a', 'a', 'a', 'a' }, { '.', '.', 'b', '-', 'b' } };
		char[][] all11 = { { 'a', 'a', 'a', 'a' }, { '.', '.', 'b', 'b' } };

		char[][] all2 = { { 'b', '-', 'b' }, { 'b', 'c', 'b' } };
		char[][] all22 = { { 'b', 'b' }, { 'b', 'c' } };

		char[][] all3 = { { '.', '.', '.', 'b', 'c', 'b' }, { 'a', 'a', 'a', 'b', '-', 'b' } };
		char[][] all33 = { { '.', 'b', 'c', 'b' }, { 'a', 'b', '-', 'b' } };
		char[][] all5 = { { 'c', 'c', '-', 'c' }, { '.', 'c', 'c', 'c' } };

		// la liste des alignements
		// char[][][] alignements = { all2, all3, all4, all5 };
		listAlignement.add(all1);
		listAlignement.add(all22);
		// listAlignement.add(all3);
		// listAlignement.add(all4);
		// listAlignement.add(all5);

		// voteMajorite(consensus(listAlignement));
		u.displayArrayLists(consensus(listAlignement));
	}

	public static ArrayList<ArrayList<Character>> consensus(ArrayList<char[][]> listAlignement) throws IOException {
		Utilitaires u = new Utilitaires();
		int count = 0;
		int cols, cols_, lines, lines_;
		char[][] matrix = null;
		ArrayList<Character> lastLineMatrix = new ArrayList<Character>();
		ArrayList<ArrayList<Character>>  matriceConsensus = new ArrayList<ArrayList<Character>>();
		for (char[][] currentAlignement : listAlignement) {
			ArrayList<Character> align0 = u.convertCharArrayToCharacterArray(currentAlignement[0]);
			ArrayList<Character> align1 = u.convertCharArrayToCharacterArray(currentAlignement[1]);
			// ArrayList<ArrayList<Character>> alignement = new
			// ArrayList<ArrayList<Character>>();
			// alignement.add(align0);
			// alignement.add(align1);
			// u.displayArrayLists(alignement);

			int positionFirstSymboletPrecedent, pfsss;
			int currAliCols = currentAlignement[0].length;
			if (count == 0) { // c'est le premier alignement
				matriceConsensus.add(align0);
				matriceConsensus.add(align1);
				count++;
				lastLineMatrix = align1;
				// System.out.println("je m'execute une seule fois ^_^");

			} else {

				positionFirstSymboletPrecedent = 0;
				while (lastLineMatrix.get(positionFirstSymboletPrecedent) == '.') {
					positionFirstSymboletPrecedent++;
				}
				// u.displayLine(lastLineMatrix);

				if (currentAlignement[0][0] != '.') {

					for (int j = 0; j < positionFirstSymboletPrecedent; j++) {
						align0.add(0, '.');
						align1.add(0, '.');
					}
					matriceConsensus.add(align0);
					matriceConsensus.add(align1);
				} // fin if alligne commence pas par '.'
				if (currentAlignement[0][0] == '.') {
					int decalage;
					pfsss = 0;
					while (currentAlignement[0][pfsss] == '.') {
						pfsss++;
					}
					decalage = positionFirstSymboletPrecedent - pfsss;
					if (decalage < 0) {
						int decalageAbs = Math.abs(decalage);

						for (ArrayList<Character> line : matriceConsensus) {

							for (int j = 0; j < decalageAbs; j++) {
								line.add(0, '.');
							}
						}
						matriceConsensus.add(align0);
						matriceConsensus.add(align1);

					} // fin if decalage <0
					else {
						for (int i = 0; i < decalage; i++) {
							align0.add(0, '.');
							align1.add(0, '.');
						}
						matriceConsensus.add(align0);
						matriceConsensus.add(align1);

					} // fin if decalage >=0

				} // fin if alligne commence par '.'
					// System.out.println("avant injection du gap");
					// new Utilitaires().displayMatrix(matrix);

				// Injection des gaps
				// lines = matrix.length;
				// cols = matrix[0].length;
				// char[] sCurrentAlign = matrix[lines - 2];
				// int posGap;
				// // new Utilitaires().displayLine(sCurrentAlign);
				// for (int i = 0; i < currAliCols; i++) {
				// if (sCurrentAlign[i] == '-') {
				// posGap = i;
				// // System.out.println("Hello wold i am a gap in
				// // "+posGap);
				// int j = 0;
				// for (int s = 0; s < lines - 2; s++) {
				// if (matrix[s][cols - 1] == '.') {
				// j++;
				// }
				// }
				//
				// char[][] matrix_;
				// if (j == lines - 2) {
				// // System.out.println("##############################rani
				// // hna");
				// matrix_ = new char[lines][cols];
				// for (int ii = 0; ii < lines - 2; ii++) {
				// for (int jj = 0; jj < posGap; jj++) {
				// matrix_[ii][jj] = matrix[ii][jj];
				// }
				// matrix_[ii][posGap] = '-';
				// for (int jj = posGap + 1; jj < cols; jj++) {
				// matrix_[ii][jj] = matrix[ii][jj - 1];
				// // System.out.println(
				// // "element matrix[" + ii + "][" + (jj - 1)
				// // + "]" + matrix[ii][jj - 1]);
				// }
				// }
				// matrix_[lines - 1] = matrix[lines - 1];
				// matrix_[lines - 2] = matrix[lines - 2];
				// } else {
				//
				// matrix_ = new char[lines][cols + 1];
				// for (int ii = 0; ii < lines - 2; ii++) {
				// for (int jj = 0; jj < posGap; jj++) {
				// matrix_[ii][jj] = matrix[ii][jj];
				// }
				// matrix_[ii][posGap] = '-';
				// for (int jj = posGap + 1; jj < cols + 1; jj++) {
				// matrix_[ii][jj] = matrix[ii][jj - 1];
				// }
				// }
				// System.arraycopy(matrix[lines - 1], 0, matrix_[lines - 1], 0,
				// matrix[lines - 1].length);
				// matrix_[lines - 1][cols] = '.';
				// System.arraycopy(matrix[lines - 2], 0, matrix_[lines - 2], 0,
				// matrix[lines - 2].length);
				// matrix_[lines - 2][cols] = '.';
				//
				// }
				// matrix = matrix_;
				//
				// } // fin si egale '-'
				//
				// }

				// Injection des gaps
				//System.out.println("Injection des gaps");

				int ind = 0;
				int size = matriceConsensus.size();
				Character t = matriceConsensus.get(size - 3).get(ind);
				Character s = matriceConsensus.get(size - 2).get(ind);
				// while( !t.equals(null) && !s.equals(null)){

				while (t != null && s != null) {
					// u.displayArrayLists(matrice);
					if (s.equals('-') && t.equals('-')) {

					} else if (s.equals('-')) {

						for (int k = 0; k < size - 2; k++) {
							int taille = matriceConsensus.get(k).size();
							if (ind < taille) {
								matriceConsensus.get(k).add(ind, '-');
							}
						}

					} else if (t.equals('-')) {
						for (int i = size - 2; i < size; i++) {
							if (matriceConsensus.get(i).get(ind).equals('.')) {
								matriceConsensus.get(i).add(ind, '.');
							} else {
								matriceConsensus.get(i).add(ind, '-');
							}
						}
					}

					ind++;
					if (ind < matriceConsensus.get(size - 3).size()) {
						t = matriceConsensus.get(size - 3).get(ind);
					} else {
						t = null;
					}
					if (ind < matriceConsensus.get(size - 2).size()) {
						s = matriceConsensus.get(size - 2).get(ind);
					} else {
						s = null;
					}
					// u.displayArrayLists(matrice);
				}
				lastLineMatrix = matriceConsensus.get(matriceConsensus.size() - 1);

				int removedIndex = matriceConsensus.size() - 2;
				matriceConsensus.remove(removedIndex);

			} // fin count
				// System.out.println("apres injection du gap");
				// new Utilitaires().displayMatrix(matrix);
				// u.displayArrayLists(matrice);
				// System.out.println("#######################################################");

			//u.displayArrayLists(matrice);
		} // fin for alignements
		return matriceConsensus;
	}

	public static char[] voteMajorite(ArrayList<ArrayList<Character>> consensus) throws IOException {
		Integer[] tab = { 0, 0, 0, 0, 0, 0 };
		Map<Integer, Integer[]> positionsDetails = new HashMap<Integer, Integer[]>();

		for (ArrayList<Character> line : consensus) {
			Integer col = 0;
			for (char acide : line) {
				if (positionsDetails.containsKey(col)) {
					switch (acide) {
					case 'a':
						positionsDetails.get(col)[0]++;
						break;
					case 'c':
						positionsDetails.get(col)[1]++;
						break;
					case 'g':
						positionsDetails.get(col)[2]++;
						break;
					case 't':
						positionsDetails.get(col)[3]++;
						break;
					case '.':
						positionsDetails.get(col)[4]++;
						break;
					case '-':
						positionsDetails.get(col)[5]++;
						break;

					default:
						break;
					}

				} else {
					switch (acide) {
					case 'a':
						positionsDetails.put(col, new Integer[] { 1, 0, 0, 0, 0, 0 });
						break;
					case 'c':
						positionsDetails.put(col, new Integer[] { 0, 1, 0, 0, 0, 0 });
						break;
					case 'g':
						positionsDetails.put(col, new Integer[] { 0, 0, 1, 0, 0,0 });
						break;
					case 't':
						positionsDetails.put(col, new Integer[] { 0, 0, 0, 1, 0, 0 });
						break;
					case '.':
						positionsDetails.put(col, new Integer[] { 0, 0, 0, 0, 1, 0 });
						break;
					case '-':
						positionsDetails.put(col, new Integer[] { 0, 0, 0, 0, 0, 1 });
						break;

					default:
						break;
					} // fin switch
				} // fin if
				col++;
			} // fin for fin parcour de la ligne

		} // fin parcour des

		// positionsDetails.put(i, new HashMap<Character, Integer>());
		// for (Character acide : consensus.get(i)) {
		//
		// if (positionsDetails.get(i).keySet().contains(acide)) {
		// positionsDetails.get(i).put(acide,
		// positionsDetails.get(i).get(acide) + 1);
		// } else {
		// positionsDetails.get(i).put(acide, 0);
		// }
		//
		// }

		char[] consensusChaine = new char[positionsDetails.size()];
		int j = 0, indexPossibilite;
		for (Map.Entry<Integer, Integer[]> entry : positionsDetails.entrySet()) {
			// System.out.println("Key : " + entry.getKey() + " Value : " +
			// entry.getValue());
			// ArrayList<Integer>=new ArrayList<Integer>();

			int maxValue = entry.getValue()[0], i;

			for (i = 1; i < entry.getValue().length && i!=4; i++) {
				if (entry.getValue()[i] > maxValue) {
					maxValue = entry.getValue()[i];
				}
			}
		//	System.out.println("Key : " + entry.getKey() + " Value : ");

			for (Integer e : entry.getValue()) {
				//System.out.println(e);
			}

			ArrayList<Integer> possibilite = new ArrayList<Integer>();

			for (int m = 0; m < entry.getValue().length; m++) {

				if (entry.getValue()[m] == maxValue) {
					possibilite.add(m);

				}
			}
			//System.out.println("possibilite " + possibilite.size());

			if (possibilite.size() == 1) {
				indexPossibilite = possibilite.get(0);
			} else {
				//indexPossibilite = 5;
				indexPossibilite = new Random().nextInt(possibilite.size() - 1);

			}

			switch (indexPossibilite) {
			case 0:
				consensusChaine[j] = 'a';

				break;
			case 1:
				consensusChaine[j] = 'c';
				break;
			case 2:
				consensusChaine[j] = 'g';
				break;
			case 3:
				consensusChaine[j] = 't';
				break;
			case 4:
				consensusChaine[j] = '.';
				break;
			case 5:
				consensusChaine[j] = '-';
				break;

			default:
				break;
			}
			//System.out.println(consensusChaine[j] + "!!!!!!!!!!!!!!!!");
			j++;
		}

		//
		// for (int i = 0; i < cols; i++) {
		// Map<Character, Integer> hashChars = new HashMap<Character,
		// Integer>();
		// Set<Character> keys = hashChars.keySet();
		// for (int j = 0; j < lines; j++) {
		// if (matrix[j][i] != '.') {
		// if (keys.contains(matrix[j][i])) {
		// hashChars.put(matrix[j][i], (hashChars.get(matrix[j][i]) + 1));
		// } else {
		// hashChars.put(matrix[j][i], 1);
		// }
		// }
		// }
		// Integer maxWeight = Collections.max(hashChars.values());
		// ArrayList<Character> result = new ArrayList<Character>();
		//
		// Set<Character> keys_ = hashChars.keySet();
		// for (Character key : keys_) {
		// if (hashChars.get(key).equals(maxWeight)) {
		// result.add(key);
		// }
		// }
		// // System.out.println("has table " + hashChars.keySet().toString() +
		// // " " + hashChars.values().toString());
		// // System.out.println("resultats pour la colonne courante " +
		// // result.toString());
		// if (result.size() == 1) {
		// consensus[i] = result.get(0);
		// } else {
		// consensus[i] = result.get(new Random().nextInt(result.size() - 1));
		// }
		// }
		// new Utilitaires().displayLine(consensus);
		//System.out.println("###################" + consensusChaine.length);
		//new Utilitaires().displayLine(consensusChaine);
		return consensusChaine;
	}

	public static int getMax(Integer[] inputArray) {
		int maxValue = inputArray[0];
		for (int i = 1; i < inputArray.length; i++) {
			if (inputArray[i] > maxValue) {
				maxValue = inputArray[i];
			}
		}
		return maxValue;
	}

}
