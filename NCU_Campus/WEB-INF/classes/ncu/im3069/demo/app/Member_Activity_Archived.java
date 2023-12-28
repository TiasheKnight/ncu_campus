package ncu.im3069.demo.app;

import org.json.JSONObject;

public class Member_Activity_Archived {

    private int ID;
    private int Activity_ID;
    private String User_ID;
    private Member_Activity_ArchivedHelper MAAH = Member_Activity_ArchivedHelper.getHelper();

    public Member_Activity_Archived(int Activity_ID, String User_ID) {
        this.Activity_ID = Activity_ID;
        this.User_ID = User_ID;
    }

    public int getID() {
        return this.ID;
    }

    public int getActivity_ID() {
        return this.Activity_ID;
    }

    public String getUser_ID() {
        return this.User_ID;
    }

    public JSONObject getData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("activity_id", getActivity_ID());
        jso.put("user_id", getUser_ID());
        return jso;
    }

    public JSONObject update() {
        JSONObject data = new JSONObject();
        
        if(this.ID != 0){
            data = AH.update(this);

        return data;
    }
}
