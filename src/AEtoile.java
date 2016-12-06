class AEtoile {
	private Case c_;
	private AEtoile parent_;
	
	private double g_;
	private double h_;
	private double f_;

	public AEtoile(Case c, AEtoile parent, double g, double h){
		c_ = c;
		parent_ = parent;
		g_ = g;
		h_ = h;
		f_ = g_+h_;
	}

	public Case getCase(){
		return c_;
	}

	public AEtoile getParent(){
		return parent_;
	}

	public double getG(){
		return g_;
	}

	public double getH(){
		return h_;
	}

	public double getF(){
		return f_;
	}

	public void update(double g, double h){
		g_ = g;
		h_ = h;
		f_ = g_+h_;
	}

	public boolean equals(Object o){
		AEtoile ae = (AEtoile)o;
		return c_.equals(ae.getCase());
	}
}