package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.json.*;
import ncu.im3069.demo.app.Member;
import ncu.im3069.demo.app.MemberHelper;
import ncu.im3069.tools.JsonReader;

/**
 * Servlet implementation class PasswordController
 */
@WebServlet("/PasswordController")
public class PasswordController extends HttpServlet {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** mh，MemberHelper之物件與Member相關之資料庫方法（Sigleton） */
    private MemberHelper mh =  MemberHelper.getHelper();

    /** message，output，用於儲存回傳訊息與結果 */
    private String message = "";
    private String output = "";       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            JsonReader jsr = new JsonReader(request);
            JSONObject jso = jsr.getObject();
            
            String email = jso.getString("email");
            String Password = jso.getString("password");
            
            Member m = new Member(email, Password);
            JSONObject data = mh.updateForgetPassword(m);
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 更新密碼資料 for 忘記密碼");
            resp.put("response", data);
            message = "成功! 更新密碼資料 for 忘記密碼";
            output = data.toString();
            jsr.response(resp, response);
        }

}
