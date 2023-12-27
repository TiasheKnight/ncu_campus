package ncu.im3069.demo.app;

import org.json.JSONObject;

public class Report {

    private int ID;
    private int User_ID;
    private int Activity_ID;
    private ReportHelper RH = ReportHelper.getHelper();

    public Report(int ID, int User_ID, int Activity_ID) {
        this.ID = ID;
        this.User_ID = User_ID;
        this.Activity_ID = Activity_ID;
    }

    public int getID() {
        return this.ID;
    }

    public int getUser_ID() {
        return this.User_ID;
    }

    public int getActivity_ID() {
        return this.Activity_ID;
    }


    public JSONObject getData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("user_id", getUser_ID());
        jso.put("activity_id", getActivity_ID());
        return jso;
    }

    public JSONObject update() {
    	JSONObject data = new JSONObject();
        
        if(this.ID != 0){
            data = RH.update(this);
        }
        return data;
    }
}
