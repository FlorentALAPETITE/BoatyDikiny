import javafx.scene.paint.Color;
import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;
import java.lang.Math;

class MoteurDonnees {

	private Case [] [] matriceCase_;
	private int lignes_;
	private int colonnes_;
	private boolean tour_;
	private	int nbCasesObjectif_;

	private int scoreR_;
	private int scoreB_;

	private String victoire;

	private String typeJeu;

	private HashSet<ClasseUnion> unionFindSetRed_;
	private HashSet<ClasseUnion> unionFindSetBlue_;

	public MoteurDonnees(int lignes, int colonnes, int nbCasesObjectif){

		victoire ="";

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

		ClasseUnion c1,c2;

		boolean seul;
		ArrayList<Case> voisins_;
		Case c_;

		while(casesObjectifJ1>0){
			seul=true;
			trouveCaseVide = false;

			while(!trouveCaseVide){
				randX = r.nextInt(colonnes);
				randY = r.nextInt(lignes);

				trouveCaseVide=!matriceCase_[randX][randY].testCaseObjectif();
			}

			matriceCase_[randX][randY].setCaseObjectif(Color.RED);
			unionFindSetRed_.add(matriceCase_[randX][randY].getClasseUnion());
			casesObjectifJ1-=1;	

			c_ = getCase(randX,randY);
			voisins_ = getVoisins(c_);

			for(Case c : voisins_){
				if(c_.getCouleur() == c.getCouleur()){
					seul = false;
					c1 = c_.getClasseUnion().classe();
					c2 = c.getClasseUnion().classe();
					if(c1!=c2){
						c1.union(c2);
						unifierClasseUnion(c_.getClasseUnion(),c.getClasseUnion());
					}

				}
				if(seul){
					ajouterClasseUnion(c_.getClasseUnion());
				}
			}		
		}


		while(casesObjectifJ2>0){
			seul=true;
			trouveCaseVide = false;

			while(!trouveCaseVide){
				randX = r.nextInt(colonnes);
				randY = r.nextInt(lignes);

				trouveCaseVide=!matriceCase_[randX][randY].testCaseObjectif();
			}

			matriceCase_[randX][randY].setCaseObjectif(Color.BLUE);
			unionFindSetBlue_.add(matriceCase_[randX][randY].getClasseUnion());
			casesObjectifJ2-=1;		

			c_ = getCase(randX,randY);
			voisins_ = getVoisins(c_);

			for(Case c : voisins_){
				if(c_.getCouleur() == c.getCouleur()){
					seul = false;
					c1 = c_.getClasseUnion().classe();
					c2 = c.getClasseUnion().classe();
					if(c1!=c2){
						c1.union(c2);
						unifierClasseUnion(c_.getClasseUnion(),c.getClasseUnion());
					}

				}
				if(seul){
					ajouterClasseUnion(c_.getClasseUnion());
				}
			}			
		}

		tour_=true; // bleu

		testVictoire();


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
	public boolean existeCheminCases(Case c1, Case c2){
		ArrayList<Case> chemin = plusCourtChemin(c1, c2);
		return !(chemin.size()==0);
	}

	//4 : Donne le nombre min de case à colorier pour relier deux cases
	public ArrayList<Case> relierCasesMin(Case c1, Case c2){
		ArrayList<Case> chemin = plusCourtChemin(c1,c2);
		ArrayList<Case> minCaseColorier = new ArrayList<Case>();
		for(Case c : chemin){
			if(c.getCouleur() != c1.getCouleur()){
				minCaseColorier.add(c);				
			}
		}

		return minCaseColorier;
	}

	//5 : Affiche nombre de cases étoiles dans c :
	public int nombreEtoiles(Case c){
		if(c.getCouleur()!=Color.WHITE)
			return c.getClasseUnion().classe().getNbObjectif();
		else
			return 0;
	}


	public void setTypeJeu(String typeJ){
		typeJeu = typeJ;
	}

	public int getScoreR(){
		return scoreR_;
	}

	public int getScoreB(){
		return scoreB_;
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


		if(typeJeu == "ia"){

			// FAIRE IA ICI


		}


	}

	public String getVictoire(){
		return victoire;
	}

	public void testVictoire(){

		int nbObjCase;
		for(ClasseUnion cu : unionFindSetRed_){
			nbObjCase = cu.getNbObjectif();
			if(nbObjCase>scoreR_)
				scoreR_=nbObjCase;
			if(nbObjCase==nbCasesObjectif_){				
				victoire = "rouge";				
				break;
			}
		}

		if(victoire!="rouge"){
			for(ClasseUnion cu : unionFindSetBlue_){
				nbObjCase = cu.getNbObjectif();
				if(nbObjCase>scoreB_)
					scoreB_=nbObjCase;
				if(nbObjCase==nbCasesObjectif_){	
					victoire="bleu";					
					break;
				}
			}
		}		
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

	public ArrayList<Case> plusCourtChemin(Case c1, Case c2){

		ArrayList<AEtoile> openList = new ArrayList<AEtoile>();
		ArrayList<AEtoile> closedList = new ArrayList<AEtoile>();

		double minValue;
		AEtoile current;
		openList.add(new AEtoile(c1, null, -1,-1));
		AEtoile target = new AEtoile(c2, null, -1, -1);
		while(openList.size() > 0 && !closedList.contains(target)){
			current = openList.get(0);
			minValue = current.getF();
			for(AEtoile ae : openList){
				if(ae.getF()<minValue){
					current = ae;
					minValue = ae.getF();
				}
			}
			openList.remove(current);
			closedList.add(current);

			for(Case v: getVoisins(current.getCase())){
				if(!closedList.contains(new AEtoile(v, current, -1, -1))){
					if(v.getCouleur() == Color.WHITE || v.getCouleur() == c1.getCouleur()){

						double heuristique = Math.abs(v.getColonne()-c2.getColonne())+Math.abs(v.getLigne()-c2.getLigne());
						AEtoile toAdd;
						if(v.getCouleur() == Color.WHITE)
							toAdd = new AEtoile(v, current, current.getG()+10, heuristique);
						else
							toAdd = new AEtoile(v, current, current.getG(), heuristique);

						if(!openList.contains(toAdd))
							openList.add(toAdd);

						else {
							int index = openList.indexOf(toAdd);
							if (current.getG()+10<openList.get(index).getG())
							openList.get(index).update(current.getG()+10, openList.get(index).getH());
						}
					}
				}
			}
		}

		int targetIndex = closedList.indexOf(target);

		if(targetIndex == -1)
			return new ArrayList<Case>();

		target = closedList.get(targetIndex);

		ArrayList<Case> res = new ArrayList<Case>();
		res.add(target.getCase());

		AEtoile parent = target.getParent();

		while(parent != null){
			res.add(parent.getCase());
			parent = parent.getParent();
		}

		return res;
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