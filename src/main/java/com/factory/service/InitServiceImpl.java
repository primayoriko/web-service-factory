package com.factory.service;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.jws.WebService;

import com.factory.model.User;
import com.factory.service.InitService;
import com.factory.service.Service;

import org.postgresql.util.MD5Digest;

@WebService(endpointInterface = "com.factory.service.InitService")
public class InitServiceImpl extends Service implements InitService {
    @Override 
    public String[][] resetDatabase(){
        return new String[][]{
                initSchema(),
                initDummy()
            };
    }

    @Override
    public String[] initSchema(){
        try{
            initConnection();

            String sql;
            int rs;
            List<String> output = new ArrayList<>();
            Statement statement = conn.createStatement();

            sql = "DROP TABLE IF EXISTS balance, users, requests, recipes, items, stocks, chocolates";
            rs = statement.executeUpdate(sql);

            sql = "CREATE TABLE chocolates ("
                    + "id SERIAL PRIMARY KEY, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "amount INT NOT NULL "
                + ")";
            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("chocolates table successfully created");
            else
                output.add("ERROR create table chocolates");

             sql = "CREATE TABLE items ("
                     + "id SERIAL PRIMARY KEY, "
                     + "name VARCHAR(50) NOT NULL"
                 + ")";
             rs = statement.executeUpdate(sql);

             if(rs == 0)
                 output.add("items table successfully created");
             else
                 output.add("ERROR create table items");

            sql = "CREATE TABLE stocks ("
                    + "item_id SERIAL, "
                    + "amount INT NOT NULL, "
                    + "expire_date DATE NOT NULL, "
                    + "PRIMARY KEY (item_id, expire_date), "
                    + "FOREIGN KEY (item_id) REFERENCES items(id)"
                    + ")";

            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("stocks table successfully created");
            else
                output.add("ERROR create table stocks");

             sql = "CREATE TABLE recipes ("
                     + "chocolate_id INT NOT NULL, "
                     + "item_id INT NOT NULL, "
                     + "amount INT NOT NULL, "
                     + "PRIMARY KEY (chocolate_id, item_id), "
                     + "FOREIGN KEY (chocolate_id) REFERENCES chocolates(id), "
                     + "FOREIGN KEY (item_id) REFERENCES items(id)"
                 + ")";
             rs = statement.executeUpdate(sql);

             if(rs == 0)
                 output.add("recipes table successfully created");
             else
                 output.add("ERROR create table recipes");

            sql = "CREATE TABLE requests ("
                    + "id SERIAL PRIMARY KEY, "
                    + "chocolate_id INT NOT NULL, "
                    + "amount INT NOT NULL, "
                    + "status VARCHAR(10), "
                    + "dateRequested DATE NOT NULL, "
                    + "FOREIGN KEY (chocolate_id) REFERENCES chocolates(id)"
                + ")";
            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("requests table successfully created");
            else
                output.add("ERROR create table requests");

            sql = "CREATE TABLE balance ("
                    + "id SERIAL PRIMARY KEY, "
                    + "amount BIGINT NOT NULL"
                + ")";
            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("balance table successfully created");
            else
                output.add("ERROR create table balance");

            sql = "CREATE TABLE users ("
                    + "username VARCHAR(30) NOT NULL PRIMARY KEY, "
                    + "email VARCHAR(50) NOT NULL UNIQUE, "
                    + "name VARCHAR(80) NOT NULL, "
                    + "password VARCHAR(32) NOT NULL"
                + ")";
            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("users table successfully created");
            else
                output.add("ERROR create table users");

            String[] outputString = new String[output.size()];
            outputString = output.toArray(outputString);

            return outputString;
        } catch (Exception e){
            e.printStackTrace();
            return new String[]{"INTERNAL ERROR", e.getMessage()};
        } finally {
            closeConnection();
        }
    }

