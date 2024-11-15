package tiqueto.model;

import tiqueto.EjemploTicketMaster;

public class PromotoraConciertos extends Thread {

	//Variables
	final WebCompraConciertos webCompra;
	private int totalEntradasRepuestas= 0;

	//Constructor
	public PromotoraConciertos(WebCompraConciertos webCompra) {
		super();
		this.webCompra = webCompra;
	}//Fin constructor

	//Método 'run()'
	@Override
	public void run() {
		while (totalEntradasRepuestas < EjemploTicketMaster.TOTAL_ENTRADAS) {
			synchronized (webCompra){
				if (webCompra.entradasRestantes() == 0 && webCompra.isVentaAbierta()){
					mensajePromotor("VAMOS A REPONER ENTRADAS");
					int entradasAReponer= Math.min(EjemploTicketMaster.REPOSICION_ENTRADAS, EjemploTicketMaster.TOTAL_ENTRADAS - totalEntradasRepuestas);
					/*Usamos Math.min para determinar el menor de dos valores: REPOSICION_ENTRADAS y TOTAL_ENTRADAS - totalEntradasRepuestas
					* Ej:
					  TOTAL_ENTRADAS = 100; REPOSICION_ENTRADAS = 19; totalEntradasRepuestas = 85. El cálculo sería:
					  100 - 85 = 15 entradas restantes, por tanto Math.min(19, 15) es 15 porque es el menor de los dos valores.
					  Se repondrían 15 entradas*/

					if (entradasAReponer > 0){
						mensajePromotor("REPONGO " + entradasAReponer + " ENTRADAS");
						webCompra.reponerEntradas(entradasAReponer);
						totalEntradasRepuestas += entradasAReponer;

						try {
							//El promotor espera entre 3 y 8 segundos después de reponer las entradas
							int tiempoEspera= 3000 + (int)(Math.random() * 5000);
							mensajePromotor("ESPERANDO HASTA QUE LOS FANS INTENTEN COMPRAR...");
							Thread.sleep(tiempoEspera);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}
				} else if (!webCompra.isVentaAbierta()) {
					break;
				}
			}//Fin sincronización
		}//Fin while

		mensajePromotor("YA NO HAY MÁS ENTRADAS DISPONIBLES");
		webCompra.cerrarVenta();
	}//Fin run

	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajePromotor(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| Promotora: " + mensaje);
	}//Fin método
}