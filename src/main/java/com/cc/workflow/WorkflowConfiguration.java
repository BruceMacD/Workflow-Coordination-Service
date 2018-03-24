package com.cc.workflow;

import com.cc.workflow.data.EmpDAO;
import com.cc.workflow.data.EmpInMem;
import com.cc.workflow.data.InsDAO;
import com.cc.workflow.data.InsInMem;
import com.cc.workflow.data.MbrDAO;
import com.cc.workflow.data.MbrInMem;
import com.cc.workflow.data.ReDAO;
import com.cc.workflow.data.ReInMem;
import com.cc.workflow.data.TraceDAO;
import com.cc.workflow.data.TraceInMem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkflowConfiguration {
    @Value("${db.in.mem}")
    boolean dbInMem;

    @Value("${db.path}")
    String dbPath;

    @Value("${db.username}")
    String dbUsername;

    @Value("${db.password}")
    String dbPassword;

    @Bean
    public EmpDAO getEmpDAO() {
        if (dbInMem) {
            return new EmpInMem();
        }
        // TODO: Create SQL implementation
        return new EmpInMem();
    }

    @Bean
    public InsDAO getInsDAO() {
        if (dbInMem) {
            return new InsInMem();
        }
        // TODO: Create SQL implementation
        return new InsInMem();
    }

    @Bean
    public MbrDAO getMbrDAO() {
        if (dbInMem) {
            return new MbrInMem();
        }
        // TODO: Create SQL implementation
        return new MbrInMem();
    }

    @Bean
    public ReDAO getReDAO() {
        if (dbInMem) {
            return new ReInMem();
        }
        // TODO: Create SQL implementation
        return new ReInMem();
    }

    @Bean
    public TraceDAO getTraceDAO() {
        if (dbInMem) {
            return new TraceInMem();
        }
        // TODO: Create SQL implementation
        return new TraceInMem();
    }
}