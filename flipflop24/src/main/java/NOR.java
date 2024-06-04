package main.java;
 
public class NOR {
    private boolean input1;
    private boolean input2;

    public NOR(boolean input1, boolean input2) {
        this.input1 = input1;
        this.input2 = input2;
    }

    public void setInput1(boolean input1) {
        this.input1 = input1;
    }

    public void setInput2(boolean input2) {
        this.input2 = input2;
    }

    public boolean getOutput() {
        return !(input1 || input2);
    }
}

