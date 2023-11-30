package adda.tests;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import adda.ejemplos.Ejemplo1;
import adda.ejemplos.Ejemplo2;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestsEjemplos {

	public static void test1() {
		System.out.println("EJEMPLO 1");
		SimpleWeightedGraph<Ciudad, Carretera> g = GraphsReader.newGraph(
			"./ficheros/Andalucia.txt", 
			Ciudad::ofFormat, 
			Carretera::ofFormat, 
			() -> Graphs2.simpleWeightedGraph(),
			a -> a.km()
		);
		Graph<Ciudad, Carretera> g2 = Ejemplo1.ejemplo1(
			g, 
			v -> v.nombre().contains("e"), 
			e -> e.km() >= 200
		);
		GraphColors.toDot(
			g, 
			"./exports/ejemplo1.dot",
			v -> v.nombre(),
			e -> e.nombre() + " " + e.km(),
			c -> GraphColors.colorIf(Color.blue, Color.black, g2.containsVertex(c)),
			e -> GraphColors.color(Color.black)
		);
		System.out.println(g2);
	}
	
	public static void test2() {
		SimpleWeightedGraph<Ciudad, Carretera> g = GraphsReader.newGraph(
			"./ficheros/Andalucia.txt", 
			Ciudad::ofFormat, 
			Carretera::ofFormat, 
			() -> Graphs2.simpleWeightedGraph(),
			a -> a.km()
		);
		Ejemplo2.apartadoA(g, Ciudad.of("Sevilla", 1535379), Ciudad.of("Almeria", 257207));
		Ejemplo2.apartadoB(g);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test1();
		test2();
	}

}
