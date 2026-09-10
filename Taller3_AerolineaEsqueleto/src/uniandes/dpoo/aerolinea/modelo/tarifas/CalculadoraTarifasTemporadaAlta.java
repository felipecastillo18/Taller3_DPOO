
public class CalculadoraTarifasTemporadaAlta extends CalculadoraTarifas {

	protected int COSTO_POR_KM = 1000;
	
	@Override
	public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		
		Ruta ruta = vuelo.getRuta();
		
		int km_totales = calcularDistanciaVuelo(ruta);
		
		int costo = COSTO_POR_KM * km_totales;
		
		return costo;
		
	}
	
	@Override
	public double calcularPorcentajeDescuento(Cliente cliente) {
		return 0;
	}
}
