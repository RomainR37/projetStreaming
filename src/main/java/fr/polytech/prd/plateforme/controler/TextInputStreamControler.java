package fr.polytech.prd.plateforme.controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <b>The TextInputStreamControler is an Observable class that reads the input
 * stream.</b>
 * 
 * 
 * This class reads the input given and notifies the observer for two
 * behaviours:
 * <ul>
 * <li>When the channel address appears in the log</li>
 * <li>When the message "Stream ended" appears</li>
 * </ul>
 * 
 * The observer present in the StreamControler class will handle those changes.
 * 
 * 
 * @author Romain Rousseau
 *
 */
public class TextInputStreamControler extends Observable implements Runnable {

	/**
	 * Logger object that displays informations to the user
	 */
	Logger log = LoggerFactory.getLogger("fr.polytech.prd.plateforme.controler.TextInputStreamControler");

	/**
	 * Object that the input stream in the log
	 */
	private final InputStream inputStream;

	/**
	 * Represents the address to read. Set to 127.0.0.1 by default.
	 */
	private String addressToPlay = "http://127.0.0.1:";

	/**
	 * Attribute for reading the input.
	 */
	String line = "";

	/**
	 * Constructor that set the inputStream object and the address.
	 * 
	 * @param inputStream:
	 *            text input stream to give
	 * @param port:
	 *            communication port to be read
	 */
	public TextInputStreamControler(InputStream inputStream, int port) {
		this.inputStream = inputStream;
		addressToPlay += port + "/";
	}

	/**
	 * Getter for the line.
	 * 
	 * @return the line
	 */
	public String getLine() {
		return line;
	}

	/**
	 * Getter for the BufferedReader object
	 * 
	 * @param is:
	 *            the input stream to read
	 * @return an object BufferedReader
	 */
	private BufferedReader getBufferedReader(InputStream is) {
		return new BufferedReader(new InputStreamReader(is));
	}

	/**
	 * Run method that reads the input stream and notifies the observer when two
	 * lines appears:
	 * <ul>
	 * <li>When the channel address appears in the log</li>
	 * <li>When the message "Stream ended" appears</li>
	 * </ul>
	 * 
	 * Throws an IOException when an error appears during the reading of the
	 * input.
	 */
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
			log.error("Exception in run method: ", e);
		}
	}
}
