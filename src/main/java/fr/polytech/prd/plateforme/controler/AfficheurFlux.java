package fr.polytech.prd.plateforme.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;

public class AfficheurFlux extends Observable implements Runnable {

	private final InputStream inputStream;
	private String addressToPlay = "http://127.0.0.1:";

	public AfficheurFlux(InputStream inputStream, int port){
		this.inputStream = inputStream;
		addressToPlay += port + "/";
	}

	private BufferedReader getBufferedReader(InputStream is) {
		return new BufferedReader(new InputStreamReader(is));
	}

	public void run() {
		BufferedReader br = getBufferedReader(inputStream);
		String ligne = "";
		try {
			while ((ligne = br.readLine()) != null) {
				System.out.println(ligne);
				if (ligne.contains(addressToPlay))
				{
				    setChanged();
					notifyObservers();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

