package ncu.im3069.demo.app;

import org.json.*;
import java.sql.*;
import java.util.*;


public class Notification {
	private int ID;
    private int Activity_ID;
    private int User_ID;
    private String Notification_Title;
    private String Notification_Content;
    private NotificationHelper NH = NotificationHelper.getHelper();
       

    public Notification(int ID, int Activity_ID, int User_ID, String Notification_Title, String Notification_Content) {
        this.ID = ID;
        this.Activity_ID = Activity_ID;
        this.User_ID = User_ID;
        this.Notification_Title = Notification_Title;
        this.Notification_Content = Notification_Content;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public int getID() {
        return this.ID;
    }

    public int getActivity_ID() {
        return this.Activity_ID;
    }

    public int getUser_ID() {
        return this.User_ID;
    }

    public String getNotification_Title() {
        return this.Notification_Title;
    }

    public String getNotification_Content() {
        return this.Notification_Content;
    }

    // Other methods
    public JSONObject getData() {
        JSONObject jso = new JSONObject();
        jso.put("ID", getID());
        jso.put("Activity_ID", getActivity_ID());
        jso.put("User_ID", getUser_ID());
        jso.put("Notification_Title", getNotification_Title());
        jso.put("Notification_Content", getNotification_Content());
        return jso;
    }

    public JSONObject update() {
        JSONObject data = new JSONObject();
        // Update logic here
        // ...

        return data;
    }
}
