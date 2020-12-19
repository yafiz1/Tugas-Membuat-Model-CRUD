/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import library.model.Book;

/**
 *
 * @author user
 */
public class Database {
    private final String URL = "jdbc:mysql://localhost:3306/semester5_db_library?serverTimezone=Asia/Makassar";
    private final String USER = "root";
    private final String PASSWORD = "";
    private final String TABLE_NAME = "books";
    private final String PRIMARY_KEY = "id";
    private final String[] FIELD_NAME = new String[]{"book_name","author_name","book_page","publisher"};
    private final Connection CONN = this.getConnection();

    public String[] getFIELD_NAME() {
        return FIELD_NAME;
    }
    
    
    
    private Connection getConnection(){
        Connection conn;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // untuk mengecek driver
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection Successfully");
            return conn;
        } catch (ClassNotFoundException ex) {
            System.err.println("ERROR ClassNotFoundException : " + ex.toString());
        } catch (SQLException ex) {
            System.err.println("ERROR SQLException : " + ex.toString());
        }
        return null;
    }
    
    public boolean create(Book book){
        String createSQL = "INSERT INTO "
                            + TABLE_NAME + " ("+FIELD_NAME[0]+","+FIELD_NAME[1]+","+FIELD_NAME[2]+","+FIELD_NAME[3]+")"
                            + " VALUES "
                            + "(?,?,?,?)";
            
        PreparedStatement ps;
        try {
            ps = this.CONN.prepareStatement(createSQL);
            ps.setString(1,book.getBookName());
            ps.setString(2,book.getBookAuthor());
            ps.setInt(3,book.getBookPage());
            ps.setString(4,book.getBookPublisher());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex);
            return false;
        }
    }
    
    public ArrayList<Book> read(){
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        ArrayList<Book> books = new ArrayList<>();
        Statement statement; // mengeksekusi query
        ResultSet resultSet; // berlaku untuk menyimpan hasil select
        try {
            statement = this.CONN.createStatement();
            resultSet = statement.executeQuery(selectSQL);

            while(resultSet.next()){
                Book book = new Book();
                book.setBookId(resultSet.getInt(PRIMARY_KEY));
                book.setBookName(resultSet.getString(FIELD_NAME[0]));
                book.setBookAuthor(resultSet.getString(FIELD_NAME[1]));
                book.setBookPage(resultSet.getInt(FIELD_NAME[2]));
                book.setBookPublisher(resultSet.getString(FIELD_NAME[3]));
                books.add(book);
            }
            return books;
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex);
            return null;
        }
    }
    
    public boolean update(Book book){
        String updateSQL = "UPDATE "
                            + TABLE_NAME + " "
                            + "SET "
                            + FIELD_NAME[0] + " = ?,"
                            + FIELD_NAME[1] + " = ?,"
                            + FIELD_NAME[2] + " = ?,"
                            + FIELD_NAME[3] + " = ? "
                            + "WHERE "
                            + PRIMARY_KEY + " = ?";
        
        PreparedStatement ps;
        try {
            ps = this.CONN.prepareStatement(updateSQL);
            ps.setString(1,book.getBookName());
            ps.setString(2,book.getBookAuthor());
            ps.setInt(3,book.getBookPage());
            ps.setString(4,book.getBookPublisher());
            ps.setInt(5,book.getBookId());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex);
            return false;
        }
    }
    
    public boolean delete(int id){
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + PRIMARY_KEY + " = ?";
        
        PreparedStatement ps;
        try {
            ps = this.CONN.prepareStatement(deleteSQL);
            ps.setInt(1,id);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex);
            return false;
        }
    }
    
    public ArrayList<Book> searchData(int id){
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + PRIMARY_KEY + " = ?";
        
        PreparedStatement ps;
        ResultSet resultSet;
        
        ArrayList<Book> result = new ArrayList<>();
        try {
            ps = this.CONN.prepareStatement(selectSQL);
            ps.setInt(1,id);
            resultSet = ps.executeQuery();
            
            while(resultSet.next()){
                Book book = new Book();
                book.setBookId(resultSet.getInt(PRIMARY_KEY));
                book.setBookName(resultSet.getString(FIELD_NAME[0]));
                book.setBookAuthor(resultSet.getString(FIELD_NAME[1]));
                book.setBookPage(resultSet.getInt(FIELD_NAME[2]));
                book.setBookPublisher(resultSet.getString(FIELD_NAME[3]));
                result.add(book);
            }
            
            return result;
        } catch (SQLException ex) {
            System.out.println("ERROR : " + ex);
            return result;
        }
    }
    
}
