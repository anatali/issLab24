//package main.resources.utils;

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.stage.Stage
import javafx.scene.layout.GridPane
import javafx.scene.Scene
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.chart.XYChart.Series
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.text.Text
import unibo.basicomm23.utils.CommUtils

class DisplayData : Application() {
    //protected var stage: Stage? = null
    val series = Series<Number, Number>()

    companion object {
        protected var stage: Stage? = null
        protected var launched = false
        protected var outarea: TextArea? = null
        protected var outtext: Text? = null
        protected var myButton: Button? = null
        protected var created = false
        protected var display: DisplayData? = null
        
        public fun create() : DisplayData{
        CommUtils.outred("WARNING: displayData create")
           if( ! created ) {
        	   val d   = DisplayData()
               display = d
        	   created = true;
			   kotlin.concurrent.thread(start = true) {
			     d.initialize( ) 
			   } 
           }else CommUtils.outred("WARNING: displayData already created  ")
           return display!!
        }
    }

    fun initialize() {
        if (!launched) {
            launch(*arrayOf())
            launched = true
        }
    }

    //@kotlin.Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        stage = primaryStage
       createFunScene(stage)
    }


    protected fun setOutArea() {
        outarea = TextArea()
        outarea!!.prefColumnCount = 15
        outarea!!.prefHeight = 280.0
        outarea!!.prefWidth = 800.0
        outarea!!.setWrapText(true)
        myButton = Button("Clear")
        myButton!!.onAction = EventHandler { _: ActionEvent? ->
            //println("Clear!")
            outarea!!.text = ""
        }
    }

    override fun stop() {
        System.exit(0)
    }

    protected fun createScene(stage: Stage?) {
        val root = GridPane()
        root.add(outarea, 0, 0)
        root.add(myButton, 0, 1)
        val scene = Scene(root, 800.0, 300.0)
        stage!!.title = "actorgui"
        stage.scene = scene
        stage.show()
        stage.isAlwaysOnTop = true
        print("createScene $stage")
    }

    fun write(s: String?) {
        if (s == null) return  //defensive
        while (outarea == null){
            Thread.sleep(500L)
            //CommUtils.outred("WARNING: display non yet started")
        }  //defensive
        var outs = s.replace("show(", "").replace("out(", "")
        val i = outs.lastIndexOf(")")
        if (i >= 0) outs = outs.substring(0, i)
        outarea!!.appendText("""$outs""".trimIndent())
        outarea!!.appendText("\n")
    }
    fun print(s: String?) {
        if (s == null) return    //defensive
        //CommUtils.outred("print: $s")
        while (outarea == null){ //defensive
            Thread.sleep(500L)
            CommUtils.outred("WARNING print: display non yet started")
        }  
         //outarea!!.appendText("""$s""".trimIndent()+"__${display!!}  $stage")
         outarea!!.appendText("""$s""".trimIndent() )
         outarea!!.appendText("\n")
    }
    fun simulateButtonPress() { //(Button button
        // Puoi chiamare il metodo fire() per simulare la pressione del pulsante
        if (myButton != null) myButton!!.fire() else CommUtils.outred("ActorIO |  simulateButtonPress null  ")
    }

    fun waitUserCmd( prompt: String ) : Int {
		try {
		    CommUtils.outgreen("$prompt")
		    val V = readLine()   
 			if( V !== null ) {
 				println("$V")
 				return V.toInt()
 			}
 			else return 0
		} catch (e: java.lang.Exception) {
			CommUtils.outred("ERROR: ${e} ")
			return 0
		}
	}

    fun createData(){
        // Definizione della serie dei dati
        //val series = Series<Number, Number>()
        series.name = "f(x) = x^2" // Modificare la funzione qui

        // Aggiunta dei dati alla serie
        var x = -5.0
        while (x <= 5) {
            // Intervallo di visualizzazione della funzione
            val y = x * x // Modificare la funzione qui
            series.data.add(XYChart.Data(x, y))
            x += 0.5
        }
        CommUtils.outblue(" ${series.data} ")
    }

    fun showData(){
        print("DATA ${series.data}")
    }

    fun createFunScene(stage: Stage?){
        createData()
        // Definizione degli assi
        val xAxis = NumberAxis()
        val yAxis = NumberAxis()
        // Creazione del grafico a linee
        val lineChart = LineChart(xAxis, yAxis)
        // Aggiunta della serie al grafico
        lineChart.data.add(series)
        CommUtils.outblue(" lineChart done $stage")
        //WARNING: Not on FX application thread;
        val scene = Scene(lineChart, 800.0, 300.0)
        stage!!.scene = scene
        stage!!.show()
        stage.isAlwaysOnTop = true

    }


    fun fshow( ) {

         // Definizione degli assi
         val xAxis = NumberAxis()
         val yAxis = NumberAxis()

         // Creazione del grafico a linee
         val lineChart = LineChart(xAxis, yAxis)

         // Impostazione del titolo del grafico
         lineChart.title = "Grafico Funzione"

         // Definizione della serie dei dati
         val series = Series<Number, Number>()
         series.name = "f(x) = x^2" // Modificare la funzione qui

         // Aggiunta dei dati alla serie
         var x = -10.0
         while (x <= 10) {
             // Intervallo di visualizzazione della funzione
             val y = x * x // Modificare la funzione qui
             series.data.add(XYChart.Data(x, y))
             x += 0.1
         }
        //print("TEST $series")
        //print("TEST1 ${display!!.stage}")
         // Aggiunta della serie al grafico
        lineChart.data.add(series)
          print("DATA ${lineChart.data}")
        /*
          val scene = Scene(lineChart, 800.0, 300.0)
          stage!!.scene = scene
          stage!!.show()

           */

    }


}