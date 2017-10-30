package io.swagger.api;

import org.springframework.stereotype.Component;
import io.swagger.model.User;

@Component
public class UserApiRouteBuilder extends UserApi {

    @Override
    public void configure() throws Exception {
        super.restConfigurationDefinition();

        
        super.createUser().log("post createUser API")
        .log("body: " + bodyAs(User.class).toString())
        
        .to("mock:createUser");
        
        super.createUsersWithArrayInput().log("post createUsersWithArrayInput API")
        .log("body: " + bodyAs(User[].class).toString())
        
        .to("mock:createUsersWithArrayInput");
        
        super.createUsersWithListInput().log("post createUsersWithListInput API")
        .log("body: " + bodyAs(User[].class).toString())
        
        .to("mock:createUsersWithListInput");
        
        super.deleteUser().log("delete deleteUser API")
        
        .log("username: ${header.username}")
        .to("mock:deleteUser");
        
        super.getUserByName().log("get getUserByName API")
        
        .log("username: ${header.username}")
        .to("mock:getUserByName");
        
        super.loginUser().log("get loginUser API")
        
        .log("username: ${header.username}")
        
        .log("password: ${header.password}")
        .to("mock:loginUser");
        
        super.logoutUser().log("get logoutUser API")
        .to("mock:logoutUser");
        
        super.updateUser().log("put updateUser API")
        
        .log("username: ${header.username}")
        .log("body: " + bodyAs(User.class).toString())
        
        .to("mock:updateUser");
        
    }
}
