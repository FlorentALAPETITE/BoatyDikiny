import java.util.*;

class ClasseUnion {

	private int nbNoeud_;
	private Case rep_;
	private ClasseUnion pere_;
	private ArrayList<ClasseUnion> fils_;
	private int nbObjectif_;

	public ClasseUnion(Case rep){
		rep_ = rep;
		nbNoeud_ = 1;
		pere_ = null;
		fils_ = new ArrayList<ClasseUnion>();
		if(rep_.testCaseObjectif())
			nbObjectif_ = 1;
		else
			nbObjectif_=0;
	}

	public void union(ClasseUnion x){
		if(x!=null){
			if(nbNoeud_ > x.nbNoeud_){
				nbNoeud_ += x.nbNoeud_;
				fils_.add(x);
				x.pere_ = this;
				nbObjectif_ += x.nbObjectif_;
			}else{
				x.nbNoeud_ += nbNoeud_;
				x.fils_.add(this);
				pere_ = x;
				x.nbObjectif_ += nbObjectif_;
			}
		}	
	}

	public ClasseUnion classe(){
		if(pere_ == null){
			return this;
		}else{
			return pere_.classe();
		}
	}

	public Case getRep(){
		return rep_;
	}

	public int getNbNoeud(){
		return nbNoeud_;
	}

	public ArrayList<ClasseUnion> getFils(){
		return fils_;
	}

	public int getNbObjectif(){
		return nbObjectif_;
	}

	public String parcoursClasseUnion(){
		String res = toString();
		for(ClasseUnion cu : fils_)
			res+=cu.parcoursClasseUnion();
		return res;
	}

	@Override
	public String toString(){
		return ("Représentant : ("+classe().rep_.getColonne()+","+classe().rep_.getLigne()+") ; "+"Nombre de noeuds : "+nbNoeud_+", Nombre objectifs : "+nbObjectif_);
	}

}
