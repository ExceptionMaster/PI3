package adda.ejercicios;

import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import us.lsi.graphs.views.SubGraphView;

public class Ejercicio2 {
	public static <V, E> Graph<V, E> ejercicio2(Graph<V, E> g, GraphPath<V, E> caminoMinimo) {
        Graph<V, E> subG = SubGraphView.of(g, caminoMinimo.getVertexList().stream().collect(Collectors.toSet()));
        return subG;
    }
}
