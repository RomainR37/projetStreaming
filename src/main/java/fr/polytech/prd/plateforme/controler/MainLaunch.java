package fr.polytech.prd.plateforme.controler;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class MainLaunch {

	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger("fr.polytech.prd.plateforme.controler.MainLaunch");
		log.debug("Looking for vlc-j library");
		
		new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
           
            	log.debug("Creation of the MainControler");
                MainControler mc = new MainControler(args[0]);
                
                log.debug("starting MainControler");
                mc.start();
            }
        });
	}

}
