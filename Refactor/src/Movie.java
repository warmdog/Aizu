
public class Movie {
	
	public static final int CHILDRENS=2;
	public static final int REGULAR=0;
	public static final int NEW_RELEASE=1;
	
	private String _title; 
	private int _priceCode;
	public Price _price;
	public  Movie(String title,int priceCode,Price _price){ 
		this._title=title;
		//   this._priceCode=priceCode;
		setPriceCode(priceCode);
		this._price = _price;
	}
	public int getPriceCode(){
		return _price.getPriceCode();
	}
	public void setPriceCode(int arg){ 
		_priceCode=arg;
	}
	public String getTitle(){ 
		return _title;
	}
	
	double getCharge(int daysRented){
		return _price.getCharge(daysRented);
	}
	 int getFrequentRenterpoints( int daysRented){
		 return _price.getFrequentRenterpoints(daysRented);
	 }
	
}








