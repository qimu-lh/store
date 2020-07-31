package com.ytu.store.service;

import com.ytu.store.entity.Operator;

public interface IOperatorService {

    Operator login(String operatorName, String password);
}
