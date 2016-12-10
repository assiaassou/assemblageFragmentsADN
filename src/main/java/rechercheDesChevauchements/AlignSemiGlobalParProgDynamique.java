package rechercheDesChevauchements;

import java.util.ArrayList;



public class AlignSemiGlobalParProgDynamique {
	
	private ArrayList<FragmentADN>  listeTousFragEtCompInv = new ArrayList<FragmentADN>();

	public ArrayList<FragmentADN> getListeTousFragEtCompInv() {
		return listeTousFragEtCompInv;
	}

	public void setListeTousFragEtCompInv(
			ArrayList<FragmentADN> listeTousFragEtCompInv) {
		this.listeTousFragEtCompInv = listeTousFragEtCompInv;
	}

	public AlignSemiGlobalParProgDynamique() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AlignSemiGlobalParProgDynamique(
			ArrayList<FragmentADN> listeTousFragEtCompInv) {
		super();
		this.listeTousFragEtCompInv = listeTousFragEtCompInv;
	}
	

public Integer[][] alignementSemiGlobal ( ){
					
		// la matrice de tous les scores
		int nbrLignesColonnes=this.listeTousFragEtCompInv.size(); 		
		Integer tousLesScores[][]=new Integer[nbrLignesColonnes][nbrLignesColonnes];
		
		
		
		int i,j;
		for( i=0 ; i < listeTousFragEtCompInv.size(); i=i+2)
		{
		for( j=i+2 ; j < listeTousFragEtCompInv.size(); j=j+2)
		{

		int a1; 
		a1=programmationDynamique(listeTousFragEtCompInv.get(i).getAcides(),listeTousFragEtCompInv.get(j).getAcides());
		tousLesScores[listeTousFragEtCompInv.get(i).getId()-1][listeTousFragEtCompInv.get(j).getId()-1]=a1;
		
		int a2; 
		a2=programmationDynamique(listeTousFragEtCompInv.get(j).getAcides(),listeTousFragEtCompInv.get(i).getAcides());
		tousLesScores[listeTousFragEtCompInv.get(j).getId()-1][listeTousFragEtCompInv.get(i).getId()-1]=a2;
		
		int a3; 
		a3=programmationDynamique(listeTousFragEtCompInv.get(i).getAcides(),listeTousFragEtCompInv.get(j+1).getAcides());
		tousLesScores[listeTousFragEtCompInv.get(i).getId()-1][listeTousFragEtCompInv.get(j+1).getId()-1]=a3;
		
		int a4; 
		a4=programmationDynamique(listeTousFragEtCompInv.get(j+1).getAcides(),listeTousFragEtCompInv.get(i).getAcides());
		tousLesScores[listeTousFragEtCompInv.get(j+1).getId()-1][listeTousFragEtCompInv.get(i).getId()-1]=a4;
		
		int a5; 
		a5=programmationDynamique(listeTousFragEtCompInv.get(i+1).getAcides(),listeTousFragEtCompInv.get(j).getAcides());
		tousLesScores[listeTousFragEtCompInv.get(i+1).getId()-1][listeTousFragEtCompInv.get(j).getId()-1]=a5;
		
		int a6; 
		a6=programmationDynamique(listeTousFragEtCompInv.get(j).getAcides(),listeTousFragEtCompInv.get(i+1).getAcides());
		tousLesScores[listeTousFragEtCompInv.get(j).getId()-1][listeTousFragEtCompInv.get(i+1).getId()-1]=a6;
		
		int a7; 
		a7=programmationDynamique(listeTousFragEtCompInv.get(i+1).getAcides(),listeTousFragEtCompInv.get(j+1).getAcides());
		tousLesScores[listeTousFragEtCompInv.get(i+1).getId()-1][listeTousFragEtCompInv.get(j+1).getId()-1]=a7;
		
		int a8; 
		a8=programmationDynamique(listeTousFragEtCompInv.get(j+1).getAcides(),listeTousFragEtCompInv.get(i+1).getAcides());
		tousLesScores[listeTousFragEtCompInv.get(j+1).getId()-1][listeTousFragEtCompInv.get(i+1).getId()-1]=a8;
		}
		}
		

		//affichage de la matrice de tous les scores
		System.out.println("la matrice de tous les scores est: \n");
		
		for(i=0;i<tousLesScores.length;i++)
		{
		   for(j=0;j<tousLesScores.length;j++)
		   {
			   System.out.print(tousLesScores[i][j]+"  ");
		   }
		   System.out.println("\n  ___________________________ ");
		}
		
		return tousLesScores;
	
	}
	
	
	public int programmationDynamique(char[] s,char[] t){
		
		   int[][] a= new int[s.length+1][t.length+1];   
		   int ll=s.length, gg=t.length+1;
		   
		//   System.out.println("   la langueure est    "+ll);
		//   System.out.println("   la largeure est    "+gg);
		   
		   //initialisation de la matrice a
		   int i=0;
		   int j=0;
		
		   for(i=0;i<=s.length;i++) 
			   {
			   a[i][0]=0;
			   }
		   
		   for(j=0;j<=t.length;j++) 
			   {
			   a[0][j]=0;
			   }
		   
		   // l'affichage de t
		/*   
		   System.out.print("      ");
		   int k;
		   for(j=0;j<=t.length;j++) { 
			   if(j==0) System.out.print("0"+"   |   "); 
			   else 
			   {
				   k=j-1;
			       System.out.print(t[k]+"   |   ");
			   } 
			   }
		   System.out.println("\n ____________________________________________________");
		   
		  */
		   //------
		   
		   //appliquer l algo de programmation dynamique sur le reste de la matrice a
		   
		   int g=-2, p;
		   int sim1,sim2,sim3;
		   int nbrPossibilitésconduiMax;
		   
		   for(i=0;i<=s.length;i++)
		   {
			   // l'affichage de s
			 /* 
			   int l;
				   if(i==0) System.out.print("0"+"   |   "); 
				   else 
				   {
					   l=i-1;
				       System.out.print(s[l]+"   |   ");
				   } 
                  */
			   //------ 
			   for(j=0;j<=t.length;j++)
		   
			   {
				if(i!=0 && j!=0)
				{
					int m=i-1,n=j-1;
				   if(s[m]==t[n]) p=1; else p=-1;
				sim1=a[i-1][j]+g;   
				sim2=a[i-1][j-1]+p;
				sim3=a[i][j-1]+g; 
				
				a[i][j]=Math.max(sim1, Math.max(sim2, sim3));
		        
				}
				   
		//	  System.out.print(a[i][j] +"   |   ");
			   }
		 // System.out.println("\n __________________________________________________ ");
	   }
		   
		 //************** Calculer Score***************************			
		   
		// le max pour alignement semi global
			int maxLigne,maxColonne,max;
			int ligne=0,colonne=0; 
			
			//Calcule max derniere ligne
			maxLigne=a[a.length-1][1];
			for(i=0;i<a[0].length;i++)  
				if (a[a.length-1][i] > maxLigne && i!=0) {
					maxLigne=a[a.length-1][i];
					colonne = i;
				}
			
			// verifier si on a plusieurs valeurs = maxLigne dans la derniere ligne
			// et mettre leurs positions dans ArrayList
			ArrayList tab=new ArrayList();
			for (i = 0; i < a[0].length; i++)
				if (a[a.length - 1][i] == maxLigne) {
					tab.add(i);	
				}
			
			// choisir une des ces posisitions aléatoirement 
			int rnd = (int)(Math.random()*tab.size());
			// retourner la val de la position choisie
		    colonne= (Integer) tab.get(rnd); 
			
		 // prendre juste le max de la derniere ligne 
		    return max = a[a.length - 1][colonne];
		 		
			
		/*	//Calcule max derniere colonne
			maxColonne=a[1][a[0].length-1];
			for(i=1;i<a.length;i++)  
				if (a[i][a[0].length-1] > maxColonne) 
					{
					maxColonne=a[i][a[0].length-1];
					ligne=i;
					}

			if (maxLigne==maxColonne) max=maxLigne;
			else max=Math.max(maxLigne,maxColonne);
		 */
		 
		}
		
}