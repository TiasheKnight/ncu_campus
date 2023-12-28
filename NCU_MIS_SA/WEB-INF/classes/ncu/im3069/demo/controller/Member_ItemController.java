package ncu.im3069.demo.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.json.*;
import ncu.im3069.demo.app.Member_Item;
import ncu.im3069.demo.app.Member_ItemHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/Member_ItemController.do")

public class Member_ItemController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private Member_ItemHelper mih = Member_ItemHelper.getHelper();
    
    private String message = "";
    private String output = "";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        int User_ID = jso.getInt("user_id");
        int Item_ID = jso.getInt("item_id");
        int Item_Quantity = 1;  // 一次只能購買一個

        // Create a new Member_Item instance for the purchase
        Member_Item memberItem = new Member_Item(User_ID, Item_ID, Item_Quantity);

        // Perform the purchase
        JSONObject data = mih.create(memberItem);

        message = "成功! 購買道具";
        output = data.toString();
        responseMessage(response, 200, message);
    }


    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        int User_ID = jso.getInt("user_id");
        int Item_ID = jso.getInt("item_id");
        int Item_Quantity = jso.getInt("item_quantity");

        Member_Item memberItem = new Member_Item(User_ID, Item_ID, Item_Quantity);

        JSONObject data = memberItem.update();

        message = "成功! 丟棄道具";
        output = data.toString();
        responseMessage(response, 200, message);
    }
    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        int User_ID = jso.getInt("user_id");
        int Item_ID = jso.getInt("item_id");
        int Item_Quantity = jso.getInt("item_quantity");

        // Check if the item exists in the user's inventory
        Member_Item existingItem = mih.getByUserIDAndItemID(User_ID, Item_ID);

        if (existingItem != null) {
            // Item exists, update the quantity
            existingItem.setItem_Quantity(existingItem.getItem_Quantity() + Item_Quantity);
            JSONObject data = existingItem.update();
            message = "成功! 更新道具數量";
            output = data.toString();
            responseMessage(response, 200, message);
        } else {
            // Item does not exist, return an error message
            message = "錯誤! 使用者尚未擁有該道具";
            responseMessage(response, 400, message);
        }
    }

    private void responseMessage(HttpServletResponse response, int status, String message) throws IOException {
        JSONObject resp = new JSONObject();
        resp.put("status", String.valueOf(status));
        resp.put("message", message);
        resp.put("response", output);

        JsonReader jsr = new JsonReader(request);
        jsr.response(resp, response);
    }
}

