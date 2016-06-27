class XX {

	public void show() {
            System.out.println("XX");
	}
}

class YY4 extends XX {

	public void show() {
	}

	public static void main(String[] args) {
		YY4 ext;
		XX sup1 = new YY4();
		XX sup2 = new XX();

		if (sup1 instanceof YY4)
//			ext = (YY) sup1;
                     System.out.println("sup1 is instanceof YY4");
		else if (sup2 instanceof XX)
//			ext = new YY();
                     System.out.println("sup2 is instanceof XX");
	}
}
