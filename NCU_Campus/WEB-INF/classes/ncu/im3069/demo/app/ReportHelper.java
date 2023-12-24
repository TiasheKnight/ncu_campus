package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;

import ncu.im3069.demo.util.DBMgr;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class ReportHelper<br>
 * ReportHelper類別（class）主要管理所有與Report相關與資料庫之方法（method）
 * </p>
 *
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class ReportHelper {

    /**
     * 實例化（Instantiates）一個新的（new）ReportHelper物件<br>
     * 採用Singleton不需要透過new
     */
    private ReportHelper() {

    }

    /** 靜態變數，儲存ReportHelper物件 */
    private static ReportHelper rh;

    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;

    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;

    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個ReportHelper物件
     *
     * @return the helper 回傳ReportHelper物件
     */
    public static ReportHelper getHelper() {
        /** Singleton檢查是否已經有ReportHelper物件，若無則new一個，若有則直接回傳 */
        if(rh == null) rh = new ReportHelper();

        return rh;
    }



}
