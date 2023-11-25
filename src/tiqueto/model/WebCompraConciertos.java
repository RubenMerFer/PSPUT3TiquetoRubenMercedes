package tiqueto.model;

import tiqueto.IOperacionesWeb;
import static tiqueto.EjemploTicketMaster.TOTAL_ENTRADAS;
import static tiqueto.EjemploTicketMaster.NUM_FANS;
import static tiqueto.EjemploTicketMaster.REPOSICION_ENTRADAS;

public class WebCompraConciertos implements IOperacionesWeb{

	//Variables
	static int entradasDisponibles;
	private boolean ventaAbierta;
	private boolean reposicionIniciada= false;
	public WebCompraConciertos(int entradasDisponibles) {
		this.entradasDisponibles= TOTAL_ENTRADAS;
		this.ventaAbierta=true;
	}

	public synchronized boolean comprarEntrada() {
		if (entradasDisponibles > 0) {
			// Lógica para comprar entrada
			entradasDisponibles--;
			System.out.println("Entrada comprada. Entradas disponibles: " + entradasDisponibles);
			return true;
		}
		return false;
	}

	@Override
	public synchronized int reponerEntradas(int numeroEntradas) {
		//Para reponer las entradas
		entradasDisponibles += numeroEntradas;

		mensajeWeb("REPONIENDO " + numeroEntradas + " ENTRADAS. ENTRADAS" +
				" DISPONIBLES EN TOTAL: " + entradasDisponibles);

		ventaAbierta=true;
		notifyAll(); //Se notifica a los fans que se repusieron entradas
		return entradasDisponibles;
	}//Fin sincronización


	@Override
	public synchronized void cerrarVenta() {
		//Ponemos la variable ventaAbierta a false
		ventaAbierta=false;
		mensajeWeb("VENTA DE ENTRADAS CERRADA. QUIEN QUIERA COMPRAR MÁS ENTRADAS " +
				"YA NO PUEDE PORQUE ESTÁN AGOTADAS");
	}//Fin sincronización

	@Override
	public synchronized boolean hayEntradas() {
		return entradasDisponibles > 0;
	}


	@Override
	public synchronized int entradasRestantes() {
		return entradasDisponibles;
	}//Fin sincronización


	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeWeb(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| WebCompra: " + mensaje);

	}

}