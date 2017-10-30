package io.swagger.api;

import io.swagger.model.User;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.model.rest.RestParamType;
import java.util.List;
import java.util.Map;


public abstract class UserApi extends ApiConfiguration {

    protected RestDefinition user;

    public UserApi() {
        this.user = rest("/user").description("the user API");
    }

    
    public RouteDefinition createUser() {
        return this.user.post()
            .description("Create user")
    .produces("application/xml").produces("application/json")
    .type(User.class)
    
            .param().name("body").type(RestParamType.body).description("Created user object").endParam()
            .responseMessage().code(0).message("successful operation").endResponseMessage()

            .route().routeId("createUser").process(new UserApiValidator("createUser"));
    }
    
    public RouteDefinition createUsersWithArrayInput() {
        return this.user.post("/createWithArray")
            .description("Creates list of users with given input array")
    .produces("application/xml").produces("application/json")
    .type(User[].class)
    
            .param().name("body").type(RestParamType.body).description("List of user object").endParam()
            .responseMessage().code(0).message("successful operation").endResponseMessage()

            .route().routeId("createUsersWithArrayInput").process(new UserApiValidator("createUsersWithArrayInput"));
    }
    
    public RouteDefinition createUsersWithListInput() {
        return this.user.post("/createWithList")
            .description("Creates list of users with given input array")
    .produces("application/xml").produces("application/json")
    .type(User[].class)
    
            .param().name("body").type(RestParamType.body).description("List of user object").endParam()
            .responseMessage().code(0).message("successful operation").endResponseMessage()

            .route().routeId("createUsersWithListInput").process(new UserApiValidator("createUsersWithListInput"));
    }
    
    public RouteDefinition deleteUser() {
        return this.user.delete("/{username}")
            .description("Delete user")
    .produces("application/xml").produces("application/json")
    
    
            .param().name("username").type(RestParamType.path).required(Boolean.TRUE).description("The name that needs to be deleted").endParam()
            .responseMessage().code(400).message("Invalid username supplied").endResponseMessage()
            .responseMessage().code(404).message("User not found").endResponseMessage()

            .route().routeId("deleteUser").process(new UserApiValidator("deleteUser"));
    }
    
    public RouteDefinition getUserByName() {
        return this.user.get("/{username}")
            .description("Get user by user name")
    .produces("application/xml").produces("application/json")
    
    .outType(User.class)
            .param().name("username").type(RestParamType.path).required(Boolean.TRUE).description("The name that needs to be fetched. Use user1 for testing. ").endParam()
            .responseMessage().code(200).message("successful operation").responseModel(User.class).endResponseMessage()
            .responseMessage().code(400).message("Invalid username supplied").endResponseMessage()
            .responseMessage().code(404).message("User not found").endResponseMessage()

            .route().routeId("getUserByName").process(new UserApiValidator("getUserByName"));
    }
    
    public RouteDefinition loginUser() {
        return this.user.get("/login")
            .description("Logs user into the system")
    .produces("application/xml").produces("application/json")
    
    .outType(String.class)
            .param().name("username").type(RestParamType.query).required(Boolean.TRUE).defaultValue("").description("The user name for login").endParam()
            .param().name("password").type(RestParamType.query).required(Boolean.TRUE).defaultValue("").description("The password for login in clear text").endParam()
            .responseMessage().code(200).message("successful operation").responseModel(String.class).endResponseMessage()
            .responseMessage().code(400).message("Invalid username/password supplied").endResponseMessage()

            .route().routeId("loginUser").process(new UserApiValidator("loginUser"));
    }
    
    public RouteDefinition logoutUser() {
        return this.user.get("/logout")
            .description("Logs out current logged in user session")
    .produces("application/xml").produces("application/json")
    
    
            .responseMessage().code(0).message("successful operation").endResponseMessage()

            .route().routeId("logoutUser").process(new UserApiValidator("logoutUser"));
    }
    
    public RouteDefinition updateUser() {
        return this.user.put("/{username}")
            .description("Updated user")
    .produces("application/xml").produces("application/json")
    .type(User.class)
    
            .param().name("username").type(RestParamType.path).required(Boolean.TRUE).description("name that need to be deleted").endParam()
            .param().name("body").type(RestParamType.body).description("Updated user object").endParam()
            .responseMessage().code(400).message("Invalid user supplied").endResponseMessage()
            .responseMessage().code(404).message("User not found").endResponseMessage()

            .route().routeId("updateUser").process(new UserApiValidator("updateUser"));
    }
    

}
