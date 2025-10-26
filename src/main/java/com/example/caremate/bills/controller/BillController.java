package com.example.caremate.bills.controller;

import com.example.caremate.bills.dto.BillResponse;
import com.example.caremate.bills.dto.CreateBillRequest;
import com.example.caremate.bills.dto.PaymentRequest;
import com.example.caremate.bills.entity.BillStatus;
import com.example.caremate.bills.service.BillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
@CrossOrigin(origins = "*")
@Tag(name = "Bills")
public class BillController {

    @Autowired
    private BillService billService;

    // Create new bill
    @PostMapping
    public ResponseEntity<BillResponse> createBill(@RequestBody CreateBillRequest request) {
        try {
            BillResponse response = billService.createBill(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get bill by ID
    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable Long id) {
        try {
            BillResponse response = billService.getBillById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get bill by bill number
    @GetMapping("/number/{billNumber}")
    public ResponseEntity<BillResponse> getBillByNumber(@PathVariable String billNumber) {
        try {
            BillResponse response = billService.getBillByNumber(billNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all bills
    @GetMapping
    public ResponseEntity<List<BillResponse>> getAllBills() {
        try {
            List<BillResponse> bills = billService.getAllBills();
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get bills by patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BillResponse>> getBillsByPatientId(@PathVariable Long patientId) {
        try {
            List<BillResponse> bills = billService.getBillsByPatientId(patientId);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get bill by appointment ID
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<BillResponse> getBillByAppointmentId(@PathVariable Long appointmentId) {
        try {
            BillResponse response = billService.getBillByAppointmentId(appointmentId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get bills by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BillResponse>> getBillsByStatus(@PathVariable BillStatus status) {
        try {
            List<BillResponse> bills = billService.getBillsByStatus(status);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get bills by patient ID and status
    @GetMapping("/patient/{patientId}/status/{status}")
    public ResponseEntity<List<BillResponse>> getBillsByPatientIdAndStatus(
            @PathVariable Long patientId,
            @PathVariable BillStatus status) {
        try {
            List<BillResponse> bills = billService.getBillsByPatientIdAndStatus(patientId, status);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get bills by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<BillResponse>> getBillsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        try {
            List<BillResponse> bills = billService.getBillsByDateRange(startDate, endDate);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all pending bills
    @GetMapping("/pending")
    public ResponseEntity<List<BillResponse>> getPendingBills() {
        try {
            List<BillResponse> bills = billService.getPendingBills();
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get pending bills by patient
    @GetMapping("/patient/{patientId}/pending")
    public ResponseEntity<List<BillResponse>> getPendingBillsByPatientId(@PathVariable Long patientId) {
        try {
            List<BillResponse> bills = billService.getPendingBillsByPatientId(patientId);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update bill
    @PutMapping("/{id}")
    public ResponseEntity<BillResponse> updateBill(
            @PathVariable Long id,
            @RequestBody CreateBillRequest request) {
        try {
            BillResponse response = billService.updateBill(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Make payment
    @PostMapping("/{id}/payment")
    public ResponseEntity<BillResponse> makePayment(
            @PathVariable Long id,
            @RequestBody PaymentRequest request) {
        try {
            BillResponse response = billService.makePayment(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cancel bill
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BillResponse> cancelBill(@PathVariable Long id) {
        try {
            BillResponse response = billService.cancelBill(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete bill
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBill(@PathVariable Long id) {
        try {
            billService.deleteBill(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}