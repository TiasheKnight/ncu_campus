package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.json.*;
import ncu.im3069.demo.app.Item;
import ncu.im3069.demo.app.ItemHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/ItemController.do")

public class ItemController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ItemHelper IH = ItemHelper.getHelper();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        String ID = jsr.getParameter("id");

        if (ID.isEmpty()) {
            JSONObject query = ih.getAll();

            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有道具資料取得成功");
            resp.put("response", query);

            jsr.response(resp, response);
        } else {
            JSONObject query = ih.getByID(id);

            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "道具資料取得成功");
            resp.put("response", query);

            jsr.response(resp, response);
        }
    }
}


