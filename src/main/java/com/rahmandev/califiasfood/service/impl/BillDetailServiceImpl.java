package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.entity.BillDetail;
import com.rahmandev.califiasfood.repository.BillDetailRepository;
import com.rahmandev.califiasfood.service.BillDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class BillDetailServiceImpl implements BillDetailService {
    private final BillDetailRepository billDetailRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BillDetail create(BillDetail billDetail) {
        return billDetailRepository.saveAndFlush(billDetail);
    }


}
