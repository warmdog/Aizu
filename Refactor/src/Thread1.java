
public class Thread1  implements Runnable{
	 final Account acc = new Account("John", 1000.0f);
	 public void run() {
		 
     acc.deposit(100.0f);
      acc.withdraw(100.0f);
}
	public void start() {
		// TODO Auto-generated method stub
		
	}
}
