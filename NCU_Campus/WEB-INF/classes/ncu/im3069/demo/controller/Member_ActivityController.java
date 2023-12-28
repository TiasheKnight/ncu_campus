package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.json.*;
import ncu.im3069.demo.app.Member_Activity;
import ncu.im3069.demo.app.Member_ActivityHelper;
import ncu.im3069.demo.app.ActivityHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/Member_ActivityController.do")

/**
 * The Class Member_ActivityController.
 * Member_Activity_Controller 類別（class）主要用於處理 Member_Activity 相關之 Http 請求（Request），繼承 HttpServlet
 */
public class Member_ActivityController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** mah，Member_ActivityHelper 之物件與 member_activity 相關之資料庫方法（Singleton） */
    private Member_ActivityHelper mah = Member_ActivityHelper.getHelper();
    private ActivityHelper ah = new ActivityHelper();
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
        
        String activityID = jsr.getParameter("activity_id");
        int member_id = Integer.parseInt(jsr.getParameter("member_id"));

        if (activityID.isEmpty()) {
            JSONArray query = mah.getMemberActivity(member_id);
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有會員活動資料取得成功");
            resp.put("response", query);
            jsr.response(resp, response);
        } else {
            int activityIDValue = Integer.parseInt(activityID);
            JSONObject query = ah.getByID(activityIDValue);
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
        
        int id = jso.getInt("activity_id");
        
        JSONObject query = ah.deleteByID(id);
        
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
        
        int ma_id = jso.getInt("id");
        int User_ID = jso.getInt("user_id");
        int Activity_ID = jso.getInt("activity_id");
        
        // 使用正確的參數初始化 Member_Activity 物件
        Member_Activity ma = new Member_Activity(ma_id, User_ID, Activity_ID);
        //原本長這樣ArrayList<Member_Activity> memberid = new ArrayList<Member_Activity>(memberid);
        ArrayList<Member_Activity> memberid = new ArrayList<>(memberid);

        
        if (User_ID <= 0 || Activity_ID <= 0) {
            String resp = "{\"status\": '400', \"message\": '格式錯誤\\n請再次確認', 'response': ''}";
            jsr.response(resp, response);
        } else {
            JSONArray data = mah.createByList(Activity_ID, memberid);
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "會員活動新增成功");
            resp.put("response", data);
            
            jsr.response(resp, response);
        }
    }

}
