package minepump;

import org.sosy_lab.sv_benchmarks.Verifier;

public class Main {

  private static int cleanupTimeShifts = 2;

  public static void main(String[] args) { 
	  randomSequenceOfActions(3); 
	  //randomSequenceOfActions(false, false, false, false, false, false, false, false, false, false, false, false);
  }

  public static boolean getBoolean() { return Verifier.nondetBoolean(); }

  public static void randomSequenceOfActions(int maxLength) {
    Actions a = new Actions();

    int counter = 0;
    while (counter < maxLength) {
      counter++;

      boolean action1 = getBoolean();
      boolean action2 = getBoolean();
      boolean action3 = getBoolean();
      boolean action4 = false;
      if (!action3)
        action4 = getBoolean();
      
      System.out.println(" counter = " + counter + " a1=" + action1 + " a2=" +action2 + " a3=" +action3 + " a4=" + action4);
      
      if (action1) {
        a.waterRise();
      }

      if (action2) {
        a.methaneChange();
      }

      if (action3) {
        a.startSystem();
      } else if (action4) {
        a.stopSystem();
      }

      a.timeShift();
    }

    for (counter = 0; counter < cleanupTimeShifts; counter++) {
      a.timeShift();
    }
  }
  
  public static void randomSequenceOfActions(boolean a10, boolean a20, boolean a30, boolean a40,
		  									 boolean a11, boolean a21, boolean a31, boolean a41,
		  									 boolean a12, boolean a22, boolean a32, boolean a42) {
	    Actions a = new Actions();

	    int maxLength = 3;
	    int counter = 0;
	    while (counter < maxLength) {
	      counter++;

	      boolean action1 = false;
	      boolean action2 = false;
	      boolean action3 = false;
	      boolean action4 = false;
	      
	      if (counter == 1) {
	    	  action1 = a10;
	    	  action2 = a20;
	    	  action3 = a30;
	    	  if (!action3)
	  	        action4 = a40;
	  	      
	      } else if (counter == 2) {
	    	  action1 = a11;
	    	  action2 = a21;
	    	  action3 = a31;
	    	  if (!action3)
		  	        action4 = a41;
	      } else {
	    	  action1 = a12;
	    	  action2 = a22;
	    	  action3 = a32;
	    	  if (!action3)
		  	        action4 = a42;
	      }
	      
	      
	      System.out.println(" counter = " + counter + " a1=" + action1 + " a2=" +action2 + " a3=" +action3 + " a4=" + action4);
	      
	      if (action1) {
	        a.waterRise();
	      }

	      if (action2) {
	        a.methaneChange();
	      }

	      if (action3) {
	        a.startSystem();
	      } else if (action4) {
	        a.stopSystem();
	      }

	      a.timeShift();
	    }

	    for (counter = 0; counter < cleanupTimeShifts; counter++) {
	      a.timeShift();
	    }
	  }

  
}
