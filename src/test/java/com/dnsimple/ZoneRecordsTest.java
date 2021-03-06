package com.dnsimple;

import com.dnsimple.response.ListZoneRecordsResponse;
import com.dnsimple.response.GetZoneRecordResponse;
import com.dnsimple.response.CreateZoneRecordResponse;
import com.dnsimple.response.UpdateZoneRecordResponse;
import com.dnsimple.response.DeleteZoneRecordResponse;

import com.dnsimple.exception.DnsimpleException;
import com.dnsimple.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.util.Data;

public class ZoneRecordsTest extends DnsimpleTestBase {

  @Test
  public void testListZoneRecordsSupportsPagination() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1/zones/example.com/records?page=1");
    String accountId = "1";
    String zoneId = "example.com";
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put("page", 1);
    httpClient.zones.listZoneRecords(accountId, zoneId, options);
  }

  @Test
  public void testListZoneRecordsSupportsExtraRequestOptions() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1/zones/example.com/records?foo=bar");
    String accountId = "1";
    String zoneId = "example.com";
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put("foo", "bar");
    httpClient.zones.listZoneRecords(accountId, zoneId, options);
  }

  @Test
  public void testListZoneRecordsSupportsSorting() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1/zones/example.com/records?sort=name%3Aasc");
    String accountId = "1";
    String zoneId = "example.com";
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put("sort", "name:asc");
    httpClient.zones.listZoneRecords(accountId, zoneId, options);
  }

  @Test
  public void testListZoneRecordsProducesDomainList() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("listZoneRecords/success.http"));

    String accountId = "1";
    String zoneId = "example.com";

    ListZoneRecordsResponse response = httpClient.zones.listZoneRecords(accountId, zoneId);

    List<ZoneRecord> zoneRecords = response.getData();
    assertEquals(5, zoneRecords.size());
    assertEquals(64779, zoneRecords.get(0).getId().intValue());
  }

  @Test
  public void testListZoneRecordsExposesPaginationInfo() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("listZoneRecords/success.http"));

    String accountId = "1";
    String zoneId = "example.com";

    ListZoneRecordsResponse response = httpClient.zones.listZoneRecords(accountId, zoneId);

    Pagination pagination = response.getPagination();
    assertEquals(1, pagination.getCurrentPage().intValue());
  }

  @Test
  public void testGetZoneRecord() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("getZoneRecord/success.http"));

    String accountId = "1";
    String zoneId = "example.com";
    String recordId = "2";

    GetZoneRecordResponse response = httpClient.zones.getZoneRecord(accountId, zoneId, recordId);

    ZoneRecord record = response.getData();
    assertEquals(64784, record.getId().intValue());
    assertEquals("example.com", record.getZoneId());
    assertTrue(Data.isNull(record.getParentId()));
    assertEquals("www", record.getName());
    assertEquals("127.0.0.1", record.getContent());
    assertEquals(600, record.getTtl().intValue());
    assertTrue(Data.isNull(record.getPriority()));
    assertEquals("A", record.getType());
    assertFalse(record.getSystemRecord());
    assertEquals("2016-01-07T17:45:13.653Z", record.getCreatedAt());
    assertEquals("2016-01-07T17:45:13.653Z", record.getUpdatedAt());
  }

  @Test(expected=ResourceNotFoundException.class)
  public void testGetZoneRecordWhenRecordNotFound() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("notfound-record.http"));

    String accountId = "1";
    String domainId = "example.com";
    String recordId = "2";

    httpClient.zones.getZoneRecord(accountId, domainId, recordId);
  }

  @Test
  public void testCreateZoneSendsCorrectRequest() throws DnsimpleException, IOException {
    String accountId = "1010";
    String zoneId = "example.com";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("name", "www");

    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1010/zones/example.com/records", HttpMethods.POST, attributes);

    httpClient.zones.createZoneRecord(accountId, zoneId, attributes);
  }

  @Test
  public void testCreateZoneRecordProducesZoneRecord() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("createZoneRecord/created.http"));

    String accountId = "1";
    String zoneId = "example.com";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("name", "www");

    CreateZoneRecordResponse response = httpClient.zones.createZoneRecord(accountId, zoneId, attributes);
    ZoneRecord record = response.getData();
    assertEquals(64784, record.getId().intValue());
  }

  @Test
  public void testUpdateZoneRecordProducesZoneRecord() throws DnsimpleException, IOException {
    String accountId = "1";
    String zoneId = "example.com";
    String recordId = "2";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("name", "www");

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1/zones/example.com/records/2", HttpMethods.PATCH, attributes, resource("updateZoneRecord/success.http"));

    UpdateZoneRecordResponse response = httpClient.zones.updateZoneRecord(accountId, zoneId, recordId, attributes);
    ZoneRecord record = response.getData();
    assertEquals(64784, record.getId().intValue());
  }

  @Test
  public void testDeleteZoneRecord() throws DnsimpleException, IOException {
    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1/zones/example.com/records/2", HttpMethods.DELETE, resource("deleteZoneRecord/success.http"));

    String accountId = "1";
    String zoneId = "example.com";
    String recordId = "2";

    DeleteZoneRecordResponse response = httpClient.zones.deleteZoneRecord(accountId, zoneId, recordId);
    assertEquals(null, response.getData());
  }

}
