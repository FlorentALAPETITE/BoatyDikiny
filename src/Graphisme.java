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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import java.io.File;
import javafx.scene.layout.GridPane;


public class Graphisme extends Application {

	private MoteurDonnees d_;
	private int h_;
	private int w_;
	private int tailleCase_;
	private int nbLignes;
	private int nbColonnes;
	private int nbObj;
	private Spinner<Integer> spinnerColonne;
	private Spinner<Integer> spinnerLigne;
	private Spinner<Integer> spinnerObjectif;
	private ArrayList< ArrayList<Rectangle>> rectangleList;

	private String selectedButton;

	private Button bComposante;

	private Button bExisteChemin;
	private TextField existeCheminLabel;

	private Button bPlusCourtChemin;

	private Button bNombreEtoiles;
	private TextField nombreEtoilesLabel;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		
 		Pane root = new Pane();

	    spinnerColonne = new Spinner<Integer>(5, 15, 9);
	    spinnerColonne.setLayoutX(200);
	    spinnerColonne.setLayoutY(175);

		spinnerLigne = new Spinner<Integer>(5, 15, 9);
		spinnerLigne.setLayoutX(200);
	    spinnerLigne.setLayoutY(225);

	    spinnerObjectif = new Spinner<Integer>(2, 5, 3);
		spinnerObjectif.setLayoutX(200);
	    spinnerObjectif.setLayoutY(275);

	   	Label nbCol = new Label("Nombre de colonnes :");
	   	nbCol.setLayoutX(20);
	    nbCol.setLayoutY(180);
	  
	    Label nbLig = new Label("Nombre de lignes :");
	    nbLig.setLayoutX(20);
	    nbLig.setLayoutY(230);

	    Label nbObj = new Label("Cases objectif :");
	    nbObj.setLayoutX(20);
	    nbObj.setLayoutY(280);


	    Button btn = new Button();
        btn.setText("Lancer partie");
        btn.setOnAction(new LaunchButtonHandler(this, primaryStage));
        btn.setLayoutX(150);
        btn.setLayoutY(330);

	    Scene scene = new Scene(root, 400, 400,Color.BLACK);
	    

	    root.getChildren().add(nbCol);
	    root.getChildren().add(nbLig);
	    root.getChildren().add(nbObj);
	    root.getChildren().add(spinnerColonne);
	    root.getChildren().add(spinnerLigne);
	    root.getChildren().add(spinnerObjectif);
	  	root.getChildren().add(btn);

