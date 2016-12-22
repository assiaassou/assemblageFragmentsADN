package ordonnerLesFragments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.classes.Arc;
import main.utilitaires.Utilitaires;
import rechercheDesChevauchements.AlignSemiGlobalParProgDynamique;
import rechercheDesChevauchements.CreationFragCompInv;
import rechercheDesChevauchements.FragmentADN;

/**
 * This class has objective to create Hamiltonian path Each Node is the
 * identifier of the Fragment, it is a unique integer the method
 * constructFinalPath return the node that construct Hamiltonian path, in the
 * same order as returned from the this function
 * 
 * @author bellafkih
 */
public class HamiltonianPath {
	public ArrayList<FragmentADN> listeTousFragEtCompInv=new ArrayList<FragmentADN>();
	public Map<Integer, Integer> in = null;
	public Map<Integer, Integer> out = null;
	public static int totalNumberOfFragments ;
	// TODO Update totalNumberOfFragments to be automatic
	public static int totalNumberOfArcs ;
	public List<Integer[]> listOfSelectedArcs;
	public Integer headOfPath;
	public Integer[][] matrixOfScores;
	public Arc[] OrderedArcs;

	public Map<Integer, Integer> getIn() {
		return in;
	}

	public Map<Integer, Integer> getOut() {
		return out;
	}

	public static int getTotalNumberOfFragments() {
		return totalNumberOfFragments;
	}

	public Integer[][] getMatrixOfScores() {
		return matrixOfScores;
	}

	public void setMatrixOfScores(Integer[][] matrixOfScores) {
		this.matrixOfScores = matrixOfScores;
	}

	public Arc[] getOrderedArcs() {
		return OrderedArcs;
	}

	/**
	 * initialize the variables: in [FragmentIdentifier] : set to 1 if the
	 * FragmentIdentifier node is chosen as inbound of arc
	 * out[FragmentIdentifier] : set to 1 if the FragmentIdentifier node is
	 * chosen as outbound of arc OrderedArcs : filter the matrix of similarity,
	 * OrderedArcs contains only possible arcs, the arc between the fragment and
	 * it complementary is elmiminated, also the cycle of nodes
	 * 
	 * @return void
	 */
	public void initializeData() {
		// initialize the input and the output flag of each fragment
		for (int i = 1; i <= getTotalNumberOfFragments(); i++) {
			in.put(i, 0);
			out.put(i, 0);
		}
		int m = 0, i = 0, j = 0;
		for (i = 0; i < totalNumberOfFragments; i++) {
			for (j = 0; j < totalNumberOfFragments; j++) {
				if (this.matrixOfScores[i][j] != null) {
					this.OrderedArcs[m] = new Arc(i + 1, j + 1, this.matrixOfScores[i][j]);
					m++;
				}
			}
		}
	}

	/**
	 * Sort filtered arcs descending order by arcs weight
	 * 
	 * @return ordered arcs in descending order
	 * @author bellafkih
	 */
	public Arc[] sortArcs() {
		this.initializeData();
		Arc t;
		if (totalNumberOfArcs == 1) {
		} else if (totalNumberOfArcs == 0) {
		} else {
			for (int i = 1; i < totalNumberOfArcs; ++i) {
				for (int j = totalNumberOfArcs - 1; j >= i; --j) {
					if (this.OrderedArcs[j - 1].getScore() < this.OrderedArcs[j].getScore()) {
						t = this.OrderedArcs[j - 1];
						this.OrderedArcs[j - 1] = this.OrderedArcs[j];
						this.OrderedArcs[j] = t;
					}
				}
			}
		}
		return this.OrderedArcs;
	}

	/**
	 * Get the head of the Hamiltonian path
	 * 
	 * @return : Integer , Get the head, the identifier of Fragment, of the
	 *         Hamiltonian path
	 */
	public Integer getHead() {
		return this.headOfPath;
	}

	/**
	 * @author bellafkih
	 * @return
	 */
	public void hameltonienPath() {
		this.initializeData();
		ManageArcsOfOverlapGraph uf = new ManageArcsOfOverlapGraph(totalNumberOfFragments);
		this.sortArcs();
		this.OrderedArcs = this.randomTheDuplicatedScoreArcs();
		uf.makeSet(totalNumberOfFragments);
		while (uf.getListOfSelectedArcs().size() != totalNumberOfFragments / 2 - 1) {
			for (Arc arc : this.OrderedArcs) {
				int source = arc.getSource(), sink = arc.getSink();
				if (in.get(arc.getSink()) == 0 && out.get(arc.getSource()) == 0
						&& !uf.findSet(arc.getSink()).equals(uf.findSet(arc.getSource()))) {
					uf.select(arc);
					in.put(sink, 1);
					out.put(source, 1);
					if (sink % 2 == 0) {
						in.put(sink - 1, 1);
						out.put(sink - 1, 1);
					} else {
						in.put(sink + 1, 1);
						out.put(sink + 1, 1);
					}
					if (source % 2 == 0) {
						out.put(source - 1, 1);
						in.put(source - 1, 1);
					} else {
						out.put(source + 1, 1);
						in.put(source + 1, 1);
					}
					uf.union(uf.findSet(source), uf.findSet(sink));
				}
				if (uf.getListOfRemnantSets().size() == 1) {
					break;
				}
			}
		}
		this.headOfPath = uf.getHead();
		this.listOfSelectedArcs = uf.getListOfSelectedArcs();
		for (Integer[] arc : this.listOfSelectedArcs) {
			System.out.println("[" + arc[0] + "," + arc[1] + "]");
		}

	}

