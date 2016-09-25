package com.dnsimple.response;

import com.dnsimple.Domain;

import com.google.api.client.util.Key;

public class TransferDomainResponse extends ApiResponse {
  @Key("data")
  private Domain data;

  public Domain getData() {
    return data;
  }
}
