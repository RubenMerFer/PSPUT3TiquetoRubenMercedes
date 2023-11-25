package tiqueto.model;

public class FanGrupo extends Thread {

	final WebCompraConciertos webCompra;
	int numeroFan;
	private String tabuladores = "\t\t\t\t";
	int entradasCompradas = 0;

	public FanGrupo(WebCompraConciertos web, int numeroFan) {
		super();
		this.numeroFan = numeroFan;
		this.webCompra = web;
	}
	
	@Override
	public void run() {
		
		//TODO
		
	}
	
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
