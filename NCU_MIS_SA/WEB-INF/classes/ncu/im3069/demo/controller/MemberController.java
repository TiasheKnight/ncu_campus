package ncu.im3069.demo.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.json.*;
import ncu.im3069.demo.app.Member;
import ncu.im3069.demo.app.MemberHelper;
import ncu.im3069.demo.util.DBMgr;
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

    /**
     * 處理Http Method請求POST方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

            JsonReader jsr = new JsonReader(request);
            JSONObject jso = jsr.getObject();
            
            String lastName = jso.getString("lastname");
            String firstName = jso.getString("firstname");
            String birthday = jso.getString("birthday");
            String email = jso.getString("email");
            String cellphone = jso.getString("cellphone");
            String password = jso.getString("password");
            String userName = jso.getString("username");
            
            // 註冊以新建帳號
            Member m = new Member(lastName,firstName,birthday,email,cellphone,password,userName);
            /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
            if(lastName.isEmpty() || firstName.isEmpty() || birthday.isEmpty() || email.isEmpty() || password.isEmpty() || userName.isEmpty()) {
                /** 以字串組出JSON格式之資料 */
                String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
                /** 透過JsonReader物件回傳到前端（以字串方式） */
                jsr.response(resp, response);
            }
            /** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
            else if (!mh.checkDuplicate(m)) {
                /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
                JSONObject data = mh.create(m);
                
                /** 新建一個JSONObject用於將回傳之資料進行封裝 */
                JSONObject resp = new JSONObject();
                resp.put("status", "200");
                resp.put("message", "成功! 註冊會員資料...");
                resp.put("response", data);
                
                /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
                jsr.response(resp, response);
            }
            else {
                /** 以字串組出JSON格式之資料 */
                String resp = "{\"status\": \'400\', \"message\": \'新增帳號失敗，此E-Mail帳號重複！\', \'response\': \'\'}";
                /** 透過JsonReader物件回傳到前端（以字串方式） */
                jsr.response(resp, response);
            }
            
            // 進行帳號密碼驗證，以及獲取使用者權限
            JSONObject loginResult = validateLogin(email, password);

            // 回傳結果給前端
            jsr.response(loginResult, response);
    }

    private JSONObject validateLogin(String email, String password) {

        /** 儲存JDBC資料庫連線 */
        Connection conn = null;

        /** 儲存JDBC預準備之SQL指令 */
        PreparedStatement pres = null;

        /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;

        String pwd = null;
        String authority = null;

        JSONObject resp = new JSONObject();

        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT `count(*)`, `password`, `authority` FROM `campus`.`members` WHERE `email` = ?";

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, email);

            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 讓指標移往最後一列，取得目前有幾行在資料庫內 */
            rs.next();
            row = rs.getInt("count(*)");

            if(row == 0){
                pwd = rs.getString("password");
                authority = rs.getString("authority");
            }
            else{
                pwd = null;
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        if (email.equals(email) && pwd.equals(password)) {
            resp.put("status", "success");
            resp.put("message", "Login successful");
            resp.put("authority", authority); // 將使用者權限加入回應中

        } else {
            // 登入失敗
            resp.put("status", "error");
            resp.put("message", "Invalid email or password");
        }

        return resp;
    }
}
