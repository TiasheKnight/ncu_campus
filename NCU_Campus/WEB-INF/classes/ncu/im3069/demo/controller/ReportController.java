package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Report;
import ncu.im3069.demo.app.ReportHelper;
import ncu.im3069.tools.JsonReader;

public class ReportController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private String message;
    private String output;
    private ReportHelper RH = ReportHelper.getHelper();

    public void Init() {
        message = "";
        output = "";
    }

    /**
     * 處理 HTTP POST 請求（新增舉報資料）。
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        int ID = jso.getInt("ID");
        int User_ID = jso.getInt("User_ID");
        int Activity_ID = jso.getInt("Activity_ID");
        
        Report r = new Report(ID, User_ID, Activity_ID);
        
        if (ID == 0 || User_ID == 0 || Activity_ID == 0) {
            String resp = "{\"status\": \"400\", \"message\": \"格式錯誤，請再次確認\", \"response\": \"\"}";
            jsr.response(resp, response);
        } else {
            JSONObject data = r.update();

            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "新增舉報資料成功");
            resp.put("response", data);

            jsr.response(resp, response);
        }
    }

}
