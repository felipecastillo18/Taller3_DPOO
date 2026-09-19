package uniandes.dpoo.aerolinea.consola;

import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;

public class ConsolaArerolinea extends ConsolaBasica {

	private Aerolinea unaAerolinea;

	public void correrAplicacion() {

		try {
			unaAerolinea = new Aerolinea();

			// 1. Cargar la aerolínea y los tiquetes
			System.out.println("--- Cargar aerolínea ---");
			unaAerolinea.cargarAerolinea("./datos/aerolinea.json", CentralPersistencia.JSON);
			unaAerolinea.cargarTiquetes("./datos/tiquetes.json", CentralPersistencia.JSON);
			System.out.println("Aviones: " + unaAerolinea.getAviones().size());
			System.out.println("Rutas: " + unaAerolinea.getRutas().size());
			System.out.println("Vuelos: " + unaAerolinea.getVuelos().size());
			System.out.println("Clientes: " + unaAerolinea.getClientes().size());

			// 2. Programar un vuelo
			System.out.println("\n--- Programar vuelo ---");
			unaAerolinea.programarVuelo("2024-12-20", "4558", "Airbus A320");
			System.out.println("Vuelo 4558 del 2024-12-20 programado. Vuelos: " + unaAerolinea.getVuelos().size());

			try {
				unaAerolinea.programarVuelo("2024-12-20", "4558", "Airbus A320");
			}
			catch (Exception e) {
				System.out.println("No se pudo programar: " + e.getMessage());
			}

			// 3. Vender tiquetes
			System.out.println("\n--- Vender tiquetes ---");
			int ventaBaja = unaAerolinea.venderTiquetes("Alice", "2024-11-05", "4558", 2);
			System.out.println("Alice compra 2 tiquetes en temporada baja: $" + ventaBaja);

			int ventaCorporativa = unaAerolinea.venderTiquetes("Apple", "2024-11-05", "4558", 1);
			System.out.println("Apple compra 1 tiquete en temporada baja: $" + ventaCorporativa);

			int ventaAlta = unaAerolinea.venderTiquetes("Alice", "2024-12-20", "4558", 1);
			System.out.println("Alice compra 1 tiquete en temporada alta: $" + ventaAlta);

			try {
				unaAerolinea.venderTiquetes("Bob", "2024-12-20", "4558", 500);
			}
			catch (Exception e) {
				System.out.println("No se pudieron vender: " + e.getMessage());
			}

			// 4. Consultar el saldo pendiente
			System.out.println("\n--- Consultar saldo pendiente ---");
			mostrarSaldos();

			// 5. Registrar un vuelo realizado
			System.out.println("\n--- Registrar vuelo realizado ---");
			unaAerolinea.registrarVueloRealizado("2024-11-05", "4558");
			System.out.println("El vuelo 4558 del 2024-11-05 ya se realizó, estos son los saldos:");
			mostrarSaldos();

			// 6. Salvar la aerolínea
			System.out.println("\n--- Salvar aerolínea ---");
			unaAerolinea.salvarAerolinea("./datos/aerolinea_salida.json", CentralPersistencia.JSON);
			System.out.println("La aerolínea quedó guardada en ./datos/aerolinea_salida.json");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void mostrarSaldos() {
		System.out.println("Alice: $" + unaAerolinea.consultarSaldoPendienteCliente("Alice"));
		System.out.println("Bob: $" + unaAerolinea.consultarSaldoPendienteCliente("Bob"));
		System.out.println("Apple: $" + unaAerolinea.consultarSaldoPendienteCliente("Apple"));
	}

	public static void main(String[] args) {
		ConsolaArerolinea ca = new ConsolaArerolinea();
		ca.correrAplicacion();
	}
}
