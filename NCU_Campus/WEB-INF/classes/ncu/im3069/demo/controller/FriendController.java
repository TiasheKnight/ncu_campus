package ncu.im3069.demo.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import ncu.im3069.demo.app.FriendHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/friend.do")

public class FriendController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private FriendHelper FH = FriendHelper.getHelper();

    public FriendController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        String action = jsr.getParameter("action");

        JSONObject resp = new JSONObject();

        if ("getFriendById".equals(action)) {
            int friendId = jsr.getParameterInt("friend_id");
            JSONObject query = fh.getFriendById(friendId);

            resp.put("status", "200");
            resp.put("message", "好友資料取得成功");
            resp.put("response", query);
        } else {
            resp.put("status", "400");
            resp.put("message", "未知的操作");
        }

        jsr.response(resp, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        String action = jsr.getParameter("action");

        JSONObject resp = new JSONObject();

        if ("addFriend".equals(action)) {
            int UserId = jsr.getParameterInt("user_id");
            int FriendId = jsr.getParameterInt("friend_id");
            
            if (fh.addFriend(UserId, FriendId)) {
                resp.put("status", "200");
                resp.put("message", "新增好友成功");
            } else {
                resp.put("status", "400");
                resp.put("message", "新增好友失敗");
            }
        } else {
            resp.put("status", "400");
            resp.put("message", "未知的操作");
        }

        jsr.response(resp, response);
    }


    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonReader jsr = new JsonReader(request);
        String action = jsr.getParameter("action");

        JSONObject resp = new JSONObject();

        if ("deleteFriend".equals(action)) {
            int id = jsr.getParameterInt("id");
            
            if (fh.deleteFriendById(id)) {
                resp.put("status", "200");
                resp.put("message", "刪除好友成功");
            } else {
                resp.put("status", "400");
                resp.put("message", "刪除好友失敗");
            }
        } else {
            resp.put("status", "400");
            resp.put("message", "未知的操作");
        }

        jsr.response(resp, response);
    }
}
