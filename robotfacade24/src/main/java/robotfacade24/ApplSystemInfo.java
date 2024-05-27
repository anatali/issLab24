package main.java.robotfacade24;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;
import unibo.basicomm23.utils.CommUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
readConfig invocato da CustomContainer

setup() e getActorNamesInApplCtx() invocati da
  ApplguiCore create da FacadeBuilder
  
  MA PERCHE' dovrebbe conoscere basicrobot24.pl??
 */
public class ApplSystemInfo {
    public static String qakSysHost;
    public static String qakSysPort;
    public static String qakSysCtx;
    public static String applActorName;
    public static String ctxportStr;
    public static int ctxport;
    public static String facadeportStr;
    public static int facadeport;
    public static String appName;

    private static Prolog pengine;

    /*
    facadeConfig.json

		{"host":"localhost", "port":"8020", "context":"ctxbasicrobot", "facade": "basicrobot", 
		"facadeport":"8085", "sysdescr":"basicrobot24" }
     */
    public static void readConfig(){
        List<String> config = QaksysConfigSupport.readConfig("facadeConfig.json");
        if( config != null ) {
            qakSysHost    = config.get(0);
            ctxportStr    = config.get(1);
            qakSysCtx     = config.get(2);
            applActorName = config.get(3);
            facadeportStr = config.get(4);
            appName       = config.get(5);
            ctxport       = Integer.parseInt(ctxportStr);
            facadeport    = Integer.parseInt(facadeportStr);
        }
    }

    public  static List<String> getActorNamesInApplCtx( ) {
        //CommUtils.outcyan( "ApplSystemInfo | getActorNames ctx=" + ctx  );
        List<String> actors = getAllActorNames(qakSysCtx);
        CommUtils.outcyan( "ApplSystemInfo ACTORS ON THE localhost  "  );
        actors.forEach( a -> CommUtils.outcyan( a) );

        return actors;
    }

    public static List<String> getAllActorNames(String ctxName )  {
        try {
            SolveInfo actorNamesSol = pengine.solve("getActorNames(A," + ctxName + ")."  );
            String actorNames = actorNamesSol.getVarValue("A").toString();
            return  Arrays.asList(actorNames.replace("[", "")
                    .replace("]", "").split(","));
        } catch (Exception e) {
            CommUtils.outred("ApplSystemInfo | getAllActorNames");
            return new ArrayList<String>();
        }
    }

    public static void setup() {
        try {
            pengine = new Prolog();
            Theory systemTh = new Theory(new FileInputStream(appName + ".pl"));
            Theory rulesTh  = new Theory(new FileInputStream("sysRules.pl"));
            CommUtils.outgreen("ApplSystemInfo | setup  "  );
            //CommUtils.outblue("" + systemTh);  //VISUALIZZA basicrobot24.pl
            pengine.addTheory(systemTh);
            pengine.addTheory(rulesTh);
        } catch (Exception e) {
            CommUtils.outred("ApplSystemInfo | setup ERROR:" + e.getMessage());
        }
    }
}
