package rechercheDesChevauchements;

public class PositionScore {
	private int positionLigne;
	private int positionColonne;
	private int scoreCalculé;

	public PositionScore() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PositionScore(int i, int j, int score) {
		super();
		// TODO Auto-generated constructor stub
		positionLigne = i;
		positionColonne = j;
		scoreCalculé = score;
	}

	public int getPositionLigne() {
		return positionLigne;
	}

	public void setPositionLigne(int positionLigne) {
		this.positionLigne = positionLigne;
	}

	public int getPositionColonne() {
		return positionColonne;
	}

	public void setPositionColonne(int positionColonne) {
		this.positionColonne = positionColonne;
	}

	public int getScoreCalculé() {
		return scoreCalculé;
	}

	public void setScoreCalculé(int scoreCalculé) {
		this.scoreCalculé = scoreCalculé;
	}

	@Override
	public String toString() {
		return "PositionScore [positionLigne=" + positionLigne
				+ ", positionColonne=" + positionColonne + ", scoreCalculé="
				+ scoreCalculé + "]";
	}

}
