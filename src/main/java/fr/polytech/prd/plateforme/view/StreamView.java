package fr.polytech.prd.plateforme.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.polytech.prd.plateforme.controler.AfficheurFlux;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class StreamView {


	private final JFrame frame;

	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;


	public StreamView(){
		frame = new JFrame("Plateforme d'acquisition");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println(e);
				mediaPlayerComponent.release();
				System.exit(0);
			}
		});

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);


		mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void playing(MediaPlayer mediaPlayer) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						frame.setTitle("Test Stream");
					}
				});
			}

			@Override
			public void finished(MediaPlayer mediaPlayer) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						closeWindow();
					}
				});
			}

			@Override
			public void error(MediaPlayer mediaPlayer) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(
								frame,
								"Failed to play media",
								"Error",
								JOptionPane.ERROR_MESSAGE
								);
						closeWindow();
					}
				});
			}
		});

		frame.setContentPane(contentPane);
		frame.setVisible(true);


		try {
			Process p = Runtime.getRuntime().exec("streamlink https://www.france.tv/france-3/direct.html best --player-external-http --player-external-http-port 52053");
			
			AfficheurFlux fluxSortie = new AfficheurFlux(p.getInputStream());
            AfficheurFlux fluxErreur = new AfficheurFlux(p.getErrorStream());

            new Thread(fluxSortie).start();
            new Thread(fluxErreur).start();

            p.wait();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MediaPlayer mediaplayer = mediaPlayerComponent.getMediaPlayer();
		mediaplayer.playMedia("http://127.0.0.1:52053/");

	}
	
	private void closeWindow() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}