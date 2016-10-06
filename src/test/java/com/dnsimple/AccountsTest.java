package com.dnsimple;

import com.dnsimple.response.ListAccountsResponse;
import com.dnsimple.exception.DnsimpleException;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountsTest extends DnsimpleTestBase {

  @Test
  public void testListAccounts() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("listAccounts/success.http"));

    ListAccountsResponse response = httpClient.accounts.listAccounts();

    List<Account> accounts = response.getData();
    assertEquals(1, accounts.size());
    assertEquals(123, accounts.get(0).getId().intValue());
  }

}
