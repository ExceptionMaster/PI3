package adda.util;

public record Relacion(Integer id, Double distancia, Double tiempoMedio) {
	private static int num =0;
	
	public static Relacion ofFormat(String[] formato) {
		Integer i = num;
		Double d = Double.parseDouble(formato[2]);
		Double tm = Double.parseDouble(formato[3]);
		num++;
		return new Relacion(i, d, tm);
	}
	
	public static Relacion of(Integer id, Double distancia, Double tiempoMedio) {
		return new Relacion(id,distancia, tiempoMedio);
	}
}
