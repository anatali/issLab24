package main.resources.map;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

import unibo.basicomm23.utils.CommUtils;
/*
 * ----------------------------------------
 * RoomMap.java
 * ----------------------------------------
 */
public class RoomMap implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	public static enum cellvalue {UNKNOWN, OBSTACLE, FREE, ROBOT}
	
	private int nr = 0;
	private int nc = 0;	
	private cellvalue [][] roomMap; 
		
	
	public RoomMap( int nr, int nc) {
		this.nr=nr;
		this.nc=nc;
		roomMap = new cellvalue[nr][nc];
		clear();
	}
	
	/*
	 * v = 0 : UNKNOWN
	 * v = 1 : obstacle
	 * v = 2 : free
	 * v = 3 : robot
	 */
	public void setPos(int x, int y, cellvalue v) {
		roomMap[x][y] = v;
	}
	
	public void clear() {
		for( int i=0;i<nr;i++) {
			for( int j=0;j<nc;j++) {
				roomMap[i][j] = cellvalue.UNKNOWN;
			}
		}
	}
	
	public boolean typeOfCell( int x, int y, cellvalue v ) {
		return roomMap[x][y] == v;
	}
	
	public void setCell( int x, int y, cellvalue v ) {
		 roomMap[x][y] = v;
	}
	public void setCellClean( int x, int y  ) {
		 roomMap[x][y] = cellvalue.FREE;
	}
	public void setRobot( int x, int y  ) {
		 roomMap[x][y] = cellvalue.ROBOT;
	}
	public void setFree( int x, int y  ) {
		 roomMap[x][y] = cellvalue.FREE;
	}
	public void setObstacle( int x, int y  ) {
		 roomMap[x][y] = cellvalue.OBSTACLE;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( int i=0;i<nr;i++) {
			sb.append("|");
			for( int j=0;j<nc;j++) {
				if (typeOfCell(i,j,cellvalue.ROBOT)) sb.append("r, ");
				else if (typeOfCell(i,j,cellvalue.OBSTACLE)) sb.append("X, ");
				else if (typeOfCell(i,j,cellvalue.UNKNOWN)) sb.append("0, ");
				else if (typeOfCell(i,j,cellvalue.FREE)) sb.append("1, ");
			}
			sb.append("\n");
		}
		return sb.toString();		
	}
	
	public String toProlog() {
		StringBuilder sb = new StringBuilder();
		for( int i=0;i<nr;i++) {
			for( int j=0;j<nc;j++) {
				sb.append("cell(");
				sb.append(""+i+","+j+",");
				if (typeOfCell(i,j,cellvalue.ROBOT)) sb.append("r");
				else if (typeOfCell(i,j,cellvalue.OBSTACLE)) sb.append("X");
				else if (typeOfCell(i,j,cellvalue.UNKNOWN)) sb.append("0");
				else if (typeOfCell(i,j,cellvalue.FREE)) sb.append("1");
				sb.append(").\n");
			}
		}  
		return sb.toString();		
	}
	
	public void showMap() {
		String mapStr = toString();
		mapStr = mapStr + " DIR->" + RobotDir.getDir();
		CommUtils.outyellow(mapStr);
		 
	}

    public static RoomMap loadRoomMap( String fname ){  
        try{
            ObjectInputStream inps = new ObjectInputStream(new FileInputStream(fname+".bin"));
            RoomMap map  = (RoomMap)inps.readObject();
            CommUtils.outyellow("loadRoomMap DONE from "+fname);
            //RoomMap.setRoomMap( map );
            inps.close();
            return map;
        }catch(Exception e){
        	CommUtils.outred("loadRoomMap FAILURE "+ e.getMessage());
        	return null;
        }
    }
    
    public  void saveRoomMap(  String fname, String map  ) throws Exception {
    	CommUtils.outyellow("saveRoomMap in "+ fname );
        PrintWriter pw = new PrintWriter( new FileWriter(fname+".txt") );
        pw.print( map );
        pw.close();

        ObjectOutputStream os = new ObjectOutputStream( new FileOutputStream(fname+".bin") );
        os.writeObject( this );
        os.flush();
        os.close();
    }
    
    public  void saveRoomMapProlog(  String fname, String map  ) throws Exception {
    	CommUtils.outyellow("saveRoomMap in "+ fname );
        PrintWriter pw = new PrintWriter( new FileWriter(fname+".txt") );
        pw.print( map );
        pw.close();
    }
    
}
