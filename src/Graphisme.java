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
import java.util.ArrayList;

public class Graphisme extends Application {

	private MoteurDonnees d_;
	private int h_;
	private int w_;
	private int tailleCase_;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		h_ = 800;
		w_ = 800;
		d_ = new MoteurDonnees(15,15,2);

		
		StackPane root = new StackPane();

		Pane plateau = new Pane();
		Scene scene = new Scene(root, h_, w_, Color.BLACK);
		tailleCase_ = h_/Math.max(d_.getLignes(), d_.getColonnes())-1;

		Case[][] matrice = d_.getCases();
		
		Rectangle r;
		Canvas canvas;
		GraphicsContext gc;

		ArrayList<Canvas> canvasList = new ArrayList<Canvas>();

		for(int i=0; i<d_.getColonnes(); ++i){
			for(int j=0; j<d_.getLignes(); ++j){
				r = new Rectangle(i*(tailleCase_+1), j*(tailleCase_+1), tailleCase_, tailleCase_);
				r.setFill(matrice[i][j].getCouleur());
				r.addEventHandler(MouseEvent.MOUSE_PRESSED,new RectangleClickHandler(d_,d_.getCase(i,j)));
				plateau.getChildren().add(r);

				if(matrice[i][j].getCouleur()!=Color.WHITE){
					canvas = new Canvas(tailleCase_, tailleCase_);
					gc = canvas.getGraphicsContext2D();
					gc.setFill(Color.BLACK);
	  				gc.setLineWidth(4);
	  				gc.strokeLine(tailleCase_/4,tailleCase_/4,(tailleCase_)-tailleCase_/4,(tailleCase_)-tailleCase_/4);
	  				gc.strokeLine((tailleCase_)-tailleCase_/4,tailleCase_/4,tailleCase_/4,(tailleCase_)-tailleCase_/4);
	  				gc.strokeLine(tailleCase_/2,tailleCase_/4-tailleCase_/10,tailleCase_/2,(tailleCase_)-tailleCase_/4+tailleCase_/10);
	  				gc.strokeLine(tailleCase_/4-tailleCase_/10,tailleCase_/2,(tailleCase_)-tailleCase_/4+tailleCase_/10,tailleCase_/2);
	  				canvas.setTranslateX(-((h_/2)-i*(tailleCase_+1))+tailleCase_/2);
   					canvas.setTranslateY(-((w_/2)-j*(tailleCase_+1))+tailleCase_/2);
	  				canvasList.add(canvas);
	  			}

			}
		}

		root.getChildren().add(plateau);	

		for(Canvas c : canvasList){
			root.getChildren().add(c);	
		}	
		

		primaryStage.setTitle("Improved-Potatoe");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}