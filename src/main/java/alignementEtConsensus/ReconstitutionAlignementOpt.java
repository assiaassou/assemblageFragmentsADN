package alignementEtConsensus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import rechercheDesChevauchements.FragmentADN;
import rechercheDesChevauchements.PositionScore;
import rechercheDesChevauchements.ResultatProgDynam;

public class ReconstitutionAlignementOpt {
private ArrayList<Integer> cheminAmiltonien= new ArrayList<Integer> ();
private ArrayList<FragmentADN>  listeTousFragEtCompInv = new ArrayList<FragmentADN>();

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

public Map reconstitutionAlignementOpt(){

	 
			 Map<CoupleFragments, char [][]  > mapTousLesAlignementsOptimaux=new HashMap<CoupleFragments, char [][] >();
	// Map contient la position de chaque case ds la matrice de programmation dynamique et positions de ses sources
	Map<PositionScore, ArrayList<PositionScore>  > mapPositionsCasesEtSources=new HashMap<PositionScore, ArrayList <PositionScore> >();
	
	
	ResultatProgDynam resultatProgDynam=new ResultatProgDynam();
	
	for (int i=0;i<this.cheminAmiltonien.size()-1;i++){
		char [] acidesS=this.listeTousFragEtCompInv.get(this.cheminAmiltonien.get(i)-1).getAcides();
		char [] acidesT=this.listeTousFragEtCompInv.get(this.cheminAmiltonien.get(i+1)-1).getAcides();
	//	char [] acidesS=this.listeTousFragEtCompInv.get(16).getAcides();
	//	char [] acidesT=this.listeTousFragEtCompInv.get(0).getAcides();
	//	char [] acidesK=this.listeTousFragEtCompInv.get(21).getAcides();
		
		int idS=this.cheminAmiltonien.get(i);
		int idT=this.cheminAmiltonien.get(i+1);
		
	/*
	//char []acidesS={'c','a','g','c','g','t','g','g'};
	//char [] acidesT={'c','a','g','c','a','c','t','t','g','g','a','t','t','c','t','c','g','g'};
		char [] acidesT={'a','g','c'};
		char [] acidesS={'a','a','a','c'};
		int idS=0;
		int idT=0+1; 	*/	
		
	resultatProgDynam=programmationDynamique(acidesS,acidesT);
	mapPositionsCasesEtSources=resultatProgDynam.getMapPositionsCasesEtSources();
	PositionScore meilleurScore=new PositionScore();
	meilleurScore=score(resultatProgDynam.getA());
	
	//------------------reconsituter l'alignement------------------------ 
	//----------premiere etape-----------------------  
	  ArrayList tableAlignementOptimal = new ArrayList(); 
	  tableAlignementOptimal.add(meilleurScore);
	  ArrayList<ArrayList<PositionScore>> listAlignementsPossibles=new ArrayList<ArrayList<PositionScore>>();
	  Set<PositionScore> listKeys2=mapPositionsCasesEtSources.keySet();  // Obtenir la liste des clés
      //ajouter le meilleur score a la premier case du tableau	
	  listAlignementsPossibles= ReconstituerAlignement (tableAlignementOptimal, meilleurScore,listKeys2,mapPositionsCasesEtSources,listAlignementsPossibles);
     
	//----------Deuxieme étape -----------------------------------------   
	  // le choix un alignement optimal parmi +ieurs possiblité sera aléatoire 
	 // on choisi un numéro de possibilité
	  int alignementChoisi=(int) (Math.random() * listAlignementsPossibles.size());
	  
	  CoupleFragments couplefragments=new CoupleFragments(idS,idT);
	  
	/*  System.out.println("************************************************");
	  System.out.println("Alignements du couple : ("+this.listeTousFragEtCompInv.get(i).getId()+","+this.listeTousFragEtCompInv.get(i+1).getId()+")");
	  System.out.println("le nbr d'alignements possibles est:"+listAlignementsPossibles.size());
	  System.out.println("************************************************");
	
	  System.out.println("POSSIBILITE N  " + alignementChoisi); */
		 ReconstituerAlignement2(listAlignementsPossibles.get(alignementChoisi),acidesS,acidesT);
	 
	//--------------Fin----------------------------------- 
	  

	 char [][] matriceAl=ReconstituerAlignement2(listAlignementsPossibles.get(alignementChoisi),acidesS,acidesT);
	 
	
	 mapTousLesAlignementsOptimaux.put(couplefragments,matriceAl);
	
	  
	 	  
		
	//----------------------------------
	}
	return mapTousLesAlignementsOptimaux;	
	
}
		
		
		
