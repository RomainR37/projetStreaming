package fr.polytech.prd.plateforme.controler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.polytech.prd.plateforme.model.TVChannel;

/**
 * <b>The MainControler class reads the text file given and interprets it.</b>
 * 
 * <p>
 * This class reads the text file given in argument by the user and create
 * TVChannel objects that represents the channel that will be read. Once the
 * file is read and the object are created, it creates a StreamControler object
 * for each different stream to play. The class also have a integer representing
 * the number of streams running at the moment.
 * </p>
 * 
 * @author Romain Rousseau
 *
 */
public class MainControler extends Thread {

	/**
	 * Logger object that displays informations to the user
	 */
	Logger log = LoggerFactory.getLogger("fr.polytech.prd.plateforme.controler.MainControler");

	/**
	 * File object represting the text file given by the user.
	 */
	File file;

	/**
	 * Integer that gives the number of stream running.
	 */
	private static int nbStreamRunning = 0;

	/**
	 * Constructor that instance the File object.
	 * 
	 * @param fileName:
	 *            name of the text file given by the user.
	 */
	public MainControler(String fileName) {
		file = new File(fileName);
	}

	/**
	 * Getter for the number of streams running.
	 * 
	 * @return the number of stream running
	 */
	public static int getNbStreamRunning() {
		return nbStreamRunning;
	}

	/**
	 * Setter for the number of streams running
	 * 
	 * @param nbStreamRunning:
	 *            the number of streams running.
	 */
	public static void setNbStreamRunning(int nbStreamRunning) {
		MainControler.nbStreamRunning = nbStreamRunning;
	}

	/**
	 * Run method that read the file line by line. Each line is split in two
	 * parts: the name and the address of the TV channel, separated by a
	 * tabulation. Each of those line will create an object TVChannel and run a
	 * StreamControler thread. For each launches, the attribute nbStreamRunning
	 * will be incremented.
	 * 
	 * Throws a FileNotFoundException, if the file is not found.
	 */
	@Override
	public void run() {

		String ligne;
		String[] elementLigne;

		try (Scanner sc = new Scanner(file)) {
			log.debug("read file", file);
			while (sc.hasNextLine()) {
				ligne = sc.nextLine();
				elementLigne = ligne.split("\t");
				TVChannel channel = new TVChannel(elementLigne[0], elementLigne[1]);
				StreamControler streamcontroler = new StreamControler(channel);
				streamcontroler.launchStream();
				incrementStreamNumber();
				log.info("Number of stream running: ", nbStreamRunning);
			}
		} catch (FileNotFoundException e) {
			log.error("File not found");
		}
	}

	/**
	 * Method that increments the number of streams running
	 */
	public static synchronized void incrementStreamNumber() {
		nbStreamRunning++;
	}

	/**
	 * Method that decrements the number of streams running
	 */
	public static synchronized void decrementStreamNumber() {
		nbStreamRunning--;
	}

}
