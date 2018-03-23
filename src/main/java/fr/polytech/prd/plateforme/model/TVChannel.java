package fr.polytech.prd.plateforme.model;

/**
 * <b>TVChannel class represents an object TV Channel</b>
 * 
 * It has two arguments:
 * <ul>
 * <li>the channel Name</li>
 * <li>the channel address to read the stream from. Example, for TF1, the
 * address is https://www.tf1.fr/tf1/direct</li>
 * </ul>
 * 
 * @author Romain Rousseau
 *
 */
public class TVChannel {

	/**
	 * Name of the TV channel
	 */
	private String channelName;

	/**
	 * Address of the TV channel to read the stream from. For example, the
	 * address of the TF1 channel is https://www.tf1.fr/tf1/direct
	 */
	private String channelAddress;

	/**
	 * Constructor of the class TVChannel
	 * 
	 * @param channelName:
	 *            the name of the channel
	 * @param channelAddress:
	 *            the address to read the channel from
	 */
	public TVChannel(String channelName, String channelAddress) {
		this.channelName = channelName;
		this.channelAddress = channelAddress;
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
	 * @param channelName:
	 *            Name of the TV channel
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * Get the channel address
	 * 
	 * @return the channel address
	 */
	public String getChannelAddress() {
		return channelAddress;
	}

	/**
	 * Set the channel address
	 * 
	 * @param channelAddress:
	 *            address to read the stream from.
	 */
	public void setChannelAdress(String channelAddress) {
		this.channelAddress = channelAddress;
	}

}
