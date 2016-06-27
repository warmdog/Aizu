class AA {

	public void show() {
		System.out.println("XX");
	}
}

class YY4 extends AA {

	public void show() {
	}

	public static void main(String[] args) {
		YY4 ext;
		AA sup1 = new YY4();
		AA sup2 = new AA();

		if (sup1 instanceof YY4)
			// ext = (YY) sup1;
			System.out.println("sup1 is instanceof YY4");
		else if (sup2 instanceof AA)
			// ext = new YY();
			System.out.println("sup2 is instanceof XX");
	}
}
