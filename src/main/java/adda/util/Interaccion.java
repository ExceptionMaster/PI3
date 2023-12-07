package adda.util;

public record Interaccion(Integer id, Double indiceInteraccion) {
	
	private static int num =0;
	
	public static Interaccion ofFormat(String[] formato) {
		Double indiceInteraccion = Double.parseDouble(formato[2]);
		Integer id = num;
		num++;
		return new Interaccion(id, indiceInteraccion);
	}
	
	public static Interaccion of(Integer id, Double indiceInteraccion) {
		return new Interaccion(id,indiceInteraccion);
	}
}
