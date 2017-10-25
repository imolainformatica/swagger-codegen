package io.swagger.api;

import java.io.File;
import io.swagger.model.ModelApiResponse;
import io.swagger.model.Pet;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.model.rest.RestParamType;
import java.util.List;
import java.util.Map;


public abstract class PetApi extends ApiConfiguration {

    protected RestDefinition pet;

    public PetApi() {
        this.pet = rest("/pet").description("the pet API");
    }

    
    public RouteDefinition addPet() {
        return this.pet.post()
            .description("Add a new pet to the store")
            .consumes("application/json").consumes("application/xml").produces("application/xml").produces("application/json")
            .param().name("body").type(RestParamType.body) .description("Pet object that needs to be added to the store").endParam()

            .responseMessage().code(405).message("Invalid input").endResponseMessage()

            .route().process(new PetApiValidator("addPet"));
    }
    
    public RouteDefinition deletePet() {
        return this.pet.delete("/{petId}")
            .description("Deletes a pet")
            .produces("application/xml").produces("application/json")
            .param().name("petId").type(RestParamType.path).required(Boolean.TRUE).description("Pet id to delete").endParam()            .param().name("apiKey").type(RestParamType.header).required(Boolean.FALSE).defaultValue("").dataType("String").description("").endParam()

            .responseMessage().code(400).message("Invalid pet value").endResponseMessage()

            .route().process(new PetApiValidator("deletePet"));
    }
    
    public RouteDefinition findPetsByStatus() {
        return this.pet.get("/findByStatus")
            .description("Finds Pets by status")
            .produces("application/xml").produces("application/json")
            .param().name("status").type(RestParamType.query).required(Boolean.TRUE).defaultValue("").description("Status values that need to be considered for filter").endParam()

            .responseMessage().code(200).message("successful operation").responseModel(List.class).endResponseMessage()
            .responseMessage().code(400).message("Invalid status value").endResponseMessage()

            .route().process(new PetApiValidator("findPetsByStatus"));
    }
    
    public RouteDefinition findPetsByTags() {
        return this.pet.get("/findByTags")
            .description("Finds Pets by tags")
            .produces("application/xml").produces("application/json")
            .param().name("tags").type(RestParamType.query).required(Boolean.TRUE).defaultValue("").description("Tags to filter by").endParam()

            .responseMessage().code(200).message("successful operation").responseModel(List.class).endResponseMessage()
            .responseMessage().code(400).message("Invalid tag value").endResponseMessage()

            .route().process(new PetApiValidator("findPetsByTags"));
    }
    
    public RouteDefinition getPetById() {
        return this.pet.get("/{petId}")
            .description("Find pet by ID")
            .produces("application/xml").produces("application/json")
            .param().name("petId").type(RestParamType.path).required(Boolean.TRUE).description("ID of pet to return").endParam()
            .responseMessage().code(200).message("successful operation").responseModel(Pet.class).endResponseMessage()
            .responseMessage().code(400).message("Invalid ID supplied").endResponseMessage()
            .responseMessage().code(404).message("Pet not found").endResponseMessage()

            .route().process(new PetApiValidator("getPetById"));
    }
    
    public RouteDefinition updatePet() {
        return this.pet.put()
            .description("Update an existing pet")
            .consumes("application/json").consumes("application/xml").produces("application/xml").produces("application/json")
            .param().name("body").type(RestParamType.body) .description("Pet object that needs to be added to the store").endParam()

            .responseMessage().code(400).message("Invalid ID supplied").endResponseMessage()
            .responseMessage().code(404).message("Pet not found").endResponseMessage()
            .responseMessage().code(405).message("Validation exception").endResponseMessage()

            .route().process(new PetApiValidator("updatePet"));
    }
    
    public RouteDefinition updatePetWithForm() {
        return this.pet.post("/{petId}")
            .description("Updates a pet in the store with form data")
            .consumes("application/x-www-form-urlencoded").produces("application/xml").produces("application/json")
            .param().name("petId").type(RestParamType.path).required(Boolean.TRUE).description("ID of pet that needs to be updated").endParam()
            .responseMessage().code(405).message("Invalid input").endResponseMessage()

            .route().process(new PetApiValidator("updatePetWithForm"));
    }
    
    public RouteDefinition uploadFile() {
        return this.pet.post("/{petId}/uploadImage")
            .description("uploads an image")
            .consumes("multipart/form-data").produces("application/json")
            .param().name("petId").type(RestParamType.path).required(Boolean.TRUE).description("ID of pet to update").endParam()
            .responseMessage().code(200).message("successful operation").responseModel(ModelApiResponse.class).endResponseMessage()

            .route().process(new PetApiValidator("uploadFile"));
    }
    

}
