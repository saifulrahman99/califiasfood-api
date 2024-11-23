package com.rahmandev.califiasfood.controller;

import com.rahmandev.califiasfood.constant.APIUrl;
import com.rahmandev.califiasfood.constant.ResponseMessage;
import com.rahmandev.califiasfood.dto.request.BillRequest;
import com.rahmandev.califiasfood.dto.request.UploadProofOfPaymentRequest;
import com.rahmandev.califiasfood.dto.request.search.SearchBillRequest;
import com.rahmandev.califiasfood.dto.request.update.UpdatePaymentStatusRequest;
import com.rahmandev.califiasfood.dto.response.BillResponse;
import com.rahmandev.califiasfood.dto.response.CommonResponse;
import com.rahmandev.califiasfood.dto.response.PagingResponse;
import com.rahmandev.califiasfood.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.BILL_API)
public class BillController {
    private final BillService billService;

    @PostMapping
    public ResponseEntity<CommonResponse<BillResponse>> create(@RequestBody BillRequest billRequest) {
        BillResponse billResponse = billService.create(billRequest);
        CommonResponse<BillResponse> response = CommonResponse.<BillResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(billResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<String>> updateStatusPayment(@RequestBody UpdatePaymentStatusRequest request) {
        billService.updateStatusPayment(request);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<BillResponse>>> getAll(
            @RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "25") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "billDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction
    ) {
        SearchBillRequest request = SearchBillRequest.builder()
                .q(q)
                .startDate(startDate)
                .endDate(endDate)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<BillResponse> bills = billService.findAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(bills.getTotalPages())
                .totalElement(bills.getTotalElements())
                .page(bills.getPageable().getPageNumber() + 1)
                .size(bills.getPageable().getPageSize())
                .hasNext(bills.hasNext())
                .hasPrevious(bills.hasPrevious())
                .build();
        CommonResponse<List<BillResponse>> response = CommonResponse.<List<BillResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(bills.getContent())
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<BillResponse>> getById(@PathVariable String id) {
        BillResponse billResponse = billService.findById(id);
        CommonResponse<BillResponse> response = CommonResponse.<BillResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(billResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(
            path = "/upload-invoice",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> uploadInvoice(
            @RequestPart(name = "bill_id") String billId,
            @RequestParam(name = "image", required = true) MultipartFile image
    ) {
        UploadProofOfPaymentRequest request = UploadProofOfPaymentRequest.builder()
                .billId(billId)
                .image(image)
                .build();
        billService.uploadProofOfPayment(request);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }
}
