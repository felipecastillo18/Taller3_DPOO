package uniandes.dpoo.aerolinea.tarifas;

import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas{
	
	protected final int COSTO_POR_KM_NATURAL = 600;
	protected final int COSTO_POR_KM_CORPORATIVO = 900;
	protected final double DESCUENTO_PEQ = 0.02;
	protected final double DESCUENTO_MEDIANAS = 0.1;
	protected final double DESCUENTO_GRANDES = 0.2;
	
	@Override
	public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		
		Ruta ruta = vuelo.getRuta();
		
		int km_totales = calcularDistanciaVuelo(ruta);
		
		int costo = 0;
		
		if (cliente.getTipoCliente().equals(ClienteCorporativo.CORPORATIVO)) {
			
			costo = km_totales*COSTO_POR_KM_CORPORATIVO;
					
		}
		
		else {
			costo = km_totales*COSTO_POR_KM_NATURAL;
		}
		
		return costo;
		
	}
	@Override
	public double calcularPorcentajeDescuento(Cliente cliente) {
		double descuento = 0;
		if (cliente.getTipoCliente().equals(ClienteCorporativo.CORPORATIVO)) {
	        ClienteCorporativo clienteCorporativo = (ClienteCorporativo) cliente;

	        int tamano = clienteCorporativo.getTamanoEmpresa();
	        
			if (tamano == ClienteCorporativo.GRANDE) {
				descuento = DESCUENTO_GRANDES;
			}
			else if(tamano == ClienteCorporativo.MEDIANA) {
				descuento = DESCUENTO_MEDIANAS;

			}
			else {
				descuento = DESCUENTO_PEQ;
			}
			
		}
		return descuento;
	}
}
