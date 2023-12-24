package ncu.im3069.demo.app;

import java.sql.*;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;
import ncu.im3069.demo.app.Product;

public class ProductHelper {
    private ProductHelper() {

    }

    private static ProductHelper ph;
    private Connection conn = null;
    private PreparedStatement pres = null;

    public static ProductHelper getHelper() {
        /**
         * Singleton checks whether there is already a ProductHelper object, if not, it
         * creates a new one, and if there is, it returns it directly
         */
        if (ph == null)
            ph = new ProductHelper();

        return ph;
    }

    public JSONObject getAll() {
        /**
         * Create a new m variable of the Product object to record the product
         * information returned by each query
         */
        Product p = null;
        /** Used to store all retrieved products in JSONArray format */
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
            String sql = "SELECT * FROM `missa`.`products`";

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
                int product_id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String image = rs.getString("image");
                String describe = rs.getString("describe");

                /** Generate a new Product object for each product data */
                p = new Product(product_id, name, price, image, describe);
                /** Get the product data and package it into JSONsonArray */
                jsa.put(p.getData());
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
        response.put("data", jsa);

        return response;
    }

    public JSONObject getByIdList(String data) {
        /**
         * Create a new m variable of the Product object to record the product
         * information returned by each query
         */
        Product p = null;
        /** Used to store all retrieved products in JSONArray format */
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
            String[] in_para = DBMgr.stringToArray(data, ",");
            /** SQL command */
            String sql = "SELECT * FROM `missa`.`products` WHERE `products`.`id`";
            for (int i = 0; i < in_para.length; i++) {
                sql += (i == 0) ? "in (?" : ", ?";
                sql += (i == in_para.length - 1) ? ")" : "";
            }

            /**
             * Backfill the parameters into the SQL command. If there are none, you only
             * need to execute prepareStatement
             */
            pres = conn.prepareStatement(sql);
            for (int i = 0; i < in_para.length; i++) {
                pres.setString(i + 1, in_para[i]);
            }
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
                int product_id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String image = rs.getString("image");
                String describe = rs.getString("describe");

                /** Generate a new Product object for each product data */
                p = new Product(product_id, name, price, image, describe);
                /** Get the product data and package it into JSONsonArray */
                jsa.put(p.getData());
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
        response.put("data", jsa);

        return response;
    }

    public Product getById(String id) {
        /**
         * Create a new m variable of the Product object to record the product
         * information returned by each query
         */
        Product p = null;
        /** Record the actual executed SQL command */
        String exexcute_sql = "";
        /**
         * Store the results returned after JDBC retrieves the database, and move to the
         * next data in pointer mode
         */
        ResultSet rs = null;

        try {
            /** Get the connection to the database */
            conn = DBMgr.getConnection();
            /** SQL command */
            String sql = "SELECT * FROM `missa`.`products` WHERE `products`.`id` = ? LIMIT 1";

            /**
             * Backfill the parameters into the SQL command. If there are none, you only
             * need to execute prepareStatement
             */
            pres = conn.prepareStatement(sql);
            pres.setString(1, id);
            /** Execute the SQL command of the query and record the data returned */
            rs = pres.executeQuery();

            /** Record the actual executed SQL command and print it out **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** Move the pointer through the while loop to obtain each returned data */
            while (rs.next()) {
                /** Take out the data of ResultSet */
                int product_id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String image = rs.getString("image");
                String describe = rs.getString("describe");

                /** Generate a new Product object for each product data */
                p = new Product(product_id, name, price, image, describe);
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

        return p;
    }
}
