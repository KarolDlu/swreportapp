package com.karold.swreportapp.repository;

import com.karold.swreportapp.model.report.SimpleReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleReportRepo extends JpaRepository<SimpleReport, Long> {
}
