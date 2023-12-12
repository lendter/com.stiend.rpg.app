package com.stiend.rpg.app.models;

import character.Mercenary;
import physics.Position;

public class MercenaryConfiguration {
	private Position position;
	private Mercenary mercenary;
	
	public MercenaryConfiguration() {
		
	}

	public Mercenary getMercenary() {
		return mercenary;
	}

	public void setMercenary(Mercenary mercenary) {
		this.mercenary = mercenary;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	
}
