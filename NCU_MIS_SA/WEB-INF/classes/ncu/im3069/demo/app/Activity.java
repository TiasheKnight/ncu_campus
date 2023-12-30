package ncu.im3069.demo.app;

import org.json.JSONObject;

public class Activity {

    private int ID;
    private String Activity_Status;
    private String Activity_Name;
    private String Activity_Type;
    private String Activity_Place;
    private int Activity_Publisher_ID;
    private int Maximum_Participant;
    private int Minimum_Participant;
    private String Start_Date;
    private String Start_Time;
    private String End_Date;
    private String End_Time;
    private String Activity_Detail;
    private ActivityHelper AH = ActivityHelper.getHelper();
    private int Activity_Participant;

    public Activity(int ID, String Activity_Status, String Activity_Name, String Activity_Type, String Activity_Place,
            int Activity_Publisher_ID, int Maximum_Participant, int Minimum_Participant, String Start_Date,
            String Start_Time,
            String End_Date, String End_Time, String Activity_Detail, int Activity_Participant) {
        this.ID = ID;
        this.Activity_Status = Activity_Status;
        this.Activity_Name = Activity_Name;
        this.Activity_Type = Activity_Type;
        this.Activity_Place = Activity_Place;
        this.Activity_Publisher_ID = Activity_Publisher_ID;
        this.Maximum_Participant = Maximum_Participant;
        this.Minimum_Participant = Minimum_Participant;
        this.Start_Date = Start_Date;
        this.Start_Time = Start_Time;
        this.End_Date = End_Date;
        this.End_Time = End_Time;
        this.Activity_Detail = Activity_Detail;
        this.Activity_Participant = Activity_Participant; // Initialize to 0, assuming no participants initially
    }
    public Activity(String Activity_Status, String Activity_Name, String Activity_Type, String Activity_Place,
            int Activity_Publisher_ID, int Maximum_Participant, int Minimum_Participant, String Start_Date,
            String Start_Time,String End_Date, String End_Time, String Activity_Detail) {
        this.Activity_Status = Activity_Status;
        this.Activity_Name = Activity_Name;
        this.Activity_Type = Activity_Type;
        this.Activity_Place = Activity_Place;
        this.Activity_Publisher_ID = Activity_Publisher_ID;
        this.Maximum_Participant = Maximum_Participant;
        this.Minimum_Participant = Minimum_Participant;
        this.Start_Date = Start_Date;
        this.Start_Time = Start_Time;
        this.End_Date = End_Date;
        this.End_Time = End_Time;
        this.Activity_Detail = Activity_Detail;
        update();
    }
    public int getID() {
        return this.ID;
    }

    public String getActivity_Status() {
        return this.Activity_Status;
    }

    public String getActivity_Name() {
        return this.Activity_Name;
    }

    public String getActivity_Type() {
        return this.Activity_Type;
    }

    public String getActivity_Place() {
        return this.Activity_Place;
    }

    public int getActivity_Publisher_ID() {
        return this.Activity_Publisher_ID;
    }

    public int getMaximum_Participant() {
        return this.Maximum_Participant;
    }

    public int getMinimum_Participant() {
        return this.Minimum_Participant;
    }

    public String getStart_Date() {
        return this.Start_Date;
    }

    public String getStart_Time() {
        return this.Start_Time;
    }

    public String getEnd_Date() {
        return this.End_Date;
    }

    public String getEnd_Time() {
        return this.End_Time;
    }

    public String getActivity_Detail() {
        return this.Activity_Detail;
    }

    public int getActivity_Participant() {
        return this.Activity_Participant;
    }

    public JSONObject getData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("activity_status", getActivity_Status());
        jso.put("activity_name", getActivity_Name());
        jso.put("activity_type", getActivity_Type());
        jso.put("activity_place", getActivity_Place());
        jso.put("activity_publisher_id", getActivity_Publisher_ID());
        jso.put("maximum_participant", getMaximum_Participant());
        jso.put("minimum_participant", getMinimum_Participant());
        jso.put("start_date", getStart_Date());
        jso.put("start_time", getStart_Time());
        jso.put("end_date", getEnd_Date());
        jso.put("end_time", getEnd_Time());
        jso.put("activity_detail", getActivity_Detail());
        jso.put("activity_participant", getActivity_Participant());
        return jso;
    }

    public JSONObject update() {
    	JSONObject data = new JSONObject();
        
        if(this.ID != 0){
            data = AH.update(this);
        }
        return data;
    }
}
