package com.stiend.rpg.app.models;

import physics.Map;

public class RPGResponseEntity{
	private Map map;
	private GameState state;
	
	public RPGResponseEntity() {}
	
	public RPGResponseEntity(Map map, GameState state) {
		this.map = map;
		this.state = state;
	}
	
	public Map getMap() {
		return map;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public GameState getState() {
		return state;
	}
	
	public void setState(GameState state) {
		this.state = state;
	}
}
