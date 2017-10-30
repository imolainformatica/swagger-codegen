package io.swagger.api;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestConfigurationDefinition;

public abstract class ApiConfiguration extends RouteBuilder {
    private static RestConfigurationDefinition restConfigurationDefinition;

    public RestConfigurationDefinition restConfigurationDefinition() {
        return restConfigurationDefinition == null ? restConfigurationDefinition = restConfiguration()
            .component("servlet")
            .host("petstore.swagger.io")
            .port("80")
            .contextPath("/v2")
            .bindingMode(RestBindingMode.auto)
            .apiContextPath("/api-doc")
            .apiProperty("schemes", "http")
            .apiProperty("api.title", "")
            .apiProperty("api.description", "This is a sample server Petstore server.  You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).  For this sample, you can use the api key &#x60;special-key&#x60; to test the authorization filters.")
            .apiProperty("api.version", "1.0.0")
            .apiProperty("api.termsOfService", "")
            .apiProperty("api.contact.name", "")
            .apiProperty("api.contact.email", "apiteam@swagger.io")
            .apiProperty("api.contact.url", "")
            .apiProperty("api.license.name", "Apache-2.0")
            .apiProperty("api.license.url", "http://www.apache.org/licenses/LICENSE-2.0.html") : restConfigurationDefinition;
    }
}
