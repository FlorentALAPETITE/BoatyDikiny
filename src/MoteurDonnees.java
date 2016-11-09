import javafx.scene.paint.Color;
import java.util.Random;


class MoteurDonnees {

	private Case [] [] matriceCase_;
	private int lignes_;
	private int colonnes_;
	private boolean tour_;

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

		while(casesObjectifJ1>0){
			trouveCaseVide = false;

			while(!trouveCaseVide){
				randX = r.nextInt(colonnes);
				randY = r.nextInt(lignes);

				trouveCaseVide=!matriceCase_[randX][randY].testCaseObjectif();
			}

			matriceCase_[randX][randY].setCaseObjectif(Color.RED);
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