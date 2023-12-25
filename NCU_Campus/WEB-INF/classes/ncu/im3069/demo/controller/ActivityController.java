package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Activity;
import ncu.im3069.demo.app.ActivityHelper;
import ncu.im3069.tools.JsonReader;

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
        
        int Activity_ID = jso.getInt("Activity_ID");
        String Activity_Status = jso.getString("Activity_Status");
        String Activity_Name = jso.getString("Activity_Name");
        String Activity_Type = jso.getString("Activity_Type");
        String Activity_Place = jso.getString("Activity_Place");
        int Activity_Holder_ID = jso.getInt("Activity_Holder_ID");
        int Maximum_Participant = jso.getInt("Maximum_Participant");
        int Minimum_Participant = jso.getInt("Minimum_Participant");
        String Start_Date = jso.getString("Start_Date");
        String Start_Time = jso.getString("Start_Time");
        String End_Date = jso.getString("End_Date");
        String End_Time = jso.getString("End_Time");
        String Published_Date = jso.getString("Published_Date");
        String Published_Time = jso.getString("Published_Time");
        String Activity_Detail = jso.getString("Activity_Detail");
        int Activity_Participant = jso.getInt("Activity_Participant");
        
        Activity a = new Activity(Activity_ID, Activity_Status, Activity_Name, Activity_Type, Activity_Place, Activity_Holder_ID,
                Maximum_Participant, Minimum_Participant, Start_Date, Start_Time, End_Date, End_Time, Published_Date,
                Published_Time, Activity_Detail, Activity_Participant);
        
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
        String activityID = jsr.getParameter("Activity_ID");
        
        if (activityID.isEmpty()) {
            // 如果 Activity_ID 為空，表示取得所有活動的資料
            JSONObject query = ah.getAll();
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有任務資料取得成功");
            resp.put("response", query);
            jsr.response(resp, response);
        } else {
            // 根據指定的 Activity_ID 取得單一活動的資料
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
            
            int Activity_ID = jso.getInt("Activity_ID");
            
            if (Activity_ID == 0) {
                String resp = "{\"status\": \"400\", \"message\": \"格式錯誤，請提供正確的活動ID\", \"response\": \"\"}";
                jsr.response(resp, response);
            } else {
                boolean result = ah.delete(A_ID);
                
                if (result) {
                    String resp = "{\"status\": \"200\", \"message\": \"刪除活動成功\", \"response\": \"\"}";
                    jsr.response(resp, response);
                } else {
                    String resp = "{\"status\": \"404\", \"message\": \"找不到指定的活動，刪除失敗\", \"response\": \"\"}";
                    jsr.response(resp, response);
                }
            }
        }

    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        int activityID = jso.getInt("Activity_ID");

        // 根據 activityID 取得現有活動資料
        Activity existingActivity = ah.getByID(activityID);

        if (existingActivity != null) {
            // 更新所有可能的欄位，僅在 JSON 物件中包含相應欄位時進行更新
            if (jso.has("Activity_Status")) {
                existingActivity.setActivityStatus(jso.getString("Activity_Status"));
            }
            if (jso.has("Activity_Name")) {
                existingActivity.setActivityName(jso.getString("Activity_Name"));
            }
            if (jso.has("Activity_Type")) {
                existingActivity.setActivityType(jso.getString("Activity_Type"));
            }
            if (jso.has("Activity_Place")) {
                existingActivity.setActivityPlace(jso.getString("Activity_Place"));
            }
            if (jso.has("Activity_Holder_ID")) {
                existingActivity.setActivityHolderID(jso.getInt("Activity_Holder_ID"));
            }
            if (jso.has("Maximum_Participant")) {
                existingActivity.setMaximumParticipant(jso.getInt("Maximum_Participant"));
            }
            if (jso.has("Minimum_Participant")) {
                existingActivity.setMinimumParticipant(jso.getInt("Minimum_Participant"));
            }
            if (jso.has("Start_Date")) {
                existingActivity.setStartDate(jso.getString("Start_Date"));
            }
            if (jso.has("Start_Time")) {
                existingActivity.setStartTime(jso.getString("Start_Time"));
            }
            if (jso.has("End_Date")) {
                existingActivity.setEndDate(jso.getString("End_Date"));
            }
            if (jso.has("End_Time")) {
                existingActivity.setEndTime(jso.getString("End_Time"));
            }
            if (jso.has("Published_Date")) {
                existingActivity.setPublishedDate(jso.getString("Published_Date"));
            }
            if (jso.has("Published_Time")) {
                existingActivity.setPublishedTime(jso.getString("Published_Time"));
            }
            if (jso.has("Activity_Detail")) {
                existingActivity.setActivityDetail(jso.getString("Activity_Detail"));
            }
            if (jso.has("Activity_Participant")) {
                existingActivity.setActivityParticipant(jso.getInt("Activity_Participant"));
            }

            // 呼叫 update 方法更新活動資料
            JSONObject data = existingActivity.update();

            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "更新活動成功");
            resp.put("response", data);

            jsr.response(resp, response);
        } else {
            JSONObject resp = new JSONObject();
            resp.put("status", "404");
            resp.put("message", "找不到指定的活動");
            resp.put("response", "");
            jsr.response(resp, response);
        }
    }

}


