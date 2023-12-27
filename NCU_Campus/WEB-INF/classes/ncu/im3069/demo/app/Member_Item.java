package ncu.im3069.demo.app;
import ncu.im3069.tools.JsonReader;


import org.json.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Member_Item {
    private int ID, User_ID, Item_ID, Item_Quantity;
    private Member_ItemHelper MIH = Member_ItemHelper.getHelper();

    public Member_Item(int ID, int User_ID, int Item_ID, int Item_Quantity) {
    	this.ID = ID;
    	this.User_ID = User_ID;
        this.Item_ID = Item_ID;
        this.Item_Quantity = Item_Quantity;
        update();
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public int getUser_ID() {
        return User_ID;
    }
    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }

    public int getItem_ID() {
        return Item_ID;
    }
    public void setItem_ID(int Item_ID) {
        this.Item_ID = Item_ID;
    }

    public int getItem_Quantity() {
        return Item_Quantity;
    }
    public void setItem_Quantity(int Item_Quantity) {
        this.Item_Quantity = Item_Quantity;
    }

    public JSONObject update(){
        JSONObject data = new JSONObject();
        
        if(this.ID != 0){
            data = MIH.update(this);
        }
        return data;
    }
    public JSONObject getData(){
        JSONObject jso = new JSONObject();
        jso.put("ID", getID());
        jso.put("User_ID", getUser_ID());
        jso.put("Item_ID", getItem_ID());
        jso.put("Item_Quantity", getItem_Quantity());

        return jso;
    }
}
