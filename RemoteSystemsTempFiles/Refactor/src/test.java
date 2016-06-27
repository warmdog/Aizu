
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Customer aa = new Customer("zs");
		Price dd = new RegularPrice();
		Movie bb = new Movie("zaqws",11,dd);
		
		//Price dd = new NewReleasePrice();
		Rental cc = new Rental(bb,10);
		aa.addRental(cc);
		System.out.println(aa.statement());
		System.out.println(Static1.aa());
	}

}
