package fr.polytech.prd.plateforme.controler;

import java.util.Observable;
import java.util.Observer;

import fr.polytech.prd.plateforme.view.StreamView;

public class PlayMediaObserver implements Observer{

	public StreamView streamview = new StreamView();
	public void update(Observable o, Object arg) {
		streamview.playMedia();
	}
	
}
