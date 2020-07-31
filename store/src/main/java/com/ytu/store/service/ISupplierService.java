package com.ytu.store.service;

import com.ytu.store.entity.Supplier;

public interface ISupplierService {

    Supplier login(String supplierName, String password);
}
