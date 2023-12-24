import org.json.*;

public class Friend {
    private int ID, User_ID, Friends_User_ID;
    private String Friends_User_Name;
    private FriendHelper FH = FriendHelper.getHelper();

    public Friend(int ID, int User_ID, int Friends_User_ID, String Friends_User_Name){
        this.ID = ID;
        this.User_ID = User_ID;
        this.Friends_User_ID = Friends_User_ID;
        this.Friends_User_Name = Friends_User_Name;
        update();
    } //Instantiates 一個新的Friend 物件，採用Overload方法進行，此建構子用於新增好友時，好友列表

        public Friend(int ID, int User_ID, int Friends_User_ID, String Friends_User_Name){
        this.ID = ID;
        this.User_ID = User_ID;
        this.Friends_User_ID = Friends_User_ID;
        this.Friends_User_Name = Friends_User_Name;
        update();
    } //Instantiates 一個新的Friend 物件，採用Overload方法進行，此建構子用於刪除好友時，好友列表

    public Friend(int ID, int User_ID, int Friends_User_ID){
        this.ID = ID;
        this.User_ID = User_ID;
        this.Friends_User_ID = Friends_User_ID;
    } //Instantiates 一個新的Friend 物件，採用Overload方法進行，此建構子用於查看好友時，好友列表

    public int getID(){
        return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }

    public int getUser_ID(){
        return User_ID;
    }
    public void setUser_ID(int User_ID){
        this.User_ID = User_ID;
    }

    public int getFriends_User_ID(){
        return Friends_User_ID;
    }
    public void setFriends_User_ID(int Friends_User_ID){
        this.Friends_User_ID = Friends_User_ID;
    }

    public String getFriends_User_Name(){
        return Friends_User_Name;
    }
    public void setFriends_User_Name(String Friends_User_Name){
        this.Friends_User_Name = Friends_User_Name;
    }

    public JSONObject update(){
        JSONObject data = new JSONObject();
        
        if(this.ID != 0){
            data = FH.update(this);
        }
        return data;
    }

    public JSONObject getData(){
        JSONObject jso = new JSONObject();
        jso.put("ID", getID());
        jso.put("User_ID", getUser_ID());
        jso.put("Friends_User_ID", getFriends_User_ID());
        jso.put("Friends_User_Name", getFriends_User_Name());

        return jso;
    }
}
