package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class NotificationHelper<br>
 * NotificationHelper類別（class）主要管理所有與Notification相關與資料庫之方法（method）
 * </p>
 *
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class NotificationHelper {

    /**
     * 實例化（Instantiates）一個新的（new）NotificationHelper物件<br>
     * 採用Singleton不需要透過new
     */
    private NotificationHelper() {

    }

    /** 靜態變數，儲存NotificationHelper物件 */
    private static NotificationHelper nh;

    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;

    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;

    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個NotificationHelper物件
     *
     * @return the helper 回傳NotificationHelper物件
     */
    public static NotificationHelper getHelper() {
        /** Singleton檢查是否已經有NotificationHelper物件，若無則new一個，若有則直接回傳 */
        if(nh == null) nh = new NotificationHelper();

        return nh;
    }
    
    public JSONObject getAll() {
    	Notification n = null;
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `campus`.`Notification`";

            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;

                /** 將 ResultSet 之資料取出 */
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int activity_id = rs.getInt("activity_id");
                String notification_title=rs.getString("notification_title");
                String notification_content=rs.getString("notification_content");


                /** 將每一筆商品資料產生一名新Product物件 */
                n = new Notification(id, user_id, activity_id,notification_title,notification_content);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(n.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    public JSONObject getById(String Notification_id) {
    	Notification n = null;
    	/** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `campus`.`Notification` WHERE `Notification`.`id` = ?";

            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            pres.setString(1, Notification_id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 將 ResultSet 之資料取出 */
            	row += 1;
            	int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int activity_id = rs.getInt("activity_id");
                String notification_title=rs.getString("notification_title");
                String notification_content=rs.getString("notification_content");

                /** 將每一筆商品資料產生一名新Product物件 */
                n = new Notification(id, user_id, activity_id,notification_title,notification_content);
                jsa.put(n.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
 
    public JSONObject create(Notification Notification) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `campus`.`Notification`(`id`, `user_id`, `activity_id`,`notification_title`,`notification_content`)"
                    + " VALUES(?, ?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            int ID = Notification.getID();
            int user_id = Notification.getUser_ID();
            int activity_id = Notification.getActivity_ID();
            String notification_title=Notification.getNotification_Title();
            String notification_content=Notification.getNotification_Content();

            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, ID);
            pres.setInt(2, user_id);
            pres.setInt(3, activity_id);
            pres.setString(4, notification_title);
            pres.setString(5, notification_content);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
    }
    public JSONObject deleteByID(int id) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();

            /** SQL指令 */
            String sql = "DELETE FROM `campus`.`Notification` WHERE `id` = ? LIMIT 1";

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
    }
    public JSONObject update(Notification n) {
        /** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `campus`.`Notification` SET `id` = ? ,`user_id` = ? , `activity_id` = ? WHERE `id` = ?";
            /** 取得所需之參數 */
            int  ID = n.getID();
            int  user_id = n.getUser_ID();
            int  activity_id = n.getActivity_ID();

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, ID);
            pres.setInt(2, user_id);
            pres.setInt(3, activity_id);
            /** 執行更新之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    public JSONArray createByList(long Notification_ID, List<Notification> Notification) {
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";

        for(int i=0 ; i < Notification.size() ; i++) {
        	Notification n = Notification.get(i);

            /** 取得所需之參數 */
        	int ID = n.getID();
            int user_id = n.getUser_ID();
            int activity_id = n.getActivity_ID();
            String notification_title=n.getNotification_Title();
            String notification_content=n.getNotification_Content();

            try {
                /** 取得資料庫之連線 */
                conn = DBMgr.getConnection();
                /** SQL指令 */
                String sql = "INSERT INTO `campus`.`Notification`(`id`, `user_id`, `activity_id`,`notification_title`,`notification_content`)"
                        + " VALUES(?, ?, ?)";

                /** 將參數回填至SQL指令當中 */
                pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pres.setInt(1, ID);
                pres.setInt(2, user_id);
                pres.setInt(3, activity_id);
                pres.setString(4, notification_title);
                pres.setString(5, notification_content);


                /** 執行新增之SQL指令並記錄影響之行數 */
                pres.executeUpdate();

                /** 紀錄真實執行的SQL指令，並印出 **/
                exexcute_sql = pres.toString();
                System.out.println(exexcute_sql);

                ResultSet rs = pres.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    jsa.put(id);
                }
            } catch (SQLException e) {
                /** 印出JDBC SQL指令錯誤 **/
                System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                /** 若錯誤則印出錯誤訊息 */
                e.printStackTrace();
            } finally {
                /** 關閉連線並釋放所有資料庫相關之資源 **/
                DBMgr.close(pres, conn);
            }
        }

        return jsa;
    }
    public ArrayList<Notification> getNotificationByMemberId(int member_id) {
        ArrayList<Notification> result = new ArrayList<Notification>();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        ResultSet rs = null;
        Notification n;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `campus`.`Notification` WHERE `Notification`.`id` = ?";

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, member_id);

            /** 執行新增之SQL指令並記錄影響之行數 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */

                /** 將 ResultSet 之資料取出 */
                int  ID = rs.getInt("id");
                int  user_id = rs.getInt("user_id");
                int  activity_id = rs.getInt("activity_id");
                String notification_title=rs.getString("notification_title");
                String notification_content=rs.getString("notification_content");


                /** 將參數回填至SQL指令當中 */

                /** 將每一筆會員資料產生一名新Member物件 */
                n = new Notification(ID, user_id, activity_id,notification_title,notification_content);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                result.add(n);
            }
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }

        return result;
    }
}



}
