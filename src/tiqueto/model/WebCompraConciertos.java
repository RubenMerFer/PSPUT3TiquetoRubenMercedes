package tiqueto.model;

import tiqueto.IOperacionesWeb;

public class WebCompraConciertos implements IOperacionesWeb{

	
	public WebCompraConciertos() {
		super();
	}


	@Override
	public boolean comprarEntrada() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int reponerEntradas(int numeroEntradas) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void cerrarVenta() {
		// TODO Auto-generated method stub
	}


	@Override
	public boolean hayEntradas() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int entradasRestantes() {
		// TODO Auto-generated method stub
		return 0;
	}


	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeWeb(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| WebCompra: " + mensaje);
		
	}

}
