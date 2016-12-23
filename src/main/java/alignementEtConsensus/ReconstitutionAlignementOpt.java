package alignementEtConsensus;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import rechercheDesChevauchements.FragmentADN;
import rechercheDesChevauchements.PositionScore;
import rechercheDesChevauchements.ResultatProgDynam;

public class ReconstitutionAlignementOpt {
	private ArrayList<Integer> cheminAmiltonien = new ArrayList<Integer>();
	private ArrayList<FragmentADN> listeTousFragEtCompInv = new ArrayList<FragmentADN>();

	public ArrayList<Integer> getCheminAmiltonien() {
		return cheminAmiltonien;
	}

	public void setCheminAmiltonien(ArrayList<Integer> cheminAmiltonien) {
		this.cheminAmiltonien = cheminAmiltonien;
	}

	public ArrayList<FragmentADN> getListeTousFragEtCompInv() {
		return listeTousFragEtCompInv;
	}

	public void setListeTousFragEtCompInv(
			ArrayList<FragmentADN> listeTousFragEtCompInv) {
		this.listeTousFragEtCompInv = listeTousFragEtCompInv;
	}

	public ReconstitutionAlignementOpt(ArrayList<Integer> cheminAmiltonien,
			ArrayList<FragmentADN> listeTousFragEtCompInv) {
		super();
		this.cheminAmiltonien = cheminAmiltonien;
		this.listeTousFragEtCompInv = listeTousFragEtCompInv;
	}

	public ReconstitutionAlignementOpt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Map reconstitutionAlignementOpt() {

		Map<CoupleFragments, char[][]> mapTousLesAlignementsOptimaux = new HashMap<CoupleFragments, char[][]>();
		// Map contient la position de chaque case ds la matrice de
		// programmation dynamique et positions de ses sources

		Map<PositionScore, ArrayList<PositionScore>> mapPositionsCasesEtSources = new HashMap<PositionScore, ArrayList<PositionScore>>();

		ResultatProgDynam resultatProgDynam = new ResultatProgDynam();

		for (int i = 0; i < this.cheminAmiltonien.size() - 1; i++) {
			char[] acidesS = this.listeTousFragEtCompInv.get(
					this.cheminAmiltonien.get(i) - 1).getAcides();
			char[] acidesT = this.listeTousFragEtCompInv.get(
					this.cheminAmiltonien.get(i + 1) - 1).getAcides();

			int idS = this.cheminAmiltonien.get(i);
			int idT = this.cheminAmiltonien.get(i + 1);

			resultatProgDynam = programmationDynamique(acidesS, acidesT);
			mapPositionsCasesEtSources = resultatProgDynam
					.getMapPositionsCasesEtSources();
			PositionScore meilleurScore = new PositionScore();
			meilleurScore = score(resultatProgDynam.getA());

			ArrayList tableAlignementOptimal = new ArrayList();
			tableAlignementOptimal.add(meilleurScore);
			ArrayList<ArrayList<PositionScore>> listAlignementsPossibles = new ArrayList<ArrayList<PositionScore>>();
			Set<PositionScore> listKeys2 = mapPositionsCasesEtSources.keySet();

			// ajouter le meilleur score a la premier case du tableau
			tableAlignementOptimal = ReconstituerAlignement(
					tableAlignementOptimal, meilleurScore,
					mapPositionsCasesEtSources);

			int alignementChoisi = (int) (Math.random() * listAlignementsPossibles
					.size());

			CoupleFragments couplefragments = new CoupleFragments(idS, idT);

			char[][] matriceAl = ReconstituerAlignement2(
					tableAlignementOptimal, acidesS, acidesT);

			mapTousLesAlignementsOptimaux.put(couplefragments, matriceAl);

			// ----------------------------------
		}
		return mapTousLesAlignementsOptimaux;

	}

