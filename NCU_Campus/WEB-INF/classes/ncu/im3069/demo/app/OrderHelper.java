package ncu.im3069.demo.app;

import java.sql.*;
import java.util.*;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;

public class OrderHelper {

    private static OrderHelper oh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    private OrderItemHelper oph = OrderItemHelper.getHelper();

    private OrderHelper() {
    }

    public static OrderHelper getHelper() {
        if (oh == null)
            oh = new OrderHelper();

        return oh;
    }

    public JSONObject create(Order order) {
        /** Record the actual executed SQL command */
        String exexcute_sql = "";
        long id = -1;
        JSONArray opa = new JSONArray();

        try {
            /** Get the connection to the database */
            conn = DBMgr.getConnection();
            /** SQL command */
            String sql = "INSERT INTO `missa`.`orders`(`last_name`, `first_name`, `email`, `address`, `phone`, `create`, `modify`)"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?)";

            /** Get the required parameters */
            String first_name = order.getFirstName();
            String last_name = order.getLastName();
            String email = order.getEmail();
            String address = order.getAddress();
            String phone = order.getPhone();
            Timestamp create = order.getCreateTime();
            Timestamp modify = order.getModifyTime();

            /** Backfill the parameters into the SQL command */
            pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pres.setString(1, last_name);
            pres.setString(2, first_name);
            pres.setString(3, email);
            pres.setString(4, address);
            pres.setString(5, phone);
            pres.setTimestamp(6, create);
            pres.setTimestamp(7, modify);

            /** Execute the new SQL command and record the number of affected rows */
            pres.executeUpdate();

            /** Record the actual executed SQL command and print it out **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            ResultSet rs = pres.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getLong(1);
                ArrayList<OrderItem> opd = order.getOrderProduct();
                opa = oph.createByList(id, opd);
            }
        } catch (SQLException e) {
            /** Print JDBC SQL command errors **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** If error occurs, print error message */
            e.printStackTrace();
        } finally {
            /** Close the connection and release all database-related resources **/
            DBMgr.close(pres, conn);
        }

        /**
         * Encapsulate the SQL command, time spent and number of affected rows into
         * JSONObject and return
         */
        JSONObject response = new JSONObject();
        response.put("order_id", id);
        response.put("order_product_id", opa);

        return response;
    }

    public JSONObject getAll() {
        Order o = null;
        JSONArray jsa = new JSONArray();
        /** Record the actual executed SQL command */
        String exexcute_sql = "";
        /** Record program start execution time */
        long start_time = System.nanoTime();
        /** Record the total number of SQL rows */
        int row = 0;
        /**
         * Store the results returned after JDBC retrieves the database, and move to the
         * next data in pointer mode
         */
        ResultSet rs = null;

        try {
            /** Get the connection to the database */
            conn = DBMgr.getConnection();
            /** SQL command */
            String sql = "SELECT * FROM `missa`.`orders`";

            /**
             * Backfill the parameters into the SQL command. If there are none, you only
             * need to execute prepareStatement
             */
            pres = conn.prepareStatement(sql);
            /** Execute the SQL command of the query and record the data returned */
            rs = pres.executeQuery();

            /** Record the actual executed SQL command and print it out **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** Move the pointer through the while loop to obtain each returned data */
            while (rs.next()) {
                /** Each time the loop is executed, it means there is a piece of data */
                row += 1;

                /** Take out the data of ResultSet */
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Timestamp create = rs.getTimestamp("create");
                Timestamp modify = rs.getTimestamp("modify");

                /** Generate a new Product object for each product data */
                o = new Order(id, first_name, last_name, email, address, phone, create, modify);
                /** Get the product data and package it into JSONsonArray */
                jsa.put(o.getOrderAllInfo());
            }

        } catch (SQLException e) {
            /** Print JDBC SQL command errors **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** If error occurs, print error message */
            e.printStackTrace();
        } finally {
            /**
             * Close the connection and release all database phases
             * Guanzhi Resources
             **/
            DBMgr.close(rs, pres, conn);
        }

        /** Record program end execution time */
        long end_time = System.nanoTime();
        /** Record program execution time */
        long duration = (end_time - start_time);

        /**
         * Encapsulate the JSONArray of SQL commands, time spent, number of affected
         * rows and all member data into JSONObject and return
         */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }

    public JSONObject getById(String order_id) {
        JSONObject data = new JSONObject();
        Order o = null;
        /** Record the actual executed SQL command */
        String exexcute_sql = "";
        /** Record program start execution time */
        long start_time = System.nanoTime();
        /** Record the total number of SQL rows */
        int row = 0;
        /**
         * Store the results returned after JDBC retrieves the database, and move to the
         * next data in pointer mode
         */
        ResultSet rs = null;

        try {
            /** Get the connection to the database */
            conn = DBMgr.getConnection();
            /** SQL command */
            String sql = "SELECT * FROM `missa`.`orders` WHERE `orders`.`id` = ?";

            /**
             * Backfill the parameters into the SQL command. If there are none, you only
             * need to execute prepareStatement
             */
            pres = conn.prepareStatement(sql);
            pres.setString(1, order_id);
            /** Execute the SQL command of the query and record the data returned */
            rs = pres.executeQuery();

            /** Record the actual executed SQL command and print it out **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** Move the pointer through the while loop to obtain each returned data */
            while (rs.next()) {
                /** Each time the loop is executed, it means there is a piece of data */
                row += 1;

                /** Take out the data of ResultSet */
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Timestamp create = rs.getTimestamp("create");
                Timestamp modify = rs.getTimestamp("modify");

                /** Generate a new Product object for each product data */
                o = new Order(id, first_name, last_name, email, address, phone, create, modify);
                /** Get the product data and package it into JSONsonArray */
                data = o.getOrderAllInfo();
            }

        } catch (SQLException e) {
            /** Print JDBC SQL command errors **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** If error occurs, print error message */
            e.printStackTrace();
        } finally {
            /** Close the connection and release all database-related resources **/
            DBMgr.close(rs, pres, conn);
        }

        /** Record program end execution time */
        long end_time = System.nanoTime();
        /** Record program execution time */
        long duration = (end_time - start_time);

        /**
         * Encapsulate the JSONArray of SQL commands, time spent, number of affected
         * rows and all member data into JSONObject and return
         */

        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", data);

        return response;
    }
}
