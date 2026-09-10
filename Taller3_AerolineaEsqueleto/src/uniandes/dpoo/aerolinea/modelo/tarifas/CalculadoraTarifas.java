
public abstract class CalculadoraTarifas {
	
	public double IMPUESTO = 0.28; 
	private Vuelo vuelo;
	private Cliente cliente;
	
	public int calcularTarifa(Vuelo vuelo, Cliente clinete) {
		//TODO
	}
	
	protected abstract int calculrarCostoBase(Vuelo vuelo, Cliente cliente);
	
	protected abstract double calcularPorcentajeDescuento(Cliente cliente);
	
	protected int calcularDistanciaVuelo(Ruta ruta) {
		//TODO
	}
	
	protected calcularValorImpuestos(int costoBase) {
		//TODO
	}

}
