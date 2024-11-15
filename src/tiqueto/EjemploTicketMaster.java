package tiqueto;

import java.util.ArrayList;
import java.util.List;
import tiqueto.model.FanGrupo;
import tiqueto.model.PromotoraConciertos;
import tiqueto.model.WebCompraConciertos;

public class EjemploTicketMaster {

	// Total de entradas que se vender�n
	public static int TOTAL_ENTRADAS = 100;

	// El número de entradas que reponerá cada vez el promotor
	public static int REPOSICION_ENTRADAS = 19;

	// El número máximo de entradas por fan
	public static int MAX_ENTRADAS_POR_FAN = 7;

	// El número total de fans
	public static int NUM_FANS = 10;

	public static int fansActivos; //Los fans que aún están comprando entradas
	public static final Object fansActivosLock= new Object();
	//Objeto de bloque para sincronizar el acceso a la variable compartida fansActivos

	public static void main(String[] args) throws InterruptedException {

		String mensajeInicial = "[ Empieza la venta de tickets. Se esperan %d fans, y un total de %d entradas ]";
		System.out.println(String.format(mensajeInicial, NUM_FANS, TOTAL_ENTRADAS));
		WebCompraConciertos webCompra = new WebCompraConciertos(0);
		PromotoraConciertos liveNacion = new PromotoraConciertos(webCompra);
		List<FanGrupo> fans = new ArrayList<>();

		synchronized (fansActivosLock) {
			fansActivos = NUM_FANS;
		}//Fin sincronización

		//Creamos todos los fans
		for (int numFan = 1; numFan <= NUM_FANS; numFan++) {
			FanGrupo fan = new FanGrupo(webCompra, numFan);
			fans.add(fan);
			fan.start();
		}//Fin for

		//Lanzamos al promotor para que empiece a reponer entradas
		liveNacion.start();

		//Esperamos a que el promotor termine, para preguntar a los fans cu�ntas entradas tienen compradas
		liveNacion.join();

		//Esperamos a que todos los fans terminen
		for (FanGrupo fan : fans) {
			fan.join();
		}//Fin for

		System.out.println("\n [ Terminada la fase de venta - Sondeamos a pie de calle a los compradores ] \n");
		System.out.println("Total entradas ofertadas: " + TOTAL_ENTRADAS);
		System.out.println("Total entradas disponibles en la web: " + (TOTAL_ENTRADAS - FanGrupo.totalEntradasVendidas) );

		//Les preguntamos a cada uno
		for (FanGrupo fan : fans) {
			fan.dimeEntradasCompradas();
		}//Fin for
	}
}