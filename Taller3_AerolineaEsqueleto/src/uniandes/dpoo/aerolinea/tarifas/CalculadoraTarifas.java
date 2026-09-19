package uniandes.dpoo.aerolinea.tarifas;

import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public abstract class CalculadoraTarifas {
	
	public static final double IMPUESTO = 0.28;
	
	public int calcularTarifa(Vuelo vuelo, Cliente cliente) {
	    int costoBase = calcularCostoBase(vuelo, cliente);
	    double porcentajeDescuento = calcularPorcentajeDescuento(cliente);
	    int impuestos = calcularValorImpuestos(costoBase);

	    int tarifa = (int) (costoBase - costoBase * porcentajeDescuento + impuestos);

	    return tarifa;
	}
	
	protected abstract int calcularCostoBase(Vuelo vuelo, Cliente cliente);
	protected abstract double calcularPorcentajeDescuento(Cliente cliente);
	
	protected int calcularDistanciaVuelo(Ruta ruta) {
		
		Aeropuerto origen = ruta.getOrigen();
		Aeropuerto destino = ruta.getDestino();
		
		int km_totales = Aeropuerto.calcularDistancia(origen, destino);
		return km_totales;
	}
	
	protected int calcularValorImpuestos(int costoBase) {
		int impuestos = (int) (costoBase*IMPUESTO);
		return impuestos;
	}

}
