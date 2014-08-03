package MessageBoard_OL.DB;

import java.sql.*;

/**
 * Created by DELL on 14-8-2.
 */
public class DbHandler {
    private Connection connection=null;

    private DbHandler(){

    }

    private static DbHandler dbHandler=new DbHandler();
    public static DbHandler getDbHandler(){
        return  dbHandler;
    }

    public void init(){
        String url="jdbc:postgresql://127.0.0.1:5432/mydb";
        String name="deepfuture";
        String password="123123";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection= DriverManager.getConnection(url,name,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rsTable=connection.getMetaData().getTables(null,null,"messcontent",null);
            if(rsTable.next()){
                System.err.println("The table MessContent exists");
            }
            else {
                String sql="create table MessContent (id int primary key,Content varchar(8192));";
                Statement statement=connection.createStatement();
                statement.executeUpdate(sql);
                System.out.println("The Table MessContent is created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void des(){
        try {

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean write(int id,String content){
        try {
            Statement statement=connection.createStatement();
            String sql="INSERT INTO MessContent VALUES ("+id+",'"+content+"')";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  true;
    }

    public int readcount(String str){
        int count=0;
        String sql="select count(*) from messcontent;";
        try {
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            ResultSetMetaData rsm=rs.getMetaData();
//            int num=rsm.getColumnCount();
            rs.next();
            String ColumnName=rsm.getColumnName(1);
            Object sqlview=rs.getString(ColumnName);
            count=Integer.valueOf(sqlview.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public String read(int id){

        String content=new String();
        try {
            Statement statement=connection.createStatement();
            String sql="select content from messcontent where id="+id;
            ResultSet rs=statement.executeQuery(sql);
            ResultSetMetaData rsm=rs.getMetaData();
            int count=rsm.getColumnCount();

            if(!rs.next()){
                System.out.println("No Data in");
            }else {
                for(int i=0;i<count;i++){
                    String ColumnName=rsm.getColumnName(i+1);
                    Object sqlview=rs.getString(ColumnName);
                    content=sqlview.toString();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  content;
    }



}
