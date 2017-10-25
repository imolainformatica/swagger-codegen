package io.swagger.api;

import io.swagger.model.Order;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.model.rest.RestParamType;
import java.util.List;
import java.util.Map;


public abstract class StoreApi extends ApiConfiguration {

    protected RestDefinition store;

    public StoreApi() {
        this.store = rest("/store").description("the store API");
    }

    
    public RouteDefinition deleteOrder() {
        return this.store.delete("/order/{orderId}")
            .description("Delete purchase order by ID")
            .produces("application/xml").produces("application/json")
            .param().name("orderId").type(RestParamType.path).required(Boolean.TRUE).description("ID of the order that needs to be deleted").endParam()
            .responseMessage().code(400).message("Invalid ID supplied").endResponseMessage()
            .responseMessage().code(404).message("Order not found").endResponseMessage()

            .route().process(new StoreApiValidator("deleteOrder"));
    }
    
    public RouteDefinition getInventory() {
        return this.store.get("/inventory")
            .description("Returns pet inventories by status")
            .produces("application/json")

            .responseMessage().code(200).message("successful operation").responseModel(Map.class).endResponseMessage()

            .route().process(new StoreApiValidator("getInventory"));
    }
    
    public RouteDefinition getOrderById() {
        return this.store.get("/order/{orderId}")
            .description("Find purchase order by ID")
            .produces("application/xml").produces("application/json")
            .param().name("orderId").type(RestParamType.path).required(Boolean.TRUE).description("ID of pet that needs to be fetched").endParam()
            .responseMessage().code(200).message("successful operation").responseModel(Order.class).endResponseMessage()
            .responseMessage().code(400).message("Invalid ID supplied").endResponseMessage()
            .responseMessage().code(404).message("Order not found").endResponseMessage()

            .route().process(new StoreApiValidator("getOrderById"));
    }
    
    public RouteDefinition placeOrder() {
        return this.store.post("/order")
            .description("Place an order for a pet")
            .produces("application/xml").produces("application/json")
            .param().name("body").type(RestParamType.body) .description("order placed for purchasing the pet").endParam()

            .responseMessage().code(200).message("successful operation").responseModel(Order.class).endResponseMessage()
            .responseMessage().code(400).message("Invalid Order").endResponseMessage()

            .route().process(new StoreApiValidator("placeOrder"));
    }
    

}
