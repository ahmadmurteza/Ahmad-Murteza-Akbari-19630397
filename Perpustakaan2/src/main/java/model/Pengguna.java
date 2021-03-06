package model;

import db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pengguna {
        private int id;
        private String username;
        private String password;
        private String namaLengkap;
        private Boolean admin;

        public Database database;
        public Connection connection;
        private boolean isAdmin;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNamaLengkap() {
            return namaLengkap;
        }

        public void setNamaLengkap(String namaLengkap) {
            this.namaLengkap = namaLengkap;
        }

        public Boolean getAdmin() {
            return admin;
        }

        public void setAdmin(Boolean admin) {
            this.admin = admin;
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

        public boolean isIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }



        public Pengguna(){
            this.database = new Database();
            this.connection = database.getConnection();
        }

        public boolean login(){
            String loginSQL = "SELECT * FROM pengguna WHERE username = ? AND password = MD5(?)"; 
             try {
                PreparedStatement ps = connection.prepareStatement(loginSQL);
                ps.setString(1, this.username);
                ps.setString(2, this.password);
                ps.execute();
                ResultSet resultSet = ps.executeQuery();

                while(resultSet.next()){
                    this.id = resultSet.getInt(1);
                    this.username = resultSet.getString(2);
                    this.namaLengkap = resultSet.getString(4);
                    this.isAdmin = resultSet.getBoolean(5);
                    return true;
                }
                    System.out.println(this.id);
                    System.out.println(this.isAdmin);


                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
             return false;  
        }
}
