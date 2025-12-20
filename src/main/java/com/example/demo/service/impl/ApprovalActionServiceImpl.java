package com.example.demo.service.impl;

import com.example.demo.model.ApprovalAction;
import com.example.demo.repository.ApprovalActionRepository;
import com.example.demo.service.ApprovalActionService;
import org.springframework.stereotype.Service;

@Service
public class ApprovalActionServiceImpl implements ApprovalActionService {

    private final ApprovalActionRepository repository;

    public ApprovalActionServiceImpl(ApprovalActionRepository repository) {
        this.repository = repository;
    }

    @Override
    public ApprovalAction recordAction(ApprovalAction action) {
        return repository.save(action);
    }
}
