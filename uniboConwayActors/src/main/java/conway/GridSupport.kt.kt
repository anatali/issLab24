//package conway

import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.CoapObserverSupport
import it.unibo.kactor.sysUtil
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import unibo.basicomm23.utils.CommUtils
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*
import org.eclipse.californium.core.*

object `GridSupport.kt` {

    var RowsNum  = 3    //Numero iniziale delle righe
    var ColsNum  = 3    //Numero iniziale delle colonne (che non cambia)
    var CellSize = 40   //Ampiezza della cella sul display

    //lateinit var displayOwnerActor : ActorBasic; //vedi ConwayIO.initialize


    /*
     * Ricava le coordinate di una cella da suo nome cell_x_y
     */
    @JvmStatic fun getCellCoords(name : String) : Vector<Int> {
        val out = Vector<Int>()
        val coords = name.replace("cellc_","").split("_")
        val x = coords[0].toInt()
        val y = coords[1].toInt()
        out.add(x)
        out.add(y)
        return out
    }

    /*
    * Utility per salvare una stringa in un file
    */
    @JvmStatic  fun saveOnFile(s: String?, fName: String?) {
        try {
            val myWriter = FileWriter(fName)
            myWriter.write(s)
            myWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
/*
-------------------------------------------------
FUNZIONI DI SUPPORTO ALLA LOGICA APPLICATIVA
-------------------------------------------------
 */
    /*
     * 1) Legge la configurazione logica delle celle da un file (gridConfig.json)
     */
    @JvmStatic
    fun readGridConfig(fName: String) : Vector<Int>{
        val outV = Vector<Int>()
        val jsonParser = JSONParser()
        val config     = File("${fName}").readText(Charsets.UTF_8)
        //CommUtils.outred("${fName}   $config")
        val jsonObject = jsonParser.parse(config) as JSONObject
        RowsNum  = jsonObject.get("rowsNum").toString().toInt()
        ColsNum  = jsonObject.get("colsNum").toString().toInt()
        CellSize = jsonObject.get("cellsize").toString().toInt()
        outV.add( RowsNum )
        outV.add( ColsNum )
        outV.add( CellSize )
        CommUtils.outyellow("GridSupport | readGridConfig RowsNum=$RowsNum, ColsNum=$ColsNum, CellSize=$CellSize")
        return outV
    }

    /*
    2) Genera i nomi delle celle vicine a quella di ccordinate x,y
    */
    @JvmStatic  fun genNeighborsNames(rNum: Int, cNum: Int, x: Int, y: Int): String? {
        var outS : String
        val nb   = java.lang.StringBuilder()
        for (i in -1..1) {
            for (j in -1..1) {
                if ( (i == 0) and (j == 0) ) continue
                val x1 = x + i
                val y1 = y + j
                if (x1 >= 0 && x1 < rNum && y1 >= 0 && y1 < cNum) {
                    val cell = ",c_" + x1 + "_" + y1
                    nb.append(cell)
                }
            }
        }
        outS = nb.toString().replaceFirst(",".toRegex(), "")
        return "[$outS]"
    }
    /*
    2) Genera una stringa contenente fatti del tipo
        nb(c_x_y,[nomi celle vicine] )
    e salva la stringa nel file fName (grid.pl)
    */
    @JvmStatic fun genNeighbornsDescr(rNum: Int, cNum: Int, fName: String?) {
        var outS = ""
        val grid = java.lang.StringBuilder()
        for (i in 0 until rNum) {
            for (j in 0 until cNum) {
                val cell = "c_" + i + "_" + j
                val nbs = genNeighborsNames(rNum, cNum, i, j)
                grid.append("nb($cell,$nbs).\n")
            }
        }
        outS = grid.toString()
        saveOnFile(outS, fName)
    }
    
    /*
    3) Crea descrizione di cella della forma cell(c_x_y,localctx).
     */
	@JvmStatic  fun genCellDescr(ctx: String, firstRowIndex: Int,
	                             rowsN: Int, colsN: Int): String? {
	    var outS = ""
	    val grid = StringBuilder()
//	    CommUtils.outgreen("genCellDescr $ctx firstRow=$firstRowIndex " +
//	            "rowsN=$rowsN colsSize=$colsSize")
	    for (i in 0 until rowsN) {
	        for (j in 0 until colsN) {
	            val cell = "c_" + (firstRowIndex + i) + "_" + j
	            grid.append("cell($cell,$ctx).\n")
	        }
	    }
	    outS = grid.toString()
//	    CommUtils.outgreen("genCellDescr $outS")
	    return outS
	}


/*
 * ====================================================================
 * SUPPORTI RUN TIME per le celle
 * ====================================================================
 */

    /*
    Genera il file gridall.pl dei nomi delle celle e il contesto in cui si trovano
    */
    @JvmStatic fun genLocalGriddescr( localCtxName : String, RowsN : Int, ColsN : Int,
    		neighBornFName : String, cellsFName : String  ){
    	val d = genCellDescr(localCtxName,0,RowsN,ColsN )
        saveOnFile(d,cellsFName)
        genNeighbornsDescr(RowsN, ColsN, neighBornFName)
    }


	/*   
	 Invoca il metodo createActorDynamically
	*/
    @JvmStatic fun declareInternalCells(  a : ActorBasic) : Int{
      var Ncells = 0
      val cells = getIteratorCellNamesInContext(a.context!!.name);
        if( cells != null ) {
            while (cells.hasNext()) {
                val next = cells.next().toString()
                Ncells = Ncells + 1
                val name = a.createActorDynamically("cell", next, false) //false isconfined
                //CommUtils.outyellow("GridSupport | declareInternalCells Created: $name"  )
            }
            //Per controllo
            CommUtils.outblue("Local cells: $Ncells")
            val actorNames = sysUtil.getAllActorNames()
            CommUtils.outblue("GridSupport declareInternalCells | Actors in context:\n" + actorNames)
        }
        return Ncells
    }


    @JvmStatic fun createCellsInternal(  a : ActorBasic) {
        val cellsList = getCellNamesInContext(a.context!!.name);
        if (cellsList != null) {
            //CommUtils.outblue("Local cells: ${cellsList} ${cellsList.listSize()}")
            val cells = cellsList.listIterator() as Iterator<Term>
            while (cells.hasNext()) {
                val nextSuffix = cells.next().toString()
                val name = a.createActorDynamically(
                    "cell", nextSuffix, false) //false for 'isconfined'
                //CommUtils.outyellow("GridSupport | declareInternalCells Created: $name"  )
            }
        }
    }


    /*
    Ricava dalla base di consocenza locale un iteratore sulla lista delle celle
   */
    fun getIteratorCellNamesInContext(ctx: String): Iterator<Term?>? {
           //CommUtils.outcyan("getIteratorCellNamesInContext $ctx")
           val sol = sysUtil.getPrologEngine().solve("findall( C, cell( C,$ctx ),CELLS).")
           //CommUtils.outcyan("GridSupport |  getIteratorCellNamesInContext $ctx sol=$sol")
           if (sol.isSuccess) {
               val t = sol.getVarValue("CELLS") as Struct
               return t.listIterator() as Iterator<Term>
           } else return null

    }
 
    fun getCellNamesInContext(ctx: String): Struct? {
       //CommUtils.outcyan("getCellNamesInContext $ctx")
       val sol = sysUtil.getPrologEngine().solve("findall( C, cell( C,$ctx ),CELLS).")
       //CommUtils.outcyan("GridSupport |  getCellNamesInContext $ctx sol=$sol")
       if (sol.isSuccess) {
           val t = sol.getVarValue("CELLS") as Struct
           return t
       } else return null

    }


    /*
     * Fornisce la lista dei nomi delle celle vicine
     */
    @JvmStatic fun getCellNeighbors(x:Int,y:Int) : Vector<Term> {
        sysUtil.loadTheory("cellnbs.pl")
        //CommUtils.outblack("getNeigbornPrologList Vector " + x + " " + y);
        val nblist = Vector<Term>()
        val goal = "nb(c_" + x + "_" + y + ",L)." //nb(c_0_0,[c_0_1,c_1_0,c_1_1]).
        //CommUtils.outblack(goal);
        val sol = sysUtil.getPrologEngine().solve(goal)
        if (sol.isSuccess) {
            val t = sol.getVarValue("L") as Struct
            //CommUtils.outred("getNeigbornPrologList class " + t.getClass() + " isList:" + t.isList() + " " + t.listSize());
            if (t.isList) {
                val it = t.listIterator() as Iterator<Term>
                while (it.hasNext()) {
                    nblist.add(it.next())
                }
                return nblist
            } else throw Exception("GridSupport | getCellNeighbors ERROR: no list")
        } else throw Exception("GridSupport | getCellNeighbors Prolog ERROR")
    }
    
    /* UTITLS cell-related  */
    
 
    @JvmStatic fun getCellNameFromClick(clickMsg:String):String{
       val template = "click(X,Y)" 
       val goal     = "$clickMsg=$template" //UNIFICAZIONE   
       val x        = sysUtil.solve( goal,"X" ) 
       val y        = sysUtil.solve( goal,"Y" ) 
	   val postfix  = x + "_" + y
	   val cellName = "cellc_$postfix"
	   CommUtils.outcyan("GridSupport |  getCellNameFromClick $cellName")
	   return cellName     
    }
    
    @JvmStatic fun displayCellState(CellName:String,State:String,display:ConwayIO ){
       val coords  = getCellCoords(CellName)
	   val x       = coords[0]
	   val y       = coords[1] 
		//CommUtils.outcyan("todisplay $State")   
		if( State=="true") display.cellOn( x,y )
		else display.cellOff( x,y )
	}
    @JvmStatic fun displayCellState(CellName:String,State:String,display:ConwayIOTwostep ){  //ADDED
       val coords  = getCellCoords(CellName)
	   val x       = coords[0]
	   val y       = coords[1] 
		//CommUtils.outcyan("todisplay $State")   
		if( State=="true") display.cellOn( x,y )
		else display.cellOff( x,y )
	}
    @JvmStatic fun displayCellState(CellName:String,State:String,display:ConwayIOMorerows ){  //ADDED
       val coords  = getCellCoords(CellName)
 	   val x       = coords[0]
 	   val y       = coords[1] 
 		//CommUtils.outcyan("todisplay $State")   
 		if( State=="true") display.cellOn( x,y )
 		else display.cellOff( x,y )
 	}
	
	/*
	Determina il numero totale di celle
	*/
	@JvmStatic fun getNumOfCells() : Int {
        val res    = readGridConfig("gridConfig.json")
		val RowsN  = res.get(0)
		val ColsN  = res.get(1)
		return RowsN * ColsN	
    }
	
	var NumOfCellsAlsoRemote = 0
//	@JvmStatic fun setNumOfCellsAlsoRemote(n : Int)   {
//		NumOfCellsAlsoRemote = n
//    }	
//	@JvmStatic fun getNumOfCellsAlsoRemote( ) : Int  {
//		return NumOfCellsAlsoRemote 
//    }
	@JvmStatic fun createCellsLocal(a:ActorBasic, RowsN:Int, ColsN:Int, neighBornFName:String, cellsFName:String){
		/*2*/ genLocalGriddescr( a.context!!.name,RowsN,ColsN,neighBornFName,cellsFName)
		/*3*/ sysUtil.loadTheory(cellsFName)
		/*4*/ createCellsInternal(a)
	}
	
	@JvmStatic fun subscribeToNeighbors(  a:ActorBasic, cellName: String  ) : Int {
 	      val Coords= conway.GridSupport.getCellCoords(cellName)
	      val x = Coords[0]
	      val y = Coords[1]
             
    /*1*/ val nblist = getCellNeighbors(x, y)
	      val nblistiter  = nblist.iterator()
	      var countnb=0
	      while( nblistiter.hasNext() ){
	         val next = nblistiter.next().toString()
	/*2*/    a.subscribeTo( "cell$next", cellName )
	         countnb=countnb+1
	      } 
          return countnb
    }
    
    /*
    Sottoscrive ogni cella ai sui cicini e invia alla cella il numero dei vicini
    */
    @JvmStatic suspend fun configureTheCells(a:ActorBasic){
	   val ctx       = a.context!!.name
	   val cellsList = getCellNamesInContext(ctx)
	   val cells= cellsList?.listIterator()
	   while( cells!!.hasNext() ){ //Per ogni cella.. 
	      val name    = "cell${cells!!.next().toString()}"
          val countnb = subscribeToNeighbors(a,name)
	      //INVIO DEL NUMERO DI VICINI
	/*3*/ a.forward("nbconfig", "nbconfig($countnb)",name)
	   }    
    }
    
    @JvmStatic suspend fun sendStopToAllCells(a: ActorBasic ){
       val ctx       = a.context!!.name
	   val cellsList = getCellNamesInContext(ctx)
	   val cells=cellsList?.listIterator()
	   while( cells!!.hasNext() ){
	      val name  = "cell${cells!!.next().toString()}"
	      a.forward("stopthecell","stopthecell(ok)",name)
	      //MsgUtil.sendMsg("sender", "stopthecell", "stopthecell(ok)", a)
 	   }
      }

     @JvmStatic fun observeAllCells(a: ActorBasic, ctx : String, msgid: String="coapUpdate" ) {
        val cellsList = getCellNamesInContext(ctx)   //GUARDA NEL CONTESTO (in cui opera gridactor)
        CommUtils.outcyan("observeAllCells cellsList $cellsList")  
        val cells=cellsList?.listIterator()
        while( cells!!.hasNext() ) {
            val name  = "cell${cells!!.next().toString()}"
            //CommUtils.outcyan("observeAllCells $name")  
            CoapObserverSupport(a, "localhost", "8360", ctx, name, msgid)  //Trasforma update in msg
        }
    }
     @JvmStatic fun observeExternalCells(a: ActorBasic, host: String, ctxName: String, ctxPort:String, msgId:String ) {
         val cellsList = getCellNamesInContext(ctxName)
        		 CommUtils.outgreen("observeExternalCells $cellsList")
         val cells=cellsList?.listIterator()
         while( cells!!.hasNext() ) {
             val name  = "cell${cells!!.next().toString()}"
             CommUtils.outgreen("observeExternalCells $name")
             CoapObserverSupport(a, host, ctxPort, ctxName, name,msgId)
         }
     }
     /*
     AGGIUNTA CELLE
     */
     /*
         Descrizione delle celle lato master
     */
      @JvmStatic fun genMasterGriddescr( localCtxName : String, OtherCtxName: String,
                    	FirstRowInFirstCtx:Int,   RowsNumInFirstCtx:Int,
      				FirstRowInSecondCtx: Int, RowsNumInSecondCtx:Int, 
      				ColsNum:Int,cellsFName:String, nbsFName:String ){
         CommUtils.outgreen("GridSupport genMasterGriddescr | RowsNumInFirstCtx=$RowsNumInFirstCtx"  );
         val s1 = genCellDescr(localCtxName, FirstRowInFirstCtx,  RowsNumInFirstCtx,  ColsNum )
         var s2 = genCellDescr(OtherCtxName, FirstRowInSecondCtx, RowsNumInSecondCtx, ColsNum )
         saveOnFile(s1+s2,cellsFName) //"grid.pl"
         val RowsNum = RowsNumInFirstCtx+RowsNumInSecondCtx
         genNeighbornsDescr(RowsNum,ColsNum, nbsFName) //"cellnbs.pl"
      }   
      
      
  	/*   
 	  Asserisce fatti qactor(cell$next,$OtherCtxName,external) 
 	  alla teoria gridconway.pl caricata allo startup del sistema
 	*/
     @JvmStatic fun declareExternalCells(OtherCtxName: String) : Int {
         var NOthers = 0;
         var outS = "";
         val othercells        = getIteratorCellNamesInContext(OtherCtxName);
         val initialactorNames = sysUtil.getAllActorNames()
         CommUtils.outblue("Actors initially in context:\n $initialactorNames"   )
         if( othercells != null ){
             while( othercells.hasNext() ){
                 val next =  othercells.next().toString()
                 NOthers   = NOthers + 1
                 val fact  = "qactor(cell$next,$OtherCtxName,external)"
                 //CommUtils.outcyan("fact= " + fact)
                 outS = outS + fact +".\n"
                 //Aggiungo l'info next al contesto corrente.
                 //val sol =
                   sysUtil.getPrologEngine().solve("assert("+ fact +").") //Adesso il ctx corrent sa che esiste una cell remota
                 //CommUtils.outcyan("sol= " + sol)
             }
             CommUtils.outblue("GridSupport declareExternalCells | Other cells:\n" + outS)
             return NOthers
         }else return 0
     }    

}