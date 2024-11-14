package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.update.UpdateDiscountRequest;
import com.rahmandev.califiasfood.dto.response.DiscountResponse;
import com.rahmandev.califiasfood.entity.Discount;
import com.rahmandev.califiasfood.repository.DiscountRepository;
import com.rahmandev.califiasfood.service.DiscountService;
import com.rahmandev.califiasfood.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public DiscountResponse updateDiscount(UpdateDiscountRequest request) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        Discount discount = getDiscountById(request.getId());

        discount.setDiscountAmount(request.getDiscountAmount());
        discount.setStartDate(DateUtil.parseDate(request.getStartDate(), pattern));
        discount.setEndDate(DateUtil.parseDate(request.getEndDate(), pattern));
        discount.setIsActive(request.getIsActive());

        return getDiscountResponse(discount);
    }


    @Override
    public Discount getDiscountById(String id) {
        return discountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Override
    public DiscountResponse getById(String id) {
        Discount discount = getDiscountById(id);
        return getDiscountResponse(discount);
    }

    @Override
    public List<DiscountResponse> getAll() {
        List<Discount> discounts = discountRepository.findAll();

        return discounts.stream().map(DiscountServiceImpl::getDiscountResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusDiscount(String id) {
        getDiscountById(id).setIsActive(!getDiscountById(id).getIsActive());
    }

    private static DiscountResponse getDiscountResponse(Discount discount) {
        return DiscountResponse.builder()
                .id(discount.getId())
                .discountAmount(discount.getDiscountAmount())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .isActive(discount.getIsActive())
                .MenuName(discount.getMenu().getName())
                .MenuPrice(discount.getMenu().getPrice())
                .build();
    }
}