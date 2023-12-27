package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Comment;
import ncu.im3069.demo.app.CommentHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/CommentController.do")
/**
 * The Class CommentController.
 * CommentController類別主要用於處理Comment相關之Http請求（Request），繼承HttpServlet
 */
public class CommentController extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** ch，CommentHelper之物件與Comment相關之資料庫方法（Singleton） */
    private CommentHelper ch = CommentHelper.getHelper();


    private int id;
    private int user_id;
    private int activity_id;
    private String comment;
    private int time;

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
        Activity_ID = jsr.getInt("activity_id");

        // 根據指定的活動ID取得該活動的留言資料
        JSONObject query = ch.getByActivityID(Activity_ID);

        // 檢查是否找到相對應的資料
        if (query != null) {
            message = "留言資料取得成功";
            output = query.toString();
            responseMessage(response, 200, message);
        } else {
            message = "找不到指定活動的留言資料";
            responseMessage(response, 404, message);
        }
    }

    /**
     * 處理Http Method請求POST方法（新增）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        Activity_ID = jso.getInt("activity_id");
        User_ID = jso.getInt("user_id");
        Comment = jso.getString("comment");
        Time = jso.getInt("time");

        // 建立Comment物件
        Comment comment = new Comment(User_id, Activity_ID, Comment, Time);

        // 新增留言資料
        JSONObject data = ch.create(comment);

        // 建立回傳結果的JSONObject
        message = "成功! 新增留言資料";
        output = data.toString();
        responseMessage(response, 200, message);
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

        ID = jso.getInt("comment_id");

        // 刪除留言資料
        boolean result = ch.delete(id);

        // 建立回傳結果的JSONObject
        if (result) {
            message = "成功! 刪除留言資料";
            responseMessage(response, 200, message);
        } else {
            message = "找不到指定留言資料";
            responseMessage(response, 404, message);
        }
    }

    /**
     * 回傳訊息給前端
     *
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @param status HTTP狀態碼
     * @param message 回傳的訊息
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void responseMessage(HttpServletResponse response, int status, String message) throws IOException {
        JSONObject resp = new JSONObject();
        resp.put("status", String.valueOf(status));
        resp.put("message", message);
        resp.put("response", output);

        JsonReader jsr = new JsonReader(request);
        jsr.response(resp, response);
    }
}



