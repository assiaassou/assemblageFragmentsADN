package main.utilitaires;

import java.io.IOException;

public class TestUtilitaires {

	public static void main(String args[]) throws IOException {
		Utilitaires u = new Utilitaires();
		int number = u.getCollectionName(
				"/home/bellafkih/Documents/2016-2017/BIOINFORMATIQUE/collections/Collections/16320/collection5.fasta");
		System.out.println(number);
	}
}
