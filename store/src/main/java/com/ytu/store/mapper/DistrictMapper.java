package com.ytu.store.mapper;

import com.ytu.store.entity.District;

import java.util.List;

public interface DistrictMapper {

    /**
     * 通过父code查询省份
     * @param parent
     * @return
     */
    List<District> findByParent(String parent);

    /**
     * 根据代号查询名称
     * @param code
     * @return
     */
    District findByCode(String code);
}
