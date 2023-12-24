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
        jso.put("ID", getID());
        jso.put("Activity_ID", getActivity_ID());
        jso.put("User_ID", getUser_ID());
        return jso;
    }

    public JSONObject update() {
        JSONObject data = new JSONObject();
        // Update logic here
        // ...

        return data;
    }
}