	/**
	 * Reassort the list of arcs that have same weight value, to diversify the
	 * solution A random solution is implemented
	 * 
	 * @return
	 */
	public Arc[] randomTheDuplicatedScoreArcs() {
		Arc last = this.OrderedArcs[0], current = null;
		final Arc[] copyOfOrderedArcs = this.OrderedArcs.clone();
		int debut = -1, fin = -1;
		boolean flag = false;
		int lenghtOfArcs = this.OrderedArcs.length;
		for (int i = 1; i < lenghtOfArcs; i++) {
			current = this.OrderedArcs[i];
			if (current.getScore() == last.getScore()) {
				if (flag == false) {
					debut = i - 1;
				}
				if (i + 1 == lenghtOfArcs) {
					fin = i;
					ArrayList<Integer> obj = new ArrayList<Integer>();
					for (int j = debut; j <= fin; j++) {
						obj.add(j);
					}
					Collections.shuffle(obj);
					int s = 0;
					for (int j = debut; j <= fin; j++) {
						this.OrderedArcs[j] = copyOfOrderedArcs[obj.get(s)];
						s++;
					}
				}
				flag = true;
			}
			if (current.getScore() != last.getScore()) {
				if (flag == true) {
					fin = i - 1;
					ArrayList<Integer> obj = new ArrayList<Integer>();
					for (int j = debut; j <= fin; j++) {
						obj.add(j);
					}
					Collections.shuffle(obj);
					int m = 0;
					for (int j = debut; j <= fin; j++) {
						this.OrderedArcs[j] = copyOfOrderedArcs[obj.get(m)];
						m++;
					}
				}
				flag = false;
			}
			last = current;
		}
		return this.OrderedArcs;
	}

	/**
	 * After getting the chosen arcs that maximize the similarity, A Hamiltonian
	 * path is constructed, the path is a sequence of integers, each integer
	 * refer to a given Fragment
	 * 
	 * @author bellafkih
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public Integer[] constructFinalPath() throws FileNotFoundException, UnsupportedEncodingException {
		Integer headOfPath = this.getHead();
		int i = 0;
		Integer[] contigFragments = new Integer[totalNumberOfFragments/2];
		contigFragments[0] = headOfPath;

		/*
		 * 
		 */

		List<Integer[]> copyOfListOfSelectedArcs = this.listOfSelectedArcs;
		List<Integer[]> tt = this.listOfSelectedArcs;
		PrintWriter writer = new PrintWriter("arcs.net", "UTF-8");

		
		//writer.println("*arcs");

		for (Integer[] n : this.listOfSelectedArcs) {
			//writer.println(n[0] + " " + n[1]);
			 writer.println(n[0]+" "+n[1]+ "  "+
			 this.matrixOfScores [n[0]-1][n[1]-1]);

		}
		
		int j;
		i = i + 1;
		while (i <= totalNumberOfFragments / 2) {
			for (j = 0; j < copyOfListOfSelectedArcs.size(); j++) {
				if (headOfPath.equals( copyOfListOfSelectedArcs.get(j)[0])) {
					contigFragments[i] = copyOfListOfSelectedArcs.get(j)[1];
					headOfPath = copyOfListOfSelectedArcs.get(j)[1];
					copyOfListOfSelectedArcs.remove(copyOfListOfSelectedArcs.get(j));
					i++;
					break;
				}
			}
			if(i==totalNumberOfFragments/2){
				break;
			}
		}
/*
		writer.println("*Vertices");
		
		

		for (Integer n : contigFragments) {
			//writer.println(n[0] + " " + n[1]);
			if(n != null)
			 writer.println(n);

		}
		writer.close();
		*/
		return contigFragments;
	}

	public HamiltonianPath(String filepath) throws IOException {
		super();
		// TODO Auto-generated constructor stub
		in = new HashMap<Integer, Integer>();
		out = new HashMap<Integer, Integer>();
		// Chevauchement ch = new Chevauchement();
		// ch.readFile();
		// AlignementSemiGlobal al = new AlignementSemiGlobal(ch.readFile());

		CreationFragCompInv ch = new CreationFragCompInv();
		this.listeTousFragEtCompInv=ch.creationFragCompInv(filepath);
		
		AlignSemiGlobalParProgDynamique al = new AlignSemiGlobalParProgDynamique(this.listeTousFragEtCompInv);
		this.matrixOfScores = al.alignementSemiGlobal();
		totalNumberOfFragments= this.matrixOfScores.length;
		totalNumberOfArcs = (totalNumberOfFragments * totalNumberOfFragments)
				- (4 * totalNumberOfFragments / 2);
		OrderedArcs = new Arc[totalNumberOfArcs];
	}

}