import java.lang.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


class RectangleClickHandler implements EventHandler<MouseEvent>{

	private MoteurDonnees moteurD_;
	private Case c_;
	private ArrayList<Case> voisins_;

	public RectangleClickHandler(MoteurDonnees m, Case c){
		moteurD_ = m;
		c_=c;
		voisins_ = moteurD_.getVoisins(c_);
	}

	
	public void handle(MouseEvent event){

		if(c_.getCouleur()==Color.WHITE){
			Color col; 
			if (moteurD_.getTour())
				col = Color.RED;
			else
				col = Color.BLUE;
			((Rectangle)event.getSource()).setFill(col);
						
			moteurD_.colorerCase(c_.getColonne(),c_.getLigne(),col);
			
			ClasseUnion c1,c2;

			boolean seul = true;

			System.out.println(relieComposantes());

			for(Case c : voisins_){
				if(c_.getCouleur() == c.getCouleur()){
					seul = false;
					c1 = c_.getClasseUnion().classe();
					c2 = c.getClasseUnion().classe();
					if(c1!=c2){
						c1.union(c2);
						moteurD_.unifierClasseUnion(c_.getClasseUnion(),c.getClasseUnion());
					}

				}
				if(seul){
					moteurD_.ajouterClasseUnion(c_.getClasseUnion());
				}
			}

			moteurD_.afficheComposante(c_);
			moteurD_.nombreEtoiles(c_);	
			moteurD_.existeCheminCases(c_,moteurD_.getCase(0,0));
			moteurD_.relierCasesMin(c_,moteurD_.getCase(0,0));
			ArrayList<Case> plusCourtCommeTaBite = moteurD_.plusCourtChemin(c_,moteurD_.getCase(0,0));

			moteurD_.changeTour();	
	
		}

			
	}

	// 7
	public boolean relieComposantes(){
		boolean res = false;
		int i =0;
		ClasseUnion c1,c2;
		while(i<voisins_.size() && !res){
			if(c_.getCouleur() == voisins_.get(i).getCouleur()){
				c1 = c_.getClasseUnion().classe();
				c2 = voisins_.get(i).getClasseUnion().classe();
				if(c1!=c2)
					res=true;
			}
			++i;
		}
		return res;
	}


}
