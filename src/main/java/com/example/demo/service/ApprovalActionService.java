package com.example.demo.service;

import com.example.demo.model.ApprovalAction;

public interface ApprovalActionService {

    ApprovalAction save(ApprovalAction action);

    ApprovalAction findByLevelAndAction(Integer level, String action);
}
