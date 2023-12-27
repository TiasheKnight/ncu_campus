    package ncu.im3069.demo.controller;

    import java.io.*;
    import javax.servlet.*;
    import javax.servlet.http.*;
    import org.json.*;
    import ncu.im3069.demo.app.Organization;
    import ncu.im3069.demo.app.OrganizationHelper;
    import ncu.im3069.tools.JsonReader;

    @WebServlet("/api/Organization.do")
    
    /**
     * The Class OrganizationController.
     * OrganizationController類別主要用於處理Organization相關之Http請求（Request），繼承HttpServlet
     */
    public class OrganizationController extends HttpServlet {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;

        /** oh，OrganizationHelper之物件與Organization相關之資料庫方法（Singleton） */
        private OrganizationHelper oh = OrganizationHelper.getHelper();

        /** ID，Organization_Name，Organization_Detail，Member_Organization，用於處理Organization相關之資料 */
        private int ID;
        private String Organization_Name;
        private String Organization_Detail;

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
            ID = jsr.getInt("organization_id");

            // 根據指定的組織ID取得該組織的詳細資料
            JSONObject query = oh.getByID(ID);

            // 檢查是否找到相對應的資料
            if (query != null) {
                message = "組織詳細資料取得成功";
                output = query.toString();
                responseMessage(response, 200, message);
            } else {
                message = "找不到指定組織的詳細資料";
                responseMessage(response, 404, message);
            }
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

            Organization_Name = jso.getString("organization_name");
            Organization_Detail = jso.getString("organization_detail");

            // 建立Organization物件
            Organization organization = new Organization(Organization_Name, Organization_Detail);

            // 新增組織資料
            JSONObject data = oh.create(organization);

            // 建立回傳結果的JSONObject
            message = "成功! 新增組織資料";
            output = data.toString();
            responseMessage(response, 200, message);
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
            JsonReader jsr = new JsonReader(request);
            JSONObject jso = jsr.getObject();

            ID = jso.getInt("organization_id");

            // 刪除組織資料
            boolean result = oh.delete(ID);

            // 建立回傳結果的JSONObject
            if (result) {
                message = "成功! 刪除組織資料";
                responseMessage(response, 200, message);
            } else {
                message = "找不到指定組織資料";
                responseMessage(response, 404, message);
            }
        }

        /**
         * 處理Http Method請求DELETE方法（移除組織管理員）
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

            int userID = jso.getInt("user_id");
            ID = jso.getInt("organization_id");

            // 移除指定使用者的組織管理員權限
            boolean result = oh.removeAdmin(userID, ID);

            // 建立回傳結果的JSONObject
            if (result) {
                message = "成功! 移除組織管理員";
                responseMessage(response, 200, message);
            } else {
                message = "找不到指定使用者或組織";
                responseMessage(response, 404, message);
            }
        }
        /**
         * 處理Http Method請求PUT方法（新增組織管理員）
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

            int userID = jso.getInt("user_id");
            ID = jso.getInt("organization_id");

            // 將指定使用者設為組織管理員
            boolean result = oh.addAdmin(userID, ID);

            // 建立回傳結果的JSONObject
            if (result) {
                message = "成功! 新增組織管理員";
                responseMessage(response, 200, message);
            } else {
                message = "找不到指定使用者或組織";
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

