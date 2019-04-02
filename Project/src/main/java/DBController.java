import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DBController {
    // Connection connection;


    public static Connection dbConnect() {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
            return connection;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void loadNodeData(File file, Connection connection){
        BufferedReader br = null;
        String line = "";
        String[] arr;
        try{
            br = new BufferedReader(new FileReader(file));
            br.readLine(); // skip header
            while((line = br.readLine()) != null){
                arr = line.split(",");
                connection.createStatement().execute("insert into NODES " +
                        "values ('"+ arr[0] +"',"+ arr[1]+","+ arr[2]+",'"+ arr[3]+"','"+ arr[4]+"','"+ arr[5]+"','"+ arr[6]+"','"+ arr[7]+"')");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void loadEdgeData(File file, Connection connection){
        BufferedReader br = null;
        String line = "";
        String[] arr;
        try{
            br = new BufferedReader(new FileReader(file));
            br.readLine(); // skip header
            while((line = br.readLine()) != null){
                arr = line.split(",");
                connection.createStatement().execute("insert into EDGES " +
                        "values ('"+ arr[0] + "','"+ arr[1]+"','"+ arr[2]+"')");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void createTable(String createStatement, Connection conn){
        try {
            conn.createStatement().execute(createStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void enterData(List<Node> nodes, Connection connection){
        try {
            //connection = DriverManager.getConnection("jdbc:derby:myDB");
            Statement s = connection.createStatement();
            for (Node node : nodes) {
                nodeInsert(s,node);
            }
            //connection.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * updateNode
     *
     * updates node of given id, overriding all fields
     * @param node desired node content -- Must have an existing ID --
     */
    public void updateNode(Node node, Connection connection){
        try{
            //connection = DriverManager.getConnection("jdbc:derby:myDB");
            Statement s = connection.createStatement();
            s.execute("UPDATE NODES" +
                    " SET XCOORD ="+ node.getXcoord() +","+
                    "YCOORD ="+ node.getYcoord() + ","+
                    "FLOOR ="+ node.getFloor() + ","+
                    "BUILDING ='"+ node.getBuilding() + "',"+
                    "NODETYPE = '"+ node.getNodeType() + "',"+
                    "LONGNAME = '"+ node.getLongName() + "',"+
                    "SHORTNAME = '"+ node.getShortName() +"'"+
                    "where NODEID = '" + node.getNodeID() +"'");

            //connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * delete node
     *
     * deletes node of given ID
     * @param ID of node to be deleted
     */
    public void deleteNode(String ID, Connection connection){
        try {
            Statement s = connection.createStatement();
            s.execute("Delete from NODES where NODEID = '"+ ID +"'");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * addNode
     *
     * lets user introduce a single node to the DB
     * @param node new node object
     */
    public void addNode(Node node, Connection connection){
        try{
            //connection = DriverManager.getConnection("jdbc:derby:myDB");
            Statement s = connection.createStatement();
            nodeInsert(s,node);
            //connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static Edge fetchEdge(String ID, Connection connection){
        try{
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("Select from EDGES where EDGEID= '" + ID + "'");
            rs.next();
            Edge edge = new Edge(rs.getString(1), rs.getString(2), rs.getString(3));
            return edge;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void multiFetchEdge(List<String> IDList, Connection connection) {
        try{
            Statement s = connection.createStatement();
            LinkedList<Edge> listOfEdges = new LinkedList<>();
            for(int x = 0; x < IDList.size(); x++) {
                ResultSet rs = s.executeQuery("Select from EDGES where EDGEID = '" + IDList.get(x) + "'");
                rs.next();
                Edge edge = new Edge(rs.getString(1), rs.getString(2), rs.getString(3));
                listOfEdges.add(edge);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static Node fetchNode(String ID, Connection connection) {
        try{
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM NODES WHERE NODEID ='" + ID + "'");
            rs.next();
            Node node = new Node(rs.getString(1),rs.getInt(2),rs.getInt(3),
                    rs.getString(4),rs.getString(5),rs.getString(6),
                    rs.getString(7),rs.getString(8));
            return node;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LinkedList<Node> multiNodeFetch(List<String> IDList, Connection connection) {
        try{
            Statement s = connection.createStatement();
            LinkedList<Node> listOfNodes = new LinkedList<>();
            for(int x = 0; x < IDList.size(); x++) {
                ResultSet rs = s.executeQuery("SELECT * FROM NODES WHERE NODEID='" + IDList.get(x) + "'");
                rs.next();
                Node node = new Node(rs.getString(1), rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8));
                listOfNodes.add(node);
            }
            return listOfNodes;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static

    /**
     * generateListofNodes
     *
     * creates and returns a list of node objects
     * @return LinkedList</Node>
     */
    public static LinkedList<Node> generateListofNodes(Connection connection){
        try{
            //connection = DriverManager.getConnection("jdbc:derby:myDB");
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT * from NODES");
            LinkedList<Node> listOfNodes = new LinkedList<>();
            while(rs.next()){
                Node node = new Node(rs.getString(1),rs.getInt(2),rs.getInt(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8));
                listOfNodes.add(node);
            }
            //connection.close();
            return listOfNodes;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * nodeInsert
     *
     * helper method, inserts nodes into existing table
     * @param s existing statement
     * @param node new node object
     */
    public void nodeInsert(Statement s, Node node){
        try {
            s.execute("insert into NODES values ('"+node.getNodeID()+"',"+
                    node.getXcoord()+","
                    +node.getYcoord()+","+
                    node.getFloor() + "," +
                    " '" + node.getBuilding() + "'," +
                    " '" + node.getNodeType() + "'," +
                    " '" + node.getLongName() + "'," +
                    " '" + node.getShortName() + "')");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * exportData
     *
     * selects all content held in Nodes table and prints it to a file
     * @param filename name of output file
     */
    public void exportData(String filename) {
        Connection connection = null;
        Statement stmt;
        String query = "Select * from nodes";
        try {
            connection = DriverManager.getConnection("jdbc:derby:myDB");
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            File file = new File(filename);
            FileWriter fw = new FileWriter(filename);
            fw.write("nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName \r\n");
            while(rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append(',');
                fw.append(rs.getString(7));
                fw.append(',');
                fw.append(rs.getString(8));
                fw.write("\r\n");
            }
            fw.flush();
            fw.close();
            connection.close();
        } catch(Exception e) {
            e.printStackTrace();
            stmt = null;

        }
    }


    /**
     * ClearData
     * Drops all data stored in Nodes
     */
    public void clearData(Connection connection){
        try {
            Statement s = connection.createStatement();
            s.execute("DELETE from nodes");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}










