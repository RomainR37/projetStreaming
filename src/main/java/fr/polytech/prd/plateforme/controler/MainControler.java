package fr.polytech.prd.plateforme.controler;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import fr.polytech.prd.plateforme.model.TVChannel;

public class MainControler extends Thread{
	
	File fichier;
	
	public MainControler(){
		
	}
	
	public MainControler(String nomfichier){
		fichier = new File(nomfichier);
	}
	
	
	public void run(){
	
		String ligne;
		String[] elementLigne;
		
		try {
			Scanner sc = new Scanner(fichier);
			while (sc.hasNextLine()){
				ligne = sc.nextLine();
				elementLigne = ligne.split("\t");
				TVChannel channel = new TVChannel(elementLigne[0], elementLigne[1]);
				StreamControler streamcontroler = new StreamControler(channel);
				streamcontroler.launchStream();
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
