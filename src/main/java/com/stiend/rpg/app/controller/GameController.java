package com.stiend.rpg.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import physics.Field;
import physics.Map;
import physics.Position;

@RestController()
@RequestMapping("/api/game")
public class GameController {
	private RPGResponseEntity responseEntity = new RPGResponseEntity();

	@PostMapping("/map")
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
	
	@PostMapping("/start")
	public RPGResponseEntity startGame(){
		responseEntity.setState(GameState.PLAY_STATE);
		return responseEntity;
	}

	@PostMapping("/wall")
	public ResponseEntity<HttpStatus> postWall(@RequestBody Position position) {
		try {
			System.out.println(position.getY() + "  " + position.getX());
			boolean success = this.responseEntity.getMap().getField(position).setWall(true);
			return isSuccess(success);	
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/playable")
	public ResponseEntity<HttpStatus> postPlayable(@RequestBody Position position) {
		try {
			System.out.println(position.getY() + "  "+ position.getX());
			boolean success = this.responseEntity.getMap().getField(position).setWall(false);
			return isSuccess(success);
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/knight")
	public ResponseEntity<HttpStatus> postKnight(@RequestBody KnightConfiguration config) {
		return postCharacter(config.getPosition(), config.getKnight());
	}

	@PostMapping("/mercenary")
	public ResponseEntity<HttpStatus> postMercenary(@RequestBody MercenaryConfiguration config) {
		return postCharacter(config.getPosition(), config.getMercenary());
	}

	@PostMapping("/sorcerer")
	public ResponseEntity<HttpStatus> postSorcerer(@RequestBody SorcererConfiguration config) {
		return postCharacter(config.getPosition(), config.getSorcerer());
	}

	@PostMapping("/wizard")
	public ResponseEntity<HttpStatus> postWizard(@RequestBody WizardConfiguration config) {
		return postCharacter(config.getPosition(), config.getWizard());
	}

	@PostMapping("/monster")
	public ResponseEntity<HttpStatus> postMonster(@RequestBody MonsterConfiguration config) {
		try {
			System.out.println(config.getMonster().getName());
			boolean success = this.responseEntity.getMap().getField(config.getPosition())
					.setMonster(config.getMonster());
			return isSuccess(success);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<HttpStatus> postCharacter(Position position, PlayerCharacter character) {
		System.out.println(character.getName());
		try {
			boolean success = this.responseEntity.getMap().getField(position).setCharacter(character);
			return isSuccess(success);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private ResponseEntity<HttpStatus> isSuccess(boolean success){
		if (success) {
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} else {
			return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
		}
	}
}
