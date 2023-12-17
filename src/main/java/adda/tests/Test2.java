package adda.tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;
import org.jgrapht.graph.SimpleWeightedGraph;

import adda.util.Atraccion;
import adda.util.Relacion;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Test2 {
	private static int nEj2 = 1;
	public static void apartadoA(String fichero, String atraccion1, String atraccion2) {
		System.out.println("Usando fichero de datos: ejercicio2_"+nEj2);
		
		SimpleWeightedGraph<Atraccion, Relacion> g = GraphsReader.newGraph(
				fichero, 
				Atraccion::ofFormat, 
				Relacion::ofFormat, 
				() -> Graphs2.simpleWeightedGraph(),
				a -> a.distancia()
		);
		
		DijkstraShortestPath<Atraccion, Relacion> dijkstra = new DijkstraShortestPath<Atraccion, Relacion>(g);
		GraphPath<Atraccion, Relacion> caminoMinimo = dijkstra.getPath(getAtraccionPorNombre(g, atraccion1), 
															getAtraccionPorNombre(g, atraccion2));
		GraphColors.toDot(
                g,
                "./exports/ejercicio2a_" + nEj2 + ".dot",
                v -> v.nombre(),
                e -> e.distancia()+" km, "+e.tiempoMedio()+" min",
                v -> GraphColors.colorIf(Color.magenta, Color.black, caminoMinimo.getVertexList().contains(v)),
				e -> GraphColors.colorIf(Color.magenta, Color.black, caminoMinimo.getEdgeList().contains(e))
        );
		
		nEj2++;
		System.out.println("    "+caminoMinimo);
	}
	
	private static Atraccion getAtraccionPorNombre(Graph<Atraccion,Relacion> g, String atraccion) {
		return g.vertexSet().stream().filter(a -> a.nombre().equals(atraccion)).findFirst().get();
	}

	public static void apartadoB(String fichero) {
		System.out.println("Usando fichero de datos: ejercicio2_"+nEj2);
		
		SimpleWeightedGraph<Atraccion, Relacion> g = GraphsReader.newGraph(
				fichero, 
				Atraccion::ofFormat, 
				Relacion::ofFormat, 
				() -> Graphs2.simpleWeightedGraph(),
				a -> a.distancia()
		);
		
		HeldKarpTSP<Atraccion, Relacion> tsp = new HeldKarpTSP<>();
		GraphPath<Atraccion, Relacion> caminoOptimo = tsp.getTour(g);
		
		GraphColors.toDot(
                g,
                "./exports/ejercicio2b_" + nEj2 + ".dot",
                v -> v.nombre(),
                e -> e.distancia()+" km, "+e.tiempoMedio()+" min",
                v -> GraphColors.colorIf(Color.magenta, Color.black, caminoOptimo.getVertexList().contains(v)),
				e -> GraphColors.colorIf(Color.magenta, Color.black, caminoOptimo.getEdgeList().contains(e))
        );
		
		nEj2++;
		System.out.println("    "+caminoOptimo);
	}
	
	public static void apartadoC(String fichero, Integer horasDisponibles) {
		Set<Atraccion> visitadas = new HashSet<>();
		List<Atraccion> ruta = new ArrayList<>();
		double tiempoTotal = 0.;
		
		System.out.println("Usando fichero de datos: ejercicio2_"+nEj2);
		SimpleWeightedGraph<Atraccion, Relacion> g = GraphsReader.newGraph(
				fichero, 
				Atraccion::ofFormat, 
				Relacion::ofFormat, 
				() -> Graphs2.simpleWeightedGraph(),
				a -> a.tiempoMedio()
		);
		
		Atraccion atrInicial = g.vertexSet().stream()
				.sorted(Comparator
						.comparing(Atraccion::popularidad)
						.reversed())
				.findFirst().get();
		
		visitadas.add(atrInicial); // ya se ha visitado al empezar por ella
		ruta.add(atrInicial);
		
		while(horasDisponibles*60 > tiempoTotal) {
			Atraccion atrActual = ruta.get(ruta.size()-1);
			
			List<Atraccion> noVisitadas = g.edgesOf(atrActual).stream()
			        .map(e -> {
			            Atraccion src = g.getEdgeSource(e);
			            Atraccion trg = g.getEdgeTarget(e);
			            return src.equals(atrActual) ? trg : src;
			        })
			        .filter(vecina -> !visitadas.contains(vecina))
			        .sorted(Comparator.comparing(Atraccion::popularidad).reversed())
			        .toList();
			
			if (noVisitadas.isEmpty()) {
                break;
            }
			
			Atraccion atrNext = noVisitadas.get(0);
			
			DijkstraShortestPath<Atraccion, Relacion> dijkstra = new DijkstraShortestPath<>(g);
			
            double tiempoViaje = dijkstra.getPathWeight(atrActual, atrNext);
            double tiempoEspera = atrNext.tiempoEsperaMedio();
            double duracionAtraccion = atrNext.duracion();
            double tiempoTotalAtraccion = tiempoViaje + tiempoEspera + duracionAtraccion;
            
            if (tiempoTotal + tiempoTotalAtraccion <= horasDisponibles*60) {
                ruta.add(atrNext);
                visitadas.add(atrNext);
                tiempoTotal += tiempoTotalAtraccion;
            } else {
                break;
            }
		}
		
		GraphColors.toDot(
	            g,
	            "./exports/ejercicio2c_" + nEj2 + ".dot",
	            v -> v.nombre(),
	            e -> e.distancia()+" km, "+e.tiempoMedio()+" min",
	            v -> GraphColors.colorIf(Color.magenta, Color.black, ruta.contains(v)),
	            e -> GraphColors.colorIf(Color.magenta, Color.black, containsEdge(g,e,ruta)
	        )
	    );
		
		nEj2++;
		System.out.println("    "+ruta.stream().map(x->x.nombre()).toList());
	}
	
	private static Boolean containsEdge(Graph<Atraccion,Relacion> g, Relacion e, List<Atraccion> ruta) {
		Boolean res = false;
		for(int i = 0; i < ruta.size()-1; i++) {
			if(g.getEdge(ruta.get(i), ruta.get(i+1)).equals(e)) {
				res = true;
				break;
			}
		}
		return res;
	}
	
	public static void test() {
		System.out.println("--------- EJERCICIO 2 ---------\n=== APARTADO A ===");
		apartadoA("./ficheros/ejercicio2_1.txt", "Coches de choque", "Raton Vacilon");
		apartadoA("./ficheros/ejercicio2_2.txt", "Coches de choque", "Patitos");
		apartadoA("./ficheros/ejercicio2_3.txt", "Casa del Terror", "Pim pam pum");
		nEj2=1;
		System.out.println("\n=== APARTADO B ===");
		apartadoB("./ficheros/ejercicio2_1.txt");
		apartadoB("./ficheros/ejercicio2_2.txt");
		apartadoB("./ficheros/ejercicio2_3.txt");
		nEj2=1;
		System.out.println("\n=== APARTADO C ===");
		apartadoC("./ficheros/ejercicio2_1.txt",5);
		apartadoC("./ficheros/ejercicio2_2.txt",2);
		apartadoC("./ficheros/ejercicio2_3.txt",3);
	}
	
}
