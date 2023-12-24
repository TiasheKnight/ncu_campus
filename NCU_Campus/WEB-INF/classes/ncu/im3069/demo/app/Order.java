package ncu.im3069.demo.app;

import java.sql.*;
import java.util.*;
import org.json.*;

public class Order {

    /** id, order number */
    private int id;

    /** first_name, member name */
    private String first_name;

    /** last_name, member’s last name */
    private String last_name;

    /** email, member email address */
    private String email;

    /** address, member address */
    private String address;

    /** phone, member’s mobile phone */
    private String phone;

    /** list, order list */
    private ArrayList<OrderItem> list = new ArrayList<OrderItem>();

    /** create, order creation time */
    private Timestamp create;

    /** modify, order modification time */
    private Timestamp modify;

    /** oph, OrderItemHelper object and Order-related database method (Sigleton) */
    private OrderItemHelper oph = OrderItemHelper.getHelper();

    /**
     * Instantiates a new Order object<br>
     * Using the overload method, this constructor is used to generate a new order
     * when creating order data.
     *
     * @param first_name member name
     * @param last_name  member’s last name
     * @param email      member’s email address
     * @param address    member address
     * @param phone      member name
     */
    public Order(String first_name, String last_name, String email, String address, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.create = Timestamp.valueOf(LocalDateTime.now());
        this.modify = Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * Instantiates a new Order object<br>
     * Use the overload method. This constructor is used to modify orders that
     * already exist in the database when modifying order data.
     *
     * @param first_name member name
     * @param last_name  member’s last name
     * @param email      member’s email address
     * @param address    member address
     * @param phone      member name
     * @param create     order creation time
     * @param modify     order modification time
     */
    public Order(int id, String first_name, String last_name, String email, String address, String phone,
            Timestamp create, Timestamp modify) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.create = create;
        this.modify = modify;
        getOrderProductFromDB();
    }

    /**
     * Add an order product and its quantity
     */
    public void addOrderProduct(Product pd, int quantity) {
        this.list.add(new OrderItem(pd, quantity));
    }

    /**
     * Add an order product
     */
    public void addOrderProduct(OrderItem op) {
        this.list.add(op);
    }

    /**
     * Set order number
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get order number
     *
     * @return int Return order number
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the name of the ordering member
     *
     * @return String Returns the name of the order member
     */
    public String getFirstName() {
        return this.first_name;
    }

    /**
     * Get the last name of the ordering member
     *
     * @return String Returns the last name of the order member
     */
    public String getLastName() {
        return this.last_name;
    }

    /**
     * Get order mailbox
     *
     * @return String Return order mailbox
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get the order creation time
     *
     * @return Timestamp returns the order creation time
     */
    public Timestamp getCreateTime() {
        return this.create;
    }

    /**
     * Get the order modification time
     *
     * @return Timestamp returns the order modification time
     */
    public Timestamp getModifyTime() {
        return this.modify;
    }

    /**
     * Get order address
     *
     * @return String Return order address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Get order phone number
     *
     * @return String Return order phone number
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Get all the information of the member
     *
     * @return the data Get all the information of the member and encapsulate it in
     *         a JSONObject object
     */
    public ArrayList<OrderItem> getOrderProduct() {
        return this.list;
    }

    /**
     * Get order products from DB
     */
    private void getOrderProductFromDB() {
        ArrayList<OrderItem> data = oph.getOrderProductByOrderId(this.id);
        this.list = data;
    }

    /**
     * Obtain basic order information
     *
     * @return JSONObject Get the basic information of the order
     */
    public JSONObject getOrderData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("first_name", getFirstName());
        jso.put("last_name", getLastName());
        jso.put("email", getEmail());
        jso.put("address", getAddress());
        jso.put("phone", getPhone());
        jso.put("create", getCreateTime());
        jso.put("modify", getModifyTime());

        return jso;
    }

    /**
     * Obtain order product information
     *
     * @return JSONArray Get order product information
     */
    public JSONArray getOrderProductData() {
        JSONArray result = new JSONArray();

        for (int i = 0; i < this.list.size(); i++) {
            result.put(this.list.get(i).getData());
        }

        return result;
    }

    /**
     * Get all order information
     *
     * @return JSONObject Get all order information
     */
    public JSONObject getOrderAllInfo() {
        JSONObject jso = new JSONObject();
        jso.put("order_info", getOrderData());
        jso.put("product_info", getOrderProductData());

        return jso;
    }

    /**
     * Set order product number
     */
    public void setOrderProductId(JSONArray data) {
        for (int i = 0; i < this.list.size(); i++) {
            this.list.get(i).setId((int) data.getLong(i));
        }
    }

}
