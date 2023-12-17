package adda.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;

import adda.util.RelacionTarea;
import adda.util.Tarea;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.List2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class Test3 {
	private static int nEj3 = 1;
	
	public static void apartadoA(String fichero) {
		System.out.println("Usando fichero de datos: ejercicio3_"+nEj3);
		
		SimpleDirectedGraph<Tarea, RelacionTarea> g = GraphsReader.newGraph(
				fichero, 
				Tarea::ofFormat, 
				RelacionTarea::ofFormat, 
				() -> Graphs2.simpleDirectedGraph(),
				null
		);
		
		TopologicalOrderIterator<Tarea, RelacionTarea> toi = new TopologicalOrderIterator<>(g);
		List<String> tareas = new ArrayList<>();
		toi.forEachRemaining(v->tareas.add(v.nombre()));
		
		nEj3++;
		System.out.println("    "+tareas);
	}
	
	public static void apartadoB(String fichero, String tarea) {
		System.out.println("Usando fichero de datos: ejercicio3_"+nEj3);
		
		SimpleDirectedGraph<Tarea, RelacionTarea> g = GraphsReader.newGraph(
				fichero, 
				Tarea::ofFormat, 
				RelacionTarea::ofFormat, 
				() -> Graphs2.simpleDirectedGraph(),
				null
		);
		
	    Tarea tInicial = g.vertexSet().stream()
	            .filter(t -> t.nombre().equals(tarea))
	            .findFirst()
	            .orElse(null);

	    List<Tarea> tareas = new ArrayList<>();
	    Map<Tarea, List<Tarea>> map = new HashMap<>();
	    List<Tarea> pendientes = new LinkedList<>();

	    pendientes.add(tInicial);
	    tareas.add(tInicial);
	    map.put(tInicial, tareas);

	    while (!pendientes.isEmpty()) {
	        Tarea tareaActual = pendientes.remove(0);

	        List<Tarea> tareasPrecedentes = g.incomingEdgesOf(tareaActual).stream()
	                .map(e -> g.getEdgeSource(e))
	                .toList();

	        for (Tarea t : tareasPrecedentes) {
	            if (!map.containsKey(t)) {
	            	tareas.add(t);
	            	map.put(t, tareas);
	            	pendientes.add(t);
	            }
	        }
	    }

	    GraphColors.toDot(
                g,
                "./exports/ejercicio3b_"+nEj3+".dot",
                v -> v.nombre(),
                e -> e.nombre(),
                v -> GraphColors.colorIf(Color.magenta, Color.black, tareas.contains(v)),
                e -> GraphColors.color(Color.black)
        );
		
		nEj3++;
		System.out.println(tareas);
	}
	
	public static void apartadoC(String fichero) {
		System.out.println("Usando fichero de datos: ejercicio3_"+nEj3);

        SimpleDirectedGraph<Tarea, RelacionTarea> g = GraphsReader.newGraph(
                fichero,
                Tarea::ofFormat,
                RelacionTarea::ofFormat,
                () -> Graphs2.simpleDirectedGraph(),
                null
        );
        
		List<Tarea> tareasNivelCero = g.vertexSet().stream()
		        .filter(v -> g.incomingEdgesOf(v).isEmpty())
		        .collect(Collectors.toList());

		Map<Tarea, Integer> map = dependenciasPorMasDependientes(g, tareasNivelCero);

		Integer masValue = map.values().stream()
		        .max((n1, n2) -> n1.compareTo(n2))
		        .orElse(0);

		List<Tarea> tareas = map.entrySet().stream()
		        .filter(e -> e.getValue().equals(masValue))
		        .map(Entry::getKey)
		        .toList(); 
		
		GraphColors.toDot(
                g,
                "./exports/ejercicio3c_"+nEj3+".dot",
                v -> v.nombre(),
                e -> e.nombre(),
                v -> GraphColors.colorIf(Color.magenta, Color.black, tareas.contains(v)),
                e -> GraphColors.color(Color.black)
        );
		
		nEj3++;
		System.out.println(tareas);

	}
	
	private static Map<Tarea, Integer> dependenciasPorMasDependientes(
	        Graph<Tarea, RelacionTarea> grafo, List<Tarea> tDependientes) {
	    Map<Tarea, Integer> res = new HashMap<>();

	    for (Tarea t : tDependientes) {
	        DepthFirstIterator<Tarea, RelacionTarea> dfi = new DepthFirstIterator<>(grafo, t);
	        List<Tarea> aux = List2.empty();
	        dfi.forEachRemaining(aux::add);
	        res.put(t, aux.size());
	    }

	    return res;
	}


	public static void test() {
		System.out.println("--------- EJERCICIO 3 ---------\n=== APARTADO A ===");
		apartadoA("./ficheros/ejercicio3_1.txt");
		apartadoA("./ficheros/ejercicio3_2.txt");
		apartadoA("./ficheros/ejercicio3_3.txt");
		nEj3=1;
		System.out.println("\n=== APARTADO B ===");
		apartadoB("./ficheros/ejercicio3_1.txt", "Tarea5");
		apartadoB("./ficheros/ejercicio3_2.txt", "Tarea8");
		apartadoB("./ficheros/ejercicio3_3.txt", "Tarea8");
		nEj3=1;
		System.out.println("\n=== APARTADO C ===");
		apartadoC("./ficheros/ejercicio3_1.txt");
		apartadoC("./ficheros/ejercicio3_2.txt");
		apartadoC("./ficheros/ejercicio3_3.txt");
	}

}
