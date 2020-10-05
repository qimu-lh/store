package com.ytu.jierui.store.service.impl;

import com.ytu.jierui.store.entity.Attach;
import com.ytu.jierui.store.mapper.AttachMapper;
import com.ytu.jierui.store.service.IAttachService;
import com.ytu.jierui.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachServiceImpl implements IAttachService {

    @Autowired
    private AttachMapper attachMapper;

    @Override
    public void saveAttach(Attach attach) {
        Integer rows = attachMapper.save(attach);
        if (rows!=1){
            throw new InsertException("插入数据时出现异常！");
        }
    }
}
