package com.vincenzomariacalandra.provaFinale.BachecaUniCollege.utility;

public enum Activity_credits {
	TWO(0.2), 
	THREE(0.3), 
	FIVE(0.5);

	private final double val;
	
	Activity_credits(double val) {
		this.val = val;
	}
	
	public double getVal() {
		return val;
	}
}
