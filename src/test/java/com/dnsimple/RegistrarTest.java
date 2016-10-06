package com.dnsimple;

import com.dnsimple.response.CheckDomainResponse;
import com.dnsimple.response.RegisterDomainResponse;
import com.dnsimple.response.RenewDomainResponse;
import com.dnsimple.response.TransferDomainResponse;
import com.dnsimple.response.TransferDomainOutResponse;

import com.dnsimple.exception.DnsimpleException;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.api.client.http.HttpMethods;

public class RegistrarTest extends DnsimpleTestBase {

  @Test
  public void testCheckDomain() throws DnsimpleException, IOException {
    String accountId = "1010";
    String name = "example.com";

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/example.com/check", HttpMethods.GET, null, resource("checkDomain/success.http"));

    CheckDomainResponse response = httpClient.registrar.checkDomain(accountId, name);
    DomainAvailability availability = response.getData();
    assertEquals(name, availability.getDomainName());
    assertTrue(availability.getAvailable().booleanValue());
    assertFalse(availability.getPremium().booleanValue());
  }

  @Test
  public void testRegisterDomain() throws DnsimpleException, IOException {
    String accountId = "1010";
    String name = "example.com";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("registrant_id", "10");

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/example.com/register", HttpMethods.POST, attributes, resource("registerDomain/success.http"));

    RegisterDomainResponse response = httpClient.registrar.registerDomain(accountId, name, attributes);
    Domain domain = response.getData();
    assertEquals(1, domain.getId().intValue());
    assertEquals(1010, domain.getAccountId().intValue());
    assertEquals(2, domain.getRegistrantId().intValue());
    assertEquals("example.com", domain.getName());
    assertEquals("example.com", domain.getUnicodeName());
    assertEquals("cc8h1jP8bDLw5rXycL16k8BivcGiT6K9", domain.getToken());
    assertEquals("registered", domain.getState());
    assertFalse(domain.getAutoRenew());
    assertFalse(domain.getPrivateWhois());
    assertEquals("2017-01-16", domain.getExpiresOn());
    assertEquals("2016-01-16T16:08:50.649Z", domain.getCreatedAt());
    assertEquals("2016-01-16T16:09:01.161Z", domain.getUpdatedAt());
  }

  @Test
  public void testRenewDomain() throws DnsimpleException, IOException {
    String accountId = "1010";
    String name = "example.com";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("period", "3");

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/example.com/renewal", HttpMethods.POST, attributes, resource("renewDomain/success.http"));

    RenewDomainResponse response = httpClient.registrar.renewDomain(accountId, name, attributes);
    Domain domain = response.getData();
    assertEquals(1, domain.getId().intValue());
  }

  @Test(expected=DnsimpleException.class)
  public void testRenewDomainTooSoon() throws DnsimpleException, IOException {
    String accountId = "1010";
    String name = "example.com";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("period", "3");

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/example.com/renewal", HttpMethods.POST, attributes, resource("renewDomain/error-tooearly.http"));

    httpClient.registrar.renewDomain(accountId, name, attributes);
  }

  @Test
  public void testTransferDomain() throws DnsimpleException, IOException {
    String accountId = "1010";
    String name = "example.com";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("registrant_id", "1");
    attributes.put("auth_info", "x1y2z3");

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/example.com/transfer", HttpMethods.POST, attributes, resource("transferDomain/success.http"));

    TransferDomainResponse response = httpClient.registrar.transferDomain(accountId, name, attributes);
    Domain domain = response.getData();
    assertEquals(1, domain.getId().intValue());
  }

  @Test(expected=DnsimpleException.class)
  public void testTransferDomainAlreadyInDnsimple() throws DnsimpleException, IOException {
    String accountId = "1010";
    String name = "example.com";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("registrant_id", "1");
    attributes.put("auth_info", "x1y2z3");

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/example.com/transfer", HttpMethods.POST, attributes, resource("transferDomain/error-isdnsimple.http"));

    httpClient.registrar.transferDomain(accountId, name, attributes);
  }

  @Test(expected=DnsimpleException.class)
  public void testTransferDomainAuthInfoRequired() throws DnsimpleException, IOException {
    String accountId = "1010";
    String name = "example.com";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("registrant_id", "1");

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/example.com/transfer", HttpMethods.POST, attributes, resource("transferDomain/error-missing-authcode.http"));

    httpClient.registrar.transferDomain(accountId, name, attributes);
  }

  @Test
  public void testTransferDomainOut() throws DnsimpleException, IOException {
    String accountId = "1010";
    String name = "example.com";

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/registrar/domains/example.com/transfer_out", HttpMethods.POST, resource("transferDomainOut/success.http"));

    TransferDomainOutResponse response = httpClient.registrar.transferDomainOut(accountId, name);
    assertEquals(null, response.getData());
  }

}
