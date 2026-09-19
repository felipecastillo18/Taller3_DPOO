package uniandes.dpoo.aerolinea.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;
import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea {
	@Override
	public void cargarAerolinea(String archivo, Aerolinea aerolinea)
			throws IOException, InformacionInconsistenteException {
		String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
		JSONObject raiz = new JSONObject(jsonCompleto);

		// Aviones
		Map<String, Avion> aviones = new HashMap<String, Avion>();
		JSONArray jAviones = raiz.getJSONArray("aviones");
		for (int i = 0; i < jAviones.length(); i++) {
			JSONObject jAvion = jAviones.getJSONObject(i);
			Avion avion = new Avion(jAvion.getString("nombre"), jAvion.getInt("capacidad"));
			aerolinea.agregarAvion(avion);
			aviones.put(avion.getNombre(), avion);
		}

		// Aeropuertos
		Map<String, Aeropuerto> aeropuertos = new HashMap<String, Aeropuerto>();
		JSONArray jAeropuertos = raiz.getJSONArray("aeropuertos");
		for (int i = 0; i < jAeropuertos.length(); i++) {
			JSONObject jAeropuerto = jAeropuertos.getJSONObject(i);
			try {
				Aeropuerto aeropuerto = new Aeropuerto(jAeropuerto.getString("nombre"), jAeropuerto.getString("codigo"),
						jAeropuerto.getString("nombreCiudad"), jAeropuerto.getDouble("latitud"),
						jAeropuerto.getDouble("longitud"));
				aeropuertos.put(aeropuerto.getCodigo(), aeropuerto);
			} catch (AeropuertoDuplicadoException e) {
				throw new InformacionInconsistenteException(e.getMessage());
			}
		}

		// Rutas
		JSONArray jRutas = raiz.getJSONArray("rutas");
		for (int i = 0; i < jRutas.length(); i++) {
			JSONObject jRuta = jRutas.getJSONObject(i);
			Aeropuerto origen = aeropuertos.get(jRuta.getString("origen"));
			Aeropuerto destino = aeropuertos.get(jRuta.getString("destino"));
			if (origen == null || destino == null)
				throw new InformacionInconsistenteException(
						"La ruta " + jRuta.getString("codigoRuta") + " usa un aeropuerto que no existe");

			aerolinea.agregarRuta(new Ruta(origen, destino, jRuta.getString("horaSalida"),
					jRuta.getString("horaLlegada"), jRuta.getString("codigoRuta")));
		}

		// Vuelos
		JSONArray jVuelos = raiz.getJSONArray("vuelos");
		for (int i = 0; i < jVuelos.length(); i++) {
			JSONObject jVuelo = jVuelos.getJSONObject(i);
			Ruta ruta = aerolinea.getRuta(jVuelo.getString("codigoRuta"));
			Avion avion = aviones.get(jVuelo.getString("avion"));
			if (ruta == null || avion == null)
				throw new InformacionInconsistenteException("El vuelo de la ruta " + jVuelo.getString("codigoRuta")
						+ " usa una ruta o un avión que no existe");

			aerolinea.getVuelos().add(new Vuelo(ruta, jVuelo.getString("fecha"), avion));
		}
	}

	@Override
	public void salvarAerolinea(String archivo, Aerolinea aerolinea) throws IOException {
		JSONObject jobject = new JSONObject();

		// Aviones
		JSONArray jAviones = new JSONArray();
		for (Avion avion : aerolinea.getAviones()) {
			JSONObject jAvion = new JSONObject();
			jAvion.put("nombre", avion.getNombre());
			jAvion.put("capacidad", avion.getCapacidad());
			jAviones.put(jAvion);
		}
		jobject.put("aviones", jAviones);

		// Rutas y aeropuertos (cada aeropuerto se guarda una sola vez)
		Map<String, Aeropuerto> aeropuertos = new HashMap<String, Aeropuerto>();
		JSONArray jRutas = new JSONArray();
		for (Ruta ruta : aerolinea.getRutas()) {
			aeropuertos.put(ruta.getOrigen().getCodigo(), ruta.getOrigen());
			aeropuertos.put(ruta.getDestino().getCodigo(), ruta.getDestino());

			JSONObject jRuta = new JSONObject();
			jRuta.put("codigoRuta", ruta.getCodigoRuta());
			jRuta.put("origen", ruta.getOrigen().getCodigo());
			jRuta.put("destino", ruta.getDestino().getCodigo());
			jRuta.put("horaSalida", ruta.getHoraSalida());
			jRuta.put("horaLlegada", ruta.getHoraLlegada());
			jRutas.put(jRuta);
		}

		JSONArray jAeropuertos = new JSONArray();
		for (Aeropuerto aeropuerto : aeropuertos.values()) {
			JSONObject jAeropuerto = new JSONObject();
			jAeropuerto.put("codigo", aeropuerto.getCodigo());
			jAeropuerto.put("nombre", aeropuerto.getNombre());
			jAeropuerto.put("nombreCiudad", aeropuerto.getNombreCiudad());
			jAeropuerto.put("latitud", aeropuerto.getLatitud());
			jAeropuerto.put("longitud", aeropuerto.getLongitud());
			jAeropuertos.put(jAeropuerto);
		}
		jobject.put("aeropuertos", jAeropuertos);
		jobject.put("rutas", jRutas);

		// Vuelos
		JSONArray jVuelos = new JSONArray();
		for (Vuelo vuelo : aerolinea.getVuelos()) {
			JSONObject jVuelo = new JSONObject();
			jVuelo.put("codigoRuta", vuelo.getRuta().getCodigoRuta());
			jVuelo.put("fecha", vuelo.getFecha());
			jVuelo.put("avion", vuelo.getAvion().getNombre());
			jVuelos.put(jVuelo);
		}
		jobject.put("vuelos", jVuelos);

		// Escribir la estructura JSON en un archivo
		PrintWriter pw = new PrintWriter(archivo);
		jobject.write(pw, 2, 0);
		pw.close();
	}

}
