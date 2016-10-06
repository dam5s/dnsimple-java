package com.dnsimple;

import com.dnsimple.response.EnableAutoRenewalResponse;
import com.dnsimple.response.DisableAutoRenewalResponse;

import com.dnsimple.exception.DnsimpleException;
import com.dnsimple.exception.ResourceNotFoundException;

import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.api.client.http.HttpMethods;

public class RegistrarAutoRenewTest extends DnsimpleTestBase {

  @Test
  public void testEnableAutoRenewal() throws DnsimpleException, IOException {
    String accountId = "1010";
    String domainId = "example.com";

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/example.com/auto_renewal", HttpMethods.PUT, null, resource("enableAutoRenewal/success.http"));

    EnableAutoRenewalResponse response = httpClient.registrar.enableAutoRenewal(accountId, domainId);
    assertEquals(null, response.getData());
  }

  @Test(expected=ResourceNotFoundException.class)
  public void testEnableAutoRenewalDomainDoesNotExist() throws DnsimpleException, IOException {
    String accountId = "1010";
    String domainId = "0";

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/0/auto_renewal", HttpMethods.PUT, null, resource("notfound-domain.http"));

    httpClient.registrar.enableAutoRenewal(accountId, domainId);
  }

  @Test
  public void testDisableAutoRenewal() throws DnsimpleException, IOException {
    String accountId = "1010";
    String domainId = "example.com";

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/example.com/auto_renewal", HttpMethods.DELETE, null, resource("disableAutoRenewal/success.http"));

    DisableAutoRenewalResponse response = httpClient.registrar.disableAutoRenewal(accountId, domainId);
    assertEquals(null, response.getData());
  }

  @Test(expected=ResourceNotFoundException.class)
  public void testDisableAutoRenewalDomainDoesNotExist() throws DnsimpleException, IOException {
    String accountId = "1010";
    String domainId = "0";

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/0/auto_renewal", HttpMethods.DELETE, null, resource("notfound-domain.http"));

    httpClient.registrar.disableAutoRenewal(accountId, domainId);
  }
}
