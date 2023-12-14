package com.stiend.rpg.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stiend.rpg.app.models.GameState;
import com.stiend.rpg.app.models.KnightConfiguration;
import com.stiend.rpg.app.models.MapConfiguration;
import com.stiend.rpg.app.models.MercenaryConfiguration;
import com.stiend.rpg.app.models.MonsterConfiguration;
import com.stiend.rpg.app.models.RPGResponseEntity;
import com.stiend.rpg.app.models.SorcererConfiguration;
import com.stiend.rpg.app.models.WizardConfiguration;

import character.*;
import physics.Map;
import physics.Position;

@RestController()
@RequestMapping("/api/game")
public class GameController {
	private RPGResponseEntity responseEntity = new RPGResponseEntity();
	
	@PostMapping ("/map")
	public void createMap(@RequestBody MapConfiguration configuration) {
		System.out.println(configuration.getSize());
		Map map = new Map(configuration.getSize());
		this.responseEntity.setMap(map);
		this.responseEntity.setState(GameState.GAME_CREATION);
	}
	
	@GetMapping("/info")
	public RPGResponseEntity getInfo() {
		return this.responseEntity;
	}
	
	@PostMapping("/wall")
	public void postWall(@RequestBody Position position) {
		System.out.println(position.getY() + "  "+ position.getX());
		this.responseEntity.getMap().getField(position).setWall(true);
	}
	
	@PostMapping("/playable")
	public void postPlayable(@RequestBody Position position) {
		System.out.println(position.getY() + "  "+ position.getX());
		this.responseEntity.getMap().getField(position).setWall(false);
	}
	
	@PostMapping("/knight")
	public void postKnight(@RequestBody KnightConfiguration config) {
		postCharacter(config.getPosition(), config.getKnight());
	}
	
	@PostMapping("/mercenary")
	public void postMercenary(@RequestBody MercenaryConfiguration config) {
		postCharacter(config.getPosition(), config.getMercenary());
	}
	
	@PostMapping("/sorcerer")
	public void postSorcerer(@RequestBody SorcererConfiguration config) {
		postCharacter(config.getPosition(), config.getSorcerer());
	}
	
	@PostMapping("/wizard")
	public void postWizard(@RequestBody WizardConfiguration config) {
		postCharacter(config.getPosition(), config.getWizard());
	}
	
	@PostMapping("/monster")
	public void postMonster(@RequestBody MonsterConfiguration config) {
		System.out.println(config.getMonster().getName());
		this.responseEntity.getMap().getField(config.getPosition()).setMonster(config.getMonster());
	}
	
	public void postCharacter(Position position, PlayerCharacter character) {
		System.out.println(character.getName());
		this.responseEntity.getMap().getField(position).setCharacter(character);
	}
}
