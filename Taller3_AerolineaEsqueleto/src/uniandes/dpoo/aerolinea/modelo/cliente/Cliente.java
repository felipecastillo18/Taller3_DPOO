import java.util.ArrayList;
import java.util.List;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public abstract class Cliente {
	
	private List<Tiquete> tiquetesSinUsar;
	private List<Tiquete> tiquetesUsados;
	
	public Cliente() {
		tiquetesSinUsar = new ArrayList<Tiquete> ();
		tiquetesUsados = new ArrayList<Tiquete> ();
	}
	
	public abstract String getTipoCliente();
	
	public abstract String getIdentificador();
	
	public void agregarTiquete(Tiquete tiquete) {
		tiquetesSinUsar.add(tiquete);
	}
	
	public int calcularValorTotalTiquetes() {
		int valorTotal = 0;
		for (Tiquete tiquete : tiquetesSinUsar) {
			valorTotal += tiquete.getTarifa();
		}
		
		for (Tiquete tiquete : tiquetesUsados) {
			valorTotal += tiquete.getTarifa();
		}
		return valorTotal;
	}
	
	public void usarTiquetes(Vuelo vuelo) {
		
	    List<Tiquete> tiquetesDelVuelo = new ArrayList<Tiquete>();
	
	    for (Tiquete tiquete : tiquetesSinUsar) {
	        if (tiquete.getVuelo() == vuelo) {
	            tiquete.marcarComoUsado();
	            tiquetesDelVuelo.add(tiquete);
	        }
	    }
	
	    tiquetesSinUsar.removeAll(tiquetesDelVuelo);
	    tiquetesUsados.addAll(tiquetesDelVuelo);
	}
}
