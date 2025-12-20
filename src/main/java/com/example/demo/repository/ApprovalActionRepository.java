package com.example.demo.repository;

import com.example.demo.model.ApprovalAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApprovalActionRepository extends JpaRepository<ApprovalAction, Long> {
    
    @Query("SELECT a FROM ApprovalAction a WHERE a.levelNumber = :levelNumber AND a.action = :action")
    List<ApprovalAction> findByLevelAndAction(@Param("levelNumber") Integer levelNumber, @Param("action") String action);
}