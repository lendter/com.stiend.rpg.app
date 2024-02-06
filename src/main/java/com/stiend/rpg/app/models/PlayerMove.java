package com.stiend.rpg.app.models;

import physics.Position;

public class PlayerMove {
	private Position currentField;
	private Position movePosition;
	private boolean moveMonster;
	
	public PlayerMove() {}
	
	public Position getCurrentField() {
		return this.currentField;
	}
	
	public void setCurrentField(Position currentField) {
		this.currentField = currentField;
	}
	
	public Position getMovePosition() {
		return this.movePosition;
	}
	
	public void setMovePosition(Position movePosition) {
		this.movePosition = movePosition;
	}
	
	public boolean getMoveMonster() {
		return this.moveMonster;
	}
	
	public void setMoveMonster(boolean moveMonster) {
		this.moveMonster = moveMonster;
	}
}
