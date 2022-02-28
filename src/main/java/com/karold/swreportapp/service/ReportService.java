package com.karold.swreportapp.service;

import com.karold.swreportapp.model.report.Report;

import java.util.List;

public interface ReportService {

    void createOrUpdate(Long id, String searchPhrase, String planetName);

    Report getReportById(Long id);

    List<Report> getAllReports();

    void deleteReportById(Long id);

    void deleteAllReports();
}
