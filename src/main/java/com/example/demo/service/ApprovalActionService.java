package com.example.demo.service;

import com.example.demo.model.ApprovalAction;
import java.util.List;

public interface ApprovalActionService {
    ApprovalAction recordAction(ApprovalAction action);
    List<ApprovalAction> findByLevelAndAction(Integer levelNumber, String action);
}