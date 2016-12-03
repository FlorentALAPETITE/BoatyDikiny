import javafx.scene.paint.Color;
import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;

class MoteurDonnees {

	private Case [] [] matriceCase_;
	private int lignes_;
	private int colonnes_;
	private boolean tour_;
	private	int nbCasesObjectif_;

	private int scoreR_;
	private int scoreB_;

	private HashSet<ClasseUnion> unionFindSetRed_;
	private HashSet<ClasseUnion> unionFindSetBlue_;

	public MoteurDonnees(int lignes, int colonnes, int nbCasesObjectif){

		matriceCase_ = new Case [colonnes][lignes];
		nbCasesObjectif_ = nbCasesObjectif;
		lignes_=lignes;
		colonnes_=colonnes;


		for(int i=0;i<colonnes;++i){
			for(int y=0;y<lignes;y++){
				matriceCase_[i][y] = new Case(i,y,Color.WHITE);				
			}
		}

		Random r = new Random();

		int casesObjectifJ1 = nbCasesObjectif;
		int casesObjectifJ2 = nbCasesObjectif;

		int randX=0;
		int randY=0;

		boolean trouveCaseVide;

		unionFindSetRed_ = new HashSet<ClasseUnion>();
		unionFindSetBlue_ = new HashSet<ClasseUnion>();

		while(casesObjectifJ1>0){
			trouveCaseVide = false;

			while(!trouveCaseVide){
				randX = r.nextInt(colonnes);
				randY = r.nextInt(lignes);

				trouveCaseVide=!matriceCase_[randX][randY].testCaseObjectif();
			}

			matriceCase_[randX][randY].setCaseObjectif(Color.RED);
			unionFindSetRed_.add(matriceCase_[randX][randY].getClasseUnion());
			casesObjectifJ1-=1;			
		}


		while(casesObjectifJ2>0){
			trouveCaseVide = false;

			while(!trouveCaseVide){
				randX = r.nextInt(colonnes);
				randY = r.nextInt(lignes);

				trouveCaseVide=!matriceCase_[randX][randY].testCaseObjectif();
			}

			matriceCase_[randX][randY].setCaseObjectif(Color.BLUE);
			unionFindSetBlue_.add(matriceCase_[randX][randY].getClasseUnion());
			casesObjectifJ2-=1;			
		}

		tour_=true; // bleu

		scoreR_ = 0;
		scoreB_ = 0;


	}

	// 1 : Colorie une case de la matrice du jeu en la couleur c
	public void colorerCase(int colonne, int ligne, Color c){
		matriceCase_[colonne][ligne].setCouleur(c);		
	}

	//2 : Affiche composante :
	public ArrayList<ClasseUnion> afficheComposante(Case c){
		if(c.getCouleur()!=Color.WHITE)
			return c.getClasseUnion().classe().parcoursClasseUnion();
		else
			return new ArrayList<ClasseUnion>();
	}

	//3 : Dit s'il existe un chemin entre deux cases :
	public void existeCheminCases(Case c1, Case c2){
		ArrayList<Case> chemin = plusCourtChemin(c1, c2);
		System.out.println(!(chemin.size()==0));
	}

	//4 : Donne le nombre min de case à colorier pour relier deux cases
	public void relierCasesMin(Case c1, Case c2){
		ArrayList<Case> chemin = plusCourtChemin(c1,c2);
		ArrayList<Case> minCaseColorier = new ArrayList<Case>();
		for(Case c : chemin){
			if(c.getCouleur() != c1.getCouleur()){
				minCaseColorier.add(c);
				System.out.println(c);
			}
		}
	}

	//5 : Affiche nombre de cases étoiles dans c :
	public void nombreEtoiles(Case c){
		System.out.println("Cases étoiles : "+c.getClasseUnion().classe().getNbObjectif());
	}

	//6 : Affiche score
	public void afficheScores(){
		System.out.println("Score rouge : "+scoreR_+", score bleu : "+scoreB_);
	}

	public Case[][] getCases(){
		return matriceCase_;
	}

	public Case getCase(int colonne, int ligne){
		return matriceCase_[colonne][ligne];
	}

	public int getLignes(){
		return lignes_;
	}

	public int getColonnes(){
		return colonnes_;
	}

	public boolean getTour(){
		return tour_;
	}

	public void changeTour(){
		tour_=!tour_;
		testVictoire();
	}

	public void testVictoire(){

		boolean rWin = false;
		int nbObjCase;
		for(ClasseUnion cu : unionFindSetRed_){
			nbObjCase = cu.getNbObjectif();
			if(nbObjCase>scoreR_)
				scoreR_=nbObjCase;
			if(nbObjCase==nbCasesObjectif_){				
				rWin = true;
				System.out.println("OLOLOL ROUGE Y GAGNE");
				break;
			}
		}

		if(!rWin){
			for(ClasseUnion cu : unionFindSetBlue_){
				nbObjCase = cu.getNbObjectif();
				if(nbObjCase>scoreB_)
					scoreB_=nbObjCase;
				if(nbObjCase==nbCasesObjectif_){				
					System.out.println("OLOLOL BLEU Y GAGNE");
					break;
				}
			}
		}
		afficheScores();
	}

