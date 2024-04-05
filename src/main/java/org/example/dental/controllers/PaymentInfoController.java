package org.example.dental.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dental.models.PaymentInfo;
import org.example.dental.services.PaymentInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentInfoController implements RestControllerInt<PaymentInfo, Integer> {

    private final PaymentInfoService paymentInfoService;

    @PostMapping("/upload")
    public ResponseEntity<Void> upload() {
        paymentInfoService.uploadFromFile();
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping
    public ResponseEntity<PaymentInfo> save(@RequestBody PaymentInfo paymentInfo) {
        PaymentInfo savedPaymentInfo = paymentInfoService.saveOrUpdate(paymentInfo);
        return ResponseEntity.ok(savedPaymentInfo);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PaymentInfo> update(@PathVariable Integer id, @RequestBody PaymentInfo paymentInfo) {
        PaymentInfo existingPaymentInfo = paymentInfoService.getById(id).orElse(null);
        if (existingPaymentInfo == null) {
            return ResponseEntity.notFound().build();
        }
        paymentInfo.setId(id);
        PaymentInfo updatedPaymentInfo = paymentInfoService.saveOrUpdate(paymentInfo);
        return ResponseEntity.ok(updatedPaymentInfo);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<PaymentInfo>> getAll() {
        List<PaymentInfo> paymentInfos = paymentInfoService.getAll();
        return ResponseEntity.ok(paymentInfos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PaymentInfo> getById(@PathVariable Integer id) {
        PaymentInfo paymentInfo = paymentInfoService.getById(id).orElse(null);
        if (paymentInfo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paymentInfo);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        PaymentInfo existingPaymentInfo = paymentInfoService.getById(id).orElse(null);
        if (existingPaymentInfo == null) {
            return ResponseEntity.notFound().build();
        }
        paymentInfoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
