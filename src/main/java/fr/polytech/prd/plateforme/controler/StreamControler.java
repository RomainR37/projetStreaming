package fr.polytech.prd.plateforme.controler;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.polytech.prd.plateforme.model.TVChannel;
import fr.polytech.prd.plateforme.view.StreamView;

public class StreamControler {

	Logger log = LoggerFactory.getLogger("fr.polytech.prd.plateforme.controler.StreamControler");

	private PlayMediaObserver pmo = new PlayMediaObserver();
	private StreamView streamview;

	TVChannel channel;

	private String quality = "best";
	private Integer port = ThreadLocalRandom.current().nextInt(1025, 9999);

	public StreamControler(TVChannel channel) {
		this.channel = channel;
	}

	public void launchStream() {
		log.debug("Creation of the StreaView");
		streamview = new StreamView(channel.getChannelName());

		log.debug("Run Streamlink");
		run();
	}

	public void run() {
		String[] command = { "streamlink", channel.getChannelAdress(), quality, "--player-external-http",
				"--player-external-http-port", port.toString() };
		try {
			Process p = Runtime.getRuntime().exec(command);

			TextInputStreamControler fluxSortie = new TextInputStreamControler(p.getInputStream(), port);
			TextInputStreamControler fluxErreur = new TextInputStreamControler(p.getErrorStream(), port);

			fluxSortie.addObserver(pmo);

			new Thread(fluxSortie).start();
			new Thread(fluxErreur).start();

		} catch (IOException e1) {
			log.error("Error", e1);
			throw new RuntimeException("info erreur", e1);
		}
	}

	public class PlayMediaObserver implements Observer {

		public void update(Observable o, Object arg) {
			if (((TextInputStreamControler) o).getLine().contains("Stream ended") && MainControler.nbStreamRunning == 0) {
				log.debug("All stream closed");
				log.debug("Terminate application");
				System.exit(0);
			} else if (((TextInputStreamControler) o).getLine().contains("http://127.0.0.1:"+port)){
				log.debug("Starting "+channel.getChannelName());
				streamview.playMedia(port);
			}
		}

	}
}
