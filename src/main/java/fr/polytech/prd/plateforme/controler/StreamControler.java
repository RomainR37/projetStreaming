package fr.polytech.prd.plateforme.controler;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.polytech.prd.plateforme.model.TVChannel;
import fr.polytech.prd.plateforme.view.StreamView;

/**
 * <b>StreamControler is the class that handle with the stream to run</b>
 * 
 * <p>
 * StreamControler starts a stream by calling a StreamView class and running the
 * streamlink command
 * </p>
 * 
 * @author Romain ROUSSEAU
 * 
 */
public class StreamControler {

	/**
	 * Logger object that displays informations to the user
	 */
	Logger log = LoggerFactory.getLogger("fr.polytech.prd.plateforme.controler.StreamControler");

	/**
	 * PlayMediaObserver that will observe the TextInpuStreamControler thread.
	 */
	private PlayMediaObserver pmo = new PlayMediaObserver();

	/**
	 * Viewer of the stream.
	 */
	private StreamView streamview;

	/**
	 * TVChannel object to keep the attribute of the channel read in memory.
	 */
	TVChannel channel;

	/**
	 * Quality to give to streamlink. Set to best by default.
	 */
	private String quality = "best";

	/**
	 * Integer that represents the communication port in which the stream will
	 * be read.
	 */
	private Integer port = ThreadLocalRandom.current().nextInt(1025, 9999);

	/**
	 * Constructor of the StreamControler.
	 *
	 * The parameter is the channel that will be read.
	 * 
	 * @param channel:
	 *            channel that will be read
	 */
	public StreamControler(TVChannel channel) {
		this.channel = channel;
	}

	/**
	 * Method that launches the stream. Create a StreamView object and call the
	 * run() method.
	 */
	public void launchStream() {
		log.debug("Creation of the StreamView");
		streamview = new StreamView(channel.getChannelName());

		log.debug("Run Streamlink");
		run();
	}

	/**
	 * Run a thread that execute streamlink program and a TextInputStreamControler thread that read the log input.
	 * 
	 */
	public void run() {
		String[] command = { "streamlink", channel.getChannelAddress(), quality, "--player-external-http",
				"--player-external-http-port", port.toString() };
		try {
			Process p = Runtime.getRuntime().exec(command);

			TextInputStreamControler fluxSortie = new TextInputStreamControler(p.getInputStream(), port);

			fluxSortie.addObserver(pmo);

			new Thread(fluxSortie).start();

		} catch (IOException e1) {
			log.error("Error", e1);
			throw new RuntimeException("info erreur", e1);
		}
	}

	/**
	 * Observer class that calls its update method in two cases:
	 * 
	 * <ul>
	 *  <li>When the log displays "Stream ended" and there is no stream left to run, the program will terminate.</li>
	 * 	<li>When the log displays the address to read, which means that the stream is ready to be read.</li>
	 * </ul>
	 * 
	 * 
	 * @author Romain Rousseau
	 *
	 */
	public class PlayMediaObserver implements Observer {

		public void update(Observable o, Object arg) {
			if (((TextInputStreamControler) o).getLine().contains("Stream ended")
					&& MainControler.getNbStreamRunning() == 0) {
				log.debug("All stream closed");
				log.debug("Terminate application");
				System.exit(0);
			} else if (((TextInputStreamControler) o).getLine().contains("http://127.0.0.1:" + port)) {
				log.debug("Starting " + channel.getChannelName());
				streamview.playMedia(port);
			}
		}
	}
}
