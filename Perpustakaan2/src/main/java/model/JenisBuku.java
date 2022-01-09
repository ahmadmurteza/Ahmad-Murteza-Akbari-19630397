package model;

import db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JenisBuku {
    private int id;
    private String namajenisbuku;
    
    private Database database;
    private Connection connection;
    
    @Override
    public String toString(){
        return namajenisbuku;
    }
    
    public boolean create(){
        
        String insertSQL = "INSERT INTO jenisbuku VALUES (NULL, ?)";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, this.namajenisbuku);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean update(){
        String updateSQL = "UPDATE jenisbuku SET namajenisbuku = ? WHERE id = ?";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, this.namajenisbuku);
            preparedStatement.setInt(2, this.id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean delete(){
        String deleteSQL = "DELETE FROM jenisbuku WHERE id = ?";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, this.id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<JenisBuku> read(){
        
        ArrayList<JenisBuku> list = new ArrayList<>();
        
        String selectSQL = "SELECT * FROM jenisbuku" ;
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                JenisBuku jb = new JenisBuku();
                jb.setId(rs.getInt("id"));
                jb.setNamajenisbuku(rs.getString("namajenisbuku"));
                
                list.add(jb);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public boolean find(){
        String findSQL = "SELECT * FROM jenisbuku WHERE id = ?";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(findSQL);
            preparedStatement.setInt(1, this.id);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                this.setNamajenisbuku(rs.getString("namajenisbuku"));
                return true;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<JenisBuku> search(String keyword){
        ArrayList<JenisBuku> list = new ArrayList<>();
        
        String searchSQL = "SELECT * FROM jenisbuku WHERE namajenisbuku like ?" ;
        
        keyword = "%" + keyword + "%";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(searchSQL);
            preparedStatement.setString(1, keyword);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                JenisBuku jb = new JenisBuku();
                jb.setId(rs.getInt("id"));
                jb.setNamajenisbuku(rs.getString("namajenisbuku"));
                
                list.add(jb);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamajenisbuku() {
        return namajenisbuku;
    }

    public void setNamajenisbuku(String namajenisbuku) {
        this.namajenisbuku = namajenisbuku;
    }
}
