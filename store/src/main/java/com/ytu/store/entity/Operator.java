package com.ytu.store.entity;

import java.util.Objects;

public class Operator extends BaseEntity {

    private Integer yid;
    private String operatorName;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operator)) return false;
        Operator operator = (Operator) o;
        return yid.equals(operator.yid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(yid);
    }

    @Override
    public String toString() {
        return "Operator{" +
                "yid=" + yid +
                ", operatorName='" + operatorName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getYid() {
        return yid;
    }

    public void setYid(Integer yid) {
        this.yid = yid;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
