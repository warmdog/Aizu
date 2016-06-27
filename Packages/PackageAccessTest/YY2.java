import static java.lang.System.*;

class XX {

	private void show() {
		out.println("X.show");
	}
}

public class YY2 extends XX {

	public void show() {
		out.println("YY2.show");
	}

	public static void main(String[] args) {
		final YY2 ext = new YY2();
		final XX sup = ext;

		sup.show();   // Å® error
		ext.show();	   // Å® YY.show
	}
}
