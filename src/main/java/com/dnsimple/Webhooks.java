package com.dnsimple;

import java.io.IOException;
import java.util.Map;

import com.dnsimple.response.ListWebhooksResponse;
import com.dnsimple.response.GetWebhookResponse;
import com.dnsimple.response.CreateWebhookResponse;
import com.dnsimple.response.DeleteWebhookResponse;

import com.dnsimple.exception.DnsimpleException;

import com.google.api.client.http.HttpResponse;

/**
 * Provides access to the DNSimple Webhooks API.
 *
 * @see <a href="https://developer.dnsimple.com/v2/webhooks">https://developer.dnsimple.com/v2/webhooks</a>
 */
public class Webhooks {
  private HttpClient httpClient;

  protected Webhooks(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  /**
   * Lists the webhooks in the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/webhooks/#list">https://developer.dnsimple.com/v2/webhooks/#list</a>
   *
   * @param accountId The account ID
   * @return The list webhooks response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public ListWebhooksResponse listWebhooks(String accountId) throws DnsimpleException, IOException {
    return listWebhooks(accountId, null);
  }

  /**
   * Lists the webhooks in the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/webhooks/#list">https://developer.dnsimple.com/v2/webhooks/#list</a>
   *
   * @param accountId The account ID
   * @param options A Map of options to pass to the webhooks API
   * @return The list webhooks response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public ListWebhooksResponse listWebhooks(String accountId, Map<String,Object> options) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.get(accountId + "/webhooks", options);
    return (ListWebhooksResponse) httpClient.parseResponse(response, ListWebhooksResponse.class);
  }

  /**
   * Get a specific webhook associated to an account using the webhook's ID.
   *
   * @see <a href="https://developer.dnsimple.com/v2/webhooks/#get">https://developer.dnsimple.com/v2/webhooks/#get</a>
   *
   * @param accountId The account ID
   * @param webhookId The webhook ID
   * @return The get webhook response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public GetWebhookResponse getWebhook(String accountId, String webhookId) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.get(accountId + "/webhooks/" + webhookId);
    return (GetWebhookResponse) httpClient.parseResponse(response, GetWebhookResponse.class);
  }

  /**
   * Create a webhook in the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/webhooks/#create">https://developer.dnsimple.com/v2/webhooks/#create</a>
   *
   * @param accountId The account ID
   * @param attributes A Map of attributes for constructing the webhook
   * @return The create webhook response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public CreateWebhookResponse createWebhook(String accountId, Map<String,Object> attributes) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.post(accountId + "/webhooks", attributes);
    return (CreateWebhookResponse) httpClient.parseResponse(response, CreateWebhookResponse.class);
  }

  /**
   * Delete a webhook from the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/webhooks/#delete">https://developer.dnsimple.com/v2/webhooks/#delete</a>
   *
   * @param accountId The account ID
   * @param webhookId The webhook ID
   * @return The delete webhook response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public DeleteWebhookResponse deleteWebhook(String accountId, String webhookId) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.delete(accountId + "/webhooks/" + webhookId);
    return (DeleteWebhookResponse) httpClient.parseResponse(response, DeleteWebhookResponse.class);
  }
}
