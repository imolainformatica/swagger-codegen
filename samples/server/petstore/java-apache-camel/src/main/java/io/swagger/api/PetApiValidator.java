package io.swagger.api;

import java.io.File;
import io.swagger.model.ModelApiResponse;
import io.swagger.model.Pet;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Message;

import javax.validation.*;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

public class PetApiValidator implements Processor {

    private final String operation;

    public PetApiValidator (String operation) {
        this.operation = operation;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final Message in = exchange.getIn();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        if (this.operation.equals("addPet")) {
            Pet body = in.getBody(Pet.class);
            new PetApiConstraint<Pet>().checkConstraint(validator.validate(body));

        }
        
        if (this.operation.equals("deletePet")) {
            Long petId = in.getHeader("petId", Long.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "deletePetpetId", petId));
            String apiKey = in.getHeader("apiKey", String.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "deletePetapiKey", apiKey));

        }
        
        if (this.operation.equals("findPetsByStatus")) {
            List<String> status = in.getHeader("status", List.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "findPetsByStatusstatus", status));
            if (status != null) {
                for (String statusItem : status) {
                    new PetApiConstraint<String>().checkConstraint(validator.validate(statusItem));
                }
            }
            

        }
        
        if (this.operation.equals("findPetsByTags")) {
            List<String> tags = in.getHeader("tags", List.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "findPetsByTagstags", tags));
            if (tags != null) {
                for (String tagsItem : tags) {
                    new PetApiConstraint<String>().checkConstraint(validator.validate(tagsItem));
                }
            }
            

        }
        
        if (this.operation.equals("getPetById")) {
            Long petId = in.getHeader("petId", Long.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "getPetByIdpetId", petId));

        }
        
        if (this.operation.equals("updatePet")) {
            Pet body = in.getBody(Pet.class);
            new PetApiConstraint<Pet>().checkConstraint(validator.validate(body));

        }
        
        if (this.operation.equals("updatePetWithForm")) {
            Long petId = in.getHeader("petId", Long.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "updatePetWithFormpetId", petId));
            String name = in.getHeader("name", String.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "updatePetWithFormname", name));
            String status = in.getHeader("status", String.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "updatePetWithFormstatus", status));

        }
        
        if (this.operation.equals("uploadFile")) {
            Long petId = in.getHeader("petId", Long.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "uploadFilepetId", petId));
            String additionalMetadata = in.getHeader("additionalMetadata", String.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "uploadFileadditionalMetadata", additionalMetadata));
            File file = in.getHeader("file", File.class);
            new PetApiConstraint<PetApiValidationRequest>().checkConstraint(validator.validateValue(PetApiValidationRequest.class, "uploadFilefile", file));

        }
        
    }

    class PetApiValidationRequest {
        private  @NotNull Long deletePetpetId;
        private  String deletePetapiKey;
        private  @NotNull List<String> findPetsByStatusstatus;
        private  @NotNull List<String> findPetsByTagstags;
        private  @NotNull Long getPetByIdpetId;
        private  @NotNull Long updatePetWithFormpetId;
        private  String updatePetWithFormname;
        private  String updatePetWithFormstatus;
        private  @NotNull Long uploadFilepetId;
        private  String uploadFileadditionalMetadata;
        private  File uploadFilefile;

    }

    class PetApiConstraint<T> {
        private void checkConstraint(Set<ConstraintViolation<T>> violations) throws ConstraintViolationException {
            Iterator<ConstraintViolation<T>> iterator = violations.iterator();
            if (iterator.hasNext()) {
                ConstraintViolation<T> violation = iterator.next();
                throw new ConstraintViolationException(violation.getPropertyPath() + " " + violation.getMessage(), violations);
            }
        }
    }

}
