package fr.polytech.prd.plateforme.controler;

public class StreamControler{

	public StreamlinkGestion slg;

	public StreamControler()
	{
		
	}
	
	public void launchStream()
	{
		slg = new StreamlinkGestion();
		slg.start();
	}
	
}
