package com.stiend.rpg.app.models;

import character.Monster;
import physics.Position;

public class MonsterConfiguration {
	private Position position;
	private Monster monster;
	
	public MonsterConfiguration() {
		
	}

	public Monster getMonster() {
		return monster;
	}

	public void setMonster(Monster monster) {
		this.monster = monster;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	
}
