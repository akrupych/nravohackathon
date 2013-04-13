package com.nravo.thegame.mobilewars.Utils;

public class Utils {
	
	public  static float calculateTime (float fromX, float fromY, float toX, float toY){
		double s = Math.pow(Math.pow(fromX - toX,2) + Math.pow(fromY - toY ,2) , 0.5);
		return (float) (s/Constants.speed);			
	}
}
