package org.devathon.contest2016.bots;

public enum BotState {

	ACTIVE("§2Active"),
	PAUSED("§6Paused"),
	INACTIVE("§cInactive");
	
	private String text;
	
	BotState(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
}
