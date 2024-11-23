package com.rahmandev.califiasfood.service;

import com.rahmandev.califiasfood.dto.request.update.UpdateProofOfPaymentImage;
import com.rahmandev.califiasfood.entity.Payment;

public interface PaymentService {
    Payment getById(String id);

    void uploadProofOfPayment(UpdateProofOfPaymentImage request);
}
