import static java.lang.System.*;
class XX {
	int x = 5;
	public void show() {
		out.println("XX.show = " + x);
	}
}

class YY3 extends XX {
	int x = 6;
	public void show() {
		super.show();
		out.println("YY.show = " + super.x);
	}

	public static void main(String[] args) {
		final YY3 ext = new YY3();
		final XX sup = ext;

		sup.show();   // Å® XX.show = 5
			// Å® YY.show = 5
		ext.show();	// Å® XX.show = 5
			// Å® YY.show = 5
	}

}
