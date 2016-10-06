package com.dnsimple;

import com.dnsimple.request.Filter;

import com.dnsimple.response.ListZonesResponse;
import com.dnsimple.response.GetZoneResponse;
import com.dnsimple.response.GetZoneFileResponse;

import com.dnsimple.exception.DnsimpleException;
import com.dnsimple.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.*;

public class ZonesTest extends DnsimpleTestBase {

  @Test
  public void testListZonesSupportsPagination() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1/zones?page=1");
    String accountId = "1";
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put("page", 1);
    httpClient.zones.listZones(accountId, options);
  }

  @Test
  public void testListZonesSupportsExtraRequestOptions() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1/zones?foo=bar");
    String accountId = "1";
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put("foo", "bar");
    httpClient.zones.listZones(accountId, options);
  }

  @Test
  public void testListDomainsSupportsSorting() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1/zones?sort=expires_on%3Aasc");
    String accountId = "1";
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put("sort", "expires_on:asc");
    httpClient.zones.listZones(accountId, options);
  }

  @Test
  public void testListZonesSupportsFiltering() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1/zones?name_like=example");
    String accountId = "1";
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put("filter", new Filter("name_like", "example"));
    httpClient.zones.listZones(accountId, options);
  }

  @Test
  public void testListZonesProducesZoneList() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("listZones/success.http"));

    String accountId = "1";

    ListZonesResponse response = httpClient.zones.listZones(accountId);

    List<Zone> zones = response.getData();
    assertEquals(2, zones.size());
    assertEquals(1, zones.get(0).getId().intValue());
  }

  @Test
  public void testListZonesExposesPaginationInfo() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("listZones/success.http"));

    String accountId = "1";

    ListZonesResponse response = httpClient.zones.listZones(accountId);

    Pagination pagination = response.getPagination();
    assertEquals(1, pagination.getCurrentPage().intValue());
  }

  @Test
  public void testGetZone() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("getZone/success.http"));

    String accountId = "1";
    String zoneId = "example.com";

    GetZoneResponse response = httpClient.zones.getZone(accountId, zoneId);

    Zone zone = response.getData();
    assertEquals(1, zone.getId().intValue());
    assertEquals(1010, zone.getAccountId().intValue());
    assertEquals("example-alpha.com", zone.getName());
    assertEquals("2015-04-23T07:40:03.045Z", zone.getCreatedAt());
    assertEquals("2015-04-23T07:40:03.051Z", zone.getUpdatedAt());
  }

  @Test(expected=ResourceNotFoundException.class)
  public void testGetZoneWhenZoneNotFound() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("notfound-zone.http"));

    String accountId = "1";
    String zoneId = "example.com";

    httpClient.zones.getZone(accountId, zoneId);
  }

  @Test
  public void testGetZoneFile() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("getZoneFile/success.http"));

    String accountId = "1";
    String zoneId = "example.com";

    GetZoneFileResponse response = httpClient.zones.getZoneFile(accountId, zoneId);

    ZoneFile zoneFile = response.getData();
    assertEquals("$ORIGIN example.com.\n$TTL 1h\nexample.com. 3600 IN SOA ns1.dnsimple.com. admin.dnsimple.com. 1453132552 86400 7200 604800 300\nexample.com. 3600 IN NS ns1.dnsimple.com.\nexample.com. 3600 IN NS ns2.dnsimple.com.\nexample.com. 3600 IN NS ns3.dnsimple.com.\nexample.com. 3600 IN NS ns4.dnsimple.com.\n", zoneFile.getZone());
  }

  @Test(expected=ResourceNotFoundException.class)
  public void testGetZoneFileWhenZoneNotFound() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("notfound-zone.http"));

    String accountId = "1";
    String zoneId = "example.com";

    httpClient.zones.getZoneFile(accountId, zoneId);
  }
}
