package it.polito.tdp.PremierLeague.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleDirectedWeightedGraph <Team,DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer, Team> idMap;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap <Integer, Team>();
		dao.listAllTeams(idMap);
	}
	
	
	public void creaGrafo() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici
		Graphs.addAllVertices(grafo, idMap.values());
		
		// agiungo gli archi
		for (Adiacenza a: dao.getAdiacenze(idMap)) {
			if(this.grafo.containsVertex(a.getTh()) && this.grafo.containsVertex(a.getTa())) {
				
				Team th = a.getTh();
				Team ta = a.getTa();
				
				if(th.getPunti()>ta.getPunti()) {
					// da th a ta
					double peso = th.getPunti()-ta.getPunti();
					Graphs.addEdgeWithVertices(this.grafo, a.getTh(), a.getTa(), peso);
				}
				else if(th.getPunti()<ta.getPunti()) {
					// da ta a th
					double peso = th.getPunti()-ta.getPunti();
					Graphs.addEdgeWithVertices(this.grafo,a.getTa(), a.getTh(), Math.abs(peso));
				}
			}
		}
	}


	public int getNumVertici() {
		if (this.grafo!=null) {
			return this.grafo.vertexSet().size();
		}
		return 0;
	}
	
	public int getNumArchi() {
		if (this.grafo!=null) {
			return this.grafo.edgeSet().size();
		}
		return 0;
	}
	
	public Set<Team> getVerticiTendina(){
		return this.grafo.vertexSet();
	}
	
	public List<TeamPeggiori> getTeamPeggiori(Team t){
		
		List<TeamPeggiori> peggiori = new LinkedList<TeamPeggiori>();
		
		// l'arco è orientato da chi ha collezionato più punti a chi meno
		// quindi tutte le squadre collegate con un arco uscente da t
		// hanno meno punti in classifica
		
		for(DefaultWeightedEdge battuto: this.grafo.outgoingEdgesOf(t)) {
			
			double peso = this.grafo.getEdgeWeight(battuto);
			Team peggiore = this.grafo.getEdgeTarget(battuto);
			TeamPeggiori tp = new TeamPeggiori (peggiore, peso);
			peggiori.add(tp);
		}
		
		Collections.sort(peggiori, new Comparator<TeamPeggiori>() {

			@Override
			public int compare(TeamPeggiori o1, TeamPeggiori o2) {
				Double d1 = o1.getPunti();
				Double d2 = o2.getPunti();
				return d1.compareTo(d2);
			}
			
		});
		return peggiori;
	}
	
	public List<TeamMigliori> getTeamMigliori(Team t){
		
		List<TeamMigliori> migliori = new LinkedList<TeamMigliori>();
		
		// l'arco è orientato da chi ha collezionato più punti a chi meno
		// quindi tutti le squadre collegate con un arco entrante a t
		// hanno più punti in classifica di t
		
		for(DefaultWeightedEdge arco: this.grafo.incomingEdgesOf(t)) {
			
			double peso = this.grafo.getEdgeWeight(arco);
			Team migliore = this.grafo.getEdgeSource(arco);
			TeamMigliori tm = new TeamMigliori (migliore, peso);
			migliori.add(tm);
		}
		
		Collections.sort(migliori, new Comparator<TeamMigliori>() {

			@Override
			public int compare(TeamMigliori o1, TeamMigliori o2) {
				Double d1 = o1.getPeso();
				Double d2 = o2.getPeso();
				return d1.compareTo(d2);
			}

			
			
		});
		return migliori;
	}
}