public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas{
	
	protected int COSTO_POR_KM_NATURAL = 600;
	protected int COSTO_POR_KM_CORPORATIVO = 900;
	protected double DESCUENTO_PEQ = 0.02;
	protected double DESCUENTO_MEDIANAS = 0.1;
	protected double DESCUENTO_GRANDES = 0.2;
	
	@Override
	public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		
		Ruta ruta = vuelo.getRuta();
		
		int km_totales = calcularDistanciaVuelo(ruta);
		
		int costo = 0;
		
		if (cliente.getTipoCliente().equals("Corporativo")) {
			
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
		if (cliente.getTipoCliente().equals("Corporativo")) {
	        ClienteCorporativo clienteCorporativo = (ClienteCorporativo) cliente;

	        int tamano = clienteCorporativo.getTamanoEmpresa();
	        
			if (tamano == 1) {
				descuento = DESCUENTO_GRANDES;
			}
			else if(tamano == 2) {
				descuento = DESCUENTO_MEDIANAS;

			}
			else {
				descuento = DESCUENTO_PEQ;
			}
			
		}
		return descuento;
	}
}
