
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
	public string getTipoCliente() {
		return NATURAL;
	}

}
