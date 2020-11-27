package com.factory.service;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.jws.WebService;

import com.factory.service.InitService;
import com.factory.service.Service;

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

            sql = "DROP TABLE IF EXISTS balance, requests, recipes, ingredients, chocolates";
            rs = statement.executeUpdate(sql);

            sql = "CREATE TABLE chocolates ("
                    + "id SERIAL PRIMARY KEY, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "amount INT NOT NULL "
//                    + "description TEXT"
                + ")";
            rs = statement.executeUpdate(sql);

            if(rs == 0)
                output.add("chocolates table successfully created");
            else
                output.add("ERROR create table chocolates");

            // sql = "CREATE TABLE ingredients ("
            //         + "id SERIAL PRIMARY KEY, "
            //         // + "name VARCHAR(50) NOT NULL, "
            //         + "amount INT NOT NULL, "
            //         + "expire_date DATE NOT NULL "
            //         // + "PRIMARY KEY (id, name) "
            //     + ")";
            // rs = statement.executeUpdate(sql);

            // if(rs == 0)
            //     output.add("ingredients table successfully created");
            // else
            //     output.add("ERROR create table ingredients");

            // sql = "CREATE TABLE recipes ("
            //         + "chocolate_id INT NOT NULL, "
            //         + "ingredient_name INT NOT NULL, "
            //         + "amount INT NOT NULL, "
            //         + "PRIMARY KEY (chocolate_id, ingredient_name), "
            //         + "FOREIGN KEY (chocolate_id) REFERENCES chocolates(id), "
            //         + "FOREIGN KEY (ingredient_name) REFERENCES ingredients(name)"
            //     + ")";
            // rs = statement.executeUpdate(sql);

            // if(rs == 0)
            //     output.add("recipes table successfully created");
            // else
            //     output.add("ERROR create table recipes");

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

            // List<String[]> balance_data = new ArrayList<String[]>() {
            //     {
            //         add(new String[] { "1", "0"});
            //     }
            // };
            // sql = "INSERT INTO chocolates(id, amount) VALUES (?, ?)";
            // ps = conn.prepareStatement(sql);
            // for (String[] data : balance_data) {
            //     ps.setInt(1, Integer.parseInt(data[0]));
            //     ps.setInt(2, Integer.parseInt(data[1]));
            //     ps.addBatch();
            //     ps.clearParameters();
            // }
            // res = ps.executeBatch();
            // if (res.length == balance_data.size()){
            //     output.add("Balance data inserted successfully!");
            // } else {
            //     output.add("Error inserting balance data");
            // }

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

            // List<String[]> ingredients_data = new ArrayList<String[]>() {
            //     {
            //         add(new String[] { "coklat", "25", "2020-12-09" });
            //         add(new String[] { "susu", "32", "2020-12-15" });
            //         add(new String[] { "matcha", "12", "2021-2-14" });
            //         add(new String[] { "gula", "60", "2021-4-22" });
            //         add(new String[] { "kacang", "18", "2021-5-10" });
            //         add(new String[] { "berry", "9", "2020-12-25" });
            //     }
            // };

            // sql = "INSERT INTO ingredients(name, amount, expire_date) VALUES (?, ?, ?)";
            // ps = conn.prepareStatement(sql);
            // for (String[] data : ingredients_data) {
            //     ps.setString(1, data[0]);
            //     ps.setInt(2, Integer.parseInt(data[1]));
            //     ps.setDate(3, Date.valueOf(data[2]));
            //     ps.addBatch();
            //     ps.clearParameters();
            // }
            // res = ps.executeBatch();
            // if (res.length == ingredients_data.size()){
            //     output.add("Ingredients data inserted successfully!");
            // } else {
            //     output.add("Error inserting ingredients data");
            // }

            // List<String[]> recipes_data = new ArrayList<String[]>() {
            //     {
            //         add(new String[] { "1", "1", "2"});
            //         add(new String[] { "1", "4", "2"});
            //         add(new String[] { "1", "2", "2"});
            //         add(new String[] { "1", "6", "2"});
            //         add(new String[] { "2", "1", "2"});
            //         add(new String[] { "2", "4", "2"});
            //         add(new String[] { "2", "2", "1"});
            //         add(new String[] { "2", "5", "3"});
            //         add(new String[] { "3", "1", "1"});
            //         add(new String[] { "3", "4", "4"});
            //         add(new String[] { "3", "2", "5"});
            //         add(new String[] { "4", "1", "3"});
            //         add(new String[] { "4", "4", "2"});
            //         add(new String[] { "4", "2", "2"});
            //         add(new String[] { "4", "3", "3"});
            //         add(new String[] { "5", "1", "2"});
            //         add(new String[] { "5", "4", "3"});
            //         add(new String[] { "5", "2", "3"});
            //         add(new String[] { "6", "1", "5"});
            //         add(new String[] { "6", "4", "1"});
            //         add(new String[] { "7", "1", "3"});
            //         add(new String[] { "7", "4", "3"});
            //         add(new String[] { "7", "2", "4"});
            //         add(new String[] { "8", "1", "4"});
            //         add(new String[] { "8", "4", "1"});
            //         add(new String[] { "8", "2", "1"});
            //         add(new String[] { "9", "1", "1"});
            //         add(new String[] { "9", "4", "1"});
            //         add(new String[] { "9", "2", "2"});
            //         add(new String[] { "10", "1", "1"});
            //         add(new String[] { "10", "4", "3"});
            //         add(new String[] { "10", "2", "2"});
            //         add(new String[] { "11", "1", "2"});
            //         add(new String[] { "11", "4", "3"});
            //         add(new String[] { "11", "2", "3"});
            //         add(new String[] { "11", "5", "2"});
            //         add(new String[] { "12", "1", "1"});
            //         add(new String[] { "12", "4", "5"});
            //         add(new String[] { "12", "2", "2"});
            //     }
            // };
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

            // sql = "INSERT INTO recipes(chocolate_id, ingredient_name, amount) VALUES (?, ?, ?)";
            // ps = conn.prepareStatement(sql);
            // for (String[] data : recipes_data) {
            //     ps.setInt(1, Integer.parseInt(data[0]));
            //     ps.setString(2, data[1]);
            //     ps.setInt(3, Integer.parseInt(data[2]));
            //     ps.addBatch();
            //     ps.clearParameters();
            // }
            // res = ps.executeBatch();
            // if (res.length == recipes_data.size()){
            //     output.add("Recipes data inserted successfully!");
            // } else {
            //     output.add("Error inserting recipes data");
            // }

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