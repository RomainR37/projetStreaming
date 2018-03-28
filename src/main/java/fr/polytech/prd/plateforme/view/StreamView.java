package fr.polytech.prd.plateforme.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.polytech.prd.plateforme.controler.MainControler;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 * <b>The StreamView class is made to display the stream to the screen.</b>
 * 
 * <p>
 * StreamView creates a JFrame and add a EmbeddedMediaPlayerComponent to it, so
 * that it can play the stream.
 * </p>
 * 
 * @author Romain Rousseau
 *
 */
public class StreamView {

	/**
	 * Logger object that displays informations to the user
	 */
	Logger log = LoggerFactory.getLogger("fr.polytech.prd.plateforme.view.StreamView");

	/**
	 * JFrame object that will contain the media player.
	 */
	private final JFrame frame;

	/**
	 * EmbeddedMediaPlayerComponent provide by vlc-j library to the play the stream.
	 */
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

	/**
	 * String for the title name of the JFrame.
	 */
	private final String titleFrame;

	/**
	 * Constructor of the StreamView object.
	 * 
	 * This constructor sets the title of the frame with the channel name and his status (loading or running).
	 * It contains the windowListener and the mediaListener.
	 * 
	 * @param channelName: name of the TV Channel
	 */
	public StreamView(String channelName) {
		titleFrame = channelName;
		frame = new JFrame(channelName + "... loading");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				log.debug("Closing window", e);
				mediaPlayerComponent.release();
				frame.dispose();
				MainControler.decrementStreamNumber();
			}
		});

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

		mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void playing(MediaPlayer mediaPlayer) {
				SwingUtilities.invokeLater(() -> frame.setTitle(titleFrame + ": running..."));
			}

			@Override
			public void finished(MediaPlayer mediaPlayer) {
				SwingUtilities.invokeLater(() -> closeWindow());
			}

			@Override
			public void error(MediaPlayer mediaPlayer) {
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(frame, "Failed to play media", "Error", JOptionPane.ERROR_MESSAGE);
					closeWindow();
				});
			}
		});

		frame.setContentPane(contentPane);
		frame.setVisible(true);

	}

	/**
	 * Method that calls the event windowClosing in the constructor.
	 */
	public void closeWindow() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * This method plays the video with its media player.
	 * 
	 * It creates an object MediaPlayer and plays the stream at the address "127.0.0.1:(port)".
	 * The address "127.0.0.1" is the default address used by streamlink.
	 * 
	 * @param port: the communication port that has to be listened.
	 */
	public void playMedia(Integer port) {
		String addressToPlay = "http://127.0.0.1:" + port + "/";
		MediaPlayer mediaplayer = mediaPlayerComponent.getMediaPlayer();
		log.debug("call playMedia method");
		mediaplayer.playMedia(addressToPlay);
	}
}