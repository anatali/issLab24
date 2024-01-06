package utils

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
        outarea!!.prefWidth = 600.0
        myButton = Button("Clear")
        myButton!!.onAction = EventHandler { e: ActionEvent? ->
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
        val scene = Scene(root, 600.0, 300.0)
        stage!!.title = "actorgui"
        stage.scene = scene
        stage.show()
        stage.isAlwaysOnTop = true
    }

    fun write(s: String?) {
        if (s == null) return  //defensive
        if (outarea == null) return  //defensive
        var outs = s.replace("show(", "").replace("out(", "")
        val i = outs.lastIndexOf(")")
        if (i >= 0) outs = outs.substring(0, i)
        outarea!!.appendText(
            """$outs""".trimIndent()
        )
    }

    fun simulateButtonPress() { //(Button button
        // Puoi chiamare il metodo fire() per simulare la pressione del pulsante
        if (myButton != null) myButton!!.fire() else CommUtils.outred("ActorIO |  simulateButtonPress null  ")
    }


}