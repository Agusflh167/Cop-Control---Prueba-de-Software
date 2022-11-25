package Test_Corno;

import avion.Avion;
import avion.AvionSimple;
import copControl.Dificultad;
import copControl.Juego;
import copControl.Jugador;
import copControl.Mapa;
import copControl.Nivel;
import copControl.Posicion;

import org.junit.Before;
import org.junit.Test;
import pista.Pista;
import pista.PistaSimple;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JuegoTestIntegracion {
    Nivel nivelActual;
    Nivel nivelSiguiente;
    Pista pista,pista2;
    Jugador jugador;
    List<Nivel> niveles;
    Mapa mapa,mapa2;
    Dificultad dificultad, dificultadSiguiente;
    Juego juego;
    Posicion posicionPista;

    @Before
    public void setUp() throws Exception {
        posicionPista = new Posicion(100,100);
        List<Pista> pistas = new ArrayList<Pista>();
        pista = new PistaSimple(posicionPista);
        pistas.add(pista);
        mapa = new Mapa(pistas);

        List<Pista> pistas2 = new ArrayList<Pista>();
        pista2 = new PistaSimple(posicionPista);
        pistas2.add(pista2);
        mapa2 = new Mapa(pistas2);

        dificultad = new Dificultad(3,2,1);
        dificultadSiguiente = new Dificultad(1,5,2);

        nivelActual = new Nivel(mapa, dificultad);
        nivelSiguiente = new Nivel(mapa2,dificultadSiguiente);

        niveles = new ArrayList<Nivel>();
        niveles.add(nivelActual);
        niveles.add(nivelSiguiente);

        System.out.println(nivelActual);
        System.out.println(nivelSiguiente);
        jugador = new Jugador("Agustin");

        juego = new Juego(jugador, niveles);

    }

    @Test
    public void generacionUnAvion() {
        juego.colocarAvion();
        juego.colocarAvion();
        juego.vivir();
        juego.vivir();
        juego.colocarAvion();
        assertTrue(mapa.getAvionesEnAire().size() == 1);

    }

    @Test
    public void pasarNivelAterrizandoMinimoDeAviones() {

        Posicion posicionAvion1 = new Posicion(90,90);
        Posicion posicionAvion2 = new Posicion(85,85);
        Posicion posicionAvion3 = new Posicion(95,95);

        Avion avionUno = new AvionSimple(posicionAvion1, posicionPista, mapa);
        Avion avionDos = new AvionSimple(posicionAvion2, posicionPista, mapa);
        Avion avionTres = new AvionSimple(posicionAvion3, posicionPista, mapa);

        juego.chequearAterrizajes();
        mapa.colocarAvionEnAire(avionUno);
        juego.chequearAterrizajes();
        mapa.colocarAvionEnAire(avionDos);
        juego.chequearAterrizajes();
        mapa.colocarAvionEnAire(avionTres);
        juego.chequearAterrizajes();

        juego.vivir();

        assertEquals(juego.getNivelActual(), nivelSiguiente);
    }

    @Test
    public void dosAvionesPuedenAterrizarALaVez() {


        Posicion posicionAvion1 = new Posicion(115,100);
        Posicion posicionAvion2 = new Posicion(85,100);

        Avion avionUno = new AvionSimple(posicionAvion1, posicionPista, mapa);
        Avion avionDos = new AvionSimple(posicionAvion2, posicionPista, mapa);


        mapa.colocarAvionEnAire(avionUno);
        mapa.colocarAvionEnAire(avionDos);
        int avionesAterrizados = juego.getNivelActual().aterrizarAviones();

        assertEquals(avionesAterrizados, 2);
    }

    @Test
    public void sePasaDeNivelSiDosAvionesAterrizanALaVez() {

        Posicion posicionAvion1 = new Posicion(95,95);
        Posicion posicionAvion2 = new Posicion(105,105);
        Posicion posicionAvion3 = new Posicion(115,100);
        Posicion posicionAvion4 = new Posicion(85,100);

        Avion avionUno = new AvionSimple(posicionAvion1, posicionPista, mapa);
        Avion avionDos = new AvionSimple(posicionAvion2, posicionPista, mapa);
        Avion avionSimultaneoUno = new AvionSimple(posicionAvion3, posicionPista, mapa);
        Avion avionSimultaneoDos = new AvionSimple(posicionAvion4, posicionPista, mapa);

        mapa.colocarAvionEnAire(avionUno);
        juego.chequearAterrizajes();
        mapa.colocarAvionEnAire(avionDos);
        juego.chequearAterrizajes();

        mapa.colocarAvionEnAire(avionSimultaneoUno);
        mapa.colocarAvionEnAire(avionSimultaneoDos);
        juego.chequearAterrizajes();
        juego.vivir();

        assertEquals(juego.getNivelActual(), nivelSiguiente);
    }




}