package com.stiend.rpg.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stiend.rpg.app.models.MapConfiguration;

import character.*;
import physics.Map;
import physics.Position;

@RestController()
@RequestMapping("/api/game")
public class GameController {
	private Map map;
	
	@PostMapping ("/map")
	public void createMap(@RequestBody MapConfiguration configuration) {
		System.out.println(configuration.getSize());
		this.map = new Map(configuration.getSize());
	}
	
	@GetMapping("/map")
	public Map getMap() {
		return this.map;
	}
	
	@PostMapping("/wall")
	public void postWall(@RequestBody Position position) {
		System.out.println(position.getY() + "  "+ position.getX());
		this.map.getField(position).setWall(true);
	}
	
	@PostMapping("/knight")
	public void putKnight(@RequestBody Position position, @RequestBody Knight knight) {
		putCharacter(position, knight);
	}
	
	@PostMapping("/mercenary")
	public void putMercenary(@RequestBody Position position, @RequestBody Mercenary mercenary) {
		putCharacter(position, mercenary);
	}
	
	@PostMapping("/sorcerer")
	public void putSorcerer(@RequestBody Position position, @RequestBody Sorcerer sorcerer) {
		putCharacter(position, sorcerer);
	}
	
	@PostMapping("/wizard")
	public void putWizard(@RequestBody Position position, @RequestBody Wizard wizard) {
		putCharacter(position, wizard);
	}
	
	@PostMapping("/monster")
	public void putMonster(@RequestBody Position position, @RequestBody Monster monster) {
		this.map.getField(position).setMonster(monster);
	}
	
	public void putCharacter(Position position, PlayerCharacter character) {
		this.map.getField(position).setCharacter(character);
	}
}
