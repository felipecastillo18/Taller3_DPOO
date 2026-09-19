package uniandes.dpoo.aerolinea.consola;

import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;

public class ConsolaArerolinea extends ConsolaBasica
{
    private Aerolinea unaAerolinea;

    /**
     * Corre la aplicación mostrando, en orden, cada uno de los requerimientos de la aerolínea: cargar, programar un vuelo, vender tiquetes, consultar el saldo pendiente,
     * registrar un vuelo realizado y salvar.
     */
    public void correrAplicacion( )
    {
        try
        {
            unaAerolinea = new Aerolinea( );

            // 1. Cargar la aerolínea y los tiquetes
            System.out.println( "=== 1. Cargar aerolínea ===" );
            unaAerolinea.cargarAerolinea( "./datos/aerolinea.json", CentralPersistencia.JSON );
            unaAerolinea.cargarTiquetes( "./datos/tiquetes.json", CentralPersistencia.JSON );
            System.out.println( "Aviones: " + unaAerolinea.getAviones( ).size( ) );
            System.out.println( "Rutas: " + unaAerolinea.getRutas( ).size( ) );
            System.out.println( "Vuelos: " + unaAerolinea.getVuelos( ).size( ) );
            System.out.println( "Clientes: " + unaAerolinea.getClientes( ).size( ) );

            // 2. Programar un vuelo
            System.out.println( "\n=== 2. Programar vuelo ===" );
            unaAerolinea.programarVuelo( "2024-12-20", "4558", "Airbus A320" );
            System.out.println( "Vuelo 4558 del 2024-12-20 programado. Vuelos: " + unaAerolinea.getVuelos( ).size( ) );
            try
            {
                unaAerolinea.programarVuelo( "2024-12-20", "4558", "Airbus A320" );
            }
            catch( Exception e )
            {
                System.out.println( "Error esperado: " + e.getMessage( ) );
            }

            // 3. Vender tiquetes
            System.out.println( "\n=== 3. Vender tiquetes ===" );
            System.out.println( "Alice compra 2 tiquetes (temporada baja): $" + unaAerolinea.venderTiquetes( "Alice", "2024-11-05", "4558", 2 ) );
            System.out.println( "Apple compra 1 tiquete (temporada baja, corporativo): $" + unaAerolinea.venderTiquetes( "Apple", "2024-11-05", "4558", 1 ) );
            System.out.println( "Alice compra 1 tiquete (temporada alta): $" + unaAerolinea.venderTiquetes( "Alice", "2024-12-20", "4558", 1 ) );
            try
            {
                unaAerolinea.venderTiquetes( "Bob", "2024-12-20", "4558", 500 );
            }
            catch( Exception e )
            {
                System.out.println( "Error esperado: " + e.getMessage( ) );
            }

            // 4. Consultar saldo pendiente
            System.out.println( "\n=== 4. Consultar saldo pendiente ===" );
            System.out.println( "Alice: $" + unaAerolinea.consultarSaldoPendienteCliente( "Alice" ) );
            System.out.println( "Bob: $" + unaAerolinea.consultarSaldoPendienteCliente( "Bob" ) );
            System.out.println( "Apple: $" + unaAerolinea.consultarSaldoPendienteCliente( "Apple" ) );

            // 5. Registrar vuelo realizado
            System.out.println( "\n=== 5. Registrar vuelo realizado ===" );
            unaAerolinea.registrarVueloRealizado( "2024-11-05", "4558" );
            System.out.println( "Vuelo 4558 del 2024-11-05 realizado. Saldos pendientes:" );
            System.out.println( "Alice: $" + unaAerolinea.consultarSaldoPendienteCliente( "Alice" ) );
            System.out.println( "Bob: $" + unaAerolinea.consultarSaldoPendienteCliente( "Bob" ) );
            System.out.println( "Apple: $" + unaAerolinea.consultarSaldoPendienteCliente( "Apple" ) );

            // 6. Salvar la aerolínea
            System.out.println( "\n=== 6. Salvar aerolínea ===" );
            unaAerolinea.salvarAerolinea( "./datos/aerolinea_salida.json", CentralPersistencia.JSON );
            System.out.println( "Aerolínea guardada en ./datos/aerolinea_salida.json" );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }

    public static void main( String[] args )
    {
        ConsolaArerolinea ca = new ConsolaArerolinea( );
        ca.correrAplicacion( );
    }
}
