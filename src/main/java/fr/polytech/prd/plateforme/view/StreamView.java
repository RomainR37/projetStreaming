package fr.polytech.prd.plateforme.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class StreamView {


	private final JFrame frame;

	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	
	private final String titleFrame;


	public StreamView(String channelName){
		titleFrame = channelName;
		frame = new JFrame(channelName+"... loading");
		frame.setBounds(100, 100, 800, 600);
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
						frame.setTitle(titleFrame+" Running");
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

	}
	
	private void closeWindow() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	public void playMedia(Integer port)
	{
		String addressToPlay = "http://127.0.0.1:"+port+"/";
		MediaPlayer mediaplayer = mediaPlayerComponent.getMediaPlayer();
		mediaplayer.playMedia(addressToPlay);
	}
}