	  	primaryStage.setResizable(false);
		primaryStage.setTitle("Improved-Potatoe");
		primaryStage.setScene(scene);
		primaryStage.show();

		
	}


	public void loadGame(Stage primaryStage){

		d_ = new MoteurDonnees(nbLignes,nbColonnes,nbObj);

		h_ = 800;
		w_ = 800;	

		selectedButton = "";

		StackPane root = new StackPane();

		Pane plateau = new Pane();
		plateau.setStyle("-fx-background-color: black;");
		Scene scene = new Scene(root, h_+300, w_);
		
		tailleCase_ = h_/Math.max(d_.getLignes(), d_.getColonnes())-1;

		Case[][] matrice = d_.getCases();
		
		Rectangle r;
		Canvas canvas;
		GraphicsContext gc;

		ArrayList<Canvas> canvasList = new ArrayList<Canvas>();

		rectangleList = new ArrayList< ArrayList<Rectangle>>();

		for(int i=0; i<d_.getColonnes(); ++i){
			rectangleList.add(new ArrayList<Rectangle>());
			for(int j=0; j<d_.getLignes(); ++j){
				r = new Rectangle(i*(tailleCase_+1), j*(tailleCase_+1), tailleCase_, tailleCase_);
				r.setFill(matrice[i][j].getCouleur());
				r.addEventHandler(MouseEvent.MOUSE_PRESSED,new RectangleClickHandler(d_,d_.getCase(i,j),this));

								

				r.setOnMouseEntered(new EventHandler<MouseEvent>
			    () {

			        @Override
			        public void handle(MouseEvent event) {
			        	if(((Rectangle)event.getSource()).getFill()==Color.WHITE)			          	 
			          		((Rectangle)event.getSource()).setFill(Color.LEMONCHIFFON);			           
			        }
			    });

			    r.setOnMouseExited(new EventHandler<MouseEvent>
			    () {

			        @Override
			        public void handle(MouseEvent event) {
			        	if(((Rectangle)event.getSource()).getFill()==Color.LEMONCHIFFON)
			           		((Rectangle)event.getSource()).setFill(Color.WHITE);
			        }
			    });

			    rectangleList.get(i).add(r);
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
	  				canvas.setTranslateX(-((h_/2)-i*(tailleCase_+1))+tailleCase_/2-150);
   					canvas.setTranslateY(-((w_/2)-j*(tailleCase_+1))+tailleCase_/2);

   					canvas.setOnMouseClicked(new RectangleClickHandler(d_,d_.getCase(i,j),this));

	  				canvasList.add(canvas);
	  			}

			}
		}

		root.getChildren().add(plateau);	

		for(Canvas c : canvasList){
			root.getChildren().add(c);	
		}	
		
		Canvas testingMenu = new Canvas(300,800);	
		testingMenu.setStyle("-fx-effect: innershadow(gaussian, #6E6E6E, 10, 1.0, 0, 0);");	
		testingMenu.setTranslateX(400);
		testingMenu.setLayoutY(0);
		gc = testingMenu.getGraphicsContext2D();
		gc.setFill(Color.LIGHTGREY);
		gc.fillRect(0, 0, testingMenu.getWidth(), testingMenu.getHeight());


		Pane buttonPane = new Pane();
		buttonPane.setTranslateX(800);

		bComposante = new Button();
        bComposante.setText("afficheComposante");
        bComposante.setStyle("-fx-background-color: #FFFAFA");
        bComposante.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(getSelectedButton()!="afficheComposante"){
            		cleanSelected();
            		resetButtonColor();
                	setSelectedButton("afficheComposante");
                	((Button)event.getSource()).setStyle("-fx-background-color: #BCF5A9");
            	}
            	else{
            		cleanSelected();
            		setSelectedButton("");
            		((Button)event.getSource()).setStyle("-fx-background-color: #FFFAFA");
            	}
              
            }
        });       
        bComposante.setTranslateX(70);
        bComposante.setTranslateY(80);
        bComposante.setPrefSize(175,45);
        buttonPane.getChildren().add(bComposante);	


        bExisteChemin = new Button();
        bExisteChemin.setText("existeCheminCases");
        bExisteChemin.setStyle("-fx-background-color: #FFFAFA");
        bExisteChemin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(getSelectedButton()!="existeCheminCases"){
            		cleanSelected();
            		resetButtonColor();            		
                	setSelectedButton("existeCheminCases");
                	((Button)event.getSource()).setStyle("-fx-background-color: #BCF5A9");
            	}
            	else{
            		setSelectedButton("");
            		cleanSelected();
            		((Button)event.getSource()).setStyle("-fx-background-color: #FFFAFA");
            	}
              
            }
        });       
        bExisteChemin.setTranslateX(35);
        bExisteChemin.setTranslateY(160);
        bExisteChemin.setPrefSize(150,45);
        buttonPane.getChildren().add(bExisteChemin);	

        existeCheminLabel = new TextField("");
        existeCheminLabel.setEditable(false);
        existeCheminLabel.setTranslateX(210);
        existeCheminLabel.setTranslateY(160);
        existeCheminLabel.setPrefSize(50,45);
		buttonPane.getChildren().add(existeCheminLabel);	



		bPlusCourtChemin = new Button();
        bPlusCourtChemin.setText("relierCasesMin");
        bPlusCourtChemin.setStyle("-fx-background-color: #FFFAFA");
        bPlusCourtChemin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(getSelectedButton()!="relierCasesMin"){
            		cleanSelected();
            		resetButtonColor();
                	setSelectedButton("relierCasesMin");
                	((Button)event.getSource()).setStyle("-fx-background-color: #BCF5A9");
            	}
            	else{
            		cleanSelected();
            		setSelectedButton("");
            		((Button)event.getSource()).setStyle("-fx-background-color: #FFFAFA");
            	}
              
            }
        });       
        bPlusCourtChemin.setTranslateX(70);
        bPlusCourtChemin.setTranslateY(240);
        bPlusCourtChemin.setPrefSize(175,45);
        buttonPane.getChildren().add(bPlusCourtChemin);	


        bNombreEtoiles = new Button();
        bNombreEtoiles.setText("nombreEtoiles");
        bNombreEtoiles.setStyle("-fx-background-color: #FFFAFA");
        bNombreEtoiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(getSelectedButton()!="nombreEtoiles"){
            		cleanSelected();
            		resetButtonColor();
                	setSelectedButton("nombreEtoiles");
                	((Button)event.getSource()).setStyle("-fx-background-color: #BCF5A9");
            	}
            	else{
            		cleanSelected();
            		setSelectedButton("");
            		((Button)event.getSource()).setStyle("-fx-background-color: #FFFAFA");
            	}
              
            }
        });       
        bNombreEtoiles.setTranslateX(35);
        bNombreEtoiles.setTranslateY(300);
        bNombreEtoiles.setPrefSize(150,45);
        buttonPane.getChildren().add(bNombreEtoiles);	

        nombreEtoilesLabel = new TextField("");
        nombreEtoilesLabel.setEditable(false);
        nombreEtoilesLabel.setTranslateX(210);
        nombreEtoilesLabel.setTranslateY(300);
        nombreEtoilesLabel.setPrefSize(50,45);
		buttonPane.getChildren().add(nombreEtoilesLabel);
		
		root.getChildren().add(testingMenu);
		root.getChildren().add(buttonPane);
		
	
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}

	public void resetButtonColor(){
		bExisteChemin.setStyle("-fx-background-color: #FFFAFA");
		bComposante.setStyle("-fx-background-color: #FFFAFA");
		bPlusCourtChemin.setStyle("-fx-background-color: #FFFAFA");
		bNombreEtoiles.setStyle("-fx-background-color: #FFFAFA");
	}


	public String getSelectedButton(){
		return selectedButton;
	}

	public void setSelectedButton(String b){
		selectedButton = b;
	}

	public void saveParameters(){
		nbLignes = spinnerLigne.getValue().intValue();
		nbColonnes = spinnerColonne.getValue().intValue();
		nbObj = spinnerObjectif.getValue().intValue();
	}


	public Rectangle getRectangle(int x, int y){
		return rectangleList.get(y).get(x);
	}


	public void changeExisteCheminLabel(String msg){
		existeCheminLabel.setText(msg);
	}

	public void changeNombreEtoilesLabel(String msg){
		nombreEtoilesLabel.setText(msg);
	}


	public void cleanSelected(){

		Case selected1 = RectangleClickHandler.getSelected1();
		Case selected2 = RectangleClickHandler.getSelected2();

		if(selected1!=null){
			getRectangle(selected1.getLigne(),selected1.getColonne()).setStrokeWidth(0);
			getRectangle(selected1.getLigne(),selected1.getColonne()).setStroke(Color.WHITE);
		}

		if(selected2!=null){
			getRectangle(selected2.getLigne(),selected2.getColonne()).setStrokeWidth(0);
			getRectangle(selected2.getLigne(),selected2.getColonne()).setStroke(Color.WHITE);
		}
		RectangleClickHandler.resetSelected();
	}
}