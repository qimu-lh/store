package com.ytu.store.entity;

import java.util.Date;
import java.util.Objects;

public class OrderUser extends BaseEntity {

    private Integer oid;
    private Integer uid;
    private String recvName;
    private String recvPhone;
    private String recvEmail;
    private String recvAddress;
    private Long totalPrice;
    private Integer pay;
    private String state;
    private Date orderTime;
    private Date payTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderUser)) return false;
        OrderUser orderUser = (OrderUser) o;
        return oid.equals(orderUser.oid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oid);
    }

    @Override
    public String toString() {
        return "OrderUser{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", recvName='" + recvName + '\'' +
                ", recvPhone='" + recvPhone + '\'' +
                ", recvEmail='" + recvEmail + '\'' +
                ", recvAddress='" + recvAddress + '\'' +
                ", totalPrice=" + totalPrice +
                ", pay=" + pay +
                ", state='" + state + '\'' +
                ", orderTime=" + orderTime +
                ", payTime=" + payTime +
                '}';
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRecvName() {
        return recvName;
    }

    public void setRecvName(String recvName) {
        this.recvName = recvName;
    }

    public String getRecvPhone() {
        return recvPhone;
    }

    public void setRecvPhone(String recvPhone) {
        this.recvPhone = recvPhone;
    }

    public String getRecvEmail() {
        return recvEmail;
    }

    public void setRecvEmail(String recvEmail) {
        this.recvEmail = recvEmail;
    }

    public String getRecvAddress() {
        return recvAddress;
    }

    public void setRecvAddress(String recvAddress) {
        this.recvAddress = recvAddress;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
