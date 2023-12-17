package adda.util;

public record Atraccion(String nombre, Integer tiempoEsperaMedio, Double popularidad, Integer duracion) {
	public static Atraccion ofFormat(String[] formato) {
		String n = formato[0];
		Integer tem = Integer.valueOf(formato[1]);
		Double p = Double.parseDouble(formato[2]);
		Integer d = Integer.valueOf(formato[3]);
		return new Atraccion(n,tem,p,d);
	}
	
	public static Atraccion of(String nombre, Integer tiempoEsperaMedio, Double popularidad, Integer duracion) {
		return new Atraccion(nombre, tiempoEsperaMedio, popularidad, duracion);
	}
}
