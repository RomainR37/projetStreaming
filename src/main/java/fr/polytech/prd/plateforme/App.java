package fr.polytech.prd.plateforme;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class App 
{
	private final JFrame frame;
	
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	
    public static void main(final String[] args )
    {
    	new NativeDiscovery().discover();
    	SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App(args);
            }
        });
    }
    
    public App(String[] args) {
        frame = new JFrame("My First Media Player");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

        frame.setContentPane(contentPane);
        frame.setVisible(true);
        
        frame.setContentPane(mediaPlayerComponent);
        frame.setVisible(true);
        mediaPlayerComponent.getMediaPlayer().playMedia(args[0]);
    }
}
