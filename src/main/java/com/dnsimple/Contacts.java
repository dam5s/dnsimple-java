package com.dnsimple;

import java.io.IOException;
import java.util.Map;

import com.dnsimple.response.ListContactsResponse;
import com.dnsimple.response.GetContactResponse;
import com.dnsimple.response.CreateContactResponse;
import com.dnsimple.response.UpdateContactResponse;
import com.dnsimple.response.DeleteContactResponse;

import com.dnsimple.exception.DnsimpleException;

import com.google.api.client.http.HttpResponse;

/**
 * Provides access to the DNSimple Contacts API.
 *
 * @see <a href="https://developer.dnsimple.com/v2/contacts">https://developer.dnsimple.com/v2/contacts</a>
 */
public class Contacts {
  private HttpClient httpClient;

  protected Contacts(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  /**
   * Lists the contacts in the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/contacts/#list">https://developer.dnsimple.com/v2/contacts/#list</a>
   *
   * @param accountId The account ID
   * @return The list contacts response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public ListContactsResponse listContacts(String accountId) throws DnsimpleException, IOException {
    return listContacts(accountId, null);
  }

  /**
   * Lists the contacts in the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/contacts/#list">https://developer.dnsimple.com/v2/contacts/#list</a>
   *
   * @param accountId The account ID
   * @param options A Map of options to pass to the contacts API
   * @return The list contacts response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public ListContactsResponse listContacts(String accountId, Map<String,Object> options) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.get(accountId + "/contacts", options);
    return (ListContactsResponse) httpClient.parseResponse(response, ListContactsResponse.class);
  }

  /**
   * Get a specific contact associated to an account using the contacts's ID.
   *
   * @see <a href="https://developer.dnsimple.com/v2/contacts/#get">https://developer.dnsimple.com/v2/contacts/#get</a>
   *
   * @param accountId The account ID
   * @param contactId The contact ID
   * @return The get contact response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public GetContactResponse getContact(String accountId, String contactId) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.get(accountId + "/contacts/" + contactId);
    return (GetContactResponse) httpClient.parseResponse(response, GetContactResponse.class);
  }

  /**
   * Create a contact in the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/contacts/#create">https://developer.dnsimple.com/v2/contacts/#create</a>
   *
   * @param accountId The account ID
   * @param attributes A map of attributes to contruct the contact
   * @return The create contact response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public CreateContactResponse createContact(String accountId, Map<String,Object> attributes) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.post(accountId + "/contacts", attributes);
    return (CreateContactResponse) httpClient.parseResponse(response, CreateContactResponse.class);
  }

  /**
   * Update a contact in the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/contacts/#update">https://developer.dnsimple.com/v2/contacts/#update</a>
   *
   * @param accountId The account ID
   * @param contactId The contact ID
   * @param attributes A map of attributes to update the contact
   * @return The update contact response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public UpdateContactResponse updateContact(String accountId, String contactId, Map<String,Object> attributes) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.patch(accountId + "/contacts/" + contactId, attributes);
    return (UpdateContactResponse) httpClient.parseResponse(response, UpdateContactResponse.class);
  }

  /**
   * Delete a contact from the account.
   *
   * @see <a href="https://developer.dnsimple.com/v2/contacts/#delete">https://developer.dnsimple.com/v2/contacts/#delete</a>
   *
   * @param accountId The account ID
   * @param contactId The contact ID
   * @return The delete contact response
   * @throws DnsimpleException Any API errors
   * @throws IOException Any IO errors
   */
  public DeleteContactResponse deleteContact(String accountId, String contactId) throws DnsimpleException, IOException {
    HttpResponse response = httpClient.delete(accountId + "/contacts/" + contactId);
    return (DeleteContactResponse) httpClient.parseResponse(response, DeleteContactResponse.class);
  }
}
