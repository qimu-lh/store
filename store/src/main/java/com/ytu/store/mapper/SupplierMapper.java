package com.ytu.store.mapper;

import com.ytu.store.entity.Supplier;

public interface SupplierMapper {

    Supplier findByUsername(String supplierName);
}
