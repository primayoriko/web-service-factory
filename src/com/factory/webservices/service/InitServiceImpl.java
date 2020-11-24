package com.factory.webservices.service;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.jws.WebService;

import com.factory.webservices.service.InitService;
import com.factory.webservices.service.Service;

@WebService(endpointInterface = "com.factory.webservices.service.InitService")
public class InitServiceImpl extends Service implements InitService {
    @Override
    public String[] initDatabase(){
        try{
            initConnection();

            String sql;
            int rs;
            List<String> output = new ArrayList<>();
            Statement statement = conn.createStatement();

            sql = "DROP TABLE IF EXISTS saldo, requests, recipes, ingredients, chocolates";
            rs = statement.executeUpdate(sql);

            sql = "CREATE TABLE chocolates ("
                    + "id SERIAL PRIMARY KEY, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "amount INT NOT NULL, "
                    + "description TEXT"
                + ")";
            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("chocolates table successfully created");
            else
                output.add("ERROR create table chocolates");

            sql = "CREATE TABLE ingredients ("
                    + "id SERIAL PRIMARY KEY, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "amount INT NOT NULL, "
                    + "expire_date DATE NOT NULL"
                + ")";
            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("ingredients table successfully created");
            else
                output.add("ERROR create table ingredients");

            sql = "CREATE TABLE recipes ("
                    + "chocolate_id INT NOT NULL, "
                    + "ingredient_id INT NOT NULL, "
                    + "amount INT NOT NULL, "
                    + "PRIMARY KEY (chocolate_id, ingredient_id), "
                    + "FOREIGN KEY (chocolate_id) REFERENCES chocolates(id), "
                    + "FOREIGN KEY (ingredient_id) REFERENCES ingredients(id)"
                + ")";
            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("recipes table successfully created");
            else
                output.add("ERROR create table recipes");

            sql = "CREATE TABLE requests ("
                    + "chocolate_id INT NOT NULL, "
                    + "amount INT NOT NULL, "
                    + "status VARCHAR(10), "
                    + "FOREIGN KEY (chocolate_id) REFERENCES chocolates(id)"
                + ")";
            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("requests table successfully created");
            else
                output.add("ERROR create table requests");

            sql = "CREATE TABLE saldo ("
                    + "id SERIAL PRIMARY KEY, "
                    + "saldo BIGINT NOT NULL"
                + ")";
            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("saldo table successfully created");
            else
                output.add("ERROR create table saldo");

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
                    add(new String[] { "1", "Dairy Milk", "13", "ntap" });
                    add(new String[] { "2", "Silver Queen", "12", "enak bingits" });
                    add(new String[] { "3", "Kinder Joy", "7", "enak pisan euy" });
                    add(new String[] { "4", "Kitkat Green", "1", "booming banget dulu sampe PO ke jepun" });
                    add(new String[] { "5", "Kitkat White", "7", "putih bersih merona" });
                    add(new String[] { "6", "Kitkat Dark", "9", "hitam pekat kayak tubes" });
                    add(new String[] { "7", "Kitkat Hello Kitty", "5", "uwu banget uwuwuwu" });
                    add(new String[] { "8", "Toblerone", "6", "coklat khas dragonspine" });
                    add(new String[] { "9", "Dove", "20", "di borma murah wgwg" });
                    add(new String[] { "10", "Alpine", "2", "ini agak mahal dari dove tapi enak sih" });
                    add(new String[] { "11", "Aice", "30", "BEST BANGET ENAK PARAH MURAH" });
                    add(new String[] { "12", "Chocolatos", "45", "coklat bocil" });
                }
            };

