
class ChildrensPrice extends Price{
	int getPriceCode(){
		return Movie.CHILDRENS;
	}
	
	int getPriceCode1() {
		// TODO Auto-generated method stub
		return 0;
	}
	double getCharge(int daysRented){
		double result = 1.5;
		if(daysRented > 3)
			result += (daysRented -3) * 1.5;
		return result;
	}
}
