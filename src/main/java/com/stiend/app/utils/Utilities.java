package com.stiend.app.utils;

import java.util.ArrayList;
import java.util.List;

import character.PlayerCharacter;
import physics.Field;
import physics.Map;


public class Utilities {
	
	public static List<PlayerCharacter> getPlacedCharacters(Map map) {
		List<PlayerCharacter> characters = new ArrayList<PlayerCharacter>();
		Field field;
		
		int mapSize = map.getSize();
		for(int i=0; i<mapSize; i++) {
			for(int j=0; j<mapSize; j++) {
				field = map.getField(i, j);
				if(field.getCharacter() != null) {
					characters.add(field.getCharacter());
				} else if(field.getMonster() != null) {
					characters.add(field.getMonster());
				}
			}
		}
		return characters;
	}
}
