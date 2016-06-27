
abstract class Price {
	abstract int getPriceCode();

	abstract double getCharge(int daysRented);
	int getFrequentRenterpoints(int daysRented){
			 return 1;
	}
}
