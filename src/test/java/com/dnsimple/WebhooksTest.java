package com.dnsimple;

import com.dnsimple.response.ListWebhooksResponse;
import com.dnsimple.response.GetWebhookResponse;
import com.dnsimple.response.CreateWebhookResponse;
import com.dnsimple.response.DeleteWebhookResponse;

import com.dnsimple.exception.DnsimpleException;
import com.dnsimple.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.api.client.http.HttpMethods;

public class WebhooksTest extends DnsimpleTestBase {

  @Test
  public void testListWebhooksSupportsExtraRequestOptions() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1010/webhooks?foo=bar");

    String accountId = "1010";
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put("foo", "bar");
    httpClient.webhooks.listWebhooks(accountId, options);
  }

  @Test
  public void testListWebhooksProducesWebhookList() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("listWebhooks/success.http"));

    String accountId = "1010";

    ListWebhooksResponse response = httpClient.webhooks.listWebhooks(accountId);

    List<Webhook> webhooks = response.getData();
    assertEquals(2, webhooks.size());
    assertEquals(1, webhooks.get(0).getId().intValue());
  }

  @Test
  public void testGetWebhook() throws DnsimpleException, IOException {
    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/webhooks/1", HttpMethods.GET, resource("getWebhook/success.http"));

    String accountId = "1010";
    String webhookId = "1";

    GetWebhookResponse response = httpClient.webhooks.getWebhook(accountId, webhookId);

    Webhook webhook = response.getData();
    assertEquals(1, webhook.getId().intValue());
    assertEquals("https://webhook.test", webhook.getUrl());
  }

  @Test(expected=ResourceNotFoundException.class)
  public void testGetWebhookWhenNotFound() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("notfound-webhook.http"));

    String accountId = "1010";
    String webhookId = "1";

    httpClient.webhooks.getWebhook(accountId, webhookId);
  }

  @Test
  public void testCreateWebhook() throws DnsimpleException, IOException {
    String accountId = "1010";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("url", "https://webhook.test");

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/webhooks", HttpMethods.POST, attributes, resource("createWebhook/created.http"));

    CreateWebhookResponse response = httpClient.webhooks.createWebhook(accountId, attributes);
    Webhook webhook = response.getData();
    assertEquals(1, webhook.getId().intValue());
    assertEquals("https://webhook.test", webhook.getUrl());
  }

  @Test
  public void testDeleteWebhook() throws DnsimpleException, IOException {
    String accountId = "1010";
    String webhookId = "1";

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/webhooks/1", HttpMethods.DELETE, resource("deleteWebhook/success.http"));

    DeleteWebhookResponse response = httpClient.webhooks.deleteWebhook(accountId, webhookId);
    assertEquals(null, response.getData());
  }
}
