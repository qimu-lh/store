package com.ytu.store.mapper;

import com.ytu.store.entity.Operator;

public interface OperatorMapper {

    Operator findByUsername(String operatorName);
}
