package cn.d.domain;

import javax.servlet.ServletOutputStream;
import java.util.List;

public class Information {

    private int s_id;
    private String s_name;
    private String j_name;
    private String s_value;
    private String s_state;


    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getJ_name() {
        return j_name;
    }

    public void setJ_name(String j_name) {
        this.j_name = j_name;
    }

    public String getS_value() {
        return s_value;
    }

    public void setS_value(String s_value) {
        this.s_value = s_value;
    }

    public String getS_state() {
        return s_state;
    }

    public void setS_state(String s_state) {
        this.s_state = s_state;
    }


    @Override
    public String toString() {
        return "Information{" +
                "s_id=" + s_id +
                ", s_name=" + s_name +
                ", j_name='" + j_name + '\'' +
                ", s_value='" + s_value + '\'' +
                ", s_state=" + s_state +
                '}';
    }

}
