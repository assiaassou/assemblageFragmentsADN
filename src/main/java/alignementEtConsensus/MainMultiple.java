package alignementEtConsensus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import main.utilitaires.Utilitaires;

public class MainMultiple {

	public static void main(String args[]) {
		// decalageDroite();
	}

	public static void decalageDroite(List<char[][]> alignements) throws IOException {
		Utilitaires u = new Utilitaires();
		char[] lastLine = null;
		int lastLineIndex, k;
		char[][] matrix = { {} };
		char[][] al1 = { { 'a', 'a', 'a', '.', '.' }, { '.', 'b', 'b', 'b', 'b' } };
		char[][] al4 = { { 'b', 'b', '-', 'b', '.', '.' }, { '.', '.', 'c', 'c', 'c', 'c' } };
		char[][] al3 = { { 'c', '-', 'c', 'c', 'c', '.' }, { '.', '.', 'c', 'c', 'c', 'c' } };
		// char[][][] alignements = { al4, al3 };
		char[][] all1 = { { 'a', 'a', '.', '.' }, { '.', 'a', 'b', 'b' } };
		char[][] all2 = { { '.', '.', '.', 'a', '-', 'b', 'b' }, { 'b', 'b', 'b', 'a', 'b', 'b', 'b' } };
		char[][] all3 = { { 'b', 'b', 'b', 'a', 'b', '-', 'b', 'b' }, { 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b' } };
		char[][] all4 = { { 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b' }, { '.', 'c', 'c', 'c', '.', '.', '.', '.' } };
		char[][] all5 = { { 'c', 'c', '-', 'c' }, { '.', 'c', 'c', 'c' } };

		// la liste des alignements
		// char[][][] alignements = { all2, all3, all4, all5 };

		// copie du premier alignement dans la matrice de travail (matrix)
		// matrix = al1;

		int count = 0;
		// On commence le parcours de la liste des alignements
		for (char[][] currentAlign : alignements) {
			
			if (count == 0) {
				System.out.println("ok");
				matrix = currentAlign;
				count++;

				// on recopie l'avant dernier ligne de la matrice afin de le
				// comparer
				// avec la première ligne de l'alignement courant

				lastLine = matrix[matrix.length - 1];
			} else {
				// Afichage de l'alignement courant:
				System.out.println("Afichage de l'alignement courant");
				//u.displayAlignement(currentAlign);

				// pour chaque alignement, on test le S de cet alignement, s'il
				// commence par '.' donc evedement le T est celui qui commence
				// par
				// symbole
				int positionFirstSymbolTpRecdent = 0, numberOfPointInLastLineMatrix;
				while (lastLine[positionFirstSymbolTpRecdent] == '.') {
					positionFirstSymbolTpRecdent++;
				}

				// le s_suiv commence par un symbole (différent de '.')
				if (currentAlign[0][0] != '.') {

					int lines = matrix.length, cols = matrix[0].length, leghtNextAlignement = currentAlign[0].length;

					// creation de la matrice de travail
					char[][] matrix_ = null;

					if (positionFirstSymbolTpRecdent + leghtNextAlignement > cols) {
						matrix_ = new char[lines + 2][positionFirstSymbolTpRecdent + leghtNextAlignement];
						// deplacement des anciens elements dans la matrice de
						// travail
						for (int i = 0; i < lines; i++) {
							for (int j = 0; j < cols; j++) {
								matrix_[i][j] = matrix[i][j];
							}
						}

						matrix = matrix_;
					} else {
						matrix_ = new char[lines + 2][matrix[0].length];
						System.arraycopy(matrix, 0, matrix_, 0, matrix.length);
						matrix = matrix_;
					}
					// copier du nouveau alignement dans la matrice
					for (k = lines; k < lines + 2; k++) {
						for (int j = 0; j < positionFirstSymbolTpRecdent; j++) {
							matrix[k][j] = '.';
						}
						int finalMatrixCols = positionFirstSymbolTpRecdent + leghtNextAlignement;
						for (int j = positionFirstSymbolTpRecdent; j < finalMatrixCols; j++) {

							matrix[k][j] = currentAlign[k % 2][j - positionFirstSymbolTpRecdent];
							// System.out.println(k+" "+j+ " "+currentAlign[k %
							// 2][j
							// - positionFirstSymbolTpRecdent]);
						}

					}

					// On garde la derniere ligne du matrice pour ?

					// affichage de la matrice finale avant l'injection des gaps
					// System.out.println("affichage de la matrice finale avant
					// l'injection des gaps");
					// u.displayMatrix(matrix);

					// l'injection des gaps (matrix contient aussi l'alignement
					// finale)

					lines = matrix.length;
					int gapPosition;
					cols = matrix[0].length;
					char[] sCurrentAlign = matrix[lines - 2];

					int lenghtsCurrentAlign = sCurrentAlign.length;
					char matrixTm[][] = null;
					for (int i = 0; i < lenghtsCurrentAlign; i++) {
						if (sCurrentAlign[i] == '-') {
							gapPosition = i;
							// à chaque gape trouve on augmente les colonnes de
							// la
							// matrice
							matrixTm = new char[lines][cols + 1];
							// la recopie des elements dans la nouvelle matrice
							// en
							// decalant aussi ce qu'il faut
							for (int ii = 0; ii < lines - 2; ii++) {
								matrixTm[ii][gapPosition] = '-';
								for (int jj = 0; jj < gapPosition; jj++) { // cols+1
																			// le
																			// nombre
																			// des
																			// colonnes
																			// de
																			// la
																			// nouvelle
																			// matrice
																			// est
																			// augmenté
																			// de
																			// 1
									matrixTm[ii][jj] = matrix[ii][jj];
									// System.out.println("matrixTm["+ii+"]["+jj+"]
									// =
									// "+matrixTm[ii][jj]);
								}
								for (int jj = gapPosition + 1; jj < cols + 1; jj++) { // cols+1
																						// le
																						// nombre
																						// des
																						// colonnes
																						// de
																						// la
																						// nouvelle
																						// matrice
																						// est
																						// augmenté
																						// de
																						// 1
									matrixTm[ii][jj] = matrix[ii][jj - 1];
									// System.out.println("matrixTm["+ii+"]["+jj+"]
									// =
									// "+matrixTm[ii][jj]);
								}
							}
							// ajout des deux lignes dernières

							// resiser les deux dernier ligne
							char[] lastline = new char[cols + 1], beforLastline = new char[cols + 1];
							for (int l = 0; l < cols; l++) {
								lastline[l] = matrix[lines - 1][l];
								beforLastline[l] = matrix[lines - 2][l];

							}
							matrixTm[lines - 1] = lastline; // matrixTm[lines-1][cols-1]='.';
							matrixTm[lines - 2] = beforLastline;
							matrix = matrixTm;
						}
					}

				} else if (currentAlign[0][0] == '.') {
					// la position du premier symbole dans la dernière ligne de
					// la
					// matrice est deja calcule
					// System.out.println(positionFirstSymbolTpRecdent);

					// la position du premier symbole dans S de l'alignement en
					// cours
					// parcourir la ligne jusqu'à tombe dans le premier symbole

					int positionFirstSymbolSsUivant = 0;
					while (currentAlign[0][positionFirstSymbolSsUivant] == '.') {
						positionFirstSymbolSsUivant++;
					}
					int decalage = positionFirstSymbolSsUivant - positionFirstSymbolTpRecdent;

					// Creation de la matrice de travail
					int lines = matrix.length;
					int cols = matrix[0].length;
					char[][] matrice = new char[lines + 2][cols + decalage];

					// recopier les elements dans la matrice de travail
					for (int i = 0; i < lines; i++) {
						for (int j = 0; j < decalage; j++) {
							matrice[i][j] = '.';
						}
						for (int j = decalage; j < cols + decalage; j++) {
							matrice[i][j] = matrix[i][j - decalage];
						}
					}

					matrice[lines] = currentAlign[0];
					matrice[lines + 1] = currentAlign[1];

					// Injection de gaps
					System.out.println("Injection des gaps");
					matrix = matrice;
					int posGap;
					Boolean flag = false;
					char[] lastLineMatrix = matrix[lines];
					for (int i = 0; i < lastLineMatrix.length; i++) {
						if (lastLineMatrix[i] == '-') {
							flag = true;
							posGap = i;
							int l = matrix.length, c = matrix[0].length + 1;
							matrice = new char[l][c];
							for (int ii = 0; ii < l - 2; ii++) {
								for (int j = 0; j < posGap; j++) {
									matrice[ii][j] = matrix[ii][j];
								}
								matrice[ii][posGap] = '-';
								for (int j = posGap + 1; j < c; j++) {
									matrice[ii][j] = matrix[ii][j - 1];
								}
							}
							matrice[l - 2] = matrix[l - 2];
							matrice[l - 1] = matrix[l - 1];

						}
					}
					if (flag) {
						matrix = matrice;
					}

				}

				lastLine = matrix[matrix.length - 1];
				//System.out.println("affichage de la matrice apres chaque alignements)");
				//u.displayMatrix(matrix);
				count++;
			}

		} // fin de la boucle qui parcourt les alignements

		System.out.println("affichage de la matrice finale (tous les alignements)");
		//u.displayMatrix(matrix);

	}
}
