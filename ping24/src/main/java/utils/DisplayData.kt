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
import okhttp3.internal.toNonNegativeInt
import unibo.basicomm23.utils.CommUtils

class DisplayData : Application() {
    //protected var stage: Stage? = null
    //val series = Series<Number, Number>()

    companion object {
        protected var stage: Stage? = null
        protected var launched = false
        protected var outarea: TextArea? = null
        protected var outtext: Text? = null
        protected var myButton: Button? = null
        protected var created = false
        protected var display: DisplayData? = null
        protected val series = Series<Number, Number>()
        protected var ready = false;
//        protected val xAxis = NumberAxis()
//        protected val yAxis = NumberAxis()
//        protected val lineChart = LineChart(xAxis, yAxis)
        
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
    
    fun setReady( obs: main.java.PingData ) {
        createObsData(obs)
        ready = true;
    }

    //@kotlin.Throws(Exception::class)
    override fun start(primaryStage: Stage) {
       stage = primaryStage
//    		   while( ! ready ) {
//    			   CommUtils.delay(2000)
//    			   CommUtils.outyellow("WARNING: waiting for read")
//    		   }
//       createFunScene(stage)
    		   setOutArea()
    		   createScene(stage) 
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
        val scene = Scene(root, 800.0, 600.0)
        stage!!.title = "pinggui"
        stage.scene = scene
        stage.show()
        stage.isAlwaysOnTop = true
        print("createScene $stage")
        //Non sembra più capace di fare print e write
		   while( ! ready ) {
			   CommUtils.delay(2000)
			   CommUtils.outyellow("WARNING: waiting for read")
		   }
        addGraph(root)
    }

    fun addGraph(root:GridPane){
    	//stage!!.title = "pinggui"
        //createData() 
        // Definizione degli assi
        val xAxis = NumberAxis()
        val yAxis = NumberAxis()
        // Creazione del grafico a linee
        val lineChart = LineChart(xAxis, yAxis)
        // Aggiunta della serie al grafico
        lineChart.data.add(series)
        CommUtils.outblue(" lineChart done $stage")
        CommUtils.outblue(" ${series.data} ")
        //WARNING: Not on FX application thread;
         
        root.add(lineChart,0,2)
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

 
    
    fun createObsData(obs: main.java.PingData){
        series.name = "ping data"
        var x    = 0.0
        var vold = 0.0
 		obs.h.forEach { v : String ->
            val vv = v.toDouble() - vold 
            series.data.add(XYChart.Data(x,vv ))
            CommUtils.outcyan("v=$v vold=$vold vv=$vv x=$x");
    		vold   = v.toDouble()
            x      += vv 
        };
        CommUtils.outmagenta("${series.data}");
    }
 
    fun showData(){
        print("DATA ${series.data}")
    }
/*
    fun createFunScene(stage: Stage?){
    	stage!!.title = "pinggui"
        //createData()
        // Definizione degli assi
        val xAxis = NumberAxis() 
        val yAxis = NumberAxis()
        // Creazione del grafico a linee
        val lineChart = LineChart(xAxis, yAxis)
        // Aggiunta della serie al grafico
        lineChart.data.add(series)
        CommUtils.outblue(" lineChart done $stage")
        CommUtils.outblue(" ${series.data} ")
        //WARNING: Not on FX application thread;
        val scene = Scene(lineChart, 800.0, 400.0)
        stage!!.scene = scene
        stage!!.show()
        stage.isAlwaysOnTop = true
    }
*/
 


}