package main.java;
import unibo.basicomm23.utils.*;
public class FlipFlopSR {
    private NOR nor1;
    private NOR nor2; 
    private boolean Q;
    private boolean Qbar;

    public FlipFlopSR() {
        // Inizializziamo le porte NOR con false (0) all'inizio
        nor1 = new NOR(false, true);  //quello del RESET
        nor2 = new NOR(false, false);  //quello del SET
        // Inizializziamo le uscite Q e Qbar COMPLEMENTARI
        Q    = nor1.getOutput();
        Qbar = nor2.getOutput();
        CommUtils.outblue("Valori iniziali: Q=" + Q + " Q'=" + Qbar);
    } 

    public void setInputs(boolean S, boolean R) {
    	CommUtils.outblue("setInputs Q="  + nor1.getOutput() + " Q'=" + nor2.getOutput() + " S="+S + " R="+R);
    	CommUtils.outmagenta("setInputs Q="  + Q + " Q'=" + Qbar  );
   	 
    	
        nor1.setInput1(S);
        nor1.setInput2(Qbar);
        nor2.setInput1(R);
        nor2.setInput2(Q);

        // Aggiornare Q e Qbar ????
        Q    = nor1.getOutput();
        Qbar = nor2.getOutput();
    }

    public boolean getQ() {
        return Q;
    }

    public boolean getQbar() {
        return Qbar;
    }

    public static void main(String[] args) {
        FlipFlopSR flipFlop = new FlipFlopSR();
         
        // Test del flip-flop con vari ingressi
        flipFlop.setInputs(false, false);
        System.out.println("S=0, R=0 => Q=" + flipFlop.getQ() + ", Q'=" + flipFlop.getQbar());


        //RESET
        flipFlop.setInputs(false, true);
        System.out.println("RESET S=0, R=1 => Q=" + flipFlop.getQ() + ", Q'=" + flipFlop.getQbar());

        //SET
        flipFlop.setInputs(true, false);
        System.out.println("SET S=1, R=0 => Q=" + flipFlop.getQ() + ", Q'=" + flipFlop.getQbar());

        //ERRATO !?
        flipFlop.setInputs(false, false);
        System.out.println("S=0, R=0 => Q=" + flipFlop.getQ() + ", Q'=" + flipFlop.getQbar());

        /*
         * Questo esempio può essere migliorato 
         * gestendo lo stato non valido quando S e R sono entrambi veri.
         * 
         */
//        for(int i=1; i<=3; i++) {
//	        flipFlop.setInputs(true, true);
//	        System.out.println("S=1, R=1 => Q=" + flipFlop.getQ() + ", Q'=" + flipFlop.getQbar());
//        }
    }
}