	public ResultatProgDynam programmationDynamique(char[] s, char[] t) {

		Map<PositionScore, ArrayList<PositionScore>> mapPositionsCasesEtSources = new HashMap<PositionScore, ArrayList<PositionScore>>();

		int[][] a = new int[s.length + 1][t.length + 1];
		int ll = s.length, gg = t.length + 1;

		int i = 0;
		int j = 0;
		ArrayList<PositionScore> listePossiblités = new ArrayList<PositionScore>();
		PositionScore positionScore = new PositionScore(i, j, a[i][j]);
		for (i = 0; i <= s.length; i++) {
			a[i][0] = 0;
			positionScore = new PositionScore(i, 0, a[i][0]);
			mapPositionsCasesEtSources.put(positionScore, listePossiblités);
		}

		for (j = 0; j <= t.length; j++) {
			a[0][j] = 0;
			positionScore = new PositionScore(0, j, a[0][j]);
			mapPositionsCasesEtSources.put(positionScore, listePossiblités);
		}

		int g = -2, p;
		int sim1, sim2, sim3;
		int nbrPossibilitésconduiMax;

		for (i = 0; i <= s.length; i++) {
			for (j = 0; j <= t.length; j++)

			{
				if (i != 0 && j != 0) {
					int m = i - 1, n = j - 1;
					if (s[m] == t[n])
						p = 1;
					else
						p = -1;
					sim1 = a[i - 1][j] + g;
					sim2 = a[i - 1][j - 1] + p;
					sim3 = a[i][j - 1] + g;

					a[i][j] = Math.max(sim1, Math.max(sim2, sim3));

					positionScore = new PositionScore(i, j, a[i][j]);
					listePossiblités = new ArrayList<PositionScore>();
					if (a[i][j] == sim1) {
						PositionScore posConduitScore = new PositionScore(
								i - 1, j, g);
						listePossiblités.add(posConduitScore);
					}
					if (a[i][j] == sim2) {
						PositionScore posConduitScore = new PositionScore(
								i - 1, j - 1, p);
						listePossiblités.add(posConduitScore);
					}
					if (a[i][j] == sim3) {
						PositionScore posConduitScore = new PositionScore(i,
								j - 1, g);
						listePossiblités.add(posConduitScore);
					}

					mapPositionsCasesEtSources.put(positionScore,
							listePossiblités);

				}

			}

		}

		return new ResultatProgDynam(a, mapPositionsCasesEtSources);
	}

	public PositionScore score(int[][] a) {

		int i, maxLigne, max;
		int colonne = 0;

		PositionScore positionMax = new PositionScore();
		// Calcule max derniere ligne

		maxLigne = a[a.length - 1][1];
		for (i = 0; i < a[0].length; i++)
			if (a[a.length - 1][i] > maxLigne && i != 0) {
				maxLigne = a[a.length - 1][i];
				colonne = i;
			}

		// verifier si on a plusieurs valeurs = maxLigne dans la derniere ligne
		// et mettre leurs positions dans ArrayList

		ArrayList tab = new ArrayList();
		for (i = 0; i < a[0].length; i++)
			if (a[a.length - 1][i] == maxLigne) {
				tab.add(i);
			}

		// choisir une des ces posisitions aléatoirement 
				int rnd = (int)(Math.random()*tab.size());
				// retourner la val de la position choisie
		colonne= (Integer) tab.get(rnd); 
		//colonne = (Integer) tab.get(tab.size() - 1);

		max = a[a.length - 1][colonne];
		positionMax.setPositionLigne(a.length - 1);
		positionMax.setPositionColonne(colonne);
		positionMax.setScoreCalculé(max);
		return positionMax;

	}

