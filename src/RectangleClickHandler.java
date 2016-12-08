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
	private Graphisme graphisme_;

	private static Case selected1 = null;
	private static Case selected2 = null;

	public RectangleClickHandler(MoteurDonnees m, Case c, Graphisme g){
		moteurD_ = m;
		c_=c;
		voisins_ = moteurD_.getVoisins(c_);
		graphisme_ = g;

				
	}

	
	public void handle(MouseEvent event){
		joueDeuxHumains(event);	
	}
		
	// 8. joueDeuxHumains()
	public void joueDeuxHumains(MouseEvent event){

		if(moteurD_.getVictoire()==""){

		switch (graphisme_.getSelectedButton()) {
			case "":
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
					
					moteurD_.changeTour();
					graphisme_.afficheScores();
					graphisme_.colorerRectangleTourJeu();			
				}
				break;

			case "afficheComposante":
				final ArrayList<ClasseUnion> composantes = moteurD_.afficheComposante(c_);

				if(composantes!=null){
					for(ClasseUnion cu : composantes){						
						graphisme_.getRectangle(cu.getCase().getLigne(),cu.getCase().getColonne()).setStrokeWidth(6);
						graphisme_.getRectangle(cu.getCase().getLigne(),cu.getCase().getColonne()).setStroke(Color.YELLOW);
					}
				}

				Thread t = new Thread(){

					public void run(){
						try{
							sleep(3600);
							for(ClasseUnion cu : composantes){								
								graphisme_.getRectangle(cu.getCase().getLigne(),cu.getCase().getColonne()).setStrokeWidth(0);
								graphisme_.getRectangle(cu.getCase().getLigne(),cu.getCase().getColonne()).setStroke(Color.WHITE);
							}
						} catch(Exception e){
							e.printStackTrace();
						}
					}
					
				};

				
				t.start();

				graphisme_.resetButtonColor();
				graphisme_.setSelectedButton("");			

				break;




			case "existeCheminCases":
				if(selected1==null && selected2==null){
					selected1 = c_;
					graphisme_.getRectangle(selected1.getLigne(),selected1.getColonne()).setStrokeWidth(6);
					graphisme_.getRectangle(selected1.getLigne(),selected1.getColonne()).setStroke(Color.CHARTREUSE);

				}
				else{
					if(selected1!=null && selected2==null){
						selected2 = c_;
						graphisme_.getRectangle(selected2.getLigne(),selected2.getColonne()).setStrokeWidth(6);
						graphisme_.getRectangle(selected2.getLigne(),selected2.getColonne()).setStroke(Color.CHARTREUSE);

						boolean existeChemin = moteurD_.existeCheminCases(selected1,selected2);

						
						if(existeChemin)
							graphisme_.changeExisteCheminLabel("OUI");
						else
							graphisme_.changeExisteCheminLabel("NON");


						Thread t1 = new Thread(){

						public void run(){
							try{
								sleep(3600);																
								graphisme_.cleanSelected();								
								
								
							} catch(Exception e){
								e.printStackTrace();
							}

						}
					};

					t1.start();
					graphisme_.resetButtonColor();
					
					graphisme_.setSelectedButton("");	

					}
				}

				break;



				case "relierCasesMin":
					if(selected1==null && selected2==null){
						selected1 = c_;
						graphisme_.getRectangle(selected1.getLigne(),selected1.getColonne()).setStrokeWidth(6);
						graphisme_.getRectangle(selected1.getLigne(),selected1.getColonne()).setStroke(Color.FUCHSIA);

					}
					else{
						if(selected1!=null && selected2==null){
							selected2 = c_;
							graphisme_.getRectangle(selected2.getLigne(),selected2.getColonne()).setStrokeWidth(6);
							graphisme_.getRectangle(selected2.getLigne(),selected2.getColonne()).setStroke(Color.FUCHSIA);

							final ArrayList<Case> casesMin = moteurD_.relierCasesMin(selected1,selected2);

							
							if(casesMin!=null){
								for(Case cMin : casesMin){						
									graphisme_.getRectangle(cMin.getLigne(),cMin.getColonne()).setFill(Color.LIGHTPINK);	
													
								}
							}


							Thread t2 = new Thread(){

							public void run(){
								try{
									sleep(2000);																
									graphisme_.cleanSelected();		
									if(casesMin!=null){
										for(Case cMin : casesMin){						
											graphisme_.getRectangle(cMin.getLigne(),cMin.getColonne()).setFill(Color.WHITE);									
										}
									}
														
									
									
								} catch(Exception e){
									e.printStackTrace();
								}

							}
						};

						t2.start();
						graphisme_.resetButtonColor();
						
						graphisme_.setSelectedButton("");	

						}
					}

					break;

				case "nombreEtoiles":

					graphisme_.changeNombreEtoilesLabel(moteurD_.nombreEtoiles(c_)+"");					

					graphisme_.getRectangle(c_.getLigne(),c_.getColonne()).setStrokeWidth(6);
					graphisme_.getRectangle(c_.getLigne(),c_.getColonne()).setStroke(Color.ORANGE);
					
					Thread t3 = new Thread(){

						public void run(){
							try{
								sleep(3000);																
												
								graphisme_.getRectangle(c_.getLigne(),c_.getColonne()).setStrokeWidth(0);
								graphisme_.getRectangle(c_.getLigne(),c_.getColonne()).setStroke(Color.WHITE);
					
								
							} catch(Exception e){
								e.printStackTrace();
							}

						}
					};

					t3.start();

					graphisme_.resetButtonColor();	
					graphisme_.setSelectedButton("");			
					break;


				case "relieComposantes":
					
					boolean relieC = relieComposantes();

					if(relieC)
						graphisme_.changeRelieComposantesLabel("OUI");
					else
						graphisme_.changeRelieComposantesLabel("NON");							

					graphisme_.getRectangle(c_.getLigne(),c_.getColonne()).setStrokeWidth(6);
					graphisme_.getRectangle(c_.getLigne(),c_.getColonne()).setStroke(Color.CYAN);
					
					Thread t4 = new Thread(){

						public void run(){
							try{
								sleep(3000);																
												
								graphisme_.getRectangle(c_.getLigne(),c_.getColonne()).setStrokeWidth(0);
								graphisme_.getRectangle(c_.getLigne(),c_.getColonne()).setStroke(Color.WHITE);
					
								
							} catch(Exception e){
								e.printStackTrace();
							}

						}
					};

					t4.start();

					graphisme_.resetButtonColor();	
					graphisme_.setSelectedButton("");	
					break;

		
		}

	}

			
	}

	// 7 relieComposantes
	public boolean relieComposantes(){
		boolean res = false;
		int i =0;
		ClasseUnion c1,c2;

		Color couleur = c_.getCouleur();

		if(couleur==Color.WHITE){
			if (moteurD_.getTour())
				couleur = Color.RED;
			else
				couleur = Color.BLUE;
		}

		while(i<voisins_.size() && !res){
			if(couleur == voisins_.get(i).getCouleur()){
				res=true;
			}
			++i;
		}
			
	
		return res;
	}


	public static Case getSelected1(){
		return selected1;
	}

	public static Case getSelected2(){
		return selected2;
	}

	public static void resetSelected(){
		selected1 = null;
		selected2 = null;
	}

}
