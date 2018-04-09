package com.cc.workflow;

import com.cc.workflow.data.emp.EmpDAO;
import com.cc.workflow.data.emp.EmpInMem;
import com.cc.workflow.data.ins.InsDAO;
import com.cc.workflow.data.ins.InsInMem;
import com.cc.workflow.data.mbr.MbrDAO;
import com.cc.workflow.data.mbr.MbrInMem;
import com.cc.workflow.data.re.ReDAO;
import com.cc.workflow.data.re.ReInMem;
import com.cc.workflow.data.trace.TraceDAO;
import com.cc.workflow.data.trace.TraceInMem;

import com.cc.workflow.services.WorkflowService;
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
//        //if (dbInMem) {
//            return new EmpInMem();
//        }
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

    @Bean
    public WorkflowService getWorkflowService() {
        return new WorkflowService();
    }
}