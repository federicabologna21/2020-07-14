package it.polito.tdp.PremierLeague.db;

import it.polito.tdp.PremierLeague.model.Team;

public class Adiacenza {

	Team th;
	Team ta;
	int risultato;
	
	public Adiacenza(Team th, Team ta, int risultato) {
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
	public int getRisultato() {
		return risultato;
	}
	public void setRisultato(int risultato) {
		this.risultato = risultato;
	}
	
	
	
	
}
