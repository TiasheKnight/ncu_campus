package ncu.im3069.demo.app;

import java.sql.*;
import java.util.*;
import org.json.*;

import ncu.im3069.demo.util.DBMgr;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Member_ActivityHelper<br>
 * Member_ActivityHelper類別（class）主要管理所有與Member_Activity相關與資料庫之方法（method）
 * </p>
 *
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Member_ActivityHelper {

    /**
     * 實例化（Instantiates）一個新的（new）Member_ActivityHelper物件<br>
     * 採用Singleton不需要透過new
     */
    private Member_ActivityHelper() {

    }

    /** 靜態變數，儲存Member_ActivityHelper物件 */
    private static Member_ActivityHelper mah;

    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;

    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;

    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個Member_ActivityHelper物件
     *
     * @return the helper 回傳Member_ActivityHelper物件
     */
    public static Member_ActivityHelper getHelper() {
        /** Singleton檢查是否已經有Member_ActivityHelper物件，若無則new一個，若有則直接回傳 */
        if(mah == null) mah = new Member_ActivityHelper();

        return mah;
    }

    public JSONArray createByList(long activity_id, List<Member_Activity> memberactivity) {
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";

        for(int i=0 ; i < memberactivity.size() ; i++) {
            Member_Activity ma = memberactivity.get(i);

            /** 取得所需之參數 */
            int ma_id = ma.getID();
            int user_id = ma.getUser_ID();
            int activity_id1 = ma.getActivity_ID();

            try {
                /** 取得資料庫之連線 */
                conn = DBMgr.getConnection();
                /** SQL指令 */
                String sql = "INSERT INTO `campus`.`member_activity`(`id`, `user_id`, `activty_id`)"
                        + " VALUES(?, ?, ?)";

                /** 將參數回填至SQL指令當中 */
                pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pres.setInt(1, ma_id);
                pres.setInt(2, user_id);
                pres.setInt(3, activity_id1);

                /** 執行新增之SQL指令並記錄影響之行數 */
                pres.executeUpdate();

                /** 紀錄真實執行的SQL指令，並印出 **/
                exexcute_sql = pres.toString();
                System.out.println(exexcute_sql);

                ResultSet rs = pres.getGeneratedKeys();

                if (rs.next()) {
                    long id = rs.getLong(1);
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

    public ArrayList<Member_Activity> getMemberActivityBy(int member_id) {
        ArrayList<Member_Activity> result = new ArrayList<Member_Activity>();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        ResultSet rs = null;
        Member_Activity ma;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `campus`.`member_activity` WHERE `member_activity`.`user_id` = ?";

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
                int ma_id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int activity_id = rs.getInt("activity_id");

                /** 將每一筆會員資料產生一名新Member_Activity物件 */
                ma = new Member_Activity(ma_id, user_id, activity_id);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                result.add(ma);
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
    
    public JSONObject getAll() {
        /** 新建一個 Activity 物件之 a 變數，用於紀錄每一位查詢回之活動資料 */
        Member_Activity ma = null;
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
            String sql = "SELECT * FROM `campus`.`member_activity`";

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
                int member_activity_id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int activity_id = rs.getInt("activity_id");


                /** 將每一筆活動資料產生一名新Activity物件 */
                ma = new Member_Activity (member_activity_id,user_id,activity_id);
                /** 取出該名活動之資料並封裝至 JSONsonArray 內 */
                jsa.put(ma.getData());
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


}