package main.resources;

import java.util.Random;

public class QubitHadamardSimulator {
    private double alpha; // Coefficiente del bit |0>
    private double beta;  // Coefficiente del bit |1>
    private Random random;

    public QubitHadamardSimulator() {
        random = new Random();
        // Inizializzazione con un qubit |0>
        alpha = 1.0;
        beta = 0.0;
    }

    // Applica la porta Hadamard al qubit
    public void applyHadamardGate() {
        System.out.println("APPLICO HADAMARD GATE : crea sovrapposizione"  );
        double newAlpha = (alpha + beta) / Math.sqrt(2);
        double newBeta = (alpha - beta) / Math.sqrt(2);
        alpha = newAlpha;
        beta = newBeta;
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
        QubitHadamardSimulator qubit = new QubitHadamardSimulator();
        qubit.printState();

        // Applica la porta Hadamard
        qubit.applyHadamardGate();
        qubit.printState();

        // Eseguiamo una misurazione
        int measurementResult = qubit.measure();
        System.out.println("Measurement result: " + measurementResult);
        qubit.printState();
 
        measurementResult = qubit.measure();
        System.out.println("Measurement OTHER result: " + measurementResult);
        qubit.printState();
    }
}

