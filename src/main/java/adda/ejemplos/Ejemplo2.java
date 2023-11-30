package adda.ejemplos;

import java.util.List;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;

public class Ejemplo2 {
	public static void apartadoA(SimpleWeightedGraph<Ciudad, Carretera> g, Ciudad v1, Ciudad v2) {
		var alg = new DijkstraShortestPath<Ciudad, Carretera>(g);
		GraphPath<Ciudad, Carretera> path = alg.getPath(v1, v2);
		GraphColors.toDot(
			g, 
			"./exports/ejemplo2a.dot",
			v -> v.nombre(),
			e -> e.nombre() + " " + e.km(),
			v -> GraphColors.colorIf(Color.blue, Color.black, path.getVertexList().contains(v)),
			e -> GraphColors.colorIf(Color.blue, Color.black, path.getEdgeList().contains(e))
		);
	}
	
	public static void apartadoB(SimpleWeightedGraph<Ciudad, Carretera> g) {
		var alg = new ConnectivityInspector<Ciudad, Carretera>(g);
		List<Set<Ciudad>> componentes = alg.connectedSets();
		GraphColors.toDot(
			g, 
			"./exports/ejemplo2b.dot",
			v -> v.nombre(),
			e -> e.nombre() + " " + e.km(),
			v -> GraphColors.color(getColor(componentes,v)),
			e -> GraphColors.color(Color.black)
		);
	}
	
	public static Integer getColor(List<Set<Ciudad>> componentes, Ciudad v) {
		Integer i = 0;
		Integer res = null;
		for(Set<Ciudad> componente : componentes) {
			if(componente.contains(v)) {
				res = i;
				break;
			}
		}
		return res;
	}
}
