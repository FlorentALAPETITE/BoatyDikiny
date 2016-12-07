import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.Button;

class LaunchButtonHandler implements EventHandler<ActionEvent>{
	private Graphisme g_;
	private Stage primaryStage_;

	public LaunchButtonHandler(Graphisme g, Stage pS){
		g_=g;
		primaryStage_=pS;
	}

	public void handle(ActionEvent event){
		g_.saveParameters();
		g_.loadGame(primaryStage_);

		if( ((Button)event.getSource()).getText() == "Humain vs Humain")
			g_.setTypeJeu("humain");
		else
			g_.setTypeJeu("ia");
	}
}