            sql = "INSERT INTO chocolates(id, name, amount, description) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            for (String[] data : chocolates_data) {
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setString(2, data[1]);
                ps.setInt(3, Integer.parseInt(data[2]));
                ps.setString(4, data[3]);
                ps.addBatch();
                ps.clearParameters();
            }
            res = ps.executeBatch();
            if (res.length == chocolates_data.size()){
                output.add("Chocolates data inserted successfully!");
            } else {
                output.add("Error inserting chocolates data");
            }

            List<String[]> ingredients_data = new ArrayList<String[]>() {
                {
                    add(new String[] { "1", "coklat", "25", "2020-12-09" });
                    add(new String[] { "2", "susu", "32", "2020-12-15" });
                    add(new String[] { "3", "matcha", "12", "2021-2-14" });
                    add(new String[] { "4", "gula", "60", "2021-4-22" });
                    add(new String[] { "5", "kacang", "18", "2021-5-10" });
                    add(new String[] { "6", "berry", "9", "2020-12-25" });
                }
            };

            sql = "INSERT INTO ingredients(id, name, amount, expire_date) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            for (String[] data : ingredients_data) {
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setString(2, data[1]);
                ps.setInt(3, Integer.parseInt(data[2]));
                ps.setDate(4, Date.valueOf(data[3]));
                ps.addBatch();
                ps.clearParameters();
            }
            res = ps.executeBatch();
            if (res.length == ingredients_data.size()){
                output.add("Ingredients data inserted successfully!");
            } else {
                output.add("Error inserting ingredients data");
            }

            List<String[]> recipes_data = new ArrayList<String[]>() {
                {
                    add(new String[] { "1", "1", "2"});
                    add(new String[] { "1", "4", "2"});
                    add(new String[] { "1", "2", "2"});
                    add(new String[] { "2", "1", "2"});
                    add(new String[] { "2", "4", "2"});
                    add(new String[] { "2", "2", "1"});
                    add(new String[] { "3", "1", "1"});
                    add(new String[] { "3", "4", "4"});
                    add(new String[] { "3", "2", "5"});
                    add(new String[] { "4", "1", "3"});
                    add(new String[] { "4", "4", "2"});
                    add(new String[] { "4", "2", "2"});
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
                    add(new String[] { "12", "1", "1"});
                    add(new String[] { "12", "4", "5"});
                    add(new String[] { "12", "2", "2"});
                }
            };

            sql = "INSERT INTO recipes(chocolate_id, ingredient_id, amount) VALUES (?, ?, ?)";
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
                    add(new String[] { "1", "3", "Waiting" });
                    add(new String[] { "2", "5", "Approved" });
                    add(new String[] { "3", "1", "Rejected" });
                    add(new String[] { "4", "2", "Waiting" });
                    add(new String[] { "5", "6", "Approved" });
                    add(new String[] { "6", "2", "Rejected" });
                    add(new String[] { "7", "1", "Waiting" });
                    add(new String[] { "8", "2", "Approved" });
                    add(new String[] { "9", "8", "Rejected" });
                    add(new String[] { "10", "12", "Approved" });
                    add(new String[] { "11", "1", "Waiting" });
                    add(new String[] { "12", "25", "Rejected" });
                    add(new String[] { "3", "3", "Waiting" });
                    add(new String[] { "6", "8", "Waiting" });
                    add(new String[] { "1", "13", "Waiting" });
                    add(new String[] { "8", "16", "Waiting" });
                    add(new String[] { "3", "7", "Waiting" });
                }
            };

            sql = "INSERT INTO requests(chocolate_id, amount, status) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            for (String[] data : requests_data) {
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setInt(2, Integer.parseInt(data[1]));
                ps.setString(3, data[2]);
                ps.addBatch();
                ps.clearParameters();
            }
            res = ps.executeBatch();
            if (res.length == requests_data.size()){
                output.add("Request data inserted successfully!");
            } else {
                output.add("Error inserting request data");
            }

            List<String[]> saldo_data = new ArrayList<String[]>() {
                {
                    add(new String[] { "1", "2500000" });
                }
            };

            sql = "INSERT INTO saldo(id, saldo) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            for (String[] data : saldo_data) {
                ps.setInt(1, Integer.parseInt(data[0]));
                ps.setLong(2, Long.parseLong(data[1]));
                ps.addBatch();
                ps.clearParameters();
            }
            res = ps.executeBatch();
            if (res.length == saldo_data.size()){
                output.add("Saldo data inserted successfully!");
            } else {
                output.add("Error inserting saldo data");
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