	public static ArrayList ReconstituerAlignement(
			ArrayList tableAlignementOptimal,
			PositionScore clé,
			Map<PositionScore, ArrayList<PositionScore>> mapPositionsCasesEtSources) {
		Set<PositionScore> listKeys = mapPositionsCasesEtSources.keySet();
		PositionScore x = null;
		// pour reconsituter l'alignement
		while (clé.getPositionLigne() != 0 && clé.getPositionColonne() != 0) {
			Iterator<PositionScore> iterateur = listKeys.iterator();
			while (iterateur.hasNext()) {
				PositionScore key = iterateur.next();
				if (key.getPositionLigne() == clé.getPositionLigne()
						&& key.getPositionColonne() == clé.getPositionColonne()) {

					int test = 0;
					int sourceChoisie = 0;
					for (int i = 0; i < mapPositionsCasesEtSources.get(key)
							.size(); i++) {
						if (mapPositionsCasesEtSources.get(key).get(i)
								.getScoreCalculé() == 1) {
							sourceChoisie = i;
							i = mapPositionsCasesEtSources.get(key).size();
						} else if (mapPositionsCasesEtSources.get(key).get(i)
								.getScoreCalculé() == -1) {
							test = 2;
							sourceChoisie = i;
						} else if (mapPositionsCasesEtSources.get(key).get(i)
								.getScoreCalculé() == -2
								&& test != 2) {
							sourceChoisie = i;
						}

					}

					x = mapPositionsCasesEtSources.get(key).get(sourceChoisie);
				}
			}
			tableAlignementOptimal.add(x);
			clé = x;
		}
		return tableAlignementOptimal;
	}

