import javafx.scene.paint.Color;
import java.util.Random;


class MoteurDonnees {

	private Case [] [] matriceCase;
	private int lignes_;
	private int colonnes_;


	public MoteurDonnees(int lignes, int colonnes, int nbCasesObjectif){

		matriceCase = new Case [colonnes][lignes];

		lignes_=lignes;
		colonnes_=colonnes;


		for(int i=0;i<colonnes;++i){
			for(int y=0;y<lignes;y++){
				matriceCase[i][y] = new Case(i+1,y+1,Color.WHITE);
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

				trouveCaseVide=!matriceCase[randY][randX].testCaseObjectif();
			}

			matriceCase[randY][randX].setCaseObjectif(Color.RED);
			casesObjectifJ1-=1;			
		}


		while(casesObjectifJ2>0){
			trouveCaseVide = false;

			while(!trouveCaseVide){
				randX = r.nextInt(colonnes);
				randY = r.nextInt(lignes);

				trouveCaseVide=!matriceCase[randY][randX].testCaseObjectif();
			}

			matriceCase[randY][randX].setCaseObjectif(Color.BLUE);
			casesObjectifJ2-=1;			
		}


	}

	// 1 : Colorie une case de la matrice du jeu en la couleur c
	public void colorerCase(int x, int y, Color c){
		matriceCase[y][x].setCouleur(c);
	}

	public Case[][] getCases(){
		return matriceCase;
	}

	public int getLignes(){
		return lignes_;
	}

	public int getColonnes(){
		return colonnes_;
	}

	@Override
	public String toString(){
		String res="";
		for(int i=0;i<colonnes_;++i){
			for(int y=0;y<lignes_;y++){
				res+=matriceCase[i][y].toString();
			}
			res+="\n";
		}
		return res;
	}			

}