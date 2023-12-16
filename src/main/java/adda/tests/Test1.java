package adda.tests;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.interfaces.VertexCoverAlgorithm;
import org.jgrapht.alg.interfaces.VertexCoverAlgorithm.VertexCover;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import adda.ejercicios.Ejercicio1;
import adda.util.Interaccion;
import adda.util.Usuario;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.Map2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Test1 {
	private static int nEj1 = 1;
	public static void apartadoA(String fichero) {
		System.out.println("Usando fichero de datos: ejercicio1_"+nEj1);
		SimpleDirectedWeightedGraph<Usuario, Interaccion> g = GraphsReader.newGraph(
				fichero, 
				Usuario::ofFormat, 
				Interaccion::ofFormat, 
				() -> Graphs2.simpleDirectedWeightedGraph(),
				a -> a.indiceInteraccion()
		);
		Graph<Usuario, Interaccion> g2 = Ejercicio1.ejercicio1(
				g, 
				v -> g.outDegreeOf(v) > 3, 
				e -> e.indiceInteraccion() > 2.5
		);
		GraphColors.toDot(
				g, 
				"./exports/ejercicio1a_" + nEj1 + ".dot",
				v -> v.nombre(),
				e -> e.indiceInteraccion().toString(),
				v -> GraphColors.colorIf(Color.blue, Color.black, g2.containsVertex(v)),
				e -> GraphColors.colorIf(Color.blue, Color.black, g2.containsEdge(e))
		);
		nEj1++;
		System.out.println("    "+g2);
	}
	
	public static void apartadoB(String fichero) {
		System.out.println("Usando fichero de datos: ejercicio1_"+nEj1);
		SimpleDirectedWeightedGraph<Usuario, Interaccion> g = GraphsReader.newGraph(
				fichero, 
				Usuario::ofFormat, 
				Interaccion::ofFormat, 
				() -> Graphs2.simpleDirectedWeightedGraph(),
				a -> a.indiceInteraccion()
		);
		ConnectivityInspector<Usuario, Interaccion> connIns = new ConnectivityInspector<>(g);
		List<Set<Usuario>> connectedSets = connIns.connectedSets();
		for (Set<Usuario> group : connectedSets) {
			System.out.println("    "+ group);
			GraphColors.toDot(
					g, 
					"./exports/ejercicio1b_" + nEj1 + ".dot",
					v -> v.nombre(),
					e -> e.indiceInteraccion().toString(),
					v -> GraphColors.colorIf(Color.red, Color.blue, group.contains(v)),
	                e -> GraphColors.colorIf(Color.red, Color.blue, group.contains(g.getEdgeSource(e)) && group.contains(g.getEdgeTarget(e)))
			);
        }
		nEj1++;
	}
	
	public static void apartadoC(String fichero) {
		System.out.println("Usando fichero de datos: ejercicio1_"+nEj1);
		SimpleDirectedWeightedGraph<Usuario, Interaccion> g = GraphsReader.newGraph(
				fichero, 
				Usuario::ofFormat, 
				Interaccion::ofFormat, 
				() -> Graphs2.simpleDirectedWeightedGraph(),
				a -> a.indiceInteraccion()
				);
		
		Graph<Usuario, Interaccion> undirectedGraph = new AsUndirectedGraph<>(g);
		VertexCoverAlgorithm<Usuario> vc = new GreedyVCImpl<Usuario, Interaccion>(undirectedGraph,Map2.of(x->x.indiceActividad()));
		VertexCover<Usuario> vertexCover = vc.getVertexCover();
		
		// Imprimir el conjunto mínimo de usuarios
		System.out.println("    "+vertexCover);
		GraphColors.toDot(
				g,
				"./exports/ejercicio1c_" + nEj1 + ".dot",
				v -> v.nombre(),
				e -> e.indiceInteraccion().toString(),
				v -> GraphColors.colorIf(Color.red, Color.black, vertexCover.contains(v)),
				e -> GraphColors.color(Color.black)
				
				);
		nEj1++;
	}
	
	public static void apartadoD(String fichero) {
		System.out.println("Usando fichero de datos: ejercicio1_"+nEj1);
		SimpleDirectedWeightedGraph<Usuario, Interaccion> g = GraphsReader.newGraph(
				fichero, 
				Usuario::ofFormat, 
				Interaccion::ofFormat, 
				() -> Graphs2.simpleDirectedWeightedGraph(),
				a -> a.indiceInteraccion()
		);
		
		Predicate<Usuario> p = u ->
			g.inDegreeOf(u)>=5 && u.aficiones().size()>3 &&
			u.indiceActividad()>4;
		
		Graph<Usuario,Interaccion> g2 = Ejercicio1.ejercicio1Alt(
				g,
				g.vertexSet().stream()
				.filter(p)
				.sorted((u1,u2)-> calcularMedia(u2,g.incomingEdgesOf(u2))
						.compareTo(calcularMedia(u1,g.incomingEdgesOf(u1))))
				.limit(2)
				.collect(Collectors.toSet())
		);
			
        GraphColors.toDot(
                g,
                "./exports/ejercicio1d_" + nEj1 + ".dot",
                v -> v.nombre(),
                e -> e.indiceInteraccion().toString(),
                v -> GraphColors.colorIf(Color.red, Color.black, g2.containsVertex(v)),
                e -> GraphColors.color(Color.black)
                
        );
		nEj1++;
		System.out.println("    "+g2);
	}
	
	private static Double calcularMedia(Usuario u, Set<Interaccion> s) {
		List<Double> aux =	s.stream().map(Interaccion::indiceInteraccion).toList();
		return aux.stream().reduce((d1,d2) -> d1+d2).orElse(0.)/aux.size();
	}
	
	public static void test() {
		System.out.println("--------- EJERCICIO 1 ---------\n=== APARTADO A ===");
		apartadoA("./ficheros/ejercicio1_1.txt");
		apartadoA("./ficheros/ejercicio1_2.txt");
		apartadoA("./ficheros/ejercicio1_3.txt");
		nEj1=1;
		System.out.println("\n=== APARTADO B ===");
		apartadoB("./ficheros/ejercicio1_1.txt");
		apartadoB("./ficheros/ejercicio1_2.txt");
		apartadoB("./ficheros/ejercicio1_3.txt");
		nEj1=1;
		System.out.println("\n=== APARTADO C ===");
		apartadoC("./ficheros/ejercicio1_1.txt");
		apartadoC("./ficheros/ejercicio1_2.txt");
		apartadoC("./ficheros/ejercicio1_3.txt");
		nEj1=1;
		System.out.println("\n=== APARTADO D ===");
		apartadoD("./ficheros/ejercicio1_1.txt");
		apartadoD("./ficheros/ejercicio1_2.txt");
		apartadoD("./ficheros/ejercicio1_3.txt");
	}

}
