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
 * The Class Member_Activity_ArchivedHelper<br>
 * Member_Activity_ArchivedHelper類別（class）主要管理所有與Item相關與資料庫之方法（method）
 * </p>
 *
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Member_Activity_ArchivedHelper {

    /**
     * 實例化（Instantiates）一個新的（new）Member_Activity_ArchivedHelper物件<br>
     * 採用Singleton不需要透過new
     */
    private Member_Activity_ArchivedHelper() {

    }

    /** 靜態變數，儲存Member_Activity_ArchivedHelper物件 */
    private static Member_Activity_ArchivedHelper maah;

    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;

    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;

    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個Member_Activity_ArchivedHelper物件
     *
     * @return the helper 回傳Member_Activity_ArchivedHelper物件
     */
    public static Member_Activity_ArchivedHelper getHelper() {
        /** Singleton檢查是否已經有Member_Activity_ArchivedHelper物件，若無則new一個，若有則直接回傳 */
        if(maah == null) maah = new Member_Activity_ArchivedHelper();

        return maah;
    }

    public JSONArray createByList(long activity_id, List<Member_Activity_Archived> memberactivityarchived) {
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";

        for(int i=0 ; i < memberactivityarchived.size() ; i++) {
            Member_Activity_Archived maa = memberactivityarchived.get(i);

            /** 取得所需之參數 */
            int maa_id = maa.getID();
            int activity_id = maa.getActivity_ID();
            String user_id = maa.getUser_ID();

            try {
                /** 取得資料庫之連線 */
                conn = DBMgr.getConnection();
                /** SQL指令 */
                String sql = "INSERT INTO `campus`.`member_activty_archived`(`id`, `activity_id`, `user_id`)"
                        + " VALUES(?, ?, ?)";

                /** 將參數回填至SQL指令當中 */
                pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pres.setInt(1, maa_id);
                pres.setInt(2, activity_id);
                pres.setString(3, user_id);

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

    public ArrayList<Member_Activity_Archived> getMemberActivityArchived(int member_id) {
        ArrayList<Member_Activity_Archived> result = new ArrayList<Member_Activity_Archived>();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        ResultSet rs = null;
        Member_Activity_Archived maa;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `campus`.`member_activity_archived` WHERE `member_activity_archived`.`user_id` = ?";

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
                String user_id = rs.getString("user_id");
                int activity_id = rs.getInt("activity_id");

                /** 將每一筆會員資料產生一名新Member_Activity_Archived物件 */
                maa = new Member_Activity_Archived(activity_id, user_id);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                result.add(maa);
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
