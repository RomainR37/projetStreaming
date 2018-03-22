package fr.polytech.prd.plateforme.controler;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;

/**
 * <b>MainLaunch is the main class of the application</b>
 * <p>
 * This class looks for vlc-j library and run the MainControler class
 * </p>
 * 
 * @author Romain ROUSSEAU
 * @version 1.0.0
 */
public class MainLaunch {

	/**
	 * Main function of the program
	 * 
	 * Find libVLC library in the computer and run MainControler with the text
	 * file argument given. If the library is not found, an exception is raised.
	 * 
	 * @param args
	 *            : argument executed with the program, it has to be a text
	 *            file.
	 */
	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger("fr.polytech.prd.plateforme.controler.MainLaunch");
		log.debug("Looking for vlc-j library");

		new NativeDiscovery().discover();
		SwingUtilities.invokeLater(() -> {

			log.debug("Creation of the MainControler");
			MainControler mc = new MainControler(args[0]);

			log.debug("starting MainControler");
			mc.start();

		});
	}

}
