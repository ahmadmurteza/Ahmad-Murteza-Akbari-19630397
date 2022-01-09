/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import db.Database;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author User
 */
public class Buku {
    private int id;
    private String namaBuku;
    private double harga;
    private JenisBuku jenisBuku;
    
    private Database database;
    private Connection connection;
    
        public boolean create(){
        String insertSQL = "INSERT INTO buku VALUES (NULL, ?, ?, ?)";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, this.jenisBuku.getId());
            preparedStatement.setString(2, this.namaBuku);
            preparedStatement.setDouble(3, this.harga);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean update(){
        String updateSQL = "UPDATE buku SET idjenisbuku=?, namabuku=?, harga=? WHERE id = ?";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, this.jenisBuku.getId());
            preparedStatement.setString(2, this.namaBuku);
            preparedStatement.setDouble(3, this.harga);
            preparedStatement.setInt(4, this.id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean delete(){
        String deleteSQL = "DELETE FROM buku WHERE id = ?";
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, this.id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Buku.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void tampilLaporan(String laporanFile, String SQL) {
        try {
            File file = new File(laporanFile);
            JasperDesign jasDes = JRXmlLoader.load(file);
            
            JRDesignQuery sqlQuery = new JRDesignQuery();
            sqlQuery.setText(SQL);
            jasDes.setQuery(sqlQuery);

            JasperReport JR = JasperCompileManager.compileReport(jasDes);
            JasperPrint JP = JasperFillManager.fillReport(JR,null,getConnection());
            JasperViewer.viewReport(JP);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    
    public ArrayList<Buku> read(){
        ArrayList<Buku> list = new ArrayList<>();
        
        String selectSQL = "SELECT buku.*, jenisbuku.namajenisbuku FROM buku INNER JOIN jenisbuku ON buku.idjenisbuku = jenisbuku.id" ;
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Buku b = new Buku();
                
                b.setId(rs.getInt("id"));
                b.setNamaBuku(rs.getString("namabuku"));
                b.setHarga(rs.getDouble("harga"));
                
                JenisBuku jb = new JenisBuku();
                jb.setId(rs.getInt("idjenisbuku"));
                jb.setNamajenisbuku(rs.getString("namajenisbuku"));
                
                b.setJenisBuku(jb);
                
                list.add(b);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public ArrayList<Buku> search(String keywords){
        keywords = "%" + keywords + "%";
        ArrayList<Buku> list = new ArrayList<>();
        
        String selectSQL = "SELECT buku.*, jenisbuku.namajenisbuku FROM buku \n" +
"INNER JOIN jenisbuku ON buku.idjenisbuku = jenisbuku.id "
                + "WHERE namabuku like ? OR namajenisbuku like ?" ;
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, keywords);
            preparedStatement.setString(2, keywords);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                Buku b = new Buku();
                
                b.setId(rs.getInt("id"));
                b.setNamaBuku(rs.getString("namabuku"));
                b.setHarga(rs.getDouble("harga"));
                
                JenisBuku jb = new JenisBuku();
                jb.setId(rs.getInt("idjenisbuku"));
                jb.setNamajenisbuku(rs.getString("namajenisbuku"));
                
                b.setJenisBuku(jb);
                
                list.add(b);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void find(){
        
        String selectSQL = "SELECT buku.*, jenisbuku.namajenisbuku FROM buku \n" +
"INNER JOIN jenisbuku ON buku.idjenisbuku = jenisbuku.id "
                + "WHERE buku.id = ?" ;
        
        this.database = new Database();
        this.connection = this.database.getConnection();
        
        try{
            PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, this.id);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                
                setId(rs.getInt("id"));
                setNamaBuku(rs.getString("namabuku"));
                setHarga(rs.getDouble("harga"));
                
                JenisBuku jb = new JenisBuku();
                jb.setId(rs.getInt("idjenisbuku"));
                jb.setNamajenisbuku(rs.getString("namajenisbuku"));
                
                setJenisBuku(jb);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JenisBuku.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaBuku() {
        return namaBuku;
    }

    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public JenisBuku getJenisBuku() {
        return jenisBuku;
    }

    public void setJenisBuku(JenisBuku jenisBuku) {
        this.jenisBuku = jenisBuku;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
