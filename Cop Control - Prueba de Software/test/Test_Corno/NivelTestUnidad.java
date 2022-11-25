package Test_Corno;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import avion.AvionSimple;
import copControl.Dificultad;
import copControl.Juego;
import copControl.Jugador;
import copControl.Mapa;
import copControl.Nivel;
import copControl.Posicion;
import pista.Pista;
import pista.PistaSimple;
import pista.PosicionesEntradaVaciaException;

class NivelTestUnidad {
	Mapa mapaDeJuego;
	Dificultad dificultad_1;
	List<Nivel> niveles;
	Nivel nivel_1;
	Jugador jugador;
	Juego juego;
	List<Pista> pistas;
	Posicion posicionInicial;
	Posicion posicionFinal;
	AvionSimple avion, avion_2;

	@BeforeEach
	void setUp() throws Exception {

		mapaDeJuego = new Mapa();
		dificultad_1 = new Dificultad(2, 1, 1);
		niveles = new ArrayList<Nivel>();
		nivel_1 = new Nivel(mapaDeJuego, dificultad_1);
		niveles.add(nivel_1);
		jugador = new Jugador("Agustin");
		juego = new Juego(jugador, niveles);
		pistas = new ArrayList<Pista>();
		posicionInicial = new Posicion(20, 15);
		posicionFinal = new Posicion(5, 9);
		avion = new AvionSimple(posicionInicial, posicionFinal, mapaDeJuego);
		avion_2 = new AvionSimple(posicionInicial, posicionFinal, mapaDeJuego);
		try {
			PistaSimple pista = new PistaSimple(posicionInicial);
			pistas.add(pista);
		} catch (PosicionesEntradaVaciaException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testChequearAterrizaje() {
		mapaDeJuego.colocarAvionEnAire(avion);
		mapaDeJuego.colocarAvionEnAire(avion_2);
		mapaDeJuego.setPistas(pistas);

		juego.chequearAterrizajes();
		assertTrue(juego.getCantidadAvionesAterrizados() == 2,
				"Fallo - no registro aterrizaje de aviones correctamente");
	}

	@Test
	public void testChoqueFinDelJuego() {

		mapaDeJuego.setPistas(pistas);
		mapaDeJuego.colocarAvionEnAire(avion);
		mapaDeJuego.colocarAvionEnAire(avion_2);
		juego.huboChoque();

		assertTrue(!juego.estaJugandose());
	}

	@Test
	public void testAvanceDeNivelFinal() {
		juego.avanzarNivel();
		assertTrue(!juego.estaJugandose());

	}

	@Test
	public void testVivirAvionesAterrizadosYSeGano() {
		mapaDeJuego.colocarAvionEnAire(avion);
		mapaDeJuego.colocarAvionEnAire(avion_2);
		mapaDeJuego.setPistas(pistas);
		juego.chequearAterrizajes();
		juego.vivir();

		assertTrue(!juego.estaJugandose());
		assertTrue(!juego.esJuegoGanado());

	}
}
