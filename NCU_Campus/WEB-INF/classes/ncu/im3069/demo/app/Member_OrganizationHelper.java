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
 * The Class Member_OrganizationHelper<br>
 * Member_ActivityHelper類別（class）主要管理所有與Member_Organization相關與資料庫之方法（method）
 * </p>
 *
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Member_OrganizationHelper {

    /**
     * 實例化（Instantiates）一個新的（new）Member_OrganizationHelper物件<br>
     * 採用Singleton不需要透過new
     */
    private Member_OrganizationHelper() {

    }

    /** 靜態變數，儲存Member_OrganizationHelper物件 */
    private static Member_OrganizationHelper moh;

    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;

    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;

    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個Member_OrganizationHelper物件
     *
     * @return the helper 回傳Member_OrganizationHelper物件
     */
    public static Member_OrganizationHelper getHelper() {
        /** Singleton檢查是否已經有Member_OrganizationHelper物件，若無則new一個，若有則直接回傳 */
        if(moh == null) moh = new Member_OrganizationHelper();

        return moh;
    }

    public JSONArray createByList(long organization_id, List<Member_Organization> memberorganization) {
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";

        for(int i=0 ; i < memberorganization.size() ; i++) {
            Member_Organization mo = memberorganization.get(i);

            /** 取得所需之參數 */
            int mo_id = mo.getID();
            int user_id = mo.getUser_ID();
            int o_id = mo.getOrganization_ID();

            try {
                /** 取得資料庫之連線 */
                conn = DBMgr.getConnection();
                /** SQL指令 */
                String sql = "INSERT INTO `campus`.`member_organization`(`id`, `user_id`, `organization_id`)"
                        + " VALUES(?, ?, ?)";

                /** 將參數回填至SQL指令當中 */
                pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pres.setInt(1, mo_id);
                pres.setInt(2, user_id);
                pres.setInt(3, o_id);

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

    public ArrayList<Member_Organization> getMemberOrganizationbyId(int id) {
        ArrayList<Member_Organization> result = new ArrayList<Member_Organization>();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        ResultSet rs = null;
        Member_Organization mo;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `campus`.`organization` LEFT JOIN `campus`.`member_organization` ON `member_organization`.`organization_id`=`organization`.`id` WHERE `member_organization`.`user_id` = ?";

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);

            /** 執行新增之SQL指令並記錄影響之行數 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */

                /** 將 ResultSet 之資料取出 */
                int mo_id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int organization_id = rs.getInt("organization_id");
                String authority = rs.getString("authority");

                /** 將每一筆會員資料產生一名新Member_Organization物件 */
                mo = new  Member_Organization(mo_id, user_id, organization_id, authority);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                result.add(mo);
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
    public JSONObject update(Member_Organization m) {
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
            String sql = "Update `campus`.`Member-Organization` SET `id` = ?, `user_id` = ?, `organization_id` = ?, `authority` = ?"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?)";

            /** 取得所需之參數 */
            int id = m.getID();
            int user_id = m.getUser_ID();
            int organization_id = m.getOrganization_ID();
            String authority = m.getAuthority();

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            pres.setInt(2, user_id);
            pres.setInt(3, organization_id);
            pres.setString(4, authority);

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


}
