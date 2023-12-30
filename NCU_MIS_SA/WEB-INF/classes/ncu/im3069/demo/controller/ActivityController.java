package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Activity;
import ncu.im3069.demo.app.ActivityHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/ActivityController.do")

/**
 * The Class ActivityController.
 * ActivityController 類別用於處理與活動相關的 HTTP 請求。
 * 它繼承自 HttpServlet。
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
public class ActivityController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** ah，ActivityHelper 類別與活動資料庫相關的方法（單例模式） */
    private ActivityHelper ah = ActivityHelper.getHelper();
    
    /**
     * 處理 HTTP POST 請求（新增資料）。
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
        
        int Activity_ID = jso.getInt("id");
        String Activity_Status = jso.getString("activity_status");
        String Activity_Name = jso.getString("activity_name");
        String Activity_Type = jso.getString("activity_type");
        String Activity_Place = jso.getString("activity_place");
        int Activity_Holder_ID = jso.getInt("activity_holder_id");
        int Maximum_Participant = jso.getInt("maximum_participant");
        int Minimum_Participant = jso.getInt("minimum_participant");
        String Start_Date = jso.getString("start_date");
        String Start_Time = jso.getString("start_time");
        String End_Date = jso.getString("end_date");
        String End_Time = jso.getString("end_time");
        String Published_Date = jso.getString("published_date");
        String Published_Time = jso.getString("published_time");
        String Activity_Detail = jso.getString("activity_detail");
        int Activity_Participant = jso.getInt("activity_participant");
        
        Activity a = new Activity(Activity_ID, Activity_Status, Activity_Name, Activity_Type, Activity_Place, Activity_Holder_ID,
                Maximum_Participant, Minimum_Participant, Start_Date, Start_Time, End_Date, End_Time, Activity_Detail, Activity_Participant);
        
        if (Activity_ID == 0 || Activity_Status.isEmpty() || Activity_Name.isEmpty() || Activity_Type.isEmpty()
                || Activity_Place.isEmpty() || Activity_Holder_ID == 0 || Maximum_Participant == 0
                || Minimum_Participant == 0 || Start_Date.isEmpty() || Start_Time.isEmpty() || End_Date.isEmpty()
                || End_Time.isEmpty() || Published_Date.isEmpty() || Published_Time.isEmpty()
                || Activity_Detail.isEmpty() || Activity_Participant == 0) {
            String resp = "{\"status\": \"400\", \"message\": \"格式錯誤，請再次確認\", \"response\": \"\"}";
            jsr.response(resp, response);
        } else {
            JSONObject data = ah.create(a);

            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "發布任務成功");
            resp.put("response", data);

            jsr.response(resp, response);
        }
    }

    /**
     * 處理 HTTP GET 請求（取得資料）。
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
        
        if (activityID.isEmpty()) {
            // 如果 Activity_ID 為空，表示取得所有活動的資料
            JSONObject query = ah.getAll();
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有任務資料取得成功");
            resp.put("response", query);
            jsr.response(resp, response);
        } else {
            // 根據指定的 activity_id 取得單一活動的資料
            int activityIDValue = Integer.parseInt(activityID);
            JSONObject query = ah.getByID(activityIDValue);
            
            if (query != null) {
                JSONObject resp = new JSONObject();
                resp.put("status", "200");
                resp.put("message", "任務資料取得成功");
                resp.put("response", query);
                jsr.response(resp, response);
            } else {
                JSONObject resp = new JSONObject();
                resp.put("status", "404");
                resp.put("message", "找不到指定的任務");
                resp.put("response", "");
                jsr.response(resp, response);
            }
        }
    }


    /**
     * 處理 HTTP DELETE 請求（刪除）。
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
            
            int activityID = jso.getInt("activity_id");
            /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件 */
            JSONObject query = ah.deleteByID(activityID);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "會員移除成功！");
            resp.put("response", query);

            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
//            if (activityID == 0) {
//                String resp = "{\"status\": \"400\", \"message\": \"格式錯誤，請提供正確的活動ID\", \"response\": \"\"}";
//                jsr.response(resp, response);
//            } else {
//                boolean result = ah.deleteByID(activityID);
//                
//                if (result) {
//                    String resp = "{\"status\": \"200\", \"message\": \"刪除活動成功\", \"response\": \"\"}";
//                    jsr.response(resp, response);
//                } else {
//                    String resp = "{\"status\": \"404\", \"message\": \"找不到指定的活動，刪除失敗\", \"response\": \"\"}";
//                    jsr.response(resp, response);
//                }
//            }
        }

    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        int activityID = jso.getInt("activity_id");
        String activity_name = jso.getString("name");
        String activity_type = jso.getString("type");
        String activity_place = jso.getString("place");
        int activity_publisher_id = jso.getInt("activity_publisher_id");
        int max_participant = jso.getInt("max_participant");
        int min_participant = jso.getInt("min_participant");
        String start_date = jso.getString("start_date");
        String start_time = jso.getString("start_time");
        String end_date = jso.getString("end_date");
        String end_time = jso.getString("end_time");
        String detail = jso.getString("detail");
            
        // 根據 activityID 取得現有活動資料
        Activity a = new Activity(activityID,activity_name,activity_type,activity_place,activity_publisher_id,max_participant,min_participant,start_date,start_time,end_date,end_time,detail);

            // 呼叫 update 方法更新活動資料
            JSONObject data = a.update();

            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "更新活動成功");
            resp.put("response", data);

            jsr.response(resp, response);
    }

}





