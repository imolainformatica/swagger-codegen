package io.swagger.api;

import org.springframework.stereotype.Component;
import io.swagger.model.Order;

@Component
public class StoreApiRouteBuilder extends StoreApi {

    @Override
    public void configure() throws Exception {
        super.restConfigurationDefinition();

        
        super.deleteOrder().log("delete deleteOrder API")
        
        .log("orderId: ${header.orderId}")
        .to("mock:deleteOrder");
        
        super.getInventory().log("get getInventory API")
.to("mock:getInventory");
        
        super.getOrderById().log("get getOrderById API")
        
        .log("orderId: ${header.orderId}")
        .to("mock:getOrderById");
        
        super.placeOrder().log("post placeOrder API")
        .log("body: " + bodyAs(Order.class).toString())
        
        .to("mock:placeOrder");
        
    }
}
