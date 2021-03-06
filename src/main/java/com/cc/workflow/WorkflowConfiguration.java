package com.cc.workflow;

import com.cc.workflow.data.emp.EmpDAO;
import com.cc.workflow.data.emp.EmpInMem;
import com.cc.workflow.data.emp.EmpSQL;
import com.cc.workflow.data.ins.InsDAO;
import com.cc.workflow.data.ins.InsInMem;
import com.cc.workflow.data.ins.InsSQL;
import com.cc.workflow.data.mbr.MbrDAO;
import com.cc.workflow.data.mbr.MbrInMem;
import com.cc.workflow.data.mbr.MbrSQL;
import com.cc.workflow.data.re.ReDAO;
import com.cc.workflow.data.re.ReInMem;
import com.cc.workflow.data.re.ReSQL;
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
        if (dbInMem) {
            return new EmpInMem();
        }
        return new EmpSQL();
    }

    @Bean
    public InsDAO getInsDAO() {
        if (dbInMem) {
            return new InsInMem();
        }
        return new InsSQL();
    }

    @Bean
    public MbrDAO getMbrDAO() {
        if (dbInMem) {
            return new MbrInMem();
        }
        return new MbrSQL();
    }

    @Bean
    public ReDAO getReDAO() {
        if (dbInMem) {
            return new ReInMem();
        }
        return new ReSQL();
    }

    @Bean
    public TraceDAO getTraceDAO() {
        return new TraceInMem();
    }

    @Bean
    public WorkflowService getWorkflowService() {
        return new WorkflowService();
    }
}