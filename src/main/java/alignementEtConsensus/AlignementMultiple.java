package alignementEtConsensus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import main.utilitaires.Utilitaires;

public class AlignementMultiple {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<char[][]> listAlignement = new ArrayList<char[][]>();
		// char[][][] alignements = { al4, al3 };
		char[][] all1 = { { 'a', 'a', '.', '.' }, { '.', 'a', 'b', 'b' } };
		char[][] all2 = { { '.', '.', '.', 'a', '-', 'b', 'b' },
				{ 'b', 'b', 'b', 'a', 'b', 'b', 'b' } };
		char[][] all3 = { { 'b', 'b', 'b', 'a', 'b', '-', 'b', 'b' },
				{ 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b' } };
		char[][] all4 = { { 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b' },
				{ '.', 'c', 'c', 'c', '.', '.', '.', '.' } };
		char[][] all5 = { { 'c', 'c', '-', 'c' }, { '.', 'c', 'c', 'c' } };

		// la liste des alignements
		// char[][][] alignements = { all2, all3, all4, all5 };
		listAlignement.add(all1);
		listAlignement.add(all2);
		listAlignement.add(all3);
		listAlignement.add(all4);
		listAlignement.add(all5);
		voteMajorite(consensus(listAlignement));

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
//			System.out.println("has table   " + hashChars.keySet().toString() + "   " + hashChars.values().toString());
//			System.out.println("resultats pour la colonne courante " + result.toString());
			if (result.size() == 1) {
				consensus[i] = result.get(0);
			} else {
				consensus[i] = result.get(new Random().nextInt(result.size() - 1));
			}			
		}
		
		try {

		      File file = new File("salam.txt");

		      if (file.createNewFile()){
		        System.out.println("File is created!");
		      }else{
		    	
		    	    FileWriter fw = new FileWriter("salam.txt",true); //the true will append the new data
		    	    for( char i; i)
		    	    fw.write("");//appends the string to the file
		    	    fw.close();
		      }

	    	} catch (IOException e) {
		      e.printStackTrace();
		}
		new Utilitaires().displayLine(consensus);
	}

	public static char[][] consensus(ArrayList<char[][]> listAlignement)
			throws IOException {
		Utilitaires u = new Utilitaires();
		int count = 0;
		int cols, cols_, lines, lines_;
		char[][] matrix = null;
		char[] lastLineMatrix = null;

		for (char[][] currentAlignement : listAlignement) {
			// new Utilitaires().displayAlignement(currentAlignement);
			int pfstp, pfsss;
			int currAliCols = currentAlignement[0].length;
			if (count == 0) { // c'est le premier alignement
				matrix = currentAlignement;
				count++;
				lastLineMatrix = matrix[1];
				// System.out.println("je m'execute une seule fois ^_^");

			} else {

				pfstp = 0;
				while (lastLineMatrix[pfstp] == '.') {
					pfstp++;
				}
				// u.displayLine(lastLineMatrix);

				if (currentAlignement[0][0] != '.') {
					cols = matrix[0].length;
					lines = matrix.length;
					cols_ = Math.max(cols, pfstp + currAliCols);
					lines_ = lines + 2;
					char[][] matrix_ = new char[lines_][cols_];
					// Remplissage de la matrice de travail
					for (int i = 0; i < lines; i++) {
						for (int j = 0; j < cols; j++) {
							matrix_[i][j] = matrix[i][j];
						}
						for (int j = cols; j < cols_; j++) {
							matrix_[i][j] = '.';
						}
					}
					for (int i = lines; i < lines_; i++) {

						for (int j = 0; j < pfstp; j++) {
							matrix_[i][j] = '.';
						}
						for (int j = pfstp; j < currAliCols + pfstp; j++) {
							matrix_[i][j] = currentAlignement[i % 2][j - pfstp];
						}
						for (int j = currAliCols + pfstp; j < cols_; j++) {
							matrix_[i][j] = '.';
						}
					}
					matrix = matrix_;
				} // fin if alligne commence pas par '.'
				if (currentAlignement[0][0] == '.') {
					int decalage;
					char[][] matrix_ = null;
					pfsss = 0;
					while (currentAlignement[0][pfsss] == '.') {
						pfsss++;
					}
					decalage = pfstp - pfsss;
					lines = matrix.length;
					cols = matrix[0].length;
					lines_ = lines + 2;

					if (decalage < 0) {
						decalage = Math.abs(decalage);
						cols_ = Math.max(currAliCols, decalage + cols);
						matrix_ = new char[lines_][cols_];
						for (int i = 0; i < lines; i++) {
							System.out.println("de 0 a " + decalage);
							for (int j = 0; j < decalage; j++) {
								matrix_[i][j] = '.';
							}
							System.out.println("de  " + decalage + "  a  "
									+ (cols + decalage));
							for (int j = decalage; j < (cols + decalage); j++) {
								matrix_[i][j] = matrix[i][j - decalage];
							}
							System.out.println("de  " + cols + "  a  " + cols_);
							for (int j = (cols + decalage); j < cols_; j++) {
								matrix_[i][j] = '.';
							}
						}
						for (int i = lines; i < lines_; i++) {
							for (int j = 0; j < currAliCols; j++) {
								matrix_[i][j] = currentAlignement[i % 2][j];
							}
							for (int j = currAliCols; j < cols_; j++) {
								matrix_[i][j] = '.';
							}
						}

					} // fin if decalage <0
					else {

						cols_ = Math.max(decalage + currAliCols, cols);
						lines_ = lines + 2;
						matrix_ = new char[lines_][cols_];
						for (int i = 0; i < lines; i++) {
							for (int j = 0; j < cols; j++) {
								matrix_[i][j] = matrix[i][j];
							}
							for (int j = cols; j < cols_; j++) {
								matrix_[i][j] = '.';
							}
						}
						for (int i = lines; i < lines_; i++) {
							for (int j = 0; j < decalage; j++) {
								matrix_[i][j] = '.';
							}
							for (int j = decalage; j < decalage + currAliCols; j++) {
								matrix_[i][j] = currentAlignement[i % 2][j
										- decalage];
							}
							for (int j = decalage + currAliCols; j < cols_; j++) {
								matrix_[i][j] = '.';
							}
						}
					} // fin if decalage >=0
					matrix = matrix_;

				} // fin if alligne commence par '.'
					// System.out.println("avant injection du gap");
					// new Utilitaires().displayMatrix(matrix);

				// Injection des gaps
				lines = matrix.length;
				cols = matrix[0].length;
				char[] sCurrentAlign = matrix[lines - 2];
				int posGap;
				// new Utilitaires().displayLine(sCurrentAlign);
				for (int i = 0; i < currAliCols; i++) {
					if (sCurrentAlign[i] == '-') {
						posGap = i;
						// System.out.println("Hello wold i am a gap in "+posGap);
						int j = 0;
						for (int s = 0; s < lines - 2; s++) {
							if (matrix[s][cols - 1] == '.') {
								j++;
							}
						}

						char[][] matrix_;
						if (j == lines - 2) {
							// System.out.println("##############################rani
							// hna");
							matrix_ = new char[lines][cols];
							for (int ii = 0; ii < lines - 2; ii++) {
								for (int jj = 0; jj < posGap; jj++) {
									matrix_[ii][jj] = matrix[ii][jj];
								}
								matrix_[ii][posGap] = '-';
								for (int jj = posGap + 1; jj < cols; jj++) {
									matrix_[ii][jj] = matrix[ii][jj - 1];
									// System.out.println(
									// "element  matrix[" + ii + "][" + (jj - 1)
									// + "]" + matrix[ii][jj - 1]);
								}
							}
							matrix_[lines - 1] = matrix[lines - 1];
							matrix_[lines - 2] = matrix[lines - 2];
						} else {

							matrix_ = new char[lines][cols + 1];
							for (int ii = 0; ii < lines - 2; ii++) {
								for (int jj = 0; jj < posGap; jj++) {
									matrix_[ii][jj] = matrix[ii][jj];
								}
								matrix_[ii][posGap] = '-';
								for (int jj = posGap + 1; jj < cols + 1; jj++) {
									matrix_[ii][jj] = matrix[ii][jj - 1];
								}
							}
							System.arraycopy(matrix[lines - 1], 0,
									matrix_[lines - 1], 0,
									matrix[lines - 1].length);
							matrix_[lines - 1][cols] = '.';
							System.arraycopy(matrix[lines - 2], 0,
									matrix_[lines - 2], 0,
									matrix[lines - 2].length);
							matrix_[lines - 2][cols] = '.';

						}
						matrix = matrix_;

					} // fin si egale '-'

				}

				lastLineMatrix = matrix[matrix.length - 1];

			} // fin count
				// System.out.println("apres injection du gap");
				// new Utilitaires().displayMatrix(matrix);

		} // fin for alignements
		return matrix;
	}

}
