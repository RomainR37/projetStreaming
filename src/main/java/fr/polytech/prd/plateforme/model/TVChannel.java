package fr.polytech.prd.plateforme.model;

public class TVChannel {

	private String channelName;
	private String channelAdress;

	/**
	 * @param channelName
	 * @param channelAdress
	 */
	public TVChannel(String channelName, String channelAdress) {
		this.channelName = channelName;
		this.channelAdress = channelAdress;
	}

	/**
	 * Get the channel name
	 * 
	 * @return the channel name
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * Set the channel name
	 * 
	 * @param channelName
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * Get the channel address
	 * 
	 * @return the channel address
	 */
	public String getChannelAdress() {
		return channelAdress;
	}

	/**
	 * Set the channel address
	 * 
	 * @param channelAdress
	 */
	public void setChannelAdress(String channelAdress) {
		this.channelAdress = channelAdress;
	}

}
