package fr.polytech.prd.plateforme.model;


public class TVChannel {

	private String ChannelName;
	private String ChannelAdress;
	
	/**
	 * @param channelName
	 * @param channelAdress
	 */
	public TVChannel(String channelName, String channelAdress) {
		ChannelName = channelName;
		ChannelAdress = channelAdress;
	}
	
	
	public String getChannelName() {
		return ChannelName;
	}
	public void setChannelName(String channelName) {
		ChannelName = channelName;
	}
	public String getChannelAdress() {
		return ChannelAdress;
	}
	public void setChannelAdress(String channelAdress) {
		ChannelAdress = channelAdress;
	}
	
	
}
