package uniandes.dpoo.aerolinea.modelo.cliente;

public class ClienteNatural extends Cliente{
	public String NATURAL = "Natural";
	private String nombre;
	
	public ClienteNatural(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String getIdentificador() {
		return nombre;
	}
	
	@Override
	public String getTipoCliente() {
		return NATURAL;
	}

}
