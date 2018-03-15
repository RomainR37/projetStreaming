package fr.polytech.prd.plateforme.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AfficheurFlux implements Runnable {

	private final InputStream inputStream;

	public AfficheurFlux(InputStream inputStream) {
		this.inputStream = inputStream;
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
				if (ligne.contains("http://127.0.0.1:52053/"))
				{
					notify();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

