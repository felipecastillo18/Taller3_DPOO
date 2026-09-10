import math

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas{
	
	protected int COSTO_POR_KM_NATURAL = 60;
	protected int COSTO_POR_KM_CORPORATIVO = 90;
	protected double DESCUENTO_PEQ = 0.02;
	protected double DESCUENTO_MEDIANAS = 0.1;
	protected double DESCUENTO_GRANDES = 0.2;
	
	public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		
		Ruta ruta = vuelo.getRuta();
		
		Aeropuerto origen = ruta.getOrigen();
		Aeropuerto destino = ruta.getDestino();
		
		int latOrigen = origen.getLatitud();
		int lonOrigen = origen.getLongitud();
		
		int latDestino = destino.getLatitud();
		int lonDestino = destino.getLongitud();
		
		int km_totales = 0;
		
		int difLat = Math.abs(latOrigen - latDestino);
		int difLon = Math.abs(lonOrigen - lonDestino);
		
		
		int km_totales = Math.sqrt(Math.pow(difLat, 2)+ Math.pow(difLon, 2));
		
		int costo = 0;
		
		if (cliente.getTipoCliente().equals("CORPORATIVO")) {
			
			costo = km_totales*90;
			
			int tamano = cliente.getTamanoEmpresa;
			String clasificacion = "";
			if (tamano == 1) {
				clasificacion = "GRANDE";
				costo -= costo*DESCUENTO_GRANDE;
			}
			else if(tamano == 2) {
				clasificacion = "MEDIANA";
				costo -= costo*DESCUENTO_MEDIANA;

			}
			else {
				clasificacion = "PEQUENA";
				costo -= costo*DESCUENTO_PEQUENA;

			}
			
			
		}
		
		else {
			costo = km_totales*60;
		}
		
	}
}
