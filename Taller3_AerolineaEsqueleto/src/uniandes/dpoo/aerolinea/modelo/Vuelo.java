package uniandes.dpoo.aerolinea.modelo;

import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;

import java.util.Collection;

public class Vuelo {
	
	private Ruta ruta;
	private String fecha;
	private Avion avion;
	private Map<String, Tiquete> tiquetes; 
	
	public Vuelo(Ruta ruta, String fecha, Avion avion) {
		this.ruta = ruta;
		this.fecha = fecha;
		this.avion = avion;
		this.tiquetes = new HashMap<String, Tiquete>();
	}
	
	public Ruta getRuta() {
		return ruta;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public Avion getAvion() {
		return avion;
	}
	
	public Map<String, Tiquete> getTiquetes() {
		return tiquetes.values();
	}
	
	public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) {

	    int tarifa = calculadora.calcularTarifa(this, cliente);

	    for (int i = 0; i < cantidad; i++) {
	        Tiquete tiquete = GeneradorTiquetes.generarTiquete(this, cliente, tarifa);
	        tiquetes.put(tiquete.getCodigo(), tiquete);
	        cliente.agregarTiquete(tiquete);
	        GeneradorTiquetes.registrarTiquete(tiquete);
	    }

	    return tarifa * cantidad;
	}
	
	@Override
	public boolean equals(Object obj) {
		
	    if (obj == null) {
	        return false;
	    }

	    if (obj.getClass() != Vuelo.class) {
	        return false;
	    }

	    Vuelo otroVuelo = (Vuelo) obj;

	    boolean mismaFecha = fecha.equals(otroVuelo.getFecha());

	    boolean mismaRuta = ruta.getCodigoRuta().equals(otroVuelo.getRuta().getCodigoRuta());

	    return mismaFecha && mismaRuta;
	}

}
