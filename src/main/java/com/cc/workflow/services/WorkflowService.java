package com.cc.workflow.services;

import com.cc.workflow.data.mbr.MbrUser;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Handles sending calls to trigger workflows
 */
public class WorkflowService {

  final static String LOGIC_APP = "https://prod-06.northcentralus.logic.azure.com:443/workflows/97300de032eb4f8e9b70d9bde8877d14/triggers/manual/paths/invoke?api-version=2016-10-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=kyemEJIts_AfIVtM-LPJq8iXM7cof1B9_9tqlvEVWTk";

  //TODO: May need workflow maps for other services and may be moved
  private HashMap employerWorkflows = new HashMap<>();
  private HashMap reWorkflows = new HashMap<>();

  public  WorkflowService() {
  }

  // send POST to logic app workflow to trigger it
  public void triggerWorkflow(MbrUser user) {

    HttpClient httpClient    = HttpClientBuilder.create().build();
    HttpPost     post          = new HttpPost(LOGIC_APP);
    try {
      ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
      String json = ow.writeValueAsString(user);
      StringEntity postingString = new StringEntity(json);
      post.setEntity(postingString);
      post.setHeader("Content-type", "application/json");
      httpClient.execute(post);
    } catch (Exception e) {
      // log exception
    }
  }

  public void triggerEmployee(String mortgageId) {

    if (employerWorkflows.containsKey(mortgageId)) {
      String url = employerWorkflows.get(mortgageId).toString();

      HttpClient httpClient    = HttpClientBuilder.create().build();
      HttpPost     post          = new HttpPost(url);
      try {
        httpClient.execute(post);
      } catch (Exception e) {
        // log exception
      }
    }
  }

  public void addEmployerCallback(String id, String url) {
    employerWorkflows.put(id, url);
  }

  public boolean employerCallbacksContains(String id) {
    return employerWorkflows.containsKey(id);
  }
}
