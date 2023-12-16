package adda.ejercicios;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;

import us.lsi.graphs.views.SubGraphView;

public class Ejercicio1 {
	public static <V,E> Graph<V,E> ejercicio1(Graph<V,E> g, Predicate<V> condV, Predicate<E> condE) {
		Graph<V,E> subG = SubGraphView.of(g, condV, condE);
		return subG;
	}
	
	public static <V,E> Graph<V,E> ejercicio1Alt(Graph<V,E> g, Set<V> s) {
		Graph<V,E> subG = SubGraphView.of(g, s);
		return subG;
	}
}
