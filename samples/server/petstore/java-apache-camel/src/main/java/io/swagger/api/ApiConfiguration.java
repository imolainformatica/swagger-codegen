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
            .apiProperty("api.title", "")
            .apiProperty("schemes", "http")
            .apiProperty("api.description", "")
            .apiProperty("api.version", "1.0.0") : restConfigurationDefinition;
    }
}
