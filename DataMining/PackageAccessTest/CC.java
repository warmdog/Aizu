import a.AA;

public class CC {
  int a;
  protected int b;
}



public class BB1 extends AA {
  BB1() {
    ai = 7;
    aj = 9;
  }
}




import a.AA;
public class BB extends AA {
  BB() {
    AA aa = new AA();
    aa.ai = 7;
    aa.aj = 9;
  }
}


