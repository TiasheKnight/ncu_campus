package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Followship;
import ncu.im3069.demo.app.FollowshipHelper;
import ncu.im3069.tools.JsonReader;
@WebServlet("/api/Followship.do")
/**
 * The Class FollowshipController.
 * FollowshipController 類別（class）主要用於處理 Followship 相關之 Http 請求（Request），繼承 HttpServlet
 */
public class FollowshipController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** fh，FollowshipHelper 之物件與 Followship 相關之資料庫方法（Singleton） */
    private FollowshipHelper fh = FollowshipHelper.getHelper();
    
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
        
        int ID = jso.getInt("id");
        int Follower_User_ID = jso.getInt("follower_user_id");
        int Followed_User_ID = jso.getInt("followed_user_id");
        
        Followship f = new Followship(ID, Follower_User_ID, Followed_User_ID);
        
        if (ID <= 0 || Follower_User_ID <= 0 || Followed_User_ID <= 0) {
            String resp = "{\"status\": '400', \"message\": '格式錯誤\\n請再次確認', 'response': ''}";
            jsr.response(resp, response);
        } else if (!fh.checkDuplicate(f)) {
            JSONObject data = fh.create(f);
            
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "Followship 建立成功");
            resp.put("response", data);
            
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
        
        int ID = jso.getInt("id");
        
        JSONObject query = fh.deleteByID(id);
        
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "Followship 刪除成功！");
        resp.put("response", query);

        jsr.response(resp, response);
    }

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
        String ID = jsr.getParameter("id");
        
        if (ID.isEmpty()) {
            JSONObject query = fh.getAll();
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有 Followship 資料取得成功");
            resp.put("response", query);
            jsr.response(resp, response);
        } else {
            JSONObject query = fh.getByID(ID);
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "Followship 資料取得成功");
            resp.put("response", query);
            jsr.response(resp, response);
        }
    }
}



