package com.stiend.rpg.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stiend.app.utils.Utilities;
import com.stiend.app.utils.DexterityComparator;
import com.stiend.rpg.app.models.GameState;
import com.stiend.rpg.app.models.KnightConfiguration;
import com.stiend.rpg.app.models.MapConfiguration;
import com.stiend.rpg.app.models.MercenaryConfiguration;
import com.stiend.rpg.app.models.MonsterConfiguration;
import com.stiend.rpg.app.models.Move;
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
	private List<PlayerCharacter> initiativeList;

	@GetMapping("/placedCharacters")
	public ResponseEntity<List<PlayerCharacter>> getCharacters() {
		if (this.responseEntity.getMap() != null)
			return ResponseEntity.ok(Utilities.getPlacedCharacters(this.responseEntity.getMap()));
		return ResponseEntity.ok(null);
	}

	@GetMapping("/placedCharacters/hasPlayer")
	public ResponseEntity<Boolean> hasPlayerCharacter() {
		return ResponseEntity.ok(Utilities.hasPlayerCharacter(this.responseEntity.getMap()));
	}

	@GetMapping("/placedCharacters/hasMonster")
	public ResponseEntity<Boolean> hasMonster() {
		return ResponseEntity.ok(Utilities.hasMonster(this.responseEntity.getMap()));
	}

	@GetMapping("/round/initiative")
	public ResponseEntity<List<PlayerCharacter>> getInitiative() {
		return ResponseEntity.ok(initiativeList);
	}

	@PostMapping("/character/move")
	public ResponseEntity<HttpStatus> movePlayer(@RequestBody Position newPos) {
		try {
			PlayerCharacter character = initiativeList.get(0);

			Field oldField = responseEntity.getMap().getField(character.getPosition());
			Field newField = responseEntity.getMap().getField(newPos);

			if (character instanceof Monster) {
				oldField.setMonster(null);
				newField.setMonster((Monster) character);
				if(newField.getCharacter() != null) {
					Utilities.calculateFight(initiativeList, newField, newField.getCharacter(), (Monster) character);
				}
			} else {
				oldField.setCharacter(null);
				newField.setCharacter(character);
				if(newField.getMonster() != null) {
					Utilities.calculateFight(initiativeList, newField, character, newField.getMonster());
				}
			}
			character.setPosition(newPos);

			initiativeList.remove(character);
			initiativeList.add(character);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/info")
	public RPGResponseEntity getInfo() {
		return this.responseEntity;
	}

	@PostMapping("/placedCharacters/getMoves")
	public ResponseEntity<List<Move>> getAvailableMoves(@RequestBody Position position) {
		try {
			PlayerCharacter playerCharacter = this.responseEntity.getMap().getField(position).getCharacter();
			if (playerCharacter == null) {
				playerCharacter = this.responseEntity.getMap().getField(position).getMonster();
			}
			List<Move> moves = Utilities.getAvailableMoves(this.responseEntity.getMap(), playerCharacter);
			return ResponseEntity.ok(moves);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/map")
	public void createMap(@RequestBody MapConfiguration configuration) {
		System.out.println(configuration.getSize());
		Map map = new Map(configuration.getSize());
		this.responseEntity.setMap(map);
		this.responseEntity.setState(GameState.GAME_CREATION);
	}

	@PostMapping("/start")
	public RPGResponseEntity startGame() {
		responseEntity.setState(GameState.PLAY_STATE);
		initiativeList = Utilities.getPlacedCharacters(this.responseEntity.getMap());
		initiativeList.sort(new DexterityComparator());
		return responseEntity;
	}

	@PostMapping("/wall")
	public ResponseEntity<HttpStatus> postWall(@RequestBody Position position) {
		try {
			System.out.println(position.getY() + "  " + position.getX());
			boolean success = this.responseEntity.getMap().getField(position).setWall(true);
			return Utilities.isSuccess(success);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/remove/wall")
	public ResponseEntity<HttpStatus> postPlayable(@RequestBody Position position) {
		try {
			System.out.println(position.getY() + "  " + position.getX());
			boolean success = this.responseEntity.getMap().getField(position).setWall(false);
			return Utilities.isSuccess(success);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/knight")
	public ResponseEntity<HttpStatus> postKnight(@RequestBody KnightConfiguration config) {
		return Utilities.postCharacter(this.responseEntity.getMap(), config.getPosition(), config.getKnight());
	}

	@PostMapping("/mercenary")
	public ResponseEntity<HttpStatus> postMercenary(@RequestBody MercenaryConfiguration config) {
		return Utilities.postCharacter(this.responseEntity.getMap(), config.getPosition(), config.getMercenary());
	}

	@PostMapping("/sorcerer")
	public ResponseEntity<HttpStatus> postSorcerer(@RequestBody SorcererConfiguration config) {
		return Utilities.postCharacter(this.responseEntity.getMap(), config.getPosition(), config.getSorcerer());
	}

	@PostMapping("/wizard")
	public ResponseEntity<HttpStatus> postWizard(@RequestBody WizardConfiguration config) {
		return Utilities.postCharacter(this.responseEntity.getMap(), config.getPosition(), config.getWizard());
	}

	@PostMapping("/monster")
	public ResponseEntity<HttpStatus> postMonster(@RequestBody MonsterConfiguration config) {
		try {
			System.out.println(config.getMonster().getName());
			config.getMonster().setPosition(config.getPosition());
			boolean success = this.responseEntity.getMap().getField(config.getPosition())
					.setMonster(config.getMonster());
			return Utilities.isSuccess(success);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
