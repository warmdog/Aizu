import java.util.*;

class Customer {
	private String _name;
	private Vector _rentals=new Vector();
	
	public Customer(String name){
		_name=name;
	}
	
	public void addRental(Rental arg){
		_rentals.addElement(arg);
	}
	
	public String getName(){
		return _name;
	}
	
	public String statement(){
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for:"+getName() + "\n";
		while(rentals.hasMoreElements()){
			Rental each = (Rental)rentals.nextElement();
			result +="\t" + each.getMovie().getTitle() + "\t" + String.valueOf(each.getCharge()) + "\n";
			
		}
		result += "Amount owed is" + String.valueOf(getTotalCharge()) + "\n";
		result += "You earned" + String.valueOf(getFrequentRenterpoints()) + "frequent renter points";
		return result;
	}
	public double getTotalCharge(){
		double result=0;
		Enumeration rentals = _rentals.elements();
		while(rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();
			result += each.getCharge();
		}
		return result;
	}
	private int getFrequentRenterpoints(){
		int result = 0;
		Enumeration rentals = _rentals.elements();
		while(rentals.hasMoreElements()){
			Rental each = (Rental) rentals.nextElement();
			result += each.getFrequentRenterpoints();
		}
		return result;
	}
}
