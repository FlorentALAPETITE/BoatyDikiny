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


	}

	// 1 : Colorie une case de la matrice du jeu en la couleur c
	public void colorerCase(int colonne, int ligne, Color c){
		matriceCase_[colonne][ligne].setCouleur(c);		
	}

	//2 : Affiche composante :
	public void afficheComposante(Case c){
		System.out.println(c.getClasseUnion().classe());
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
		for(ClasseUnion cu : unionFindSetRed_){
			if(cu.getNbObjectif()==nbCasesObjectif_){
				rWin = true;
				System.out.println("OLOLOL ROUGE Y GAGNE");
				break;
			}
		}

		if(!rWin){			
			boolean bWin = false;		
			for(ClasseUnion cu : unionFindSetBlue_){
				if(cu.getNbObjectif()==nbCasesObjectif_){
					bWin = true;
					System.out.println("OLOLOL BLEU Y GAGNE");
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