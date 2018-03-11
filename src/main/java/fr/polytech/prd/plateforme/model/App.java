package fr.polytech.prd.plateforme.model;


import javax.swing.SwingUtilities;

import fr.polytech.prd.plateforme.view.MainView;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class App {

    public static void main(final String[] args) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainView();
            }
        });
    }
}

