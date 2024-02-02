package com.stiend.app.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import character.Monster;
import character.PlayerCharacter;
import physics.Field;
import physics.Map;
import physics.Position;


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
	
	public static boolean hasPlayerCharacter(Map map) {
		boolean found = false;
		if(map == null)
			return found;
		
		List<PlayerCharacter> characters = getPlacedCharacters(map);
		for (PlayerCharacter character : characters) {
			if(!(character instanceof Monster)) {
				found = true;
				return found;
			}
		}
		return found;
	}
	
	public static boolean hasMonster(Map map) {
		boolean found = false;
		if(map == null)
			return found;
		
		List<PlayerCharacter> characters = getPlacedCharacters(map);
		for (PlayerCharacter character : characters) {
			if(character instanceof Monster) {
				found = true;
				return found;
			}
		}
		return found;
	}
	
	public static ResponseEntity<HttpStatus> postCharacter(Map map, Position position, PlayerCharacter character) {
		System.out.println(character.getName());
		character.setPosition(position);
		try {
			boolean success = map.getField(position).setCharacter(character);
			return isSuccess(success);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public static ResponseEntity<HttpStatus> isSuccess(boolean success) {
		if (success) {
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} else {
			return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
		}
	}
}
