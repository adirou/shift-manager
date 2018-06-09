package my.ungersinc.user.shiftmanager;



/**
 * Created by USER on 29/06/2015.
 */

public class Event {

    public String date,location,manager,comments;
    public int hourStart,hourEnd,minuteStart,minuteEnd,id,typeEvent;
    public double sum,sumCash,hours,manualSalary,distance,travelers,parking,tipForSal,privateTip,waitersTip;
    public  int isSmsSent;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Event() {
    }

    public Event(String date, int typeEvent, double distance,double travelers, double parking, double tipForSal,
                 double privateTip, double waitersTip, int hourStart, int minuteStart, int hourEnd,
                 int minuteEnd, double sum, double sumCash,double hours,String location,String manager,int isSmsSent,double manualSalary, String comments) {
        this.date = date;
        this.typeEvent=typeEvent;
        this.distance = distance;
        this.travelers = travelers;
        this.parking = parking;
        this.tipForSal = tipForSal;
        this.privateTip = privateTip;
        this.waitersTip = waitersTip;
        this.hourStart = hourStart;
        this.minuteStart = minuteStart;
        this.hourEnd = hourEnd;
        this.minuteEnd = minuteEnd;
        this.sum = sum;
        this.sumCash = sumCash;
        this.hours=hours;
        this.location=location;
        //new
        this.manager=manager;
        this.isSmsSent=isSmsSent;
        this.manualSalary = manualSalary;
        this.comments= comments;


    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public double getManualSalary() {
        return manualSalary;
    }

    public void setManualSalary(double manualSalary) {
        this.manualSalary = manualSalary;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHourStart() {
        return hourStart;
    }

    public void setHourStart(int hourStart) {
        this.hourStart = hourStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }

    public int getMinuteStart() {
        return minuteStart;
    }

    public void setMinuteStart(int minuteStart) {
        this.minuteStart = minuteStart;
    }

    public int getMinuteEnd() {
        return minuteEnd;
    }

    public void setMinuteEnd(int minuteEnd) {
        this.minuteEnd = minuteEnd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTravelers() {
        return travelers;
    }

    public void setTravelers(double travelers) {
        this.travelers = travelers;
    }

    public double getParking() {
        return parking;
    }

    public void setParking(double parking) {
        this.parking = parking;
    }

    public double getTipForSal() {
        return tipForSal;
    }

    public void setTipForSal(double tipForSal) {
        this.tipForSal = tipForSal;
    }

    public double getPrivateTip() {
        return privateTip;
    }

    public void setPrivateTip(double privateTip) {
        this.privateTip = privateTip;
    }

    public double getWaitersTip() {
        return waitersTip;
    }

    public void setWaitersTip(double waitersTip) {
        this.waitersTip = waitersTip;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getSumCash() {
        return sumCash;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public int getIsSmsSent() {
        return isSmsSent;
    }

    public void setIsSmsSent(int isSmsSent) {
        this.isSmsSent = isSmsSent;
    }

    public void setSumCash(double sumCash) {
        this.sumCash = sumCash;
    }
}
