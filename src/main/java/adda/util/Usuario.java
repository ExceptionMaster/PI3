package adda.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public record Usuario(String nombre, Double indiceActividad, Set<String> aficiones) {
	public static Usuario ofFormat(String[] formato) {
		String nombre = formato[0];
		Double indiceActividad = Double.parseDouble(formato[1]);
		Set<String> aficiones = Arrays.stream(formato[2].replace("[]", "").split(";")).collect(Collectors.toSet());
		return new Usuario(nombre, indiceActividad, aficiones);
	}
		
	@Override
	public String toString() {
		return this.nombre+"("+this.indiceActividad+")";
	}
}
