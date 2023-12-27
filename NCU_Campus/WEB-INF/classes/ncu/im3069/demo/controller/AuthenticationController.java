package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Authentication;
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
        // 從請求中獲取使用者的驗證資訊，例如使用者名稱、權限等
        String UserName = request.getParameter("user_name");
        Authentication auth = mh.getAuthentication(UserName);

        // 判斷使用者類型
        if (auth != null) {
            // 使用者已登入，可以根據權限進行不同的處理
            if (auth.isAdmin()) {
                // 管理員處理邏輯
                // 可以回傳相應的資料給前端
                JSONObject responseData = new JSONObject();
                responseData.put("message", "您是管理員，可以進行管理員操作。");
                response.getWriter().print(responseData.toString());
            } else {
                // 一般使用者處理邏輯
                // 可以回傳相應的資料給前端
                JSONObject responseData = new JSONObject();
                responseData.put("message", "您是一般使用者，可以進行一般操作。");
                response.getWriter().print(responseData.toString());
            }
        } else {
            // 使用者未登入
            JSONObject responseData = new JSONObject();
            responseData.put("error", "使用者為登入請重新登入。");
            response.getWriter().print(responseData.toString());
        }
    }

}

