package ordonnerLesFragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.classes.Arc;


/**
 * Simple implementation of data structure UnioFind to manage the Graph of 
 * Fragments
 * @author bellafkih
 * 
 * 
 */

public class ManageArcsOfOverlapGraph {
	private List<Integer[]> listOfSelectedArcs;
	private List<List<Integer>> listOfRemnantSets;
	private Arc headOfPath;
	private Map<Integer, String[]> nodeWeight = new HashMap<Integer, String[]>();

	public ManageArcsOfOverlapGraph(int max) {
		listOfSelectedArcs = new ArrayList<Integer[]>();
		listOfRemnantSets = new ArrayList<List<Integer>>();
	}
    /**
     * @author bellafkih
     * @return  the list of final selected arcs, that construct the hameltonien path 
     */
	public List<Integer[]> getListOfSelectedArcs() {
		return listOfSelectedArcs;
	}

	public Arc getHeadOfPath() {
		return headOfPath;
	}

	public List<List<Integer>> getListOfRemnantSets() {
		return listOfRemnantSets;
	}

	/**
	 * This function create a set of singleton elements, like : {1}, {2}, ..., {max}
	 * these set are stored in listOfRemnantSets variable
	 * 
	 * @author bellafkih
	 * @param max : Integer, it represent the number of the fragments, each Fragment is associated with a unique integer
	 */
	public void makeSet(int max) {
		for (int i = 1; i <= max; i++) {
			List<Integer> tmp = new ArrayList<Integer>();
			tmp.add(i);
			this.listOfRemnantSets.add(tmp);
		}

	}

	/**
	 * This function reset the content of the listOfSelectedArcs variable 
	 * if at the end of the iteration no hamiltonian is resulted 
	 * @author bellafkih
	 * 
	 */
	public void resetListOfSelectedArcs() {
		this.listOfSelectedArcs.clear();
	}

	/**
	 * Add given Arc object in the parameter in the list of final selected Arcs, that construct the hamiltonian Path
	 * @author bellafkih
	 * @param arc
	 */
	public void select(Arc arc) {
		int i = arc.getSource(), j = arc.getSink();
		Integer[] tmpArc = new Integer[2];
		tmpArc[0] = i;
		tmpArc[1] = j;
		boolean flag = false;
		for (Integer[] s : this.listOfSelectedArcs) {
			if (s.equals(tmpArc)) {
				flag = true;
			}
		}
		/* Each identifier of Fragment is tagged if it was a source or a destination
		 * If a identifier has a value, at the end of tagging, {"t", "s"}, it refer that 
		 * the this Fragment is met one time as source ==>  the head of Hameltonian Path
		 * */
		if (flag == false) {
			this.listOfSelectedArcs.add(tmpArc);
			if (nodeWeight.containsKey(tmpArc[0])) {
				nodeWeight.put(tmpArc[0], new String[]{"f", "s"});
			} else {
				nodeWeight.put(tmpArc[0],new String[]{"t", "s"});
			}
			if (nodeWeight.containsKey(tmpArc[1])) {
				nodeWeight.put(tmpArc[1], new String[]{"f", "d"});
			} else {
				nodeWeight.put(tmpArc[1], new String[]{"t", "d"});
			}
		}
	}

	/**
	 * 
	 * Merge the two sets u and v in one set 
	 * 
	 * @author bellafkih
	 * @param u set of identifiers of fragments
	 * @param v set of identifiers of fragments
	 */
	public void union(List<Integer> u, List<Integer> v) {
		List<Integer> newList = new ArrayList<Integer>(u);
		newList.addAll(v);
		this.listOfRemnantSets.remove(v);
		this.listOfRemnantSets.remove(u);
		this.listOfRemnantSets.add(newList);
	}

	/**
	 * Check if the element i is present in one of the sets 
	 * 
	 * @param i the element to check if any of sets has this elements as member
	 * @return The set that contains i element if it exist, null if not
	 */
	public List<Integer> findSet(int i) {
		List<Integer> tmpSet = null;
		for (List<Integer> setOfints : this.listOfRemnantSets) {
			if (setOfints.contains(i)) {
				tmpSet = setOfints;
			}
		}
		return tmpSet;
	}

	/**
	 * Return the head of the constructed Hamiltonian path
	 * each identifier of fragment is taged with two chars, "t" the identifier is met one time,
	 * "s" set if the Fragment identifier is met as source or destination
	 * @author bellafkih
	 * @return Return the head of the constructed Hamiltonian path
	 */
	public Integer getHead() {
		Integer head = null;
		for (Map.Entry<Integer, String[]> entry : this.nodeWeight.entrySet()) {
			if (entry.getValue()[0].equals( "t") && entry.getValue()[1].equals( "s")) {
				head = entry.getKey();
			}
		}	
		return head;
	}
}
