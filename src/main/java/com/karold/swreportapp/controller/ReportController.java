package com.karold.swreportapp.controller;

import com.karold.swreportapp.controller.payload.CreateReportRequest;
import com.karold.swreportapp.model.report.Report;
import com.karold.swreportapp.service.impl.ReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportServiceImpl reportService;

    @PutMapping("/{id}")
    public ResponseEntity<Void> createOrUpdate(@PathVariable Long id, @RequestBody CreateReportRequest request) throws IOException, InterruptedException {
        reportService.createOrUpdate(id, request.getCharacterPhrase(), request.getPlanetName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReport(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReport(@PathVariable Long id) throws Exception {
        reportService.deleteReportById(id);
        return ResponseEntity.ok("Report with id: " + id + " has been deleted.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllReports() {
        reportService.deleteAllReports();
        return ResponseEntity.ok("All reports have been deleted.");
    }
}
