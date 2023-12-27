package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Member_Organization;
import ncu.im3069.demo.app.Member_OrganizationHelper;
import ncu.im3069.demo.app.Organization;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/Member_Organization.do")

/**
 * The Class Member_OrganizationController.
 * Member_OrganizationController類別（class）主要用於處理Member_Organization相關之Http請求（Request），繼承HttpServlet
 */
public class Member_OrganizationController extends HttpServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** moh，Member_OrganizationHelper之物件與Member_Organization相關之資料庫方法（Singleton） */
    private Member_OrganizationHelper moh = Member_OrganizationHelper.getHelper();

    /** ID，Organization_ID，User_ID，Authority，用於處理Member_Organization相關之資料 */
    private int ID;
    private int Organization_ID;
    private int User_ID;
    private String Authority;

    /** org，Organization物件，用於處理組織相關之資料 */
    private Organization org;

    /** message，output，用於儲存回傳訊息與結果 */
    private String message = "";
    private String output = "";

    /**
     * 初始化
     *
     * @param config ServletConfig物件
     * @throws ServletException Servlet例外
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * 處理Http Method請求POST方法（新增）
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

        Organization_ID = jso.getInt("organization_id");
        User_ID = jso.getInt("user_id");
        Authority = jso.getString("authority");

        // 建立Member_Organization物件
        Member_Organization mo = new Member_Organization(Organization_ID, User_ID, Authority);

        // 新增組織資料
        JSONObject data = moh.create(mo);

        // 建立回傳結果的JSONObject
        message = "成功! 使用者關注組織";
        output = data.toString();
        responseMessage(response, 200, message);
    }

    /**
     * 處理Http Method請求DELETE方法（取消關注）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        ID = jso.getInt("member_organization_id");

        // 取消收藏組織
        boolean result = moh.delete(ID);

        // 建立回傳結果的JSONObject
        if (result) {
            message = "成功! 取消關注組織";
            responseMessage(response, 200, message);
        } else {
            message = "找不到指定關注資料";
            responseMessage(response, 404, message);
        }
    }

    /**
     * 回傳訊息給前端
     *
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @param status HTTP狀態碼
     * @param message 回傳的訊息
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void responseMessage(HttpServletResponse response, int status, String message) throws IOException {
        JSONObject resp = new JSONObject();
        resp.put("status", String.valueOf(status));
        resp.put("message", message);
        resp.put("response", output);

        JsonReader jsr = new JsonReader(request);
        jsr.response(resp, response);
    }
}


