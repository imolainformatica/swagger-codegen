package io.swagger.api;

import org.springframework.stereotype.Component;

@Component
public class StoreApiRouteBuilder extends StoreApi {

    @Override
    public void configure() throws Exception {
        super.restConfigurationDefinition();

        
        super.deleteOrder().log("deleteOrder API").to("mock:deleteOrder");
        
        super.getInventory().log("getInventory API").to("mock:getInventory");
        
        super.getOrderById().log("getOrderById API").to("mock:getOrderById");
        
        super.placeOrder().log("placeOrder API").to("mock:placeOrder");
        
    }
}
