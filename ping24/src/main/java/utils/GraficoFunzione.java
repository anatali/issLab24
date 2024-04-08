package main.java.utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


public class GraficoFunzione extends Application {
 
    @Override
    public void start(Stage primaryStage) {
        // Definizione degli assi
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        
        // Creazione del grafico a linee
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        
        // Impostazione del titolo del grafico
        lineChart.setTitle("Grafico Funzione");
        
        // Definizione della serie dei dati
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("f(x) = x^2"); // Modificare la funzione qui
        
        // Aggiunta dei dati alla serie
        for (double x = -10; x <= 10; x += 0.1) { // Intervallo di visualizzazione della funzione
            double y = x * x; // Modificare la funzione qui
            series.getData().add(new XYChart.Data<>(x, y));
        }
        
        // Aggiunta della serie al grafico
        lineChart.getData().add(series);
        
        // Creazione della scena
        Scene scene = new Scene(lineChart, 800, 600);
        
        // Impostazione della scena e visualizzazione della finestra
        primaryStage.setScene(scene);
        primaryStage.setTitle("Grafico Funzione");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

