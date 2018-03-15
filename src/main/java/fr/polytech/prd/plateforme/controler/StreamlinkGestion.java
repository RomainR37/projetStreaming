package fr.polytech.prd.plateforme.controler;

import java.io.IOException;

public class StreamlinkGestion extends Thread{

	private PlayMediaObserver pmo = new PlayMediaObserver();
	public StreamlinkGestion()
	{

	}
	
	public void run()
	{
		try {
			Process p = Runtime.getRuntime().exec("streamlink https://www.france.tv/france-3/direct.html best --player-external-http --player-external-http-port 52053");
			
			AfficheurFlux fluxSortie = new AfficheurFlux(p.getInputStream());
            AfficheurFlux fluxErreur = new AfficheurFlux(p.getErrorStream());

            fluxSortie.addObserver(pmo);
            
            new Thread(fluxSortie).start();
            new Thread(fluxErreur).start();
            
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
