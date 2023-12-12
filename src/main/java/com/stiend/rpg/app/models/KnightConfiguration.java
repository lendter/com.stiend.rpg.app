package com.stiend.rpg.app.models;

import character.Knight;
import physics.Position;

public class KnightConfiguration {
	private Position position;
	private Knight knight;
	
	public KnightConfiguration() {
		
	}

	public Knight getKnight() {
		return knight;
	}

	public void setKnight(Knight knight) {
		this.knight = knight;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	
}
