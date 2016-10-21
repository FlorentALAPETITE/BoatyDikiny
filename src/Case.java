import javafx.scene.paint.Color;


class Case {

	private int x_;
	private int y_;

	private Color couleur_;

	private boolean caseObjectif_;

	public Case(int x, int y, Color col){
		x_=x;
		y_=y;
		couleur_=col;
		caseObjectif_ = false;
	}


	public boolean testCaseObjectif(){
		return caseObjectif_;
	}


	public void setCaseObjectif(Color col){
		caseObjectif_=true;
		couleur_=col;
	}

	public Color getCouleur(){
		return couleur_;
	}

	public void setCouleur(Color c){
		couleur_=c;
	}


	@Override
	public String toString(){
		if(couleur_ == Color.BLUE)
			return "+";
		else if (couleur_ == Color.RED)
			return "-";
		else 
			return "#";
	}


}