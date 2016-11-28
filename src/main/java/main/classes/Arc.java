package main.classes;

/**
 * 
 * @author bellafkih
 * 
 */
public class Arc {
	private int source;
	private int sink;
	private int score;
	
	/**
	 * 
	 * @param source
	 * @param sink
	 * @param score
	 */
	public Arc(int source, int sink, int score) {
		super();
		this.source = source;
		this.sink = sink;
		this.score=score;
	}
	/**
	 * 
	 */
	public Arc() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getSink() {
		return sink;
	}
	public void setSink(int sink) {
		this.sink = sink;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Arc : "+this.source+" --->  "+this.sink+ "  score:  "+this.score;
	}
	
	
}
