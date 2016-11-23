import java.lang.*;
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

			// for(int i=c_.getColonne()-1,i<c_.getColonne()+2,++i){
			// 	for(int j=c_.getLigne()-1,j<c_.getLigne()+2,++j){
			// 		if(i!= c_.getColonne() || j!=c_.getLigne()){
			// 			if(i>0 &&)
			// 		}
			// 	}
			// }
		}

			
	}


}
