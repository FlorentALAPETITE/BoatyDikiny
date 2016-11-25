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

	public RectangleClickHandler(MoteurDonnees m, Case c){
		moteurD_ = m;
		c_=c;
	}

	
	public void handle(MouseEvent event){
		if(c_.getCouleur()==Color.WHITE){
			Color col; 
			if (moteurD_.getTour())
				col = Color.RED;
			else
				col = Color.BLUE;
			((Rectangle)event.getSource()).setFill(col);
			moteurD_.changeTour();
			moteurD_.colorerCase(c_.getColonne(),c_.getLigne(),col);

			ArrayList<Case> casesVoisins = moteurD_.getVoisins(c_);
			ClasseUnion c1,c2;

			boolean seul = true;

			for(Case c : casesVoisins){
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

					
		}

			
	}


}
