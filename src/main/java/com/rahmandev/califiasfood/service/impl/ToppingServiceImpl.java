package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.ToppingRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateToppingRequest;
import com.rahmandev.califiasfood.dto.response.ToppingResponse;
import com.rahmandev.califiasfood.entity.Topping;
import com.rahmandev.califiasfood.repository.ToppingRepository;
import com.rahmandev.califiasfood.service.ToppingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ToppingServiceImpl implements ToppingService {
    private final ToppingRepository toppingRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ToppingResponse create(ToppingRequest request) {
        Topping topping = toppingRepository.saveAndFlush(
                Topping.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .build());
        return ToppingResponse.builder()
                .id(topping.getId())
                .name(topping.getName())
                .price(topping.getPrice())
                .build();
    }

    @Override
    public List<ToppingResponse> getAll() {
        List<Topping> toppings = toppingRepository.findAllToppings();
        return toppings.stream().map(
                topping -> {
                    return ToppingResponse.builder()
                            .id(topping.getId())
                            .name(topping.getName())
                            .price(topping.getPrice())
                            .build();
                }
        ).toList();
    }

    @Override
    public Topping getToppingById(String id) {
        return toppingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ToppingResponse update(UpdateToppingRequest request) {
        Topping topping = getToppingById(request.getId());
        topping.setName(request.getName());
        topping.setPrice(request.getPrice());

        return ToppingResponse.builder()
                .id(topping.getId())
                .name(topping.getName())
                .price(topping.getPrice())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Topping topping = getToppingById(id);
        topping.setDeleteAt(new Date());
    }
}
