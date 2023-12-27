package ncu.im3069.demo.app;

import org.json.JSONObject;

public class Comment {

    private int ID;
    private int User_ID;
    private int Activity_ID;
    private String Comment;
    private int TimeStamp;
    private CommentHelper CH = CommentHelper.getHelper();

    public Comment(int Activity_ID, int User_ID, String Comment, int TimeStamp) {
        this.Activity_ID = Activity_ID;
        this.User_ID = User_ID;
        this.Comment = Comment;
        this.TimeStamp = TimeStamp;
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

    public String getComment() {
        return this.Comment;
    }

    public int getTimeStamp() {
        return this.TimeStamp;
    }

    public JSONObject getData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("user_id", getUser_ID());
        jso.put("activity_id", getActivity_ID());
        jso.put("comment", getComment());
        jso.put("timestamp", getTimeStamp());
        return jso;
    }

}