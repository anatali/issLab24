package main.resources;
/*
 * Un qubit per rappresentare lo stato di un sistema quantistico 
 * con le operazioni base di un qubit, 
 * come l'inizializzazione, l'applicazione di una porta X (NOT) 
 * e la misurazione.
 */
import java.util.Random;

public class QubitSimulator {
    private double alpha; // Coefficiente del bit |0>
    private double beta;  // Coefficiente del bit |1>
    private Random random;

    public QubitSimulator() {
        random = new Random();
        // Inizializzazione con un qubit |0>
        alpha = 1.0;
        beta = 0.0;
    }

    // Applica la porta X (NOT) al qubit
    public void applyXGate() {
        System.out.println("APPLICO X GATE : commuto i bit"  );
        double temp = alpha;
        alpha = beta;
        beta = temp;
    }

    // Misura il qubit restituendo 0 o 1 con le rispettive probabilità
    public int measure() {
        double probabilityOfZero = alpha * alpha;
        double d = random.nextDouble();
        System.out.println("measure d="  + d + " vs. " + probabilityOfZero);
        if (d < probabilityOfZero) {
            // Misurazione di 0
            alpha = 1.0;
            beta = 0.0;
            return 0;
        } else {
            // Misurazione di 1
            alpha = 0.0;
            beta = 1.0;
            return 1;
        }
    }

    // Stampa lo stato attuale del qubit
    public void printState() {
        System.out.println("STATO ATTUALE alpha = " + alpha + ", beta = " + beta);
    }

    public static void main(String[] args) {
        QubitSimulator qubit = new QubitSimulator();
        qubit.printState();

        // Applica la porta X (NOT)
        qubit.applyXGate();
        qubit.printState();

        // Eseguiamo una misurazione
        int measurementResult = qubit.measure();
        System.out.println("Measurement result: " + measurementResult);
        qubit.printState();
    }
}

