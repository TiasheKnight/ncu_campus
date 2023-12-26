package ncu.im3069.demo.app;

import org.json.JSONObject;

public class Activity {

    private int ID;
    private String Activity_Status;
    private String Activity_Name;
    private String Activity_Type;
    private String Activity_Place;
    private int Activity_Holder_ID;
    private int Maximum_Participant;
    private int Minimum_Participant;
    private String Start_Date;
    private String Start_Time;
    private String End_Date;
    private String End_Time;
    private String Published_Date;
    private String Published_Time;
    private String Activity_Detail;
    private ActivityHelper AH = ActivityHelper.getHelper();
    private int Activity_Participant;

    public Activity(int ID, String Activity_Status, String Activity_Name, String Activity_Type, String Activity_Place,
            int Activity_Holder_ID, int Maximum_Participant, int Minimum_Participant, String Start_Date,
            String Start_Time,
            String End_Date, String End_Time, String Published_Date, String Published_Time, String Activity_Detail) {
        this.ID = ID;
        this.Activity_Status = Activity_Status;
        this.Activity_Name = Activity_Name;
        this.Activity_Type = Activity_Type;
        this.Activity_Place = Activity_Place;
        this.Activity_Holder_ID = Activity_Holder_ID;
        this.Maximum_Participant = Maximum_Participant;
        this.Minimum_Participant = Minimum_Participant;
        this.Start_Date = Start_Date;
        this.Start_Time = Start_Time;
        this.End_Date = End_Date;
        this.End_Time = End_Time;
        this.Published_Date = Published_Date;
        this.Published_Time = Published_Time;
        this.Activity_Detail = Activity_Detail;
        this.Activity_Participant = 0; // Initialize to 0, assuming no participants initially
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

    public int getActivity_Holder_ID() {
        return this.Activity_Holder_ID;
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

    public String getPublished_Date() {
        return this.Published_Date;
    }

    public String getPublished_Time() {
        return this.Published_Time;
    }

    public String getActivity_Detail() {
        return this.Activity_Detail;
    }

    public int getActivity_Participant() {
        return this.Activity_Participant;
    }

    public JSONObject getData() {
        JSONObject jso = new JSONObject();
        jso.put("ID", getID());
        jso.put("Activity_Status", getActivity_Status());
        jso.put("Activity_Name", getActivity_Name());
        jso.put("Activity_Type", getActivity_Type());
        jso.put("Activity_Place", getActivity_Place());
        jso.put("Activity_Holder_ID", getActivity_Holder_ID());
        jso.put("Maximum_Participant", getMaximum_Participant());
        jso.put("Minimum_Participant", getMinimum_Participant());
        jso.put("Start_Date", getStart_Date());
        jso.put("Start_Time", getStart_Time());
        jso.put("End_Date", getEnd_Date());
        jso.put("End_Time", getEnd_Time());
        jso.put("Published_Date", getPublished_Date());
        jso.put("Published_Time", getPublished_Time());
        jso.put("Activity_Detail", getActivity_Detail());
        jso.put("Activity_Participant", getActivity_Participant());
        return jso;
    }

    public JSONObject update() {
        JSONObject data = new JSONObject();
        // Update logic here
        // ...

        return data;
    }
}
