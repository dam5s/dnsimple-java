package com.dnsimple;

import com.dnsimple.exception.DnsimpleException;
import com.dnsimple.request.Filter;
import com.dnsimple.response.ApiResponse;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.gson.GsonFactory;
import io.mikael.urlbuilder.UrlBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static java.util.Collections.emptyMap;

/**
 * Instances of the Client handle low-level HTTP calls to the API.
 */
public class Client {

    private static final String API_VERSION_PATH = "/v2/";
    private static final Map<String, Object> EMPTY_MAP = emptyMap();

    public final Accounts accounts;
    public final Certificates certificates;
    public final Contacts contacts;
    public final Domains domains;
    public final Identity identity;
    public final Oauth oauth;
    public final Registrar registrar;
    public final Services services;
    public final Templates templates;
    public final Tlds tlds;
    public final VanityNameServers vanityNameServers;
    public final Webhooks webhooks;

    public final Zones zones;

    private HttpTransport transport;

    /**
     * Construct a new API client.
     * <p>
     * Once you have a client instance, use the public properties such as `accounts` or `domains`
     * to communicate with the remote API.
     * <p>
     * For example:
     * <p>
     * Client client = new Client();
     * WhoamiResponse response = client.accounts.whoami();
     */
    public Client() {
        this.accounts = new Accounts(this);
        this.certificates = new Certificates(this);
        this.contacts = new Contacts(this);
        this.domains = new Domains(this);
        this.identity = new Identity(this);
        this.oauth = new Oauth(this);
        this.registrar = new Registrar(this);
        this.services = new Services(this);
        this.templates = new Templates(this);
        this.tlds = new Tlds(this);
        this.vanityNameServers = new VanityNameServers(this);
        this.webhooks = new Webhooks(this);
        this.zones = new Zones(this);

        this.transport = new NetHttpTransport();
    }

    /**
     * Set the underlying transport mechanism.
     * <p>
     * This method is primarily used to specify a mocked transport layer during testing.
     *
     * @param transport The transport instance
     */
    public void setTransport(HttpTransport transport) {
        this.transport = transport;
    }


    protected HttpResponse get(String path) throws DnsimpleException, IOException {
        return get(path, EMPTY_MAP);
    }

    protected HttpResponse get(String path, Map<String, Object> options) throws DnsimpleException, IOException {
        return request(HttpMethods.GET, versionedPath(path), null, options);
    }

    protected HttpResponse post(String path) throws DnsimpleException, IOException {
        return post(path, EMPTY_MAP);
    }

    protected HttpResponse post(String path, Map<String, Object> attributes) throws DnsimpleException, IOException {
        return post(path, attributes, EMPTY_MAP);
    }

    protected HttpResponse post(String path, Map<String, Object> attributes, Map<String, Object> options) throws DnsimpleException, IOException {
        return request(HttpMethods.POST, versionedPath(path), attributes, options);
    }

    protected HttpResponse put(String path) throws DnsimpleException, IOException {
        return put(path, EMPTY_MAP);
    }

    protected HttpResponse put(String path, Object attributes) throws DnsimpleException, IOException {
        return put(path, attributes, EMPTY_MAP);
    }

    protected HttpResponse put(String path, Object attributes, Map<String, Object> options) throws DnsimpleException, IOException {
        return request(HttpMethods.PUT, versionedPath(path), attributes, EMPTY_MAP);
    }

    protected HttpResponse patch(String path, Object attributes) throws DnsimpleException, IOException {
        return patch(path, attributes, EMPTY_MAP);
    }

    protected HttpResponse patch(String path, Object attributes, Map<String, Object> options) throws DnsimpleException, IOException {
        return request(HttpMethods.PATCH, versionedPath(path), attributes, options);
    }


    protected HttpResponse delete(String path) throws DnsimpleException, IOException {
        return delete(path, EMPTY_MAP);
    }

    protected HttpResponse delete(String path, Map<String, Object> options) throws DnsimpleException, IOException {
        return request(HttpMethods.DELETE, versionedPath(path), null, options);
    }


    /**
     * Parse the response from the HTTP call into an instance of the given class.
     *
     * @param response The parsed response object
     * @param c        The class to instantiate and use to build the response object
     * @return The ApiResponse object
     * @throws IOException Any IO errors
     */
    protected ApiResponse parseResponse(HttpResponse response, Class<?> c) throws IOException {
        ApiResponse res = null;
        InputStream in = response.getContent();

        if (in == null) {
            try {
                res = (ApiResponse) c.newInstance();
                // The catch below uses a class type only available in java 1.7
                // The pom.xml specifies code compatibility with 1.6
                // This will not work on Java 6.
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Cannot instantiate " + c, e);
            }
        } else {
            try {
                JsonParser jsonParser = GsonFactory.getDefaultInstance().createJsonParser(in);
                res = (ApiResponse) jsonParser.parse(c);
            } finally {
                in.close();
            }
        }

        res.setHttpRequest(response.getRequest());
        res.setHttpResponse(response);

        return res;
    }

    protected HttpResponse request(String method, String url, Object data, Map<String, Object> options) throws DnsimpleException, IOException {
        HttpContent content = null;
        if (data != null) {
            content = new JsonHttpContent(new GsonFactory(), data);
        }

        HttpRequest request = transport.createRequestFactory().buildRequest(method, buildUrl(url, options), content);

        try {
            return request.execute();
        } catch (HttpResponseException e) {
            throw DnsimpleException.transformException(e);
        }
    }


    private String versionedPath(String path) {
        return Dnsimple.getApiBase() + API_VERSION_PATH + path;
    }

    private GenericUrl buildUrl(String url, Map<String, Object> options) {
        UrlBuilder urlBuilder = UrlBuilder.fromString(url);
        if (options.containsKey("filter")) {
            Filter filter = (Filter) options.remove("filter");
            urlBuilder = urlBuilder.addParameter(filter.name, filter.value);
        }

        for (Map.Entry<String, Object> kv : options.entrySet()) {
            urlBuilder = urlBuilder.addParameter(kv.getKey(), kv.getValue().toString());
        }

        return new GenericUrl(urlBuilder.toUrl());
    }

}
