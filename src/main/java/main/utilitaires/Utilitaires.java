package main.utilitaires;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import alignementEtConsensus.AlignementMultiple;
import main.classes.Arc;

public class Utilitaires {

	String fichierLog = "/home/bellafkih/loggioinfo.txt";

	public Utilitaires() {

		// FileOutputStream writer = new FileOutputStream(fichierLog);
		// writer.write(("").getBytes());
		// writer.close();
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
		String a = "", b = "";
		// String to be scanned to find the pattern.
		String text = "Key - Value";
		Pattern pairRegex = Pattern.compile("(.*collection)(\\d+)");
		Matcher matcher = pairRegex.matcher(text);
		if (matcher.matches()) {
			a = matcher.group(1);
			b = matcher.group(2);
		}
		System.out.println(a);
		System.out.println(b);
		return Integer.valueOf(b);

	}

	public void generateCibleFileFast(String filePathe, char[] consensus, String filePathFasta) throws IOException {
		File file = new File(filePathe);

		// creates the file
		file.createNewFile();

		// creates a FileWriter Object
		int collectionNumber = this.getCollectionName(filePathFasta);
		FileWriter writer = new FileWriter(file);
		String consensusStr = String.valueOf(consensus);
		String FirstLine = "> 1 Collection " + collectionNumber + "Longueur " + consensus.length;
		writer.write(consensusStr);
		// Writes the content to the file
		writer.write(consensus);
		writer.flush();
		writer.close();
		

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
		// PrintStream System.out = new PrintStream(new
		// FileOutputStream(fichierLog, true));

		// FileWriter System.out = new FileWriter(fichierLog, true);
		System.out.println("La ligne   est");
		for (int k = 0; k < line.length; k++) {
			System.out.print(line[k] + " ");
		}
		System.out.println("\r\n");

	}
}
