import javafx.scene.paint.Color;
import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;

class MoteurDonnees {

	private Case [] [] matriceCase_;
	private int lignes_;
	private int colonnes_;
	private boolean tour_;

	private HashSet<ClasseUnion> unionFindSet_;

	public MoteurDonnees(int lignes, int colonnes, int nbCasesObjectif){

		matriceCase_ = new Case [colonnes][lignes];

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

		unionFindSet_ = new HashSet<ClasseUnion>();

		while(casesObjectifJ1>0){
			trouveCaseVide = false;

			while(!trouveCaseVide){
				randX = r.nextInt(colonnes);
				randY = r.nextInt(lignes);

				trouveCaseVide=!matriceCase_[randX][randY].testCaseObjectif();
			}

			matriceCase_[randX][randY].setCaseObjectif(Color.RED);
			unionFindSet_.add(matriceCase_[randX][randY].getClasseUnion());
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
			unionFindSet_.add(matriceCase_[randX][randY].getClasseUnion());
			casesObjectifJ2-=1;			
		}

		tour_=true; // bleu


	}

	// 1 : Colorie une case de la matrice du jeu en la couleur c
	public void colorerCase(int colonne, int ligne, Color c){
		matriceCase_[colonne][ligne].setCouleur(c);		
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

	@Override
	public String toString(){
		String res="";
		for(int i=0;i<colonnes_;++i){
			for(int y=0;y<lignes_;y++){
				res+=matriceCase_[i][y].toString();
			}
			res+="\n";
		}
		return res;
	}			

}