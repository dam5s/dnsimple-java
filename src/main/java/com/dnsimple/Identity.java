package com.dnsimple;

import java.io.IOException;

import com.dnsimple.response.WhoamiResponse;
import com.dnsimple.exception.DnsimpleException;

import com.google.api.client.http.HttpResponse;

/**
 * Provides access to the DNSimple Identity API.
 *
 * @see <a href="https://developer.dnsimple.com/v2/identity">https://developer.dnsimple.com/v2/identity</a>
 */
public class Identity {
  private HttpClient httpClient;

  protected Identity(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  /**
   * Gets the information about the current authenticated context.
   *
   * @see <a href="https://developer.dnsimple.com/v2/identity/#whoami">https://developer.dnsimple.com/v2/identity/#whoami</a>
   *
   * @return The whoami response
   * @throws DnsimpleException Any API error
   * @throws IOException Any IO error
   */
  public WhoamiResponse whoami() throws DnsimpleException, IOException {
    HttpResponse response = httpClient.get("whoami");
    return (WhoamiResponse) httpClient.parseResponse(response, WhoamiResponse.class);
  }
}
