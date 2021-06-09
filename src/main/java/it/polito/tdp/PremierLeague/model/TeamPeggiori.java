package it.polito.tdp.PremierLeague.model;

public class TeamPeggiori {

	Team t; 
	double peso;
	public TeamPeggiori(Team t, double peso) {
		super();
		this.t = t;
		this.peso = peso;
	}
	public Team getT() {
		return t;
	}
	public void setT(Team t) {
		this.t = t;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return t.getName() + " (" + peso + ")";
	} 
	
	
	
}
