package adda.util;

public record RelacionTarea(String nombre, String independiente, String dependiente) {
	public static int i = 0;
	public static RelacionTarea ofFormat(String[] format) {
		return new RelacionTarea("Relacion-"+i++,format[0],format[1]);
	}
}
