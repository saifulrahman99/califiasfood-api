package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.BillRequest;
import com.rahmandev.califiasfood.dto.request.UploadProofOfPaymentRequest;
import com.rahmandev.califiasfood.dto.request.search.SearchBillRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdatePaymentStatusRequest;
import com.rahmandev.califiasfood.dto.response.BillResponse;
import com.rahmandev.califiasfood.entity.Bill;
import org.springframework.data.domain.Page;

public interface BillService {
    BillResponse create(BillRequest request);

    Page<BillResponse> findAll(SearchBillRequest request);

    BillResponse findById(String id);

    Bill getById(String id);

    void updateStatusPayment(UpdatePaymentStatusRequest request);

    void uploadProofOfPayment(UploadProofOfPaymentRequest request);
}
