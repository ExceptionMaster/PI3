package adda.tests;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import adda.ejercicios.Ejercicio1;
import adda.util.Interaccion;
import adda.util.Usuario;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Test {
	private static int nEj1 = 1;
	public static void ejercicio1a(String fichero) {
		System.out.println("===== EJERCICIO 1 =====");
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
		System.out.println(g2);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ejercicio1a("./ficheros/ejercicio1_1.txt");
		ejercicio1a("./ficheros/ejercicio1_2.txt");
		ejercicio1a("./ficheros/ejercicio1_3.txt");
	}

}
