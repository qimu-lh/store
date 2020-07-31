package com.ytu.store.service.impl;

import com.ytu.store.entity.District;
import com.ytu.store.mapper.DistrictMapper;
import com.ytu.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        return districtMapper.findByParent(parent);
    }

    @Override
    public District getByCode(String code) {
        return districtMapper.findByCode(code);
    }
}
