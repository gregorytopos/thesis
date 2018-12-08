package hu.ppke.itk.android.onlab.cellinfo;

/**
 * Created by User on 3.27.2018.
 */

public class Measurement {

    //Fields
    private int id;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private String name;
    private int lac;
    private int cid;
    private int mccmnc;
    private int sgn;
    private int band;

    //Getters & setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}

    public int getMonth() {return month;}
    public void setMonth(int month) {this.month = month;}

    public int getDay() {return day;}
    public void setDay(int day) {this.day = day;}

    public int getHour() {return hour;}
    public void setHour(int hour) {this.hour = hour;}

    public int getMinute() {return minute;}
    public void setMinute(int minute) {this.minute = minute;}

    public int getSecond() {return second;}
    public void setSecond(int second) {this.second = second;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getLac() {return lac;}
    public void setLac(int lac) {this.lac = lac;}

    public int getCid() {return cid;}
    public void setCid(int cid) {this.cid = cid;}

    public int getMccmnc() {return mccmnc;}
    public void setMccmnc(int mccmnc) {this.mccmnc = mccmnc;}

    public int getSgn() {return sgn;}
    public void setSgn(int sgn) {this.sgn = sgn;}

    public int getBand() {return band;}
    public void setBand(int band) {this.band = band;}

    //Constructor
    public Measurement(int id, int year, int month, int day, int hour, int minute, int second, String name, int lac, int cid, int mccmnc, int sgn, int band) {
        this.id = id;
        this.year = year;
        this.month = month + 1;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.name = name;
        this.lac = lac;
        this.cid = cid;
        this.mccmnc = mccmnc;
        this.sgn = sgn;
        this.band = band;
    }

    public Measurement(int year, int month, int day, int hour, int minute, int second, String name, int lac, int cid, int mccmnc, int sgn, int band) {
        this.year = year;
        this.month = month + 1;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.name = name;
        this.lac = lac;
        this.cid = cid;
        this.mccmnc = mccmnc;
        this.sgn = sgn;
        this.band = band;
    }

    public Measurement(int year, int month, int day, int hour, int minute, int second, int lac, int cid, int mccmnc, int sgn, int band) {
        this.year = year;
        this.month = month + 1;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.lac = lac;
        this.cid = cid;
        this.mccmnc = mccmnc;
        this.sgn = sgn;
        this.band = band;
    }

    //Empty constructor
    public Measurement(){}
}
