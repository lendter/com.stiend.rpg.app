package com.stiend.rpg.app.models;

import character.Knight;
import character.Wizard;
import physics.Position;

public class WizardConfiguration {
	private Position position;
	private Wizard wizard;
	
	public WizardConfiguration() {
		
	}

	public Wizard getWizard() {
		return wizard;
	}

	public void setWizard(Wizard wizard) {
		this.wizard = wizard;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	
}
