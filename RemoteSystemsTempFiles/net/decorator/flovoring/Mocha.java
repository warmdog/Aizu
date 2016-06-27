package flovoring;

import decorator.Beverage;
import decorator.CondimentDecorator;

public class Mocha extends Beverage {
	Beverage beverage;
	
	public Mocha(Beverage beverage) {
		this.beverage = beverage;
		
	}

	public String getDescription() {
		return beverage.getDescription() + ", Mocha";
	}

	public double cost() {
		return 0.20 + beverage.cost();
	}
}
