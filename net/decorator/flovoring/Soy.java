package flovoring;

import decorator.Beverage;
import decorator.CondimentDecorator;

public class Soy extends Beverage {
	Beverage beverage;
	private int price;
	public Soy(Beverage beverage) {
		this.beverage = beverage;
	}

	public String getDescription() {
		return beverage.getDescription() + ", Soy";
	}

	public double cost() {
		if(beverage.getSize()==0){
			return 100 + beverage.cost();
		}else{ 
			return 1 + beverage.cost();
		}
		//return price + beverage.cost();
		
	}
}
