package com.example.gibbidi.FYL;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Task")
public class Task extends ParseObject{
	public Task(){

	}

	public boolean isCompleted(){
		return getBoolean("completed");
	}

	public void setCompleted(boolean complete){
		put("completed", complete);
	}

    public String getHotelName(){
        return getString("HotelName");
    }
    public String getHotelPrice(){
        return getString("HotelPrice");
    }
    public String getHotelLocation(){
        return getString("HotelLocation");
    }
    //public ArrayList<String> getBookedDay() { return getArray("BookedDay");}

    public boolean getMon(){return getBoolean("Mon");}
    public boolean getTue(){return getBoolean("Tue");}
    public boolean getWed(){return getBoolean("Wed");}
    public boolean getThu(){return getBoolean("Thu");}
    public boolean getFri(){return getBoolean("Fri");}
    public boolean getSat(){return getBoolean("Sat");}
    public boolean getSun(){return getBoolean("Sun");}

    public void setHotelName(String HotelName){put("HotelName", HotelName);}
    public void setHotelPrice(String HotelPrice) { put("HotelPrice",HotelPrice); }
    public void setHotelLocation(String HotelLocation) { put("HotelLocation", HotelLocation);}
    //public void setBookedDay(ArrayList<String> BookedDay) {put("BookedDay",BookedDay);}
    //public void setUserType(String UserType) {put("UserType",UserType);}

    public void setMon(boolean Mon){put("Mon",Mon);}
    public void setTue(boolean Tue){put("Tue",Tue);}
    public void setWed(boolean Wed){put("Wed",Wed);}
    public void setThu(boolean Thu){put("Thu",Thu);}
    public void setFri(boolean Fri){put("Fri",Fri);}
    public void setSat(boolean Sat){put("Sat",Sat);}
    public void setSun(boolean Sunday){put("Sun",Sunday);}


    public void setUser(ParseUser currentUser) {
		put("user", currentUser);
	}
}
