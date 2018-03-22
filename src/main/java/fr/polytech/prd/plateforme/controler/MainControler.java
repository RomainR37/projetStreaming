package fr.polytech.prd.plateforme.controler;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.polytech.prd.plateforme.model.TVChannel;

public class MainControler extends Thread{
	
	Logger log = LoggerFactory.getLogger("fr.polytech.prd.plateforme.controler.MainControler");	
	
	File fichier;
	
	public static int nbStreamRunning = 0;
	
	public MainControler(){
		
	}
	
	public MainControler(String nomfichier){
		fichier = new File(nomfichier);
	}
	
	@Override
	public void run(){
	
		String ligne;
		String[] elementLigne;
		
		try (Scanner sc = new Scanner(fichier)){
			log.debug("read file",fichier);
			while (sc.hasNextLine()){
				ligne = sc.nextLine();
				elementLigne = ligne.split("\t");
				TVChannel channel = new TVChannel(elementLigne[0], elementLigne[1]);
				StreamControler streamcontroler = new StreamControler(channel);
				streamcontroler.launchStream();
				incrementStreamNumber();
				log.info("Number of stream running: ", nbStreamRunning);
			}
		} catch (FileNotFoundException e) {
			log.error("File not found");
		}
	}
	
	public static synchronized void incrementStreamNumber()
	{
		nbStreamRunning++;
	}
	
	public static synchronized void decrementStreamNumber()
	{
		nbStreamRunning--;
	}
	
}
