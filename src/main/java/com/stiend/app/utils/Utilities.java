package com.stiend.app.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stiend.rpg.app.models.Move;

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
		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				field = map.getField(i, j);
				if (field.getCharacter() != null) {
					characters.add(field.getCharacter());
				} else if (field.getMonster() != null) {
					characters.add(field.getMonster());
				}
			}
		}
		return characters;
	}

	public static boolean hasPlayerCharacter(Map map) {
		boolean found = false;
		if (map == null)
			return found;

		List<PlayerCharacter> characters = getPlacedCharacters(map);
		for (PlayerCharacter character : characters) {
			if (!(character instanceof Monster)) {
				found = true;
				return found;
			}
		}
		return found;
	}

	public static boolean hasMonster(Map map) {
		boolean found = false;
		if (map == null)
			return found;

		List<PlayerCharacter> characters = getPlacedCharacters(map);
		for (PlayerCharacter character : characters) {
			if (character instanceof Monster) {
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

	public static List<Move> getAvailableMoves(Map map, PlayerCharacter character) {
		List<Move> moves = new ArrayList<Move>();

		if (map == null || character == null) {
			return null;
		}

		int x = character.getPosition().getX();
		int y = character.getPosition().getY();
		int movement = 2;

		boolean isMonster = false;

		if (character instanceof Monster)
			isMonster = true;

		moves = getAvailableNeighbours(map, x, y, movement, moves, isMonster);

		return moves;
	}

	public static List<Move> getAvailableNeighbours(Map map, int x, int y, int movement, List<Move> moves,
			boolean isMonster) {
		moves.add(new Move(x, y, movement));
		System.out.println(x + ":" + y + " movement" + movement);
		if (movement > 0) {
			for (int i = -1; i <= 1; i += 2) {
				int newX = x + i;
				int newY = y + i;
				
				boolean checkX = false;
				boolean checkY = false;

				// Check if new X Position is free
				if (newX >= 0 && newX < map.getSize() && !map.getField(newX, y).isWall()) {
					if (!isMonster) {
						if (map.getField(newX, y).getCharacter() == null) {
							checkX = true;
						}
					} else {
						checkX = true;
					}
				}

				if (checkX == true) {
					Move move = findMove(moves, newX, y);
					if (move != null && movement - 1 > move.getMovement()) {
						moves.remove(move);
						moves = getAvailableNeighbours(map, newX, y, movement - 1, moves, isMonster);
					} else if (move == null) {
						moves = getAvailableNeighbours(map, newX, y, movement - 1, moves, isMonster);
					}
				}

				// Check if new Y Position is free
				if (newY >= 0 && newY < map.getSize() && !map.getField(x, newY).isWall()) {
					if (!isMonster) {
						if (map.getField(x, newY).getCharacter() == null) {
							checkY = true;
						}
					} else {
						checkY = true;
					}
				}

				if (checkY == true) {
					Move move = findMove(moves, x, newY);
					if (move != null && movement - 1 > move.getMovement()) {
						moves.remove(move);
						moves = getAvailableNeighbours(map, x, newY, movement - 1, moves, isMonster);
					} else if (move == null) {
						moves = getAvailableNeighbours(map, x, newY, movement - 1, moves, isMonster);
					}

				}
			}
		}
		return moves;
	}

	public static Move findMove(List<Move> moves, int x, int y) {
		for (Move move : moves) {
			Position position = move.getPosition();
			if (position.getX() == x && position.getY() == y) {
				return move;
			}
		}
		return null;
	}
}