	public static char[][] ReconstituerAlignement2(
			ArrayList<PositionScore> tableAlignementOptimal, char s[], char t[]) {
		int h = 0;
		ArrayList<Character> sAlignementOpt = new ArrayList<Character>();
		ArrayList<Character> tAlignementOpt = new ArrayList<Character>();

		while (h < tableAlignementOptimal.size()
				&& tableAlignementOptimal.get(h).getPositionLigne() != 0
				&& tableAlignementOptimal.get(h).getPositionColonne() != 0) {
			if (tableAlignementOptimal.get(h + 1).getScoreCalculé() == 1) {
				sAlignementOpt.add(s[tableAlignementOptimal.get(h)
						.getPositionLigne() - 1]);
				tAlignementOpt.add(t[tableAlignementOptimal.get(h)
						.getPositionColonne() - 1]);

			}

			if (tableAlignementOptimal.get(h + 1).getScoreCalculé() == -2) {
				if (tableAlignementOptimal.get(h + 1).getPositionLigne() == tableAlignementOptimal
						.get(h).getPositionLigne()) {

					sAlignementOpt.add('-');

					tAlignementOpt.add(t[tableAlignementOptimal.get(h)
							.getPositionColonne() - 1]);

				} else if (tableAlignementOptimal.get(h + 1)
						.getPositionColonne() == tableAlignementOptimal.get(h)
						.getPositionColonne()) {

					sAlignementOpt.add(s[tableAlignementOptimal.get(h)
							.getPositionLigne() - 1]);

					tAlignementOpt.add('-');
				}
			}

			if (tableAlignementOptimal.get(h + 1).getScoreCalculé() == -1) {

				sAlignementOpt.add(s[tableAlignementOptimal.get(h)
						.getPositionLigne() - 1]);

				tAlignementOpt.add(t[tableAlignementOptimal.get(h)
						.getPositionColonne() - 1]);

			}
			h++;

		}

		int i;

		// Affichage de l'alignement obtenu de S
		int j = sAlignementOpt.size() - 1;
		int test; // test =1 si
					// i+1==tableAlignementOptimal.get(h).getPositionLigne()
					// donc lettre existe ds alignement
		ArrayList<Character> ssAlignementOpt = new ArrayList<Character>();
		ArrayList<Character> ttAlignementOpt = new ArrayList<Character>();

		i = 0;
		while (i < s.length) {
			test = 0;
			j = 0;
			while (j < tableAlignementOptimal.size() - 1) {
				if (i + 1 == tableAlignementOptimal.get(j).getPositionLigne())
					test = 1; // !!!!!
				j++;
			}
			if (test == 0) {
				ssAlignementOpt.add(s[i]);
				i++;
			}
			if (test == 1) {
				j = sAlignementOpt.size() - 1;
				while (j >= 0) {
					ssAlignementOpt.add(sAlignementOpt.get(j));
					if (!sAlignementOpt.get(j).equals('-'))
						i++;
					j--;
				}
			}
		}

		// affichage de l'alignement obtenu de T
		j = tAlignementOpt.size() - 1;

		i = 0;
		while (i < t.length) {
			test = 0;
			j = 0;
			while (j < tableAlignementOptimal.size() - 1) {
				if (i + 1 == tableAlignementOptimal.get(j).getPositionColonne())
					test = 1;
				j++;
			}
			if (test == 0) {
				ttAlignementOpt.add(t[i]);
				i++;
			}
			if (test == 1) {
				j = tAlignementOpt.size() - 1;
				while (j >= 0) {
					ttAlignementOpt.add(tAlignementOpt.get(j));
					if (!tAlignementOpt.get(j).equals('-'))
						i++;
					j--;
				}
			}
		}

		// --------------------la matrice qui contient
		// l'alignement-----------------------------

		// Calculer la taille de la matrice qui contient l'alignement
		int difference;
		int ligneDebutAlignS = tableAlignementOptimal.get(
				tableAlignementOptimal.size() - 2).getPositionLigne();
		int nbrColonneMatriceAlignement;
		if (ligneDebutAlignS != 1) {
			nbrColonneMatriceAlignement = (tableAlignementOptimal
					.get(tableAlignementOptimal.size() - 2).getPositionLigne())
					+ (ttAlignementOpt.size() - 1);
			difference = ssAlignementOpt.size() - nbrColonneMatriceAlignement;
			if (difference > 0)
				nbrColonneMatriceAlignement = nbrColonneMatriceAlignement
						+ difference;
		} else {

			nbrColonneMatriceAlignement = (tableAlignementOptimal
					.get(tableAlignementOptimal.size() - 2)
					.getPositionColonne())
					+ (ssAlignementOpt.size() - 1);
			difference = ssAlignementOpt.size() - nbrColonneMatriceAlignement;
			if (difference > 0)
				nbrColonneMatriceAlignement = nbrColonneMatriceAlignement
						+ difference;
			difference = ttAlignementOpt.size() - nbrColonneMatriceAlignement;
			if (difference > 0)
				nbrColonneMatriceAlignement = nbrColonneMatriceAlignement
						+ difference;
		}
		// Remplire la matrice

		char[][] matriceAlignement = new char[2][nbrColonneMatriceAlignement];

		// Colonnes de debut et fin S dans la matrice d'alignement
		int colonneDebutS = tableAlignementOptimal.get(
				tableAlignementOptimal.size() - 2).getPositionColonne() - 1;
		int colonneFinS = colonneDebutS + ssAlignementOpt.size();

		i = colonneDebutS;
		j = 0;
		while (i < colonneFinS - 1)
			while (j < ssAlignementOpt.size()) {
				matriceAlignement[0][i] = ssAlignementOpt.get(j);
				i++;
				j++;
			}

		// Colonnes de debut et fin t dans la matrice d'alignement
		int colonneDebutT = tableAlignementOptimal.get(
				tableAlignementOptimal.size() - 2).getPositionLigne() - 1;
		int colonneFinT = colonneDebutT + ttAlignementOpt.size();

		i = colonneDebutT;
		j = 0;
		while (i < colonneFinT - 1)
			while (j < ttAlignementOpt.size()) {
				matriceAlignement[1][i] = ttAlignementOpt.get(j);
				i++;
				j++;
			}

		// Affichage mettre des points ds les cases null de la matrice
		for (i = 0; i < 2; i++)
			for (j = 0; j < nbrColonneMatriceAlignement; j++) {
				if (matriceAlignement[i][j] == 0)
					matriceAlignement[i][j] = '.';
			}

		return matriceAlignement;

	}

	// fin
}