	public  ResultatProgDynam programmationDynamique(char[] s,char[] t){
		// Map contient la position de chaque case ds la matrice de programmation dynamique et positions de ses sources
		   Map<PositionScore, ArrayList<PositionScore>  > mapPositionsCasesEtSources=new HashMap<PositionScore, ArrayList <PositionScore> >();
		
		   int[][] a= new int[s.length+1][t.length+1];   
		   int ll=s.length, gg=t.length+1;
		   
		//   System.out.println("   la langueure est    "+ll);
		//   System.out.println("   la largeure est    "+gg);
		   
		   //initialisation de la matrice a
		   int i=0;
		   int j=0;
		   ArrayList  <PositionScore> listePossiblités=new ArrayList<PositionScore>();	
	       PositionScore positionScore=new PositionScore(i,j,a[i][j]);
		   for(i=0;i<=s.length;i++) 
			   {
			   a[i][0]=0;
			 //  a[i][0]=i*(-2);// juste pour tester a supprimer 
			   positionScore=new PositionScore(i,0,a[i][0]);//ajouter juste pr tester exple
			   mapPositionsCasesEtSources.put(positionScore, listePossiblités);//ajouter juste pr tester exple
			   }
		   
		   for(j=0;j<=t.length;j++) 
			   {
			   a[0][j]=0;
			//  a[0][j]=j*(-2);
			   positionScore=new PositionScore(0,j,a[0][j]);//ajouter juste pr tester exple
			   mapPositionsCasesEtSources.put(positionScore, listePossiblités);//ajouter juste pr tester exple
			   }
		   
		   // l'affichage de t
		   
	/*	   System.out.print("      ");
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
			 
		/*	   int l;
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
				
		
				positionScore=new PositionScore(i,j,a[i][j]);
				listePossiblités=new ArrayList<PositionScore>();
		        if (a[i][j]==sim1) {
		        	PositionScore posConduitScore=new PositionScore(i-1,j,g); 
		        	listePossiblités.add(posConduitScore);
		        	}
		        if (a[i][j]==sim2) {
		        	PositionScore posConduitScore=new PositionScore(i-1,j-1,p); 	
		        	listePossiblités.add(posConduitScore);
		        	}
		        if (a[i][j]==sim3) {	
		        	PositionScore posConduitScore=new PositionScore(i,j-1,g); 
		        	listePossiblités.add(posConduitScore);
		        	}
		    
		       mapPositionsCasesEtSources.put(positionScore, listePossiblités);
		        
		        
				}
				   
		//	  System.out.print(a[i][j] +"   |   ");
			   }
		//  System.out.println("\n __________________________________________________ ");
		   
		   
		
	   }
		   
		// Afficher le contenu du MAP
		   
		   /*
		   	Set<PositionScore> listKeys=mapPositionsCasesEtSources.keySet();  // Obtenir la liste des clés
		   	Iterator<PositionScore> iterateur=listKeys.iterator(); //premier iterateur
		   	
		  while(iterateur.hasNext())
			{
				PositionScore key= iterateur.next();
				
				System.out.println (key+"\n =>\n ");
				
				for( i = 0; i < mapPositionsCasesEtSources.get(key).size(); i++)
				   {
				   System.out.println("donnée à l'indice " + i + " = " +mapPositionsCasesEtSources.get(key).get(i));
			       } 
				System.out.println("----------------------------------" );
			}
			*/
		   //----------------------------
		 
		  return new ResultatProgDynam(a,mapPositionsCasesEtSources);
		}
	//************** Calculer Score***************************	
		public  PositionScore score(int[][] a){
			
			// le max pour alignement semi global
			int i,maxLigne,maxColonne,max;
			int ligne=0,colonne=0;
			
			PositionScore positionMax=new PositionScore();
			//Calcule max derniere ligne
			maxLigne=a[a.length-1][1];
			for(i=0;i<a[0].length;i++)  
				if (a[a.length-1][i] > maxLigne && i!=0) {
					maxLigne=a[a.length-1][i];
					colonne = i;
				}
			
			//Calcule max derniere colonne
			maxColonne=a[1][a[0].length-1];
			for(i=1;i<a.length;i++)  
				if (a[i][a[0].length-1] > maxColonne) 
					{
					maxColonne=a[i][a[0].length-1];
					ligne=i;
					}

			max=Math.max(maxLigne,maxColonne);
			if (max==maxLigne)  {
				positionMax.setPositionLigne(a.length-1);
				positionMax.setPositionColonne(colonne);
				positionMax.setScoreCalculé(max);
				return positionMax;
			}
			else  {
				positionMax.setPositionLigne(ligne);
				positionMax.setPositionColonne(a[0].length-1);
				positionMax.setScoreCalculé(max);
				return positionMax;
			} 
			
		}
//--------------------------------------------
		//*********************	
		public static   ArrayList<ArrayList<PositionScore>> ReconstituerAlignement (ArrayList tableAlignementOptimal, PositionScore clé , Set<PositionScore> listKeys,
	            Map<PositionScore, ArrayList<PositionScore>  > myMap2, ArrayList<ArrayList<PositionScore>> listAlignementsPossibles){


	//pour reconsituter l'alignement 
	if (clé.getPositionLigne()==0 || clé.getPositionColonne()==0){	
	//System.out.println(" IF111  ligne meilleur score est  " + clé.getPositionLigne()+" colonne  meilleur score est  " + clé.getPositionColonne());
	return listAlignementsPossibles;

	}
	else
	{//System.out.println(" ELSE  ligne meilleur score est  " + clé.getPositionLigne()+" colonne  meilleur score est  " + clé.getPositionColonne());

	//pour reconsituter l'alignement f
	Iterator<PositionScore> iterateur=listKeys.iterator(); //premier iterateur

	while(iterateur.hasNext())
	{

	PositionScore key= iterateur.next();
	//System.out.println(key.getPositionLigne()+"   "+clé.getPositionLigne() +"   "+ key.getPositionColonne()+"   "+clé.getPositionColonne());
	if(key.getPositionLigne()==clé.getPositionLigne() && key.getPositionColonne()==clé.getPositionColonne())
	{
		
	if (myMap2.get(key).size()==1)
	{
	ArrayList tableAlignementOptimal1 = new ArrayList();
	tableAlignementOptimal1=updateAlignementOptimal(tableAlignementOptimal, myMap2.get(key).get(0), listAlignementsPossibles);
	clé=myMap2.get(key).get(0);
	return ReconstituerAlignement (tableAlignementOptimal1,clé,listKeys,myMap2,listAlignementsPossibles);
	}
	if (myMap2.get(key).size()==2)
	{
	ArrayList tableAlignementOptimal1 = new ArrayList();
	tableAlignementOptimal1=updateAlignementOptimal(tableAlignementOptimal,myMap2.get(key).get(0), listAlignementsPossibles);
	clé=myMap2.get(key).get(0);
	ReconstituerAlignement (tableAlignementOptimal1,clé,listKeys,myMap2,listAlignementsPossibles);
	ArrayList tableAlignementOptimal2 = new ArrayList();
	tableAlignementOptimal2=updateAlignementOptimal(tableAlignementOptimal,myMap2.get(key).get(1), listAlignementsPossibles);
	clé=myMap2.get(key).get(1);
	return ReconstituerAlignement (tableAlignementOptimal2,clé,listKeys,myMap2,listAlignementsPossibles);
	}

	if (myMap2.get(key).size()==3)
	{
	ArrayList tableAlignementOptimal1 = new ArrayList();
	tableAlignementOptimal1=updateAlignementOptimal(tableAlignementOptimal,myMap2.get(key).get(0), listAlignementsPossibles);
	clé=myMap2.get(key).get(0);
	ReconstituerAlignement (tableAlignementOptimal1,clé,listKeys,myMap2,listAlignementsPossibles);
	//--------------------------	
	ArrayList tableAlignementOptimal2 = new ArrayList();
	tableAlignementOptimal2=updateAlignementOptimal(tableAlignementOptimal,myMap2.get(key).get(1), listAlignementsPossibles);
	clé=myMap2.get(key).get(1);
	ReconstituerAlignement (tableAlignementOptimal2,clé,listKeys,myMap2,listAlignementsPossibles);
	//----------------------------
	ArrayList tableAlignementOptimal3 = new ArrayList();
	tableAlignementOptimal3=updateAlignementOptimal(tableAlignementOptimal,myMap2.get(key).get(2), listAlignementsPossibles);
	clé=myMap2.get(key).get(2);
	return ReconstituerAlignement (tableAlignementOptimal3,clé,listKeys,myMap2,listAlignementsPossibles);
	}

	
	}  

	}
	}
	return listAlignementsPossibles;


	}

