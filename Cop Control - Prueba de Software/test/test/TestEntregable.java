package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import avion.Avion;
import avion.AvionPesado;
import avion.AvionSimple;
import copControl.Dificultad;
import copControl.Juego;
import copControl.Jugador;
import copControl.Mapa;
import copControl.Nivel;
import copControl.Posicion;
import copControl.Trayectoria;
import pista.Helipuerto;
import pista.Pista;
import pista.PistaSimple;
import pista.PosicionesEntradaVaciaException;

public class TestEntregable {
    
	private Jugador jugador;
	private Juego juego;
	private Nivel nivel;
	private List<Nivel> niveles = new ArrayList<>();
	private Mapa mapa;
	private Dificultad dificultad;
	private Posicion posicion;
	private AvionSimple avion;
	private List<Pista> pistas = new ArrayList<Pista>();
	private PistaSimple pista;
	private Posicion posicionPista;
	private Nivel nivelFinal;
	private AvionSimple avion2;
	private Mapa mapa2;
	private AvionSimple avionChoque1;
	private AvionSimple avionChoque2;
	private AvionSimple avionAterrizaje3;
	private Posicion posicion2;
	private AvionPesado avionPesado;
	
	@Before
	public void setUp() throws Exception {
		jugador = new Jugador("Damian");
		dificultad = new Dificultad(1,0,1);
		mapa = new Mapa();
		mapa2 = new Mapa();
		nivel = new Nivel(mapa,dificultad);
		nivelFinal = new Nivel(mapa2, new Dificultad(1, 0, 1));
		niveles.add(nivel);
		niveles.add(nivelFinal);
		juego = new Juego(jugador, niveles);
		
		
		posicion = new Posicion(5,7);
        posicion2 = new Posicion(8,6);
		posicionPista = new Posicion(50,50);
		avion = new AvionSimple(posicionPista,posicionPista,mapa);
		avion2 = new AvionSimple(posicionPista,posicionPista,mapa);
		avionChoque1 = new AvionSimple(posicion,posicion2,mapa);
		avionChoque2 = new AvionSimple(posicion,posicion2,mapa);
		avionAterrizaje3 = new AvionSimple(posicionPista,posicionPista,mapa);
		avionPesado = new AvionPesado(posicion2, posicion, mapa);
		pista = new PistaSimple(posicionPista);
		pistas.add(pista);
		
		mapa.setPistas(pistas);
		mapa2.setPistas(pistas);
		nivel.colocarAvionEnAire(avion);
		nivelFinal.colocarAvionEnAire(avion2);
        nivel.colocarAvionEnAire(avionChoque1);
		nivel.colocarAvionEnAire(avionChoque2);
		nivel.colocarAvionEnAire(avionAterrizaje3);
        
	}
	
	@Test
	public void testAparecerUnAvion() {
		juego.colocarAvion();
		assertTrue("El juego debe tener un avion.", nivel.tieneAvionesVolando());
		System.out.println("Prueba exitosa: Aparecio un Avion");
	}
	
	

    @Test
	public void testAvionesVolando() {
		assertTrue("Hay aviones volando", nivel.tieneAvionesVolando());
		System.out.println("Prueba Exitosa: Hay aviones volando");
	}
	
	@Test
	public void testPistasCompatibles() {
		
		assertTrue("Debe haber al menos una pista compatible", nivel.tienePistaAdecuada(avionAterrizaje3));
		System.out.println("Prueba Exitosa: Debe haber al menos una pista compatible.");
	}
	
    @Test
	public void testAvionesChocaron() {
		assertTrue("Los aviones deben chocar si estan en la misma posicion", nivel.huboChoque());
		System.out.println("Prueba Exitosa: Hubo choque");
		assertFalse("El juego esta perdido si dos aviones colisionan", nivel.estaGanado());
	}
    
    @Test
	public void testGenerarPosicionExtremoAlAzar() 
	{	
		posicion = mapa.generarPosicionExtremoAlAzar();
		
		
		assertTrue("Fallo - Se genero una posicion fuera de los limites", 
				posicion.getCoordenadaX() >= 0 && 
				posicion.getCoordenadaY() >= 0 &&
				posicion.getCoordenadaX() <= mapa.getDimension() &&
				posicion.getCoordenadaY() <= mapa.getDimension() ); 
	}

	@Test
    public void testPistaPuedeAterrizarAvionSimple() {
        boolean puedeAterrizar = pista.puedeAterrizar(avion);
        assertTrue(puedeAterrizar);
		System.out.println("Prueba Exitosa: La pista puede aterrizar el avión simple.");
    }

	@Test
    public void testAvionNoPuedeAterrizarEnPista() {
        boolean puedeAterrizar = avionPesado.puedeAterrizar(pista);
        assertFalse(puedeAterrizar);
        System.out.println("Prueba Exitosa: El avion pesado no puede aterrizar en la pista.");
    }
}
