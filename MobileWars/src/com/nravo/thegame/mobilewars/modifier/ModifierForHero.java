package com.nravo.thegame.mobilewars.modifier;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;

import com.nravo.thegame.mobilewars.entity.Hero;

public class ModifierForHero extends MoveModifier {

	public Hero hero;
	
	public ModifierForHero(Hero hero) {
		super (5, hero.fromX, hero.fromY, hero.toX, hero.toY);
		this.hero = hero;
		 
	/*	MoveModifier move = new MoveModifier(5, hero.fromX, hero.fromY, hero.toX, hero.toY);
		hero.registerEntityModifier(move);
	
	*/	//this.addModifierListener(pModifierListener);
	}


	
}
