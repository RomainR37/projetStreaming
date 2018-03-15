package fr.polytech.prd.plateforme.controler;

import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class MainLaunch {

	public static void main(String[] args) {
		new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainControler mc = new MainControler();
                mc.start();
            }
        });
	}

}
