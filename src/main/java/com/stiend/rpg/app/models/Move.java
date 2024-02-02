package com.stiend.rpg.app.models;

import physics.Position;

public class Move {
	private Position position;
	private int movement;
	
	public Move() {
		
	}
	
	public Move(Position position, int movement) {
		setPosition(position);
		setMovement(movement);
	}
	
	public Move(int x, int y, int movement) {
		setPosition(new Position(x, y));
		setMovement(movement);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public int getMovement() {
		return movement;
	}
	public void setMovement(int movement) {
		this.movement = movement;
	}

}
