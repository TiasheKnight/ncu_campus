package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.json.*;
import ncu.im3069.demo.app.Member_Activity_Archived;
import ncu.im3069.demo.app.Member_Activity_ArchivedHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/Member_Activity_ArchivedController.do")

/**
 * The Class Member_Activity_ArchivedController.
 * Member_Activity_ArchivedController類別主要用於處理Member_Activity_Archived相關之Http請求（Request），繼承HttpServlet
 */
public class Member_Activity_ArchivedController extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** maaHelper，Member_Activity_ArchivedHelper之物件與Member_Activity_Archived相關之資料庫方法（Singleton） */
    private Member_Activity_ArchivedHelper maaHelper = Member_Activity_ArchivedHelper.getHelper();

    /** ID，Activity_ID，User_ID，用於處理Member_Activity_Archived相關之資料 */
    private int ID;
    private int activity_id;
    private String user_id;

    /** message，output，用於儲存回傳訊息與結果 */
    private String message = "";
    private String output = "";

    /**
     * 初始化資料，將message與output清空
     */
    public void Init() {
        message = "";
        output = "";
    }

    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        user_id = jsr.getParameter("user_id");

        // 檢查是否提供使用者ID
        if (user_id.isEmpty()) {
            message = "缺少使用者ID";
            responseMessage(response, 400, message);
            return;
        }

        // 根據指定的使用者ID取得該使用者收藏的活動資料
        int userIDValue = Integer.parseInt(user_id);
        JSONObject query = maaHelper.getByUserID(userIDValue);

        // 檢查是否找到相對應的資料
        if (query != null) {
            message = "活動資料取得成功";
            output = query.toString();
            responseMessage(response, 200, message);
        } else {
            message = "找不到指定的活動資料";
            responseMessage(response, 404, message);
        }
    }

    /**
     * 處理Http Method請求DELETE方法（刪除）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        int activity_id = jso.getInt("activity_id");
        int user_id = jso.getString("user_id");

        // 刪除已收藏的活動資料
        boolean result = maaHelper.delete(activity_id, user_id);

        // 建立回傳結果的JSONObject
        if (result) {
            String resp = "{\"成功!\", \"刪除活動資料\", \"response\": \"\"}";
            jsr.response(resp, response);
        }
        else{
            String resp = "{\"成功!\", \"刪除活動資料\", \"response\": \"\"}";
            jsr.response(resp, response);
        }
    }
}


