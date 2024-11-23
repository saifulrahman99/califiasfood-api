package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.DeliveryType;
import com.rahmandev.califiasfood.constant.PaymentStatus;
import com.rahmandev.califiasfood.constant.PaymentType;
import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.BillRequest;
import com.rahmandev.califiasfood.dto.request.UploadProofOfPaymentRequest;
import com.rahmandev.califiasfood.dto.request.search.SearchBillRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdatePaymentStatusRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdateProofOfPaymentImage;
import com.rahmandev.califiasfood.dto.response.BIllDetailToppingResponse;
import com.rahmandev.califiasfood.dto.response.BillDetailResponse;
import com.rahmandev.califiasfood.dto.response.BillResponse;
import com.rahmandev.califiasfood.dto.response.PaymentResponse;
import com.rahmandev.califiasfood.entity.*;
import com.rahmandev.califiasfood.repository.BillRepository;
import com.rahmandev.califiasfood.service.*;
import com.rahmandev.califiasfood.specification.BillSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillDetailService billDetailService;
    private final CustomerService customerService;
    private final MenuService menuService;
    private final BillDetailToppingService billDetailToppingService;
    private final ToppingService toppingService;
    private final PaymentService paymentService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BillResponse create(BillRequest request) {
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        Bill bill = Bill.builder()
                .customer(customer)
                .billDate(new Date())
                .deliveryType(DeliveryType.valueOf(request.getDeliveryType()))
                .payment(Payment.builder()
                        .paymentType(PaymentType.valueOf(request.getPaymentType()))
                        .paymentStatus(PaymentStatus.PENDING)
                        .proofOfPayment(PaymentImage.builder().build())
                        .build())
                .build();
        billRepository.saveAndFlush(bill);

        List<BillDetail> billDetails = new ArrayList<>();

        request.getBillDetails().forEach(billDetailRequest -> {
            Menu menu = menuService.getMenuById(billDetailRequest.getMenuId());
            BillDetail billDetailBuild = BillDetail.builder()
                    .bill(bill)
                    .menu(menu)
                    .price(menu.getPrice())
                    .level(billDetailRequest.getLevel())
                    .qty(billDetailRequest.getQty())
                    .build();
            BillDetail billDetail = billDetailService.create(billDetailBuild);
            billDetails.add(billDetail);

            if (billDetailRequest.getToppings() != null) {
                List<BillDetailTopping> billDetailToppings = billDetailRequest.getToppings().stream().map(billDetailToppingRequest -> {
                    Topping topping = toppingService.getToppingById(billDetailToppingRequest.getToppingId());
                    return BillDetailTopping.builder()
                            .billDetail(billDetail)
                            .topping(topping)
                            .price(topping.getPrice())
                            .build();
                }).toList();
                List<BillDetailTopping> billDetailToppingsSave = billDetailToppingService.createBulk(billDetailToppings);
                billDetailBuild.setToppings(billDetailToppingsSave);
            }
        });
        bill.setBillDetails(billDetails);

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .id(bill.getPayment().getId())
                .paymentType(bill.getPayment().getPaymentType().name())
                .paymentStatus(bill.getPayment().getPaymentStatus().name())
                .proofOfPaymentPath(bill.getPayment().getProofOfPayment().getPath())
                .build();
        List<BillDetailResponse> billDetailResponses = billDetails.stream().map(this::getBillDetailResponse).toList();

        return BillResponse.builder()
                .id(bill.getId())
                .billDate(bill.getBillDate().toString())
                .customerId(bill.getCustomer().getId())
                .customerName(bill.getCustomer().getName())
                .deliveryType(bill.getDeliveryType().name())
                .payment(paymentResponse)
                .billDetails(billDetailResponses)
                .build();
    }

    @Override
    public Page<BillResponse> findAll(SearchBillRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        Specification<Bill> specification = BillSpecification.getSpecification(request);
        Page<Bill> bills = billRepository.findAll(specification, pageable);

        List<BillResponse> billResponse = bills.getContent().stream().map(
                bill -> {
                    PaymentResponse paymentResponse = PaymentResponse.builder()
                            .id(bill.getPayment().getId())
                            .paymentType(bill.getPayment().getPaymentType().name())
                            .paymentStatus(bill.getPayment().getPaymentStatus().name())
                            .proofOfPaymentPath(bill.getPayment().getProofOfPayment().getPath())
                            .build();

                    List<BillDetailResponse> billDetailResponses = bill.getBillDetails().stream().map(this::getBillDetailResponse).toList();

                    return BillResponse.builder()
                            .id(bill.getId())
                            .billDate(bill.getBillDate().toString())
                            .deliveryType(bill.getDeliveryType().name())
                            .customerId(bill.getCustomer().getId())
                            .customerName(bill.getCustomer().getName())
                            .payment(paymentResponse)
                            .billDetails(billDetailResponses)
                            .build();
                }
        ).toList();

        return new PageImpl<>(billResponse, pageable, bills.getTotalElements());
    }

    @Override
    public BillResponse findById(String id) {
        Bill bill = getById(id);
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .id(bill.getPayment().getId())
                .paymentType(bill.getPayment().getPaymentType().name())
                .paymentStatus(bill.getPayment().getPaymentStatus().name())
                .proofOfPaymentPath(bill.getPayment().getProofOfPayment().getPath())
                .build();
        List<BillDetailResponse> billDetailResponses = bill.getBillDetails().stream().map(this::getBillDetailResponse).toList();

        return BillResponse.builder()
                .id(bill.getId())
                .billDate(bill.getBillDate().toString())
                .customerId(bill.getCustomer().getId())
                .customerName(bill.getCustomer().getName())
                .deliveryType(bill.getDeliveryType().name())
                .payment(paymentResponse)
                .billDetails(billDetailResponses)
                .build();
    }

    @Override
    public Bill getById(String id) {
        return billRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.USER_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusPayment(UpdatePaymentStatusRequest request) {
        Bill bill = getById(request.getBillId());
        bill.getPayment().setPaymentStatus(PaymentStatus.valueOf(request.getPaymentStatus()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void uploadProofOfPayment(UploadProofOfPaymentRequest request) {
        Bill bill = getById(request.getBillId());
        UpdateProofOfPaymentImage updateProofOfPaymentImage = UpdateProofOfPaymentImage.builder()
                .bill(bill)
                .image(request.getImage()).build();
        paymentService.uploadProofOfPayment(updateProofOfPaymentImage);
    }

    private BillDetailResponse getBillDetailResponse(BillDetail billDetail) {
        List<BIllDetailToppingResponse> bIllDetailToppingResponses = billDetail.getToppings().stream().map(billDetailTopping -> BIllDetailToppingResponse.builder()
                .id(billDetailTopping.getId())
                .toppingId(billDetailTopping.getTopping().getId())
                .toppingName(billDetailTopping.getTopping().getName())
                .price(billDetailTopping.getPrice())
                .build()).toList();

        return BillDetailResponse.builder()
                .id(billDetail.getId())
                .menuId(billDetail.getMenu().getId())
                .menuName(billDetail.getMenu().getName())
                .price(billDetail.getPrice())
                .level(billDetail.getLevel())
                .qty(billDetail.getQty())
                .billDetailToppings(bIllDetailToppingResponses)
                .build();
    }
}
