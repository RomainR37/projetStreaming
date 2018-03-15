package fr.polytech.prd.plateforme.controler;

import fr.polytech.prd.plateforme.view.MainView;

public class MainControler extends Thread{
	
	public void run(){
		new MainView();
	}
}
