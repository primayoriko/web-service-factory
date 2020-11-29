package com.factory.service;

import com.factory.model.Recipe;
import com.factory.model.Stock;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.SOAPFaultException;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Statement;

@WebService(endpointInterface = "com.factory.service.StockService")
@HandlerChain(file = "handlers.xml")
public class StockServiceImpl extends Service implements StockService {
    @Override

    public Stock[] getStocks() {
        try {
            initConnection();

            ps = conn.prepareStatement(
                    "SELECT * FROM stocks as s " + "LEFT JOIN items as i ON s.item_id = i.id ORDER BY i.id");

            rs = ps.executeQuery();

            List<Stock> stocks = new ArrayList<Stock>();

            while (rs.next()) {
                stocks.add(new Stock(rs));
            }

            Stock[] response = new Stock[stocks.size()];

            for (int i = 0; i < stocks.size(); i++) {
                response[i] = stocks.get(i);
            }

            return response;
        } catch (Exception err) {
            err.printStackTrace();
            throw generateSoapFaultException(500, "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }

    public String addStock(String name, String expire_date, Integer amount){
        if (name == null || expire_date == null) {
            throw generateSoapFaultException(400,
                    "Client Request Error: parameter 'id' or 'expire_date' is not specified", "Client");
        }
        if (amount == null) {
            amount = 0;
        }
        try{
            initConnection();

            ps = conn.prepareStatement(
                    "SELECT * FROM stocks as s " + "RIGHT JOIN items as i ON s.item_id = i.id AND i.name='" + name + "' ORDER BY i.id");
            rs = ps.executeQuery();
            System.out.println("abcdefg");

            if(rs.isBeforeFirst()){ 
                rs.next();
                Stock stock = new Stock(rs);
                System.out.println(stock.getAmount());
                System.out.println(stock.getName());
                Integer id = rs.getInt("item_id");
                if (stock.isExistInStocks()) {
                    if (stock.isAmountValid(amount)) {
                        stock.changeAmount(amount);
                    } else {
                        throw generateSoapFaultException(404, 
                                "Client Request Error: Amount is invalid!", "Client");
                    }
    
                    Statement statement = conn.createStatement();
                    System.out.println(stock.getAmount());
                    statement.executeUpdate("UPDATE stocks SET amount=" + stock.getAmount().toString() + " WHERE item_id=" + id.toString() + " and expire_date='" + expire_date.toString() + "'");
                    return String.format("Stock for id=%d and expire_date=%s has been modified to %d", id, expire_date, stock.getAmount());
                } else {
                    ps = conn.prepareStatement("INSERT INTO stocks (expire_date, amount) VALUES (?, ?)");

                    ps.setDate(1, Date.valueOf(expire_date));
                    ps.setInt(2, amount);

                    ps.executeUpdate();

                    return String.format("Stock for id=%d and expire_date=%s has been modified to %d", id, expire_date, stock.getAmount());
                }
            } else {
                ps.clearParameters();
                ps = conn.prepareStatement("INSERT INTO stocks (expire_date, amount) VALUES (?, ?)");

                ps.setDate(1, Date.valueOf(expire_date));
                ps.setInt(2, amount);

                ps.addBatch();

                ps.clearParameters();

                ps = conn.prepareStatement("INSERT INTO items (name) VALUES ('?')");

                ps.setString(1, name);

                ps.addBatch();
                ps.clearParameters();
                
                ps.executeBatch();

                return String.format("Stock with expire_date=%s and amount=%d has been created", expire_date, amount);
            }
        } catch (SOAPFaultException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw generateSoapFaultException(500, 
                    "Internal Server Error. Please try again later.", "Server");
        } finally {
            closeConnection();
        }
    }
}
