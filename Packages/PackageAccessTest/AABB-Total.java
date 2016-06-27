package a;

public class AA {
  int ai;
  protected int aj;
}



package b;
import a.AA;

public class BB1 extends AA {
  BB1() {
    ai = 7;
    aj = 9;
  }
}



package b;
import a.AA;
public class BB extends AA {
  BB() {
    AA aa = new AA();
    aa.ai = 7;
    aa.aj = 9;
  }
}


