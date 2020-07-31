package com.ytu.store.service;

import com.ytu.store.entity.District;

import java.util.List;

public interface IDistrictService {

    List<District> getByParent(String parent);

    District getByCode(String code);
}
