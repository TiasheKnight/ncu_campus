package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Member;
import ncu.im3069.demo.app.MemberHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/RegisterController.do")

/**
 * The Class RegisterController.
 * RegisterController 類別用於處理會員註冊相關的 HTTP 請求。
 * 它繼承自 HttpServlet。
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
public class RegisterController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** mh，MemberHelper 類別與會員資料庫相關的方法（單例模式） */
    private MemberHelper mh = MemberHelper.getHelper();
    
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
        
        int ID = jso.getInt("id");
        String Authority = jso.getString("authority");
        String FirstName = jso.getString("firstName");
        String LastName = jso.getString("last_name");
        String Birthday = jso.getString("birthday");
        String email = jso.getString("email");
        String Phone = jso.getString("phone");
        String UserName = jso.getString("user_name");
        String Password = jso.getString("password");
        
        Member m = new Member(ID, Authority, FirstName, LastName, Birthday, email, Phone, UserName, Password);
        
        if(ID.isEmpty() || Authority.isEmpty() || FirstName.isEmpty() || LastName.isEmpty() || Birthday.isEmpty() || email.isEmpty() || Phone.isEmpty() || UserName.isEmpty() || Password.isEmpty()) {
            String resp = "{\"status\": \"400\", \"message\": \"格式錯誤，請再次確認\", \"response\": \"\"}";
            jsr.response(resp, response);
        } else if (!mh.checkDuplicate(m)) {
            JSONObject data = mh.create(m);
            
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "註冊成功，請重新登入");
            resp.put("response", data);
            
            jsr.response(resp, response);
        } else {
            String resp = "{\"status\": \"409\", \"message\": \"該電子郵件已被註冊，請使用其他電子郵件\", \"response\": \"\"}";
            jsr.response(resp, response);
        }
    }
}


