package it.polito.tdp.PremierLeague.model;

public class Adiacenza {

	Team th;
	Team ta;
	double risultato; // +1, -1, 0
	public Adiacenza(Team th, Team ta, double risultato) {
		super();
		this.th = th;
		this.ta = ta;
		this.risultato = risultato;
	}
	public Team getTh() {
		return th;
	}
	public void setTh(Team th) {
		this.th = th;
	}
	public Team getTa() {
		return ta;
	}
	public void setTa(Team ta) {
		this.ta = ta;
	}
	public double getRisultato() {
		return risultato;
	}
	public void setRisultato(double risultato) {
		this.risultato = risultato;
	}
	
	
}
