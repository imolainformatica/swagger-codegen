package io.swagger.api;

import org.springframework.stereotype.Component;
import java.io.File;
import io.swagger.model.ModelApiResponse;
import io.swagger.model.Pet;

@Component
public class PetApiRouteBuilder extends PetApi {

    @Override
    public void configure() throws Exception {
        super.restConfigurationDefinition();

        
        super.addPet().log("post addPet API")
        .log("body: " + bodyAs(Pet.class).toString())
        
        .to("mock:addPet");
        
        super.deletePet().log("delete deletePet API")
        
        .log("petId: ${header.petId}")
                
        .log("apiKey: ${header.api_key}")
        .to("mock:deletePet");
        
        super.findPetsByStatus().log("get findPetsByStatus API")
        
        .log("status: ${header.status}")
        .to("mock:findPetsByStatus");
        
        super.findPetsByTags().log("get findPetsByTags API")
        
        .log("tags: ${header.tags}")
        .to("mock:findPetsByTags");
        
        super.getPetById().log("get getPetById API")
        
        .log("petId: ${header.petId}")
        .to("mock:getPetById");
        
        super.updatePet().log("put updatePet API")
        .log("body: " + bodyAs(Pet.class).toString())
        
        .to("mock:updatePet");
        
        super.updatePetWithForm().log("post updatePetWithForm API")
        
        .log("petId: ${header.petId}")
                
        .log("name: ${header.name}")
                
        .log("status: ${header.status}")
        .to("mock:updatePetWithForm");
        
        super.uploadFile().log("post uploadFile API")
        
        .log("petId: ${header.petId}")
                
        .log("additionalMetadata: ${header.additionalMetadata}")
                
        .log("file: ${header.file}")
        .to("mock:uploadFile");
        
    }
}