	public static ArrayList updateAlignementOptimal(ArrayList<PositionScore> tableAlignementOptimal,PositionScore source, ArrayList<ArrayList<PositionScore>> listAlignementsPossibles){
	ArrayList table = new ArrayList();
	int i,j;

	for( i = 0; i < tableAlignementOptimal.size(); i++)
	{
	table.add(tableAlignementOptimal.get(i));
	}

	table.add(source);
	if (source.getPositionLigne()==0 || source.getPositionColonne()==0)
	{
	listAlignementsPossibles.add(table); //mettre alignement optimal dans la liste des alignements optimaux possibles
	}
	return table;

	}

	public static char[][] ReconstituerAlignement2(ArrayList<PositionScore> tableAlignementOptimal,char s[],char t[]){
	int h=0;
	ArrayList<Character> sAlignementOpt = new ArrayList<Character>();
	ArrayList<Character>  tAlignementOpt = new ArrayList<Character>();


	while ( h < tableAlignementOptimal.size() && tableAlignementOptimal.get(h).getPositionLigne()!=0 && tableAlignementOptimal.get(h).getPositionColonne()!=0 )
	{
	if (tableAlignementOptimal.get(h+1).getScoreCalculé()==1)
	{
	sAlignementOpt.add(s[tableAlignementOptimal.get(h).getPositionLigne()-1]);
	tAlignementOpt.add(t[tableAlignementOptimal.get(h).getPositionColonne()-1]);

	}

	if (tableAlignementOptimal.get(h+1).getScoreCalculé()==-2)
	{
	if (tableAlignementOptimal.get(h+1).getPositionLigne()==tableAlignementOptimal.get(h).getPositionLigne())
	{

	sAlignementOpt.add('-');

	tAlignementOpt.add(t[tableAlignementOptimal.get(h).getPositionColonne()-1]);

	}else if (tableAlignementOptimal.get(h+1).getPositionColonne()==tableAlignementOptimal.get(h).getPositionColonne())
	{

	sAlignementOpt.add(s[tableAlignementOptimal.get(h).getPositionLigne()-1]);

	tAlignementOpt.add('-');
	}
	}

	if (tableAlignementOptimal.get(h+1).getScoreCalculé()==-1)
	{

	sAlignementOpt.add(s[tableAlignementOptimal.get(h).getPositionLigne()-1]);


	tAlignementOpt.add(t[tableAlignementOptimal.get(h).getPositionColonne()-1]);

	}
	h++;

	}
	System.out.println("\n \n \n ALIGNEMENT OPTIMAL EST :");
	System.out.print("sAlignementOpt=");
	int i;
	for( i = sAlignementOpt.size()-1; i >= 0; i--)
	{

	System.out.print(sAlignementOpt.get(i));

	}
	System.out.println();

	System.out.print("tAlignementOpt=");
	for( i = tAlignementOpt.size()-1; i >= 0; i--)
	{

	System.out.print(tAlignementOpt.get(i));

	}	
	System.out.println("\n \n -------------------------------------");


	//Affichage de l'alignement obtenu  de S
	int j=sAlignementOpt.size()-1;
	int test; //test =1 si i+1==tableAlignementOptimal.get(h).getPositionLigne() donc lettre existe ds alignement 
	ArrayList<Character>   ssAlignementOpt = new ArrayList<Character>();
	ArrayList<Character>   ttAlignementOpt = new ArrayList<Character>();

	i=0;
	while (i< s.length){
	test=0; 	
	j=0;
	while (j< tableAlignementOptimal.size()-1){	
	if (i+1==tableAlignementOptimal.get(j).getPositionLigne()) test=1;  //!!!!!
	j++;
	}	     
	if (test==0){
	ssAlignementOpt.add(s[i]); 		
	i++;
	}
	if (test==1){
	j=sAlignementOpt.size()-1;
	while (j>= 0){	   
	ssAlignementOpt.add(sAlignementOpt.get(j));  
	if (!sAlignementOpt.get(j).equals('-')) i++; 
	j--;
	}
	}
	}
	
	//affichage de l'alignement obtenu  de T	
	j=tAlignementOpt.size()-1;
	
	i=0;
	while (i< t.length){
	test=0; 	
	j=0;
	while (j< tableAlignementOptimal.size()-1){	
	if (i+1==tableAlignementOptimal.get(j).getPositionColonne()) test=1;
	j++;
	}	     
	if (test==0){
	ttAlignementOpt.add(t[i]); 
	i++;
	}
	if (test==1){
	j=tAlignementOpt.size()-1;
	while (j>= 0){	   
	ttAlignementOpt.add(tAlignementOpt.get(j));  
	if (!tAlignementOpt.get(j).equals('-')) i++; 
	j--;
	}
	}
	}
	
	//--------------------la matrice qui contient l'alignement-----------------------------

	//Calculer la taille de la matrice qui contient l'alignement
	
	
	int ligneDebutAlignS=tableAlignementOptimal.get(tableAlignementOptimal.size()-2).getPositionLigne();
	int nbrColonneMatriceAlignement;
	if (ligneDebutAlignS!=1) nbrColonneMatriceAlignement=(tableAlignementOptimal.get(tableAlignementOptimal.size()-2).getPositionLigne())+(ttAlignementOpt.size()-1);
	else nbrColonneMatriceAlignement=(tableAlignementOptimal.get(tableAlignementOptimal.size()-2).getPositionColonne())+(ssAlignementOpt.size()-1);
	int difference=ssAlignementOpt.size()-nbrColonneMatriceAlignement;
	if (difference>0) nbrColonneMatriceAlignement=nbrColonneMatriceAlignement+difference;
	
	
	//Remplire la matrice

	char[][] matriceAlignement= new char [2][nbrColonneMatriceAlignement];

  
	//Colonnes de debut et fin S dans la matrice d'alignement
	int colonneDebutS=tableAlignementOptimal.get(tableAlignementOptimal.size()-2).getPositionColonne()-1;	
	int colonneFinS=colonneDebutS+ssAlignementOpt.size();
	
	
	
	i = colonneDebutS;
	j = 0;
	while(i < colonneFinS-1)
	while (  j < ssAlignementOpt.size())
	{			 
	matriceAlignement[0][i]=ssAlignementOpt.get(j);
	i++;
	j++;
	}



	//Colonnes de debut et fin t dans la matrice d'alignement
	int colonneDebutT=tableAlignementOptimal.get(tableAlignementOptimal.size()-2).getPositionLigne()-1;	
	int colonneFinT=colonneDebutT+ttAlignementOpt.size();	


	i = colonneDebutT;
	j = 0;
	while(i < colonneFinT-1)
	while (  j < ttAlignementOpt.size())
	{			 
	matriceAlignement[1][i]=ttAlignementOpt.get(j);
	i++;
	j++;
	}


	//Affichage   mettre des points ds les cases null de la matrice 		
	for( i= 0; i < 2; i++)
	for( j = 0; j < nbrColonneMatriceAlignement; j++)
	{
	if (matriceAlignement[i][j]==0)
	matriceAlignement[i][j]='.';
	} 


	System.out.println();	
	System.out.println("la matrice est");
	for( i= 0; i < 2; i++)
	{
	for( j = 0; j < nbrColonneMatriceAlignement; j++)
	{
	System.out.print(matriceAlignement[i][j]);
	}
	System.out.println();
	}

	return matriceAlignement;

	}	
		
		
		
		
//fin		
}





