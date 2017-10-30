package io.swagger.api;

import org.springframework.stereotype.Component;

@Component
public class UserApiRouteBuilder extends UserApi {

    @Override
    public void configure() throws Exception {
        super.restConfigurationDefinition();

        
        super.createUser().log("createUser API").to("mock:createUser");
        
        super.createUsersWithArrayInput().log("createUsersWithArrayInput API").to("mock:createUsersWithArrayInput");
        
        super.createUsersWithListInput().log("createUsersWithListInput API").to("mock:createUsersWithListInput");
        
        super.deleteUser().log("deleteUser API").to("mock:deleteUser");
        
        super.getUserByName().log("getUserByName API").to("mock:getUserByName");
        
        super.loginUser().log("loginUser API").to("mock:loginUser");
        
        super.logoutUser().log("logoutUser API").to("mock:logoutUser");
        
        super.updateUser().log("updateUser API").to("mock:updateUser");
        
    }
}
