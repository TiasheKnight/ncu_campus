package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Member_Activity;
import ncu.im3069.demo.app.Member_ActivityHelper;
import ncu.im3069.tools.JsonReader;

/**
 * The Class Member_Activity_Controller.
 * Member_Activity_Controller 類別（class）主要用於處理 Member_Activity 相關之 Http 請求（Request），繼承 HttpServlet
 */
public class Member_Activity_Controller extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** mah，Member_ActivityHelper 之物件與 Member_Activity 相關之資料庫方法（Singleton） */
    private Member_ActivityHelper mah = Member_ActivityHelper.getHelper();
    
    /**
     * 處理 Http Method 請求 GET 方法（取得資料）。
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        String ID = jsr.getParameter("ID");
        
        if (ID.isEmpty()) {
            JSONObject query = mah.getAll();
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有會員活動資料取得成功");
            resp.put("response", query);
            jsr.response(resp, response);
        } else {
            JSONObject query = mah.getByID(ID);
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "會員活動資料取得成功");
            resp.put("response", query);
            jsr.response(resp, response);
        }
    }

    /**
     * 處理 Http Method 請求 DELETE 方法（刪除）。
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        int ID = jso.getInt("ID");
        
        JSONObject query = mah.deleteByID(ID);
        
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "會員活動移除成功！");
        resp.put("response", query);

        jsr.response(resp, response);
    }

    /**
     * 處理 Http Method 請求 POST 方法（新增資料）。
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
        
        int User_ID = jso.getInt("User_ID");
        int Activity_ID = jso.getInt("Activity_ID");
        
        Member_Activity ma = new Member_Activity(User_ID, Activity_ID);
        
        if (User_ID <= 0 || Activity_ID <= 0) {
            String resp = "{\"status\": '400', \"message\": '格式錯誤\\n請再次確認', 'response': ''}";
            jsr.response(resp, response);
        } else {
            JSONObject data = mah.create(ma);
            
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "會員活動新增成功");
            resp.put("response", data);
            
            jsr.response(resp, response);
        }
    }
}

