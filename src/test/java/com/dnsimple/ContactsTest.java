package com.dnsimple;

import com.dnsimple.response.ListContactsResponse;
import com.dnsimple.response.GetContactResponse;
import com.dnsimple.response.CreateContactResponse;
import com.dnsimple.response.UpdateContactResponse;
import com.dnsimple.response.DeleteContactResponse;

import com.dnsimple.exception.DnsimpleException;
import com.dnsimple.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.api.client.http.HttpMethods;

public class ContactsTest extends DnsimpleTestBase {

  @Test
  public void testListContactsSupportsPagination() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1/contacts?page=1");

    String accountId = "1";
    HashMap<String, Object> options = new HashMap<String, Object>();

    options.put("page", 1);
    httpClient.contacts.listContacts(accountId, options);
  }

  @Test
  public void testListContactsSupportsExtraRequestOptions() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1/contacts?foo=bar");
    String accountId = "1";
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put("foo", "bar");
    httpClient.contacts.listContacts(accountId, options);
  }

  @Test
  public void testListContactsSupportsSorting() throws DnsimpleException, IOException {
    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1/contacts?sort=last_name%3Aasc");
    String accountId = "1";
    HashMap<String, Object> options = new HashMap<String, Object>();
    options.put("sort", "last_name:asc");
    httpClient.contacts.listContacts(accountId, options);
  }

  @Test
  public void testListContactsProducesContactList() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("listContacts/success.http"));

    String accountId = "1";

    ListContactsResponse response = httpClient.contacts.listContacts(accountId);

    List<Contact> contacts = response.getData();
    assertEquals(2, contacts.size());
    assertEquals(1, contacts.get(0).getId().intValue());
  }

  @Test
  public void testListContactsExposesPaginationInfo() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("listContacts/success.http"));

    String accountId = "1";

    ListContactsResponse response = httpClient.contacts.listContacts(accountId);

    Pagination pagination = response.getPagination();
    assertEquals(1, pagination.getCurrentPage().intValue());
  }

  @Test
  public void testGetContact() throws DnsimpleException, IOException {
    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/contacts/1", HttpMethods.GET, null, resource("getContact/success.http"));

    String accountId = "1010";
    String contactId = "1";

    GetContactResponse response = httpClient.contacts.getContact(accountId, contactId);

    Contact contact = response.getData();
    assertEquals(1, contact.getId().intValue());
    assertEquals(1010, contact.getAccountId().intValue());
    assertEquals("Default", contact.getLabel());
    assertEquals("First", contact.getFirstName());
    assertEquals("User", contact.getLastName());
    assertEquals("CEO", contact.getJobTitle());
    assertEquals("Awesome Company", contact.getOrganizationName());
    assertEquals("first@example.com", contact.getEmail());
    assertEquals("+18001234567", contact.getPhone());
    assertEquals("+18011234567", contact.getFax());
    assertEquals("Italian Street, 10", contact.getAddress1());
    assertEquals("", contact.getAddress2());
    assertEquals("Roma", contact.getCity());
    assertEquals("RM", contact.getStateOrProvince());
    assertEquals("00100", contact.getPostalCode());
    assertEquals("IT", contact.getCountry());
    assertEquals("2016-01-19T20:50:26.066Z", contact.getCreatedAt());
    assertEquals("2016-01-19T20:50:26.066Z", contact.getUpdatedAt());
  }

  @Test(expected=ResourceNotFoundException.class)
  public void testGetContactWhenNotFound() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("notfound-contact.http"));

    String accountId = "1010";
    String contactId = "2";

    httpClient.contacts.getContact(accountId, contactId);
  }

  @Test
  public void testCreateContactSendsCorrectRequest() throws DnsimpleException, IOException {
    String accountId = "1010";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("first_name", "John");
    attributes.put("last_name", "Smith");

    HttpClient httpClient = expectClient("https://api.dnsimple.com/v2/1010/contacts", HttpMethods.POST, attributes);

    httpClient.contacts.createContact(accountId, attributes);
  }

  @Test
  public void testCreateContactProducesContact() throws DnsimpleException, IOException {
    HttpClient httpClient = mockClient(resource("createContact/created.http"));

    String accountId = "1";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("first_name", "John");
    attributes.put("last_name", "Smith");

    CreateContactResponse response = httpClient.contacts.createContact(accountId, attributes);
    Contact contact = response.getData();
    assertEquals(1, contact.getId().intValue());
  }

  @Test
  public void testUpdateContact() throws DnsimpleException, IOException {
    String accountId = "1010";
    String contactId = "1";
    HashMap<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("first_name", "John");
    attributes.put("last_name", "Smith");

    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/contacts/1", HttpMethods.PATCH, attributes, resource("updateContact/success.http"));

    UpdateContactResponse response = httpClient.contacts.updateContact(accountId, contactId, attributes);
    Contact contact = response.getData();
    assertEquals(1, contact.getId().intValue());
  }

  @Test
  public void testDeleteContact() throws DnsimpleException, IOException {
    HttpClient httpClient = mockAndExpectClient("https://api.dnsimple.com/v2/1010/contacts/1", HttpMethods.DELETE, resource("deleteContact/success.http"));

    String accountId = "1010";
    String contactId = "1";

    DeleteContactResponse response = httpClient.contacts.deleteContact(accountId, contactId);
    assertEquals(null, response.getData());
  }
}
