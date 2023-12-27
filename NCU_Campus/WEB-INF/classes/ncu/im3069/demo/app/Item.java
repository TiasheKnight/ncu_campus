package ncu.im3069.demo.app;
import ncu.im3069.tools.JsonReader;


import org.json.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Item {
    private int ID, Item_Price;
    private String Item_Name, Item_Detail;
    private ItemHelper IH = ItemHelper.getHelper();

    public Item(int ID, int Item_Price, String Item_Name, String Item_Detail) {
        this.ID = ID;
        this.Item_Price = Item_Price;
        this.Item_Name = Item_Name;
        this.Item_Detail = Item_Detail;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public int getItem_Price() {
        return Item_Price;
    }
    public void setItem_Price(int Item_Price) {
        this.Item_Price = Item_Price;
    }
    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String Item_Name) {
        this.Item_Name = Item_Name;
    }
    public String getItem_Detail() {
        return Item_Detail;
    }
    public void setItem_Detail(String Item_Detail) {
        this.Item_Detail = Item_Detail;
    }

    public JSONObject getData(){
        JSONObject jso = new JSONObject();
        jso.put("ID", getID());
        jso.put("Item_Name", getItem_Name());
        jso.put("Item_Detail", getItem_Detail());
        jso.put("Item_Price", getItem_Price());

        return jso;
    }
}
