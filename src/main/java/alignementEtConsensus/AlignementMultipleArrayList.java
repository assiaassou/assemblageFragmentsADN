package alignementEtConsensus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import main.utilitaires.Utilitaires;

/**
 *  ok ok ^_^
 * @author bellafkih
 *
 */
public class AlignementMultipleArrayList {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub  
		ArrayList<char[][]> listAlignement = new ArrayList<char[][]>();
		// char[][][] alignements = { al4, al3 };
		Utilitaires u = new Utilitaires();
		char[][] all1 = { { 'a', 'a', 'a', 'a','a' }, { '.','.', 'b', '-', 'b' } };
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
		ArrayList<ArrayList<Character>> matrice = new ArrayList<ArrayList<Character>>();
		for (char[][] currentAlignement : listAlignement) {
			ArrayList<Character> align0 = u.convertCharArrayToCharacterArray(currentAlignement[0]);
			ArrayList<Character> align1 = u.convertCharArrayToCharacterArray(currentAlignement[1]);
			// ArrayList<ArrayList<Character>> alignement = new
			// ArrayList<ArrayList<Character>>();
			// alignement.add(align0);
			// alignement.add(align1);
			// u.displayArrayLists(alignement);

			int pfstp, pfsss;
			int currAliCols = currentAlignement[0].length;
			if (count == 0) { // c'est le premier alignement
				matrice.add(align0);
				matrice.add(align1);
				count++;
				lastLineMatrix = align1;
				// System.out.println("je m'execute une seule fois ^_^");

			} else {

				pfstp = 0;
				while (lastLineMatrix.get(pfstp) == '.') {
					pfstp++;
				}
				// u.displayLine(lastLineMatrix);

				if (currentAlignement[0][0] != '.') {

					for (int j = 0; j < pfstp; j++) {
						align0.add(0, '.');
						align1.add(0, '.');
					}
					matrice.add(align0);
					matrice.add(align1);
				} // fin if alligne commence pas par '.'
				if (currentAlignement[0][0] == '.') {
					int decalage;
					pfsss = 0;
					while (currentAlignement[0][pfsss] == '.') {
						pfsss++;
					}
					decalage = pfstp - pfsss;
					if (decalage < 0) {
						int decalageAbs = Math.abs(decalage);

						for (ArrayList<Character> line : matrice) {

							for (int j = 0; j < decalageAbs; j++) {
								line.add(0, '.');
							}
						}
						matrice.add(align0);
						matrice.add(align1);

					} // fin if decalage <0
					else {
						for (int i = 0; i < decalage; i++) {
							align0.add(0, '.');
							align1.add(0, '.');
						}
						matrice.add(align0);
						matrice.add(align1);

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
				System.out.println("Injection des gaps");
				u.displayArrayLists(matrice);
				int ind = 0;
				int size = matrice.size();
				Character t = matrice.get(size - 3).get(ind);
				Character s = matrice.get(size - 2).get(ind);
				// while( !t.equals(null) && !s.equals(null)){
				while (t !=null && s!=null) {
					System.out.println("ind  " + ind);
					System.out.println(s + "    " + t);
					if (s.equals('-') && t.equals('-')) {

					} else if (s.equals('-')) {

						for (int k = 0; k < size - 2; k++) {
							matrice.get(k).add(ind, '-');
						}

					} else if (t.equals('-')) {
						for (int i = size - 2; i < size; i++) {
							matrice.get(i).add(ind, '-');
						}
					}
					ind++;
					if (ind < matrice.get(size - 3).size()) {
						t = matrice.get(size - 3).get(ind);
					} else {
						t = null;
					}
					if (ind < matrice.get(size - 2).size()) {
						s = matrice.get(size - 2).get(ind);
					} else {
						s = null;
					}
					u.displayArrayLists(matrice);
				}
				lastLineMatrix = matrice.get(matrice.size() - 1);

			} // fin count
				// System.out.println("apres injection du gap");
				// new Utilitaires().displayMatrix(matrix);
			//u.displayArrayLists(matrice);
			//System.out.println("#######################################################");
		} // fin for alignements
		return matrice;
	}

	public static void voteMajorite(char[][] matrix) throws IOException {
		int cols = matrix[0].length, lines = matrix.length;
		char[] resultats = new char[cols];
		char[] consensus = new char[cols];
		for (int i = 0; i < cols; i++) {
			Map<Character, Integer> hashChars = new HashMap<Character, Integer>();
			Set<Character> keys = hashChars.keySet();
			for (int j = 0; j < lines; j++) {
				if (matrix[j][i] != '.') {
					if (keys.contains(matrix[j][i])) {
						hashChars.put(matrix[j][i], (hashChars.get(matrix[j][i]) + 1));
					} else {
						hashChars.put(matrix[j][i], 1);
					}
				}
			}
			Integer maxWeight = Collections.max(hashChars.values());
			ArrayList<Character> result = new ArrayList<Character>();

			Set<Character> keys_ = hashChars.keySet();
			for (Character key : keys_) {
				if (hashChars.get(key).equals(maxWeight)) {
					result.add(key);
				}
			}
			// System.out.println("has table " + hashChars.keySet().toString() +
			// " " + hashChars.values().toString());
			// System.out.println("resultats pour la colonne courante " +
			// result.toString());
			if (result.size() == 1) {
				consensus[i] = result.get(0);
			} else {
				consensus[i] = result.get(new Random().nextInt(result.size() - 1));
			}
		}
		new Utilitaires().displayLine(consensus);
	}

}
