package com.dnsimple;

import java.io.IOException;

import com.dnsimple.response.EnableVanityNameServersResponse;
import com.dnsimple.response.DisableVanityNameServersResponse;

import com.dnsimple.exception.DnsimpleException;

import com.google.api.client.http.HttpResponse;

/**
 * Provides access to the  DNSimple Vanity Name Server API
 *
 * @see <a href="https://developer.dnsimple.com/v2/domains/vanity">https://developer.dnsimple.com/v2/domains/vanity</a>
 */
public class VanityNameServers {
  private HttpClient httpClient;

  protected VanityNameServers(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  /**
   * Enable vanity name servers for the domain
   *
   * @see <a href="https://developer.dnsimple.com/v2/domains/vanity/#enable">https://developer.dnsimple.com/v2/domains/vanity/#enable</a>
   * @param accountId The account ID
   * @param domainId The domain name or ID
   * @return The enable vanity name server response
   * @throws DnsimpleException Any API error
   * @throws IOException Any IO error
   */
  public EnableVanityNameServersResponse enableVanityNameServers(String accountId, String domainId) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.put(accountId + "/vanity/" + domainId);
    return (EnableVanityNameServersResponse) httpClient.parseResponse(response, EnableVanityNameServersResponse.class);
  }

  /**
   * Disable vanity name servers for the domain
   *
   * @see <a href="https://developer.dnsimple.com/v2/domains/vanity/#disable">https://developer.dnsimple.com/v2/domains/vanity/#disable</a>
   * @param accountId The account ID
   * @param domainId The domain name or ID
   * @return The disable vanity name server response
   * @throws DnsimpleException Any API error
   * @throws IOException Any IO error
   */
  public DisableVanityNameServersResponse disableVanityNameServers(String accountId, String domainId) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.delete(accountId + "/vanity/" + domainId);
    return (DisableVanityNameServersResponse) httpClient.parseResponse(response, DisableVanityNameServersResponse.class);
  }
}
