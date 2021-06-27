package it.polito.tdp.PremierLeague.model;

public class TeamPeggiori {

	Team t;
	double punti;
	public TeamPeggiori(Team t, double punti) {
		super();
		this.t = t;
		this.punti = punti;
	}
	public Team getT() {
		return t;
	}
	public void setT(Team t) {
		this.t = t;
	}
	public double getPunti() {
		return punti;
	}
	public void setPunti(double punti) {
		this.punti = punti;
	}
	@Override
	public String toString() {
		return t + ", (" + punti + ")";
	}
	
	
	
	
}
