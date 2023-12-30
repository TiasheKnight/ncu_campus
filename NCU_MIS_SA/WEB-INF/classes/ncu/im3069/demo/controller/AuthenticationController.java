package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.MemberHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/AuthenticationController.do")
/**
 * The Class AuthenticationController.
 * AuthenticationController 類別用於處理與使用者身份驗證相關的 HTTP 請求。
 * 它繼承自 HttpServlet。
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
public class AuthenticationController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** mh，MemberHelper 類別與會員資料庫相關的方法（單例模式） */
    private MemberHelper mh = MemberHelper.getHelper();
    
    /** message，output，用於儲存回傳訊息與結果 */
    private String message = "";
    private String output = "";
    /**
     * 處理 HTTP GET 請求（取得資料）。
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
            
            String email = jso.getString("email");
            String password = jso.getString("password");
            
            // 回傳權限
            JSONObject authority = mh.Login(email,password);
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功取得會員權限");
            resp.put("response", authority);
            message = "所有會員資料取得成功";
            output = resp.toString();
            jsr.response(resp, response);
    }
}

