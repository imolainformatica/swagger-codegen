package io.swagger.api;

import org.springframework.stereotype.Component;

@Component
public class PetApiRouteBuilder extends PetApi {

    @Override
    public void configure() throws Exception {
        super.restConfigurationDefinition();

        
        super.addPet().log("addPet API").to("mock:addPet");
        
        super.deletePet().log("deletePet API").to("mock:deletePet");
        
        super.findPetsByStatus().log("findPetsByStatus API").to("mock:findPetsByStatus");
        
        super.findPetsByTags().log("findPetsByTags API").to("mock:findPetsByTags");
        
        super.getPetById().log("getPetById API").to("mock:getPetById");
        
        super.updatePet().log("updatePet API").to("mock:updatePet");
        
        super.updatePetWithForm().log("updatePetWithForm API").to("mock:updatePetWithForm");
        
        super.uploadFile().log("uploadFile API").to("mock:uploadFile");
        
    }
}
