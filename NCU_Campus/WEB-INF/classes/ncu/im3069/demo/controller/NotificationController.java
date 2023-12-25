package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Notification;
import ncu.im3069.demo.app.NotificationHelper;
import ncu.im3069.tools.JsonReader;

public class NotificationController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // 建立 NotificationHelper 實例
    private NotificationHelper nh = NotificationHelper.getHelper();

    // 初始化訊息和輸出
    private String message = "";
    private String output = "";

    // 初始化方法，由 Servlet 容器呼叫以進行一次性初始化
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    // 處理 Http Method 請求 POST 方法（新增通知）
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 解析 JSON 格式的 Request 參數
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        // 取得參數值
        int Activity_ID = jso.getInt("Activity_ID");
        int User_ID = jso.getInt("User_ID");
        String Notification_Title = jso.getString("Notification_Title");
        String Notification_Content = jso.getString("Notification_Content");

        // 創建 Notification 物件
        Notification notification = new Notification(Activity_ID, User_ID, Notification_Title, Notification_Content);

        // 將通知存入資料庫
        JSONObject data = nh.create(notification);

        // 設定回傳訊息和輸出
        message = "成功! 通知已發送";
        output = data.toString();
        
        // 回傳訊息
        responseMessage(response, 200, message);
    }

    // 處理 Http Method 請求 GET 方法（取得通知）
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 解析 JSON 格式的 Request 參數
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        // 取得參數值
        int User_ID = jso.getInt("User_ID");

        // 由使用者 ID 取得通知
        JSONObject query = nh.getByUserID(User_ID);

        // 設定回傳訊息和輸出
        message = "成功! 取得通知";
        output = query.toString();
        
        // 回傳訊息
        responseMessage(response, 200, message);
    }

    // 回傳訊息的方法
    private void responseMessage(HttpServletResponse response, int status, String message) throws IOException {
        JSONObject resp = new JSONObject();
        resp.put("status", String.valueOf(status));
        resp.put("message", message);
        resp.put("response", output);

        // 透過 JsonReader 物件回傳到前端（以 JSONObject 方式）
        JsonReader jsr = new JsonReader(request);
        jsr.response(resp, response);
    }
}