    @Override
    public String[] initDummy(){
        try{
            initConnection();
            
            String sql;
            int[] res;
            List<String> output = new ArrayList<>();

            List<String[]> chocolates_data = new ArrayList<String[]>() {
                {
                    add(new String[] { "1", "Dairy Milk", "13" });
                    add(new String[] { "2", "Silver Queen", "12" });
                    add(new String[] { "3", "Kinder Joy", "7" });
                    add(new String[] { "4", "Kitkat Green", "1" });
                    add(new String[] { "5", "Kitkat White", "7" });
                    add(new String[] { "6", "Kitkat Dark", "9" });
                    add(new String[] { "7", "Kitkat Hello Kitty", "5" });
                    add(new String[] { "8", "Toblerone", "6" });
                    add(new String[] { "9", "Dove", "20", });
                    add(new String[] { "10", "Alpine", "2" });
                    add(new String[] { "11", "Aice", "30"});
                    add(new String[] { "12", "Chocolatos", "45"});
                }
            };

            sql = "INSERT INTO chocolates(id, name, amount) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            for (String[] data : chocolates_data) {
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setString(2, data[1]);
                ps.setInt(3, Integer.parseInt(data[2]));
                ps.addBatch();
                ps.clearParameters();
            }
            res = ps.executeBatch();
            if (res.length == chocolates_data.size()){
                output.add("Chocolates data inserted successfully!");
            } else {
                output.add("Error inserting chocolates data");
            }

             List<String[]> item_data = new ArrayList<String[]>() {
                 {
                     add(new String[] { "1", "coklat" });
                     add(new String[] { "2", "susu" });
                     add(new String[] { "3", "matcha" });
                     add(new String[] { "4", "gula" });
                     add(new String[] { "5", "kacang" });
                     add(new String[] { "6", "berry"});
                 }
             };

             sql = "INSERT INTO items(id, name) VALUES (?, ?)";
             ps = conn.prepareStatement(sql);
             for (String[] data : item_data) {
                 ps.setInt(1, Integer.parseInt(data[0]));
                 ps.setString(2, data[1]);
                 ps.addBatch();
                 ps.clearParameters();
             }
             res = ps.executeBatch();
             if (res.length == item_data.size()){
                 output.add("Items data inserted successfully!");
             } else {
                 output.add("Error inserting items data");
             }

             List<String[]> stock_data = new ArrayList<String[]>() {
                 {
                     add(new String[] { "1", "25", "2020-12-09" });
                     add(new String[] { "2", "32", "2020-12-15" });
                     add(new String[] { "3", "12", "2021-2-14" });
                     add(new String[] { "4", "60", "2021-4-22" });
                     add(new String[] { "5", "18", "2021-5-10" });
                     add(new String[] { "6", "9", "2020-12-25" });
                 }
             };

             sql = "INSERT INTO stocks(item_id, amount, expire_date) VALUES (?, ?, ?)";
             ps = conn.prepareStatement(sql);
             for (String[] data : stock_data) {
                 ps.setInt(1, Integer.parseInt(data[0]));
                 ps.setInt(2, Integer.parseInt(data[1]));
                 ps.setDate(3, Date.valueOf(data[2]));
                 ps.addBatch();
                 ps.clearParameters();
             }
             res = ps.executeBatch();
             if (res.length == stock_data.size()){
                 output.add("Stocks data inserted successfully!");
             } else {
                 output.add("Error inserting stocks data");
             }

             List<String[]> recipes_data = new ArrayList<String[]>() {
                 {
                     add(new String[] { "1", "1", "2"});
                     add(new String[] { "1", "4", "2"});
                     add(new String[] { "1", "2", "2"});
                     add(new String[] { "1", "6", "2"});
                     add(new String[] { "2", "1", "2"});
                     add(new String[] { "2", "4", "2"});
                     add(new String[] { "2", "2", "1"});
                     add(new String[] { "2", "5", "3"});
                     add(new String[] { "3", "1", "1"});
                     add(new String[] { "3", "4", "4"});
                     add(new String[] { "3", "2", "5"});
                     add(new String[] { "4", "1", "3"});
                     add(new String[] { "4", "4", "2"});
                     add(new String[] { "4", "2", "2"});
                     add(new String[] { "4", "3", "3"});
                     add(new String[] { "5", "1", "2"});
                     add(new String[] { "5", "4", "3"});
                     add(new String[] { "5", "2", "3"});
                     add(new String[] { "6", "1", "5"});
                     add(new String[] { "6", "4", "1"});
                     add(new String[] { "7", "1", "3"});
                     add(new String[] { "7", "4", "3"});
                     add(new String[] { "7", "2", "4"});
                     add(new String[] { "8", "1", "4"});
                     add(new String[] { "8", "4", "1"});
                     add(new String[] { "8", "2", "1"});
                     add(new String[] { "9", "1", "1"});
                     add(new String[] { "9", "4", "1"});
                     add(new String[] { "9", "2", "2"});
                     add(new String[] { "10", "1", "1"});
                     add(new String[] { "10", "4", "3"});
                     add(new String[] { "10", "2", "2"});
                     add(new String[] { "11", "1", "2"});
                     add(new String[] { "11", "4", "3"});
                     add(new String[] { "11", "2", "3"});
                     add(new String[] { "11", "5", "2"});
                     add(new String[] { "12", "1", "1"});
                     add(new String[] { "12", "4", "5"});
                     add(new String[] { "12", "2", "2"});
                 }
             };
            // List<String[]> recipes_data = new ArrayList<String[]>() {
            //     {
            //         add(new String[] { "1", "coklat", "2"});
            //         add(new String[] { "1", "gula", "2"});
            //         add(new String[] { "1", "susu", "2"});
            //         add(new String[] { "1", "berry", "2"});
            //         add(new String[] { "2", "coklat", "2"});
            //         add(new String[] { "2", "gula", "2"});
            //         add(new String[] { "2", "susu", "1"});
            //         add(new String[] { "2", "kacang", "3"});
            //         add(new String[] { "3", "coklat", "1"});
            //         add(new String[] { "3", "gula", "4"});
            //         add(new String[] { "3", "susu", "5"});
            //         add(new String[] { "3", "matcha", "3"});
            //         add(new String[] { "4", "coklat", "3"});
            //         add(new String[] { "4", "gula", "2"});
            //         add(new String[] { "4", "susu", "2"});
            //         add(new String[] { "5", "coklat", "2"});
            //         add(new String[] { "5", "gula", "3"});
            //         add(new String[] { "5", "susu", "3"});
            //         add(new String[] { "6", "coklat", "5"});
            //         add(new String[] { "6", "gula", "1"});
            //         add(new String[] { "7", "coklat", "3"});
            //         add(new String[] { "7", "gula", "3"});
            //         add(new String[] { "7", "susu", "4"});
            //         add(new String[] { "8", "coklat", "4"});
            //         add(new String[] { "8", "gula", "1"});
            //         add(new String[] { "8", "susu", "1"});
            //         add(new String[] { "9", "coklat", "1"});
            //         add(new String[] { "9", "gula", "1"});
            //         add(new String[] { "9", "susu", "2"});
            //         add(new String[] { "10", "coklat", "1"});
            //         add(new String[] { "10", "gula", "3"});
            //         add(new String[] { "10", "susu", "2"});
            //         add(new String[] { "11", "coklat", "2"});
            //         add(new String[] { "11", "gula", "3"});
            //         add(new String[] { "11", "susu", "3"});
            //         add(new String[] { "11", "kacang", "2"});
            //         add(new String[] { "12", "coklat", "1"});
            //         add(new String[] { "12", "gula", "5"});
            //         add(new String[] { "12", "susu", "2"});
            //     }
            // };

             sql = "INSERT INTO recipes(chocolate_id, item_id, amount) VALUES (?, ?, ?)";
             ps = conn.prepareStatement(sql);
             for (String[] data : recipes_data) {
                 ps.setInt(1, Integer.parseInt(data[0]));
                 ps.setInt(2, Integer.parseInt(data[1]));
                 ps.setInt(3, Integer.parseInt(data[2]));
                 ps.addBatch();
                 ps.clearParameters();
             }
             res = ps.executeBatch();
             if (res.length == recipes_data.size()){
                 output.add("Recipes data inserted successfully!");
             } else {
                 output.add("Error inserting recipes data");
             }

            List<String[]> requests_data = new ArrayList<String[]>() {
                {
                    add(new String[] { "1", "3", "Waiting", "2020-11-26" });
                    add(new String[] { "2", "5", "Delivered", "2020-11-25" });
                    add(new String[] { "3", "1", "Rejected", "2020-11-26" });
                    add(new String[] { "4", "2", "Waiting", "2020-11-24" });
                    add(new String[] { "5", "6", "Delivered", "2020-11-26" });
                    add(new String[] { "6", "2", "Rejected", "2020-11-25" });
                    add(new String[] { "7", "1", "Waiting", "2020-11-26" });
                    add(new String[] { "8", "2", "Delivered", "2020-11-26" });
                    add(new String[] { "9", "8", "Rejected", "2020-11-25" });
                    add(new String[] { "10", "12", "Delivered", "2020-11-26" });
                    add(new String[] { "11", "1", "Waiting", "2020-11-26" });
                    add(new String[] { "12", "25", "Rejected", "2020-11-26" });
                    add(new String[] { "3", "3", "Waiting", "2020-11-24" });
                    add(new String[] { "6", "8", "Waiting", "2020-11-24" });
                    add(new String[] { "1", "13", "Waiting", "2020-11-25" });
                    add(new String[] { "8", "16", "Waiting", "2020-11-24" });
                    add(new String[] { "3", "7", "Waiting", "2020-11-26" });
                }
            };

            sql = "INSERT INTO requests(chocolate_id, amount, status, dateRequested) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            for (String[] data : requests_data) {
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setInt(2, Integer.parseInt(data[1]));
                ps.setString(3, data[2]);
                ps.setDate(4, Date.valueOf(data[3]));
                ps.addBatch();
                ps.clearParameters();
            }
            res = ps.executeBatch();
            if (res.length == requests_data.size()){
                output.add("Request data inserted successfully!");
            } else {
                output.add("Error inserting request data");
            }

