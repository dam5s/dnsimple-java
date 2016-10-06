package com.dnsimple;

import java.io.IOException;
import java.util.Map;

import com.dnsimple.response.ListZonesResponse;
import com.dnsimple.response.GetZoneResponse;
import com.dnsimple.response.GetZoneFileResponse;

import com.dnsimple.response.ListZoneRecordsResponse;
import com.dnsimple.response.GetZoneRecordResponse;
import com.dnsimple.response.CreateZoneRecordResponse;
import com.dnsimple.response.UpdateZoneRecordResponse;
import com.dnsimple.response.DeleteZoneRecordResponse;

import com.dnsimple.exception.DnsimpleException;

import com.google.api.client.http.HttpResponse;

/**
 * Provides access to the DNSimple Zones API.
 *
 * @see <a href="https://developer.dnsimple.com/v2/zones">https://developer.dnsimple.com/v2/zones</a>
 */
public class Zones {
  private HttpClient httpClient;

  protected Zones(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  // Domains

  /**
   * Lists the zones in the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/zones/#list">https://developer.dnsimple.com/v2/zones/#list</a>
   *
   * @param accountId The account ID
   * @return The list zones response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public ListZonesResponse listZones(String accountId) throws DnsimpleException, IOException {
    return listZones(accountId, null);
  }

  /**
   * Lists the zones in the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/zones/#list">https://developer.dnsimple.com/v2/zones/#list</a>
   *
   * @param accountId The account ID
   * @param options A Map of options to pass to the zones API
   * @return The list zones response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public ListZonesResponse listZones(String accountId, Map<String,Object> options) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.get(accountId + "/zones", options);
    return (ListZonesResponse) httpClient.parseResponse(response, ListZonesResponse.class);
  }

  /**
   * Get a specific zone associated to an account using the zone's name or ID.
   *
   * @see <a href="https://developer.dnsimple.com/v2/zones/#get">https://developer.dnsimple.com/v2/zones/#get</a>
   * @param accountId The account ID
   * @param zoneId The zone name or ID
   * @return The get zone response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public GetZoneResponse getZone(String accountId, String zoneId) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.get(accountId + "/zones/" + zoneId);
    return (GetZoneResponse) httpClient.parseResponse(response, GetZoneResponse.class);
  }

  /**
   * Get the zone file associated to an account using the zone's name or ID.
   *
   * @see <a href="https://developer.dnsimple.com/v2/zones/#get-file">https://developer.dnsimple.com/v2/zones/#get-file</a>
   * @param accountId The account ID
   * @param zoneId The zone name or ID
   * @return The get zone file response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public GetZoneFileResponse getZoneFile(String accountId, String zoneId) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.get(accountId + "/zones/" + zoneId + "/file");
    return (GetZoneFileResponse) httpClient.parseResponse(response, GetZoneFileResponse.class);
  }

  /**
   * Lists the records in the zone.
   *
   * @see <a href="https://developer.dnsimple.com/v2/zones/records/#list">https://developer.dnsimple.com/v2/zones/records/#list</a>
   * @param accountId The account ID
   * @param zoneId The zone name or ID
   * @return The list zone records response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public ListZoneRecordsResponse listZoneRecords(String accountId, String zoneId) throws DnsimpleException, IOException {
    return listZoneRecords(accountId, zoneId, null);
  }

  /**
   * Lists the records in the zone.
   *
   * @see <a href="https://developer.dnsimple.com/v2/zones/records/#list">https://developer.dnsimple.com/v2/zones/records/#list</a>
   * @param accountId The account ID
   * @param zoneId The zone name or ID
   * @param options A Map of options to pass to the zones API
   * @return The list zone records response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public ListZoneRecordsResponse listZoneRecords(String accountId, String zoneId, Map<String,Object> options) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.get(accountId + "/zones/" + zoneId + "/records", options);
    return (ListZoneRecordsResponse) httpClient.parseResponse(response, ListZoneRecordsResponse.class);
  }

  /**
   * Get a specific record associated to a zone using the zone's name or ID.
   *
   * @see <a href="https://developer.dnsimple.com/v2/zones/records/#get">https://developer.dnsimple.com/v2/zones/records/#get</a>
   * @param accountId The account ID
   * @param zoneId The zone name or ID
   * @param recordId The zone record ID
   * @return The get zone record response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public GetZoneRecordResponse getZoneRecord(String accountId, String zoneId, String recordId) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.get(accountId + "/zones/" + zoneId + "/records/" + recordId);
    return (GetZoneRecordResponse) httpClient.parseResponse(response, GetZoneRecordResponse.class);
  }

  /**
   * Create a record in a zone.
   *
   * @see <a href="https://developer.dnsimple.com/v2/zones/records/#create">https://developer.dnsimple.com/v2/zones/records/#create</a>
   * @param accountId The account ID
   * @param zoneId The zone name or ID
   * @param attributes The zone attributes
   * @return The create zone record response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public CreateZoneRecordResponse createZoneRecord(String accountId, String zoneId, Map<String,Object> attributes) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.post(accountId + "/zones/" + zoneId + "/records", attributes);
    return (CreateZoneRecordResponse) httpClient.parseResponse(response, CreateZoneRecordResponse.class);
  }

  /**
   * Update a record in a zone.
   *
   * @see <a href="https://developer.dnsimple.com/v2/zones/records/#update">https://developer.dnsimple.com/v2/zones/records/#update</a>
   * @param accountId The account ID
   * @param zoneId The zone name or ID
   * @param recordId The zone record ID
   * @param attributes The zone attributes
   * @return The update zone record response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public UpdateZoneRecordResponse updateZoneRecord(String accountId, String zoneId, String recordId, Map<String,Object> attributes) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.patch(accountId + "/zones/" + zoneId + "/records/" + recordId, attributes);
    return (UpdateZoneRecordResponse) httpClient.parseResponse(response, UpdateZoneRecordResponse.class);
  }

  /**
   * Delete a record from a zone.
   *
   * @see <a href="https://developer.dnsimple.com/v2/zones/records/#delete">https://developer.dnsimple.com/v2/zones/records/#delete</a>
   * @param accountId The account ID
   * @param zoneId The zone name or ID
   * @param recordId The zone record ID
   * @return The delete zone record response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public DeleteZoneRecordResponse deleteZoneRecord(String accountId, String zoneId, String recordId) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.delete(accountId + "/zones/" + zoneId + "/records/" + recordId);
    return (DeleteZoneRecordResponse) httpClient.parseResponse(response, DeleteZoneRecordResponse.class);
  }
}
