package com.rahmandev.califiasfood.service.impl;

import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.update.UpdateProofOfPaymentImage;
import com.rahmandev.califiasfood.entity.Payment;
import com.rahmandev.califiasfood.entity.PaymentImage;
import com.rahmandev.califiasfood.repository.PaymentRepository;
import com.rahmandev.califiasfood.service.CloudinaryService;
import com.rahmandev.califiasfood.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public Payment getById(String id) {
        return paymentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void uploadProofOfPayment(UpdateProofOfPaymentImage request) {
        PaymentImage proofOfPayment = request.getBill().getPayment().getProofOfPayment();

        MultipartFile image = request.getImage();
        String uniqueFilename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        String url = cloudinaryService.uploadFile(image, "payment");

        proofOfPayment.setContentType(image.getContentType());
        proofOfPayment.setSize(image.getSize());
        proofOfPayment.setPath(url);
    }
}
