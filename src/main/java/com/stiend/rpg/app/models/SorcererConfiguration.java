package com.stiend.rpg.app.models;

import character.Knight;
import character.Sorcerer;
import physics.Position;

public class SorcererConfiguration {
	private Position position;
	private Sorcerer sorcerer;
	
	public SorcererConfiguration() {
		
	}

	public Sorcerer getSorcerer() {
		return sorcerer;
	}

	public void setSorcerer(Sorcerer sorcerer) {
		this.sorcerer = sorcerer;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	
}
