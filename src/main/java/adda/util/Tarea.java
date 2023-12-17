package adda.util;

public record Tarea(String nombre) {
	public static Tarea ofFormat(String[] format) {
		return new Tarea(format[0]);
	}
}
