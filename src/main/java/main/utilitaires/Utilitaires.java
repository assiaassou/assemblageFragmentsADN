package main.utilitaires;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Utilitaires {

	String fichierLog = "/home/bellafkih/loggioinfo.txt";

	public Utilitaires() {
	}

	public void displayArrayLists(ArrayList<ArrayList<Character>> arrayLists) {
		for (ArrayList<Character> arraylist : arrayLists) {
			System.out.println("");
			for (Character c : arraylist) {
				System.out.print(c + "  ");
			}
		}
	}

	public int getCollectionName(String filePath) throws IOException {
		BufferedReader brTest = new BufferedReader(new FileReader(filePath));
		String firstLine = brTest.readLine();
		String[] parts = firstLine.split("collection");
		return Integer.valueOf(parts[1].trim());

	}

	public void generateCibleFileFast(String filePathe, char[] consensus, String filePathFasta) throws IOException {
		File file = new File(filePathe);

		// creates the file
		file.createNewFile();

		// creates a FileWriter Object

		FileWriter writer = new FileWriter(file);
		String consensusStr = String.valueOf(consensus);
		String FirstLine = "> Groupe 1 Collection " + this.getCollectionName(filePathFasta) + "  Longueur "
				+ consensus.length + "  ";
		writer.write(FirstLine);
		writer.write(System.getProperty("line.separator"));
		writer.write(consensusStr);
		writer.flush();
		writer.close();

		// try {
		// BufferedWriter tampon = new BufferedWriter(new FileWriter("a" +
		// file));
		// PrintWriter sortie = new PrintWriter(tampon);
		// sortie.println(FirstLine);
		// sortie.println(consensusStr);
		//
		// sortie.flush();
		// sortie.close();
		// } catch (IOException e) {
		// System.out.println(e);
		// }

	}

	public void displayArrayList() {

	}

	public void createraph(Integer[] nodes, List<Integer[]> arcs, Integer[][] matrix) {
		try {

			PrintWriter writer = new PrintWriter("/home/bellafkih/test.net", "UTF-8");
			System.out.println("Creation des noeuds");
			writer.println("*Vertices " + nodes.length);
			System.out.println("*Vertices " + nodes.length);
			for (Integer n : nodes) {
				writer.println(n);
			}
			System.out.println("Xreation des arcs");
			writer.println("*arcs");
			System.out.println("total arcs" + arcs);
			for (Integer[] n : arcs) {
				writer.println(n[0] + " " + n[1]);
				// writer.println(n.getSource()+" "+n.getSink()+
				// matrix[n.getSource()-1][n.getSink()-1]);

			}
			System.out.println("Fin #############################################");
			writer.close();

			for (Integer node : nodes) {

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Character> convertCharArrayToCharacterArray(char[] chars) {
		ArrayList<Character> temporaryList = new ArrayList<Character>();
		for (char s : chars) {
			temporaryList.add(new Character(s));
		}
		return temporaryList;

	}

	public void displayMatrix(char[][] matrix) throws IOException {

		// // PrintStream fw = new FileOutputStream(new File(fichierLog),true);
		// PrintStream fw = new PrintStream(new FileOutputStream(fichierLog,
		// true));
		//
		// // FileWriter fw = new FileWriter(fichierLog, true); // the true will
		// // append the new
		// // data
		// fw.println("==========================Affichag de la matice
		// ===============================");
		// if (matrix != null) {
		// int lines = matrix.length, cols = matrix[0].length;
		// int i, j;
		// for (j = 0; j < cols; j++) {
		// fw.print(" " + j);
		// }
		// fw.println("\n");
		// for (i = 0; i < lines; i++) {
		// fw.print(i + " ");
		// for (j = 0; j < cols; j++) {
		// fw.print(matrix[i][j] + " ");
		// }
		// fw.println("");
		// }
		// }
		// fw.println("\n");
		// fw.close();

		System.out.println("==========================Affichag de la matice ===============================");
		if (matrix != null) {
			int lines = matrix.length, cols = matrix[0].length;
			int i, j;
			for (j = 0; j < cols; j++) {
				System.out.print("  " + j);
			}
			System.out.println("");
			for (i = 0; i < lines; i++) {
				System.out.print(i + " ");
				for (j = 0; j < cols; j++) {
					System.out.print(matrix[i][j] + "  ");
				}
				System.out.println("");
			}
		}
		System.out.println("\n");

	}

	public void displayAlignement(char[][] align) throws IOException {
		// FileWriter fw = new FileWriter(fichierLog, true);
		// PrintStream fw = new PrintStream(new FileOutputStream(fichierLog,
		// true));

		int cols = align[0].length;
		System.out.println("L'alignement courant est ");
		for (int k = 0; k < align.length; k++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(align[k][j] + " ");
			}
			System.out.println("");
		}

	}

	public void displayLine(char[] line) throws IOException {

		System.out.println("La ligne   est");
		for (int k = 0; k < line.length; k++) {
			System.out.print(line[k] + " ");
		}
		System.out.println("\r\n");

	}
}
