package com.cc.workflow.services;

import com.cc.workflow.data.mun.MUNServices;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MunService {
    public MUNServices getServices(String mortgageId) {
        MUNServices services = new MUNServices();
        services.mortgageId = mortgageId;
        services.servicesCode = new Random().nextInt(500);
        return services;
    }
}