package com.nravo.thegame.mobilewars.modifier;

import com.nravo.thegame.mobilewars.entity.Building;
import org.andengine.entity.modifier.MoveModifier;

import java.util.List;

public class ModifierForHero extends MoveModifier {
	
	public ModifierForHero(float duration, float fromX, float fromY, float toX, float toY,
                           final List<Building> from, final Building to) {
		super (5, fromX, fromY, toX, toY);
	}
	
}
