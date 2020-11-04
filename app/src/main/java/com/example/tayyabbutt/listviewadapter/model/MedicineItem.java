package com.example.tayyabbutt.listviewadapter.model;

/**
 * Created by Tayyab Butt on 12/27/2017.
 */
public class MedicineItem {

    String _medicineId;
    String name;
    String phone;
    String time;
    String date;
    String daysname;
    String radio_group;
    String _weekdaysid;
    String _dozetakenhistoryid;
    String istaken;
    String _medicine_repeat_days_id;
    String days;
    int timeFormat;

    public int getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(int timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String get_medicineId() {
        return _medicineId;
    }

    public void set_medicineId(String _medicineId) {
        this._medicineId = _medicineId;
    }

    public String getDaysname() {
        return daysname;
    }

    public void setDaysname(String daysname) {
        this.daysname = daysname;
    }

    public String getRadio_group() {
        return radio_group;
    }

    public void setRadio_group(String radio_group) {
        this.radio_group = radio_group;
    }

    public String get_weekdaysid() {
        return _weekdaysid;
    }

    public void set_weekdaysid(String _weekdaysid) {
        this._weekdaysid = _weekdaysid;
    }

    public String get_dozetakenhistoryid() {
        return _dozetakenhistoryid;
    }

    public void set_dozetakenhistoryid(String _dozetakenhistoryid) {
        this._dozetakenhistoryid = _dozetakenhistoryid;
    }

    public String getIstaken() {
        return istaken;
    }

    public void setIstaken(String istaken) {
        this.istaken = istaken;
    }

    public String get_medicine_repeat_days_id() {
        return _medicine_repeat_days_id;
    }

    public void set_medicine_repeat_days_id(String _medicine_repeat_days_id) {
        this._medicine_repeat_days_id = _medicine_repeat_days_id;
    }

    public String getSlno() {
        return _medicineId;
    }

    public void setSlno(String _medicineId) {
        this._medicineId = _medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
