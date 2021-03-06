package com.karold.swreportapp.repository;

import com.karold.swreportapp.model.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepo extends JpaRepository<Report, Long> {
}
