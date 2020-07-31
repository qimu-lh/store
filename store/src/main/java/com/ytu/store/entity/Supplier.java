package com.ytu.store.entity;

import java.util.Objects;

public class Supplier extends BaseEntity{

    private Integer gid;
    private String supplierName;
    private String password;

    @Override
    public String toString() {
        return "Supplier{" +
                "gid=" + gid +
                ", supplierName='" + supplierName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier)) return false;
        Supplier supplier = (Supplier) o;
        return gid.equals(supplier.gid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gid);
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
