package com.dnsimple;

public class Client {

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

    public Client() {
        HttpClient httpClient = new HttpClient();

        this.accounts = new Accounts(httpClient);
        this.certificates = new Certificates(httpClient);
        this.contacts = new Contacts(httpClient);
        this.domains = new Domains(httpClient);
        this.identity = new Identity(httpClient);
        this.oauth = new Oauth(httpClient);
        this.registrar = new Registrar(httpClient);
        this.services = new Services(httpClient);
        this.templates = new Templates(httpClient);
        this.tlds = new Tlds(httpClient);
        this.vanityNameServers = new VanityNameServers(httpClient);
        this.webhooks = new Webhooks(httpClient);
        this.zones = new Zones(httpClient);
    }
}