            List<String[]> balance_data = new ArrayList<String[]>() {
                {
                    add(new String[] { "1", "2500000" });
                }
            };

            sql = "INSERT INTO balance(id, amount) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            for (String[] data : balance_data) {
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setLong(2, Long.parseLong(data[1]));
                ps.addBatch();
                ps.clearParameters();
            }
            res = ps.executeBatch();
            if (res.length == balance_data.size()){
                output.add("Balance data inserted successfully!");
            } else {
                output.add("Error inserting balance data");
            }

            List<String[]> users_data = new ArrayList<String[]>() {
                {
                    add(new String[] { "mitsuha", "mitsuha@gmail.com", "Mitsuha", "test123" });
                    add(new String[] { "takikun", "taki.20@gmail.com", "Taki-kun", "password" });
                    add(new String[] { "wwchoco", "wwbgst@gmail.com", "Willy Wonka", "abc" });
                }
            };

            sql = "INSERT INTO users(username, email, name, password) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            for (String[] data : users_data) {
                String hashed_password = User.hashPassword(data[0], data[3]);
                ps.setString(1, data[0]);
                ps.setString(2, data[1]);
                ps.setString(3, data[2]);
                ps.setString(4, hashed_password);
                ps.addBatch();
                ps.clearParameters();
            }
            res = ps.executeBatch();
            if (res.length == users_data.size()){
                output.add("Users data inserted successfully!");
            } else {
                output.add("Error inserting users data");
            }

            String[] outputString = new String[output.size()];
            outputString = output.toArray(outputString);

            return outputString;
        } catch (Exception e){
            e.printStackTrace();
            return new String[]{"INTERNAL ERROR", e.getMessage()};
        } finally {
            closeConnection();
        }
    }
}