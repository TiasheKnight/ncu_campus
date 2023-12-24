package ncu.im3069.demo.app;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import java.util.*;
import org.json.*;

/**
 * The Class Member
 * Member class has attributes and methods required for a member, and stores business logic related to the member.
 */
public class Member {
    
    /** id, member number */
    private int id;
    
    /** Authority, member authority */
    private String authority;
    
    /** First_Name, member first name */
    private String first_name;
    
    /** Last_Name, member last name */
    private String last_name;
    
    /** Birthday, member birthday */
    private String birthday;
    
    /** email, member email */
    private String email;
    
    /** Phone, member phone */
    private String phone;
    
    /** Password, member password */
    private String password;
    
    /** User_Name, member username */
    private String user_name;
    
    /** mh, MemberHelper object with methods related to Member (Singleton) */
    private MemberHelper mh = MemberHelper.getHelper();
    
    /**
     * Instantiates a new Member object using overloaded method for creating a new member.
     *
     * @param first_name member first name
     * @param last_name member last name
     * @param birthday member birthday
     * @param email member email
     * @param user_name member username
     * @param phone member phone
     */
    public Member(String first_name, String last_name, String birthday, String email, String user_name, String phone) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthday = birthday;
        this.email = email;
        this.user_name = user_name;
        this.phone = phone;
    }

    /**
     * Instantiates a new Member object using overloaded method for updating a member.
     *
     * @param id member number
     * @param first_name member first name
     * @param last_name member last name
     * @param birthday member birthday
     * @param email member email
     * @param phone member phone
     * @param user_name member username
     */
    public Member(int id, String first_name, String last_name, String birthday, String email, String phone, String user_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.user_name = user_name;
        getMemberOrganizationFromDB(); // Assuming this method needs to be called
    }

    /**
     * Instantiates a new Member object using overloaded method for querying a member.
     *
     * @param id member number
     * @param user_name member username
     */
    public Member(int id, String user_name) {
        this.id = id;
        this.user_name = user_name;
    }
    
    /**
     * Get member number.
     *
     * @return int returns the member number
     */
    public int getID() {
        return this.id;
    }

    /**
     * Get member authority.
     *
     * @return String Return member authority
     */
    public String getAuthority() {
        return this.authority;
    }

    /**
     * Get member first name.
     *
     * @return String Return member first name
     */
    public String getFirstName() {
        return this.first_name;
    }

    /**
     * Get member last name.
     *
     * @return String Return member last name
     */
    public String getLastName() {
        return this.last_name;
    }

    /**
     * Get member password.
     *
     * @return String Return member password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Get member birthday.
     *
     * @return String Return member birthday
     */
    public String getBirthday() {
        return this.birthday;
    }

    /**
     * Get member email.
     *
     * @return String Return member email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get member phone.
     *
     * @return String Return member phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Get member username.
     *
     * @return String Return member username
     */
    public String getUser_Name() {
        return this.user_name;
    }

    /**
     * Update member data.
     *
     * @return JSONObject returns updated member data
     */
    public JSONObject update() {
        JSONObject data = new JSONObject();
        // Update logic here
        // ...

        return data;
    }

    /**
     * Get member data.
     *
     * @return JSONObject returns member data
     */
    public JSONObject getData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("authority", getAuthority());
        jso.put("first_name", getFirstName());
        jso.put("last_name", getLastName());
        jso.put("password", getPassword());
        jso.put("birthday", getBirthday());
        jso.put("email", getEmail());
        jso.put("phone", getPhone());
        jso.put("user_name", getUser_Name());

        return jso;
    }

    /**
     * Get member organization data.
     *
     * @return ArrayList<Member_Organization> returns member organization data
     */
    public ArrayList<Member_Organization> getMemberOrganization() {
        // Assuming this method returns a list of Member_Organization objects
    	return this.list;
    	//JSONArray result = new JSONArray();
    	//for (int i = 0; i < this.list.size(); i++) {
        //    result.put(this.list.get(i).getMemberOrganizationFromDB());
        }

    /**
     * Get member organization data from the database.
     */
    private void getMemberOrganizationFromDB() {
        // Implementation of fetching member organization from the database
    	 ArrayList<OrderItem> data = oph.getOrderProductByOrderId(this.id);
         this.list = data;
         
        //return mh.getMemberOrganization(this);
    }
}
