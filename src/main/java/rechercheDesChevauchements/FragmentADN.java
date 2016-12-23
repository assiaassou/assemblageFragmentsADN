package rechercheDesChevauchements;

public class FragmentADN {

	private int id;
	private char[] acides;

	public FragmentADN() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char[] getAcides() {
		return acides;
	}

	public void setAcides(char[] acides) {
		this.acides = acides;
	}

	public FragmentADN(int id, char[] acides) {
		super();
		this.id = id;
		this.acides = acides;
	}

	public FragmentADN complementaire() {

		FragmentADN fragComplementaire = new FragmentADN();
		// private int id;
		// private Boolean complemente;
		char[] acides = new char[this.acides.length];

		int j;
		int i = 0;

		for (j = this.acides.length - 1; j >= 0; j--) {

			switch (this.acides[j]) {
			case 'a':
				acides[i] = 't';
				break;
			case 't':
				acides[i] = 'a';
				break;
			case 'g':
				acides[i] = 'c';
				break;
			default:
				acides[i] = 'g';
			}
			i++;
		}

		fragComplementaire.setId(this.id + 1);
		fragComplementaire.setAcides(acides);
		return fragComplementaire;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub

		String str = new String(this.acides);
		return "id " + this.id + " :  " + str;
	}

}
