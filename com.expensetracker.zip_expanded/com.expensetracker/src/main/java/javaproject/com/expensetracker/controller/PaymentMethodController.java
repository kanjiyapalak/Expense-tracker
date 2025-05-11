package javaproject.com.expensetracker.controller;

import javaproject.com.expensetracker.entities.PaymentMethod;
import javaproject.com.expensetracker.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    // Get all payment methods
    @GetMapping("/all")
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodService.getAllPaymentMethods();
    }

    // Get a single payment method by ID
    @GetMapping("/get/{id}")
    public PaymentMethod getPaymentMethodById(@PathVariable Long id) {
        return paymentMethodService.getPaymentMethodById(id);
    }

    // Add a new payment method
    @PostMapping("/add")
    public PaymentMethod addPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        return paymentMethodService.addPaymentMethod(paymentMethod);
    }

    // Update an existing payment method
    @PutMapping("/update/{id}")
    public PaymentMethod updatePaymentMethod(@PathVariable Long id, @RequestBody PaymentMethod updatedPaymentMethod) {
        return paymentMethodService.updatePaymentMethod(id, updatedPaymentMethod);
    }

    // Delete a payment method
    @DeleteMapping("/delete/{id}")
    public void deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
    }
}
