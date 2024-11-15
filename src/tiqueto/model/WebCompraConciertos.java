package tiqueto.model;

import tiqueto.IOperacionesWeb;
import static tiqueto.EjemploTicketMaster.TOTAL_ENTRADAS;

public class WebCompraConciertos implements IOperacionesWeb{

	//Variables
	private int entradasDisponibles;
	private int entradasTotales= TOTAL_ENTRADAS;
	private boolean ventaAbierta;

	//Constructor
	public WebCompraConciertos(int entradasIniciales) {
		this.entradasDisponibles= entradasIniciales;
		this.ventaAbierta=true;
	}//Fin constructor

	//Método para comprar una entrada
	public synchronized boolean comprarEntrada() {
		if (entradasDisponibles > 0) {
			//Lógica para comprar entrada
			entradasDisponibles--;
			System.out.println("Entrada comprada. Entradas disponibles: " + entradasDisponibles);
			return true;
		} else if (!ventaAbierta) {
			return false;
		}else {
			try{
				mensajeWeb("ESPERAMOS A QUE REPONGAN ENTRADAS");
				wait();
			}catch (InterruptedException e){
				Thread.currentThread().interrupt();
			}
			return comprarEntrada(); //Reintenta la compra después de esperar
		}
	}//Fin método

	//Método para reponer entradas
	@Override
	public synchronized int reponerEntradas(int numeroEntradas) {
		//Para reponer las entradas
		entradasDisponibles += numeroEntradas;

		if (entradasDisponibles > entradasTotales){
			entradasDisponibles = entradasTotales;
		}
		mensajeWeb("REPONIENDO " + numeroEntradas + " ENTRADAS. ENTRADAS" +
				" DISPONIBLES EN TOTAL: " + entradasDisponibles);

		notifyAll(); //Se notifica a los fans que se repusieron entradas
		return entradasDisponibles;
	}//Fin método

	//Método para cerrar la venta de entradas
	@Override
	public synchronized void cerrarVenta() {
		//Ponemos la variable ventaAbierta a false
		ventaAbierta=false;
		notifyAll();
		mensajeWeb("VENTA DE ENTRADAS CERRADA. QUIEN QUIERA COMPRAR MÁS ENTRADAS " +
				"YA NO PUEDE PORQUE ESTÁN AGOTADAS");
	}//Fin método

	//Método para verificar si hay o no entradas
	@Override
	public synchronized boolean hayEntradas() {
		return  entradasDisponibles > 0 || ventaAbierta;
	}//Fin método

	//Método para mostrar las entradas restantes
	@Override
	public synchronized int entradasRestantes() {
		return entradasDisponibles;
	}//Fin método

	//Método para verificar si la venta está o no abierta
	public synchronized boolean isVentaAbierta(){
		return ventaAbierta;
	}//Fin método

	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeWeb(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| WebCompra: " + mensaje);
	}//Fin método
}