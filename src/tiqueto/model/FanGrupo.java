package tiqueto.model;

import static tiqueto.EjemploTicketMaster.MAX_ENTRADAS_POR_FAN;
import static tiqueto.EjemploTicketMaster.fansActivos;
import static tiqueto.EjemploTicketMaster.fansActivosLock;
public class FanGrupo extends Thread {

	//Variables
	final WebCompraConciertos webCompra;
	int numeroFan;
	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;
	public static int totalEntradasVendidas= 0; //Variable para rastrear las entradas vendidas

	//Constructor
	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.webCompra = web;
		this.numeroFan = numeroFan;
	}//Fin constructor

	//Método 'run()'
	@Override
	public void run() {
		mensajeFan("¡QUE EMOCIONADO ESTOY POR EL CONCIERTO!");

		while (entradasCompradas < MAX_ENTRADAS_POR_FAN && webCompra.hayEntradas()) {
			mensajeFan("VOY A INTENTAR COMPRAR UNA ENTRADA...");
			if (webCompra.comprarEntrada()) {
				entradasCompradas++;

				synchronized (FanGrupo.class){
					totalEntradasVendidas++; //Garantizamos que solo un fan pueda entrar al bloque al mismo tiempo
				}//Fin sincronización

				mensajeFan("¡HE COMPRADO UNA ENTRADA! ¡TENGO YA " + entradasCompradas + "!");
				try {
					//El fan espera entre 1 y 3 segundos después de comprar una entrada
					int tiempoEspera= 1000 + (int) (Math.random() * 2000);
					Thread.sleep(tiempoEspera); //Espera entre 1 y 3 segundos
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
			} else {
				mensajeFan("No hay entradas disponibles, esperando a que se repongan...");
				try {
					Thread.sleep((long) (1000 + Math.random() * 2000)); //Espera entre 1 y 3 segundos
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
			}
		}//Fin while

		if (entradasCompradas >= MAX_ENTRADAS_POR_FAN) {
			mensajeFan("HE ALCANZADO EL MÁXIMO DE ENTRADAS PERMITIDAS");
		}else {
			mensajeFan("VAYA, SE HAN AGOTADO LAS ENTRADAS");
		}

		mensajeFan("¡HE TERMINADO MI COMPRA DE ENTRADAS!");

		//Notificamos que el fan ha terminado sus compras (entradas agotadas o ya compró el máximo)
		synchronized (fansActivosLock){
			fansActivos--;
			if (fansActivos == 0){
				webCompra.cerrarVenta(); //Todos los fans terminaron de comprar
			}
		}//Fin sincronización
	}//Fin run

	//Método para mostrar las entradas compradas por cada fan
	public void dimeEntradasCompradas() {
		mensajeFan("Sólo he conseguido: " + entradasCompradas);
	}//Fin método

	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeFan(String mensaje) {
		System.out.println(System.currentTimeMillis() + "|" + tabuladores +" Fan "+this.numeroFan +": " + mensaje);
	}//Fin método
}