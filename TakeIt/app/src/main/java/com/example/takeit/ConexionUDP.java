package com.example.takeit;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConexionUDP extends Thread {

	private DatagramSocket relacion;
	private static InetAddress ip;
	private static int puerto = 5000;

	private boolean iniciado;



	static Observer observador;

	static ConexionUDP servidoractual;

	public ConexionUDP() {
		this.iniciado = true;
		start();
	
	}
	
	static public void setConexion(String tip, int tpuerto){
		try {
			ip = InetAddress.getByName(tip);
			puerto = tpuerto;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	static public ConexionUDP getInicializar() {
		if (servidoractual == null) {
			return new ConexionUDP();
		} else {
			return servidoractual;
		}
	}

	@Override
	public void run() {

		inicializar();
		while (this.iniciado) {
			recibir();
		}
	}

	private void inicializar() {
		try {

			//ip = InetAddress.getByName("127.0.0.1");
			relacion = new DatagramSocket(puerto);
			// relacion.setReuseAddress(true);
			// ip = InetAddress.getByName("172.30.198.131");
			// ip = InetAddress.getByName("10.0.2.2");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void recibir() {

		byte[] loqueRecibo = new byte[30];

		try {
			DatagramPacket paquete = new DatagramPacket(loqueRecibo, loqueRecibo.length);
			relacion.receive(paquete);
			String mensajeRec = new String(paquete.getData()).trim();
			recibomensaje(mensajeRec);

			ip = paquete.getAddress();
			puerto = paquete.getPort();

			if (observador != null) {
				observador.recibirMensaje(mensajeRec);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void enviar(final String mensaje) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				DatagramPacket paqueteE = new DatagramPacket(mensaje.getBytes(), mensaje.length(), ip, puerto);

				try {

					relacion.send(paqueteE);

					ip = paqueteE.getAddress();
					puerto = paqueteE.getPort();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	private void recibomensaje(String mensaje) {
		System.out.println(mensaje);
		this.enviar("Recibi el mensaje");

	}

	public void getObservador(Observer observador) {
		this.observador = observador;
	}



}// ---------------------------------------