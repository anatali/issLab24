package unibo.servicefacade24;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;
import it.unibo.kactor.sysUtil;
import unibo.basicomm23.utils.CommUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ApplSystemInfo {

    private   Prolog pengine  ;

    public Vector<String> readSystemFileDescriptionNames(String sysname){
        File folder = new File(System.getProperty("user.dir"));
        CommUtils.outcyan( "ApplSystemInfo | readSystemFileNames "  + folder);
        Vector<String> outS = new Vector<String>();
        for (final File fileEntry : folder.listFiles()) {
            String fname= fileEntry.getName();
            //if( fname.endsWith(".pl") && ! fname.equals("sysRules.pl")) {
            if( fname.equals(sysname+".pl") ){
                CommUtils.outcyan( "ApplSystemInfo | readSystemFileDescriptionNames: " +fname );
                outS.add(fname.trim());
            }
        }
        return outS;
    }



    public List<String> getCtxNamesFromDescription(String sysName ){
        try {
             pengine        = new Prolog();
            Theory systemTh = new Theory( new FileInputStream(sysName) );
            Theory rulesTh  = new Theory( new FileInputStream("sysRules.pl") );
            CommUtils.outblue("ApplSystemInfo | getCtxNamesFromDescription systemTh:\n"+systemTh);
            pengine.addTheory(systemTh);
            pengine.addTheory(rulesTh);
            //SolveInfo sol =  pengine.solve("getOtherContextNames(CTXNAMES,dummy)."); //li prende tutti
            SolveInfo sol =  pengine.solve("getLocalContextNames(CTXNAMES).");
            if( sol.isSuccess() ) {
                String ctxs = sol.getVarValue("CTXNAMES").toString();
                CommUtils.outcyan("ApplSystemInfo ctxs=" + ctxs);
                List<String> ctxNames = sysUtil.strRepToList(ctxs);
                return ctxNames;
            }else return new ArrayList<String>( ); //List.of("");
        } catch (Exception e) {
            CommUtils.outred("ApplSystemInfo | getCtxNamesFromDescription ERROR:" + e.getMessage());
            return new ArrayList<String>( ); //List.of("");
        }
    }



    public  List<String> getActorNames(String ctx) {
        CommUtils.outcyan( "ApplSystemInfo | getActorNames ctx=" + ctx  );
        List<String> actors = getAllActorNames(ctx); //sysUtil.getAllActorNames(ctx); OCT2023
        CommUtils.outcyan( "ApplSystemInfo ACTORS ON THE localhost  "  );
        actors.forEach( a -> CommUtils.outcyan( a) );
        return actors;
    }

    public List<String> getAllActorNames(String ctxName )  {
        try {
            SolveInfo actorNamesSol = pengine.solve("getActorNames(A," + ctxName + ")."  );
            String actorNames = actorNamesSol.getVarValue("A").toString();
            return sysUtil.strRepToList( actorNames );
        } catch (Exception e) {
            CommUtils.outred("ApplSystemInfo | getAllActorNames");
            return new ArrayList<String>();
        }
    }
}
