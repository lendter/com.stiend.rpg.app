package com.stiend.app.utils;

import java.util.Comparator;

import character.PlayerCharacter;

public class DexterityComparator implements Comparator<PlayerCharacter> {
	@Override
	public int compare(PlayerCharacter a, PlayerCharacter b) {
		return a.getDexterity() < b.getDexterity() ? -1 : a.getDexterity() == b.getDexterity() ? 0 : 1;
	}
}
