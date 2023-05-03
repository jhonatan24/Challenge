package com.evaluation.tenpo.repository;

import com.evaluation.tenpo.model.AuditFormula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditFormulaRepository extends JpaRepository<AuditFormula,Long> {

    @Query("select * from  AuditFormula order by createDate desc limit 1 ")
    public Optional<AuditFormula> findLastCreateDate();

}
