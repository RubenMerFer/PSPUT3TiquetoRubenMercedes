package tiqueto.model;

import tiqueto.EjemploTicketMaster;



public class PromotoraConciertos extends Thread {

	final WebCompraConciertos webCompra;

	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}

	//Método run

	@Override
	public void run() {
		while (webCompra.hayEntradas()) {
			if (webCompra.entradasRestantes() == 0){
				mensajePromotor("VAMOS A REPONER ENTRADAS");
				webCompra.reponerEntradas(EjemploTicketMaster.REPOSICION_ENTRADAS);

				try {
					//El promotor espera entre 3 y 8 segundos después de reponer las entradas
					int tiempoEspera= 3000 + (int)(Math.random() * 5000);
					mensajePromotor("ESPERANDO HASTA QUE LOS FANS INTENTEN COMPRAR...");
					Thread.sleep(tiempoEspera);
				} catch (InterruptedException e) {
					System.out.println("ERROR EXCEPCIÓN: " + e);
				}
			}

		}//Fin while

		if(!webCompra.hayEntradas()){
			mensajePromotor("YA NO HAY MÁS ENTRADAS DISPONIBLES");
			webCompra.cerrarVenta();
		}

	}//Fin run

	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajePromotor(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| Promotora: " + mensaje);

	}
}