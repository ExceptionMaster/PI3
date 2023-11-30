package adda.util;

import java.util.Set;

import us.lsi.grafos.datos.Carretera;

public record Usuario(String nombre, Double indiceActividad, Set<String> aficiones) {
	private static int num =0;
	
	public static Usuario ofFormat(String[] formato) {
		Double km = Double.parseDouble(formato[3]);
		String nomb = formato[2];		
		Integer id = num;
		num++;
		return new Carretera(id, km, nomb);
	}
	
	public static Carretera of(Double kms) {
		Double km = kms;
		String nomb = null;		
		Integer id = num;
		num++;
		return new Carretera(id, km, nomb);
	}
	
	@Override
	public String toString() {
		String nn = this.nombre==null?"":this.nombre+",";
		return "("+nn+this.km+")";
	}
}
