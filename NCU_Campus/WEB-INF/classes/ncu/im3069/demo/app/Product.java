package ncu.im3069.demo.app;

import org.json.*;

public class Product {

    /** id, member number */
    private int id;

    /** id, member number */
    private String name;

    /** id, member number */
    private double price;

    /** id, member number */
    private String image;

    /** id, member number */
    private String describe;

    /**
     * Instantiates a new Product object<br>
     * Using the overload method, this constructor is used when adding new products
     *
     * @param id product number
     */
    public Product(int id) {
        this.id = id;
    }

    /**
     * Instantiates a new Product object<br>
     * Using the overload method, this constructor is used when adding new products
     *
     * @param name  product name
     * @param price product price
     * @param image product image
     */
    public Product(String name, double price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    /**
     * Instantiates a new Product object<br>
     * Using the overload method, this constructor is used when modifying the
     * product
     *
     * @param id       product number
     * @param name     product name
     * @param price    product price
     * @param image    product image
     * @param describe product description
     */
    public Product(int id, String name, double price, String image, String describe) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.describe = describe;
    }

    /**
     * Get product number
     *
     * @return int returns the product number
     */
    public int getID() {
        return this.id;
    }

    /**
     * Get product name
     *
     * @return String Return product name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get product price
     *
     * @return double Return product price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Get product pictures
     *
     * @return String Return product pictures
     */
    public String getImage() {
        return this.image;
    }

    /**
     * Get product description
     *
     * @return String Return product description
     */
    public String getDescribe() {
        return this.describe;
    }

    /**
     * Get product information
     *
     * @return JSONObject returns product information
     */
    public JSONObject getData() {
        /** Encapsulate all the data required for the product through JSONObject */
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("name", getName());
        jso.put("price", getPrice());
        jso.put("image", getImage());
        jso.put("describe", getDescribe());

        return jso;
    }
}