	public ArrayList<Case> getVoisins(Case c){
		ArrayList<Case> res = new ArrayList<Case>();
		for(int i=c.getColonne()-1;i<c.getColonne()+2;++i){
				for(int j=c.getLigne()-1;j<c.getLigne()+2;++j){

					if(i!= c.getColonne() || j!=c.getLigne()){
						if(i>=0 && i<colonnes_ && j>=0 && j<lignes_){
							res.add(matriceCase_[i][j]);
						}
					}
				}
			}
		return res;
	}


	public void unifierClasseUnion(ClasseUnion c1, ClasseUnion c2){
		if(c1.getNbNoeud()>c2.getNbNoeud())	{
			if(c1.getRep().getCouleur()==Color.RED){
				unionFindSetRed_.remove(c2);
				ajouterClasseUnion(c1.classe());
			}
			else{
				unionFindSetBlue_.remove(c2);
				ajouterClasseUnion(c1.classe());
			}
		}
		else{
			if(c1.getRep().getCouleur()==Color.RED){
				unionFindSetRed_.remove(c1);
				ajouterClasseUnion(c2.classe());
			}
			else{
				unionFindSetBlue_.remove(c1);
				ajouterClasseUnion(c2.classe());
			}
		}
	}

	public void ajouterClasseUnion(ClasseUnion c){
		if(c.getRep().getCouleur() == Color.RED){
			if(!unionFindSetRed_.contains(c))
				unionFindSetRed_.add(c);
		}
		else{
			if(!unionFindSetBlue_.contains(c))
				unionFindSetBlue_.add(c);
		}
	}

	public int[][] inondation(Case c){
		int[][] res = new int[colonnes_][lignes_];
		int step = 1;
		res[c.getLigne()][c.getColonne()] = step;

		boolean done = false;
		ArrayList<Case> aModifier = getVoisins(c);
		ArrayList< ArrayList<Case> > modifies = new ArrayList< ArrayList<Case> >();

		ArrayList<Case> aAjouter;
		while(!done){
			++step;
			aAjouter = new ArrayList<Case>();
			
			for(Case voisin : aModifier){
				if(voisin.getCouleur() == Color.WHITE || voisin.getCouleur() == c.getCouleur()){
					res[voisin.getLigne()][voisin.getColonne()] = step;
					aAjouter.add(voisin);
				}
			}
			modifies.add(aAjouter);

			aModifier = new ArrayList<Case>();
			for(Case caca : modifies.get(modifies.size()-1)){
				for(Case voisin : getVoisins(caca)){
					if(res[voisin.getLigne()][voisin.getColonne()] == 0)
						aModifier.add(voisin);
				}
			}
			if(aModifier.size()==0)
				done = true;
		}

		return res;
	}

	public ArrayList<Case> plusCourtChemin(Case c1, Case c2){
		int [] [] inond = inondation(c1);

		ArrayList<Case> res = new ArrayList<Case>();
		if(!(c1.getCouleur() == Color.BLUE && c2.getCouleur() == Color.RED) && !(c2.getCouleur() == Color.BLUE && c1.getCouleur() == Color.RED)){
			res.add(c2);
			long minValue = 999999999;
			boolean noPath = false;
			do{
				Case currentCase = res.get(res.size()-1);
				ArrayList<Case> voisins = getVoisins(currentCase);
				int j = 0;
				boolean found = false;
				Case minCase = voisins.get(j);
				int currentValue;

				while(j<voisins.size() && !found){
					minCase = voisins.get(j);
					currentValue = inond[minCase.getColonne()][minCase.getLigne()];
					if(currentValue > 0){
						minValue = currentValue;
						found = true;
					}else{
						++j;
					}
				}

				noPath = !found;
				if(found){
					for(int i=j+1; i<voisins.size(); ++i){

						Case caseVoisine = voisins.get(i);
						currentValue = inond[caseVoisine.getColonne()][caseVoisine.getLigne()];
						if(currentValue < minValue && currentValue > 0){
							minCase = caseVoisine;
							minValue = currentValue;
						}
					}

					res.add(minCase);
				}

			}while(minValue != 1 && !noPath);
		}
		if(res.size()>1)
			return res;
		else
			return new ArrayList<Case>();
	}

	@Override
	public String toString(){
		String res="";
		for(ClasseUnion cu : unionFindSetRed_){			
			res+=cu.toString();				
		}
		for(ClasseUnion cu : unionFindSetBlue_){			
			res+=cu.toString();				
		}
		return res;
	}			

}