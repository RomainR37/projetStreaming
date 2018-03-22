package fr.polytech.prd.plateforme.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextInputStreamControler extends Observable implements Runnable {

	Logger log = LoggerFactory.getLogger("fr.polytech.prd.plateforme.controler.AfficheurFlux");

	private final InputStream inputStream;
	private String addressToPlay = "http://127.0.0.1:";

	String line = "";

	public TextInputStreamControler(InputStream inputStream, int port) {
		this.inputStream = inputStream;
		addressToPlay += port + "/";
	}

	public String getLine() {
		return line;
	}

	private BufferedReader getBufferedReader(InputStream is) {
		return new BufferedReader(new InputStreamReader(is));
	}

	public void run() {
		BufferedReader br = getBufferedReader(inputStream);
		try {
			while ((line = br.readLine()) != null) {
				log.info(line);
				if (line.contains(addressToPlay)) {
					setChanged();
					notifyObservers();
				}
				if (line.contains("Stream ended")) {
					setChanged();
					notifyObservers();
				}
			}
		} catch (IOException e) {
			log.error("Exception in run method: ",e);
		}
	}
}
