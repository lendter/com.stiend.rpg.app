package controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import character.*;
import physics.Map;
import physics.Position;

@RestController("/api/game")
public class GameController {
	private Map map;
	
	@PutMapping ("/map")
	public void createMap(int size) {
		this.map = new Map(size);
	}
	
	@GetMapping("/map")
	public Map getMap() {
		return this.map;
	}
	
	@PutMapping("/wall")
	public void putWall(Position position) {
		this.map.getField(position).setWall(true);
	}
	
	@PutMapping("/knight")
	public void putKnight(Position position, Knight knight) {
		putCharacter(position, knight);
	}
	
	@PutMapping("/mercenary")
	public void putMercenary(Position position, Mercenary mercenary) {
		putCharacter(position, mercenary);
	}
	
	@PutMapping("/sorcerer")
	public void putSorcerer(Position position, Sorcerer sorcerer) {
		putCharacter(position, sorcerer);
	}
	
	@PutMapping("/wizard")
	public void putWizard(Position position, Wizard wizard) {
		putCharacter(position, wizard);
	}
	
	@PutMapping("/monster")
	public void putMonster(Position position, Monster monster) {
		this.map.getField(position).setMonster(monster);
	}
	
	public void putCharacter(Position position, PlayerCharacter character) {
		this.map.getField(position).setCharacter(character);
	}
}
