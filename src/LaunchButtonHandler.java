import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

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
	}
}
