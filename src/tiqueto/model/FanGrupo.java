package tiqueto.model;

import static tiqueto.EjemploTicketMaster.MAX_ENTRADAS_POR_FAN;

public class FanGrupo extends Thread {

	final WebCompraConciertos webCompra;
	int numeroFan;
	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;

	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.webCompra = web;
		this.numeroFan = numeroFan;
	}

	@Override
	public void run() {
		mensajeFan("¡QUE EMOCIONADO ESTOY POR EL CONCIERTO!");

		while (webCompra.hayEntradas()) {
			mensajeFan("VOY A INTENTAR COMPRAR UNA ENTRADA...");
			if (entradasCompradas < MAX_ENTRADAS_POR_FAN && webCompra.comprarEntrada()) {
				entradasCompradas++;
				mensajeFan("¡HE COMPRADO UNA ENTRADA! ¡TENGO YA " + entradasCompradas + "!");
				try {
					//El fan espera entre 1 y 3 segundos después de comprar una entrada
					int tiempoEspera= 1000 + (int) (Math.random() * 2000);
					Thread.sleep(tiempoEspera); // Espera entre 1 y 3 segundos
				} catch (Exception e) {
					System.out.println("ERROR EXCEPCIÓN: " + e);
				}
			} else {
				mensajeFan("No hay entradas disponibles, esperando a que se repongan...");
				try {
					Thread.sleep((long) (1000 + Math.random() * 2000)); // Espera entre 1 y 3 segundos
				} catch (Exception e) {
					System.out.println("ERROR EXCEPCIÓN: " + e);
				}
			}
		}//Fin while

		if (!webCompra.hayEntradas()) {
			mensajeFan("VAYA, SE HAN AGOTADO LAS ENTRADAS");
		}

		if (entradasCompradas == MAX_ENTRADAS_POR_FAN || webCompra.entradasRestantes() == 0){
			mensajeFan("¡HE TERMINADO MI COMPRA DE ENTRADAS!");
		}
	}//Fin run


	public void dimeEntradasCompradas() {

		mensajeFan("Sólo he conseguido: " + entradasCompradas);
	}

	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeFan(String mensaje) {
		System.out.println(System.currentTimeMillis() + "|" + tabuladores +" Fan "+this.numeroFan +": " + mensaje);
	}

}