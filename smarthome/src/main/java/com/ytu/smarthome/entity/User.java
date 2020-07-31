package com.ytu.smarthome.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.Objects;

public class User extends BaseEntity {

    private Integer uid;
    private String username;
    private String password;
    private String salt;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer temperature;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer humidity;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String airMode;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer airTemperature;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String airWindSpeed;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String lamp1;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String lamp2;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String lamp3;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String lamp4;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String windowMode;
    private Integer isDelete;

    public void setUser(String message) {
        setTemperature(Integer.parseInt(message.substring(message.indexOf("A") + 1, message.indexOf("B"))));
        setHumidity(Integer.parseInt(message.substring(message.indexOf("B") + 1, message.indexOf("C"))));
        setAirMode(message.substring(message.indexOf("C") + 1, message.indexOf("D")));
        setAirTemperature(Integer.parseInt(message.substring(message.indexOf("D") + 1, message.indexOf("E"))));
        setAirWindSpeed(message.substring(message.indexOf("E") + 1, message.indexOf("F")));
        setLamp1(message.substring(message.indexOf("F") + 1, message.indexOf("G")));
        setLamp2(message.substring(message.indexOf("G") + 1, message.indexOf("H")));
        setLamp3(message.substring(message.indexOf("H") + 1, message.indexOf("I")));
        setLamp4(message.substring(message.indexOf("I") + 1, message.indexOf("J")));
        setWindowMode(message.substring(message.indexOf("J") + 1,message.indexOf("K")));
        setModifiedTime(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uid.equals(user.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", airMode='" + airMode + '\'' +
                ", airTemperature=" + airTemperature +
                ", airWindSpeed='" + airWindSpeed + '\'' +
                ", lamp1='" + lamp1 + '\'' +
                ", lamp2='" + lamp2 + '\'' +
                ", lamp3='" + lamp3 + '\'' +
                ", lamp4='" + lamp4 + '\'' +
                ", windowMode='" + windowMode + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }


    public String getAirWindSpeed() {
        return airWindSpeed;
    }

    public void setAirWindSpeed(String airWindSpeed) {
        this.airWindSpeed = airWindSpeed;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getAirMode() {
        return airMode;
    }

    public void setAirMode(String airMode) {
        this.airMode = airMode;
    }

    public Integer getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Integer airTemperature) {
        this.airTemperature = airTemperature;
    }

    public String getLamp1() {
        return lamp1;
    }

    public void setLamp1(String lamp1) {
        this.lamp1 = lamp1;
    }

    public String getLamp2() {
        return lamp2;
    }

    public void setLamp2(String lamp2) {
        this.lamp2 = lamp2;
    }

    public String getLamp3() {
        return lamp3;
    }

    public void setLamp3(String lamp3) {
        this.lamp3 = lamp3;
    }

    public String getLamp4() {
        return lamp4;
    }

    public void setLamp4(String lamp4) {
        this.lamp4 = lamp4;
    }

    public String getWindowMode() {
        return windowMode;
    }

    public void setWindowMode(String windowMode) {
        this.windowMode = windowMode;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}
