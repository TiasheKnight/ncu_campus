package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Member;
import ncu.im3069.demo.app.MemberHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/MemberController.do")

/**
 * The Class MemberController.
 * MemberController類別（class）主要用於處理Member相關之Http請求（Request），繼承HttpServlet
 */
public class MemberController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();

    /** message，output，用於儲存回傳訊息與結果 */
    private String message = "";
    private String output = "";

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
        String ID = jsr.getParameter("id");
        
        if (ID.isEmpty()) {
            JSONObject query = mh.getAll();
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有會員資料取得成功");
            resp.put("response", query);
            message = "所有會員資料取得成功";
            output = query.toString();
            jsr.response(resp, response);
        } else {
            JSONObject query = mh.getByID(ID);
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "會員資料取得成功");
            resp.put("response", query);
            message = "會員資料取得成功";
            output = query.toString();
            jsr.response(resp, response);
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
        // 透過JsonReader類別將Request之JSON格式資料解析並取回
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        // 取出經解析到JSONObject之Request參數
        int deletingUserId = jso.getInt("id");
        
        // 檢查管理員權限
        if (isAdmin(request)) {
            // 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名會員，回傳之資料為JSONObject物件
            JSONObject query = mh.deleteByID(deletingUserId);
            
            // 新建一個JSONObject用於將回傳之資料進行封裝
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "會員移除成功！");
            resp.put("response", query);

            // 透過JsonReader物件回傳到前端（以JSONObject方式）
            jsr.response(resp, response);
        } else {
            // 若非管理員，回傳權限不足的訊息
            JSONObject resp = new JSONObject();
            resp.put("status", "403");
            resp.put("message", "權限不足，無法執行刪除操作！");
            jsr.response(resp, response);
        }
    }

    /**
     * 檢查請求的使用者是否為管理員
     *
     * @param request HttpServletRequest之Request物件
     * @return boolean 是否為管理員
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 根據實際情況，可以從request中取得使用者資訊，判斷是否為管理員
        // 以下僅為示範，實際應用中需要根據具體情況進行修改
        // 這裡示範如果request中有名為"isAdmin"的參數，且其值為true，則視為管理員
        String isAdmin = request.getParameter("isAdmin");
        return isAdmin != null && Boolean.parseBoolean(isAdmin);
    }

    /**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        int ID = jso.getInt("id");
        String Authority = jso.getString("authority");
        String FirstName = jso.getString("first_name");
        String LastName = jso.getString("last_name");
        String Birthday = jso.getString("birthday");
        String email = jso.getString("email");
        String Phone = jso.getString("phone");
        String UserName = jso.getString("user_name");
        String Password = jso.getString("password");
        Member m = new Member(ID, Authority, FirstName, LastName, Birthday, email, Phone, UserName, Password);
        JSONObject data = m.update();
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新會員資料");
        resp.put("response", data);
        message = "成功! 更新會員資料";
        output = data.toString();
        jsr.response(resp, response);
    }
}

