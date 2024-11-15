package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.GlobalDiscountRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateGlobalDiscountRequest;
import com.rahmandev.califiasfood.dto.response.GlobalDiscountResponse;
import com.rahmandev.califiasfood.entity.GlobalDiscount;
import com.rahmandev.califiasfood.repository.GlobalDiscountRepository;
import com.rahmandev.califiasfood.service.GlobalDiscountService;
import com.rahmandev.califiasfood.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class GlobalDiscountServiceImpl implements GlobalDiscountService {
    private final GlobalDiscountRepository globalDiscountRepository;
    private final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GlobalDiscountResponse create(GlobalDiscountRequest request) {
        GlobalDiscount globalDiscount = globalDiscountRepository.saveAndFlush(GlobalDiscount.builder()
                .name(request.getName())
                .discountAmount(request.getDiscountAmount())
                .startDate(DateUtil.parseDate(request.getStartDate(), PATTERN))
                .endDate(DateUtil.parseDate(request.getEndDate(), PATTERN))
                .isActive(request.getIsActive())
                .build());
        return getGlobalDiscountResponse(globalDiscount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GlobalDiscountResponse update(UpdateGlobalDiscountRequest request) {
        GlobalDiscount globalDiscount = getGlobalDiscountById(request.getId());
        globalDiscount.setName(request.getName());
        globalDiscount.setDiscountAmount(request.getDiscountAmount());
        globalDiscount.setStartDate(DateUtil.parseDate(request.getStartDate(), PATTERN));
        globalDiscount.setEndDate(DateUtil.parseDate(request.getEndDate(), PATTERN));
        globalDiscount.setIsActive(request.getIsActive());
        return getGlobalDiscountResponse(globalDiscount);
    }

    @Override
    public GlobalDiscount getGlobalDiscountById(String id) {
        return globalDiscountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Override
    public GlobalDiscountResponse getById(String id) {
        GlobalDiscount globalDiscount = getGlobalDiscountById(id);
        return getGlobalDiscountResponse(globalDiscount);
    }

    @Override
    public List<GlobalDiscountResponse> getAll() {
        List<GlobalDiscount> globalDiscounts = globalDiscountRepository.findAll();
        return globalDiscounts.stream().map(this::getGlobalDiscountResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusGlobalDiscount(String id) {
        getGlobalDiscountById(id).setIsActive(!getGlobalDiscountById(id).getIsActive());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        GlobalDiscount globalDiscount = getGlobalDiscountById(id);
        globalDiscountRepository.delete(globalDiscount);
    }

    private GlobalDiscountResponse getGlobalDiscountResponse(GlobalDiscount globalDiscount) {
        return GlobalDiscountResponse.builder()
                .id(globalDiscount.getId())
                .name(globalDiscount.getName())
                .discountAmount(globalDiscount.getDiscountAmount())
                .startDate(globalDiscount.getStartDate().toString())
                .endDate(globalDiscount.getEndDate().toString())
                .isActive(globalDiscount.getIsActive())
                .build();
    }
}
