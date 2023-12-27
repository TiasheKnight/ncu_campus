package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
            String sql = "SELECT * FROM `campus`.`Organization` LEFT JOIN `campus`.`Member-Organization` ON `Member-Organization`.`organization_id`=`Organization`.`id` WHERE `Member-Organization`.`user_id` = ?";
            
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
                int organization_id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                String organization_name = rs.getString("organization_name");
                String organization_detail = rs.getString("organization_detail");
                
                /** 將每一筆會員資料產生一名新Member物件 */
                mo = new  Member_Organization(user_id, organization_id, organization_name, organization_detail);
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

}
