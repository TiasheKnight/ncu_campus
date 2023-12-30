package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;

import ncu.im3069.demo.util.DBMgr;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class ActivityHelper<br>
 * ActivityHelper類別（class）主要管理所有與Activity相關與資料庫之方法（method）
 * </p>
 *
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class ActivityHelper {

    /**
     * 實例化（Instantiates）一個新的（new）ActivityHelper物件<br>
     * 採用Singleton不需要透過new
     */
    private ActivityHelper() {

    }

    /** 靜態變數，儲存ActivityHelper物件 */
    private static ActivityHelper ah;

    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;

    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;

    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個ActivityHelper物件
     *
     * @return the helper 回傳ActivityHelper物件
     */
    public static ActivityHelper getHelper() {
        /** Singleton檢查是否已經有ActivityHelper物件，若無則new一個，若有則直接回傳 */
        if(ah == null) ah = new ActivityHelper();

        return ah;
    }

    /**
     * 透過活動編號（ID）刪除活動
     *
     * @param id 活動編號
     * @return the JSONObject 回傳SQL執行結果
     */
    public JSONObject deleteByID(int id) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long sys_start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();

            /** SQL指令 */
            String sql = "DELETE FROM `campus`.`activities` WHERE `id` = ? LIMIT 1";

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
        long duration = (end_time - sys_start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
    }

    /**
     * 取回所有活動資料
     *
     * @return the JSONObject 回傳SQL執行結果與自資料庫取回之所有資料
     */
    public JSONObject getAll() {
        /** 新建一個 Activity 物件之 a 變數，用於紀錄每一位查詢回之活動資料 */
        Activity a = null;
        /** 用於儲存所有檢索回之活動，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long sys_start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `campus`.`activities`";

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
                int activity_id = rs.getInt("id");
                String status = rs.getString("activity_publish_type");
                String name = rs.getString("activity_title");
                String type = rs.getString("activity_type");
                String place = rs.getString("activity_location");
                int holder_id = rs.getInt("activity_publisher_id");
                int max_participant = rs.getInt("maximun_participant");
                int min_participant = rs.getInt("minimum_participant");
                String start_date = rs.getString("start_date");
                String start_time = rs.getString("start_time");
                String end_date = rs.getString("end_date");
                String end_time = rs.getString("end_time");
                String detail = rs.getString("activity_detail");
                int activity_participant = rs.getInt("participant_number");

                /** 將每一筆活動資料產生一名新Activity物件 */
                a = new Activity(activity_id, status, name, type, place, holder_id, max_participant, min_participant, start_date, start_time,
                                end_date, end_time, detail, activity_participant);
                /** 取出該名活動之資料並封裝至 JSONsonArray 內 */
                jsa.put(a.getData());
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
        long duration = (end_time - sys_start_time);

        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }

    /**
     * 透過活動編號（ID）取得活動資料
     *
     * @param id 活動編號
     * @return the JSON object 回傳SQL執行結果與該活動編號之活動資料
     */
    public JSONObject getByID(int id) {
        /** 新建一個 Activity 物件之 a 變數，用於紀錄每一位查詢回之活動資料 */
        Activity a = null;
        /** 用於儲存所有檢索回之活動，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long sys_start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `campus`.`activities` WHERE `id` = ?";

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該活動編號之資料，因此其實可以不用使用 while 迴圈 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;

                /** 將 ResultSet 之資料取出 */
                int activity_id = rs.getInt("id");
                String status = rs.getString("activity_publish_type");
                String name = rs.getString("activity_title");
                String type = rs.getString("activity_type");
                String place = rs.getString("activity_location");
                int holder_id = rs.getInt("activity_publisher_id");
                int max_participant = rs.getInt("maximun_participant");
                int min_participant = rs.getInt("minimum_participant");
                String start_date = rs.getString("start_date");
                String start_time = rs.getString("start_time");
                String end_date = rs.getString("end_date");
                String end_time = rs.getString("end_time");
                String detail = rs.getString("activity_detail");
                int activity_participant = rs.getInt("activity_particpant");

                /** 將每一筆活動資料產生一名新Activity物件 */
                a = new Activity(activity_id, status, name, type, place, holder_id, max_participant, min_participant, start_date, start_time, end_date,
                                end_time, detail, activity_participant);
                /** 取出該名活動之資料並封裝至 JSONsonArray 內 */
                jsa.put(a.getData());
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
        long duration = (end_time - sys_start_time);

        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }

    /**
     * 建立該項活動至資料庫
     *
     * @param a 一項活動之Activity物件
     * @return the JSON object 回傳SQL指令執行之結果
     */
    public JSONObject create(Activity a) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long sys_start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `campus`.`activities`(`activity_publish_type`, `activity_title`, `activity_type`, `activity_location`, `activity_publisher_id`, `maximum_participant`, `minimum_participant`, `start_date`, `start_time`, `end_date`, `end_time`, `activity_detail`, `activity_participant`)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            /** 取得所需之參數 */
            String status = a.getActivity_Status();
            String name = a.getActivity_Name();
            String type = a.getActivity_Type();
            String place = a.getActivity_Place();
            int holder_id = a.getActivity_Publisher_ID();
            int max_participant = a.getMaximum_Participant();
            int min_participant = a.getMinimum_Participant();
            String start_date = a.getStart_Date();
            String start_time = a.getStart_Time();
            String end_date = a.getEnd_Date();
            String end_time = a.getEnd_Time();
            String detail = a.getActivity_Detail();

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, status);
            pres.setString(2, name);
            pres.setString(3, type);
            pres.setString(4, place);
            pres.setInt(5, holder_id);
            pres.setInt(6, max_participant);
            pres.setInt(7, min_participant);
            pres.setString(8, start_date);
            pres.setString(9, start_time);
            pres.setString(10, end_date);
            pres.setString(11, end_time);
            pres.setString(12, detail);

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
        long duration = (end_time - sys_start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
    }

    /**
     * 更新一項活動之活動資料
     *
     * @param a 一項活動之Activity物件
     * @return the JSONObject 回傳SQL指令執行結果與執行之資料
     */
    public JSONObject update(Activity a) {
        /** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long sys_start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `campus`.`activities` SET `activity_publish_type` = ? ,`activity_title` = ? , `activity_type` = ?, `activity_location` = ?, `activity_publisher_id` = ?, `maximum_participant` = ?, `minimum_participant` = ?, `start_date` = ?, `start_time` = ?, `end_date` = ?, `end_time` = ?, `activity_detail` = ?, `participant_number` = ? ,WHERE `id` = ?";

            /** 取得所需之參數 */
            int activity_id = a.getID();
            String status = a.getActivity_Status();
            String name = a.getActivity_Name();
            String type = a.getActivity_Type();
            String place = a.getActivity_Place();
            int holder_id = a.getActivity_Publisher_ID();
            int max_participant = a.getMaximum_Participant();
            int min_participant = a.getMinimum_Participant();
            String start_date = a.getStart_Date();
            String start_time = a.getStart_Time();
            String end_date = a.getEnd_Date();
            String end_time = a.getEnd_Time();
            String detail = a.getActivity_Detail();
            int participant_number = a.getActivity_Participant();

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, status);
            pres.setString(2, name);
            pres.setString(3, type);
            pres.setString(4, place);
            pres.setInt(5, holder_id);
            pres.setInt(6, max_participant);
            pres.setInt(7, min_participant);
            pres.setString(8, start_date);
            pres.setString(9, start_time);
            pres.setString(10, end_date);
            pres.setString(11, end_time);
            pres.setString(12, detail);
            pres.setInt(13, participant_number);

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
        long duration = (end_time - sys_start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }

}
