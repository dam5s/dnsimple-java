package com.dnsimple;

import com.dnsimple.response.EnableVanityNameServersResponse;
import com.dnsimple.response.DisableVanityNameServersResponse;

import com.dnsimple.exception.DnsimpleException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.api.client.http.HttpMethods;

public class VanityNameServersTest extends DnsimpleTestBase {
  @Test
  public void testEnableVanityNameServers() throws DnsimpleException, IOException {
    String accountId = "1010";
    String domainId = "example.com";

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/vanity/example.com", HttpMethods.PUT, null, resource("enableVanityNameServers/success.http"));

    EnableVanityNameServersResponse response = httpClient.vanityNameServers.enableVanityNameServers(accountId, domainId);
    List<NameServer> vanityNameServers = response.getData();
  }

  @Test
  public void testDisableVanityNameServers() throws DnsimpleException, IOException {
    String accountId = "1010";
    String domainId = "example.com";

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/vanity/example.com", HttpMethods.DELETE, null, resource("disableVanityNameServers/success.http"));

    DisableVanityNameServersResponse response = httpClient.vanityNameServers.disableVanityNameServers(accountId, domainId);
    assertEquals(null, response.getData());
  }
}
