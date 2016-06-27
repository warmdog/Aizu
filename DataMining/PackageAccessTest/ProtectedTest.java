class TestClass {
	public int publicint;
	protected int protectedint;
	int packageint;
	protected int privateint;
}

public class ProtectedTest extends TestClass {
	public ProtectedTest() {
		publicint = 1;
		protectedint = 2;
		packageint = 3;
		privateint = 0;
	}

	public static void main(String[] args) {
		ProtectedTest a = new ProtectedTest();
		a.publicint = 4;
		a.protectedint = 5;
		a.packageint = 6;
		a.privateint = 0;
	}
}