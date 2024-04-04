//package main.resources.utils;

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.stage.Stage
import javafx.scene.layout.GridPane
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.text.Text
import unibo.basicomm23.utils.CommUtils

class DisplayObj : Application() {
    protected var stage: Stage? = null

    companion object {
        protected var launched = false
        protected var outarea: TextArea? = null
        protected var outtext: Text? = null
        protected var myButton: Button? = null
        protected var created = false
        protected var display: DisplayObj? = null
        
        public fun create() : DisplayObj{
        //CommUtils.outred("WARNING: display create")
           if( ! created ) {
        	   val d   = DisplayObj()
               display = d
        	   created = true;
			   kotlin.concurrent.thread(start = true) {
			     d.initialize( ) 
			   } 
           }else CommUtils.outred("WARNING: display already created")
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
        val scene = Scene(root, 800.0, 300.0)
        stage!!.title = "actorgui"
        stage.scene = scene
        stage.show()
        stage.isAlwaysOnTop = true
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
            CommUtils.outred("WARNING: display non yet started")
        }  
         outarea!!.appendText("""$s""".trimIndent())
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
}