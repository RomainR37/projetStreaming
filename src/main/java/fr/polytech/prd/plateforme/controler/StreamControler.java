package fr.polytech.prd.plateforme.controler;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;

import fr.polytech.prd.plateforme.model.TVChannel;
import fr.polytech.prd.plateforme.view.StreamView;

public class StreamControler{

	private PlayMediaObserver pmo = new PlayMediaObserver();
	private StreamView streamview;
	
	TVChannel channel;
	
	private String quality = "best";
	private Integer port = ThreadLocalRandom.current().nextInt(1025, 9999);
	

	public StreamControler()
	{
		
	}
	
	public StreamControler(TVChannel channel)
	{
		this.channel = channel;
	}
	
	public void launchStream()
	{
		streamview = new StreamView(channel.getChannelName());
		run();
	}
	

	public void run()
	{
		String[] command = {
				"streamlink", 
				channel.getChannelAdress(),
				quality,
				"--player-external-http",
				"--player-external-http-port",
				port.toString()};
		try {
			Process p = Runtime.getRuntime().exec(command);
			
			AfficheurFlux fluxSortie = new AfficheurFlux(p.getInputStream(), port);
            AfficheurFlux fluxErreur = new AfficheurFlux(p.getErrorStream(), port);

            fluxSortie.addObserver(pmo);
            
            new Thread(fluxSortie).start();
            new Thread(fluxErreur).start();
            
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
	public class PlayMediaObserver implements Observer{

			public void update(Observable o, Object arg) {
				streamview.playMedia(port);
			}
			
		}
}
