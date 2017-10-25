package io.swagger.api;

import io.swagger.model.User;
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

public class UserApiValidator implements Processor {

    private final String operation;

    public UserApiValidator (String operation) {
        this.operation = operation;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final Message in = exchange.getIn();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        if (this.operation.equals("createUser")) {
            User body = in.getBody(User.class);
            new UserApiConstraint<User>().checkConstraint(validator.validate(body));

        }
        
        if (this.operation.equals("createUsersWithArrayInput")) {
            List<User> body = in.getBody(List.class);
            new UserApiConstraint<List<User>>().checkConstraint(validator.validate(body));
            if (body != null) {
                for (User bodyItem : body) {
                    new UserApiConstraint<User>().checkConstraint(validator.validate(bodyItem));
                }
            }
            

        }
        
        if (this.operation.equals("createUsersWithListInput")) {
            List<User> body = in.getBody(List.class);
            new UserApiConstraint<List<User>>().checkConstraint(validator.validate(body));
            if (body != null) {
                for (User bodyItem : body) {
                    new UserApiConstraint<User>().checkConstraint(validator.validate(bodyItem));
                }
            }
            

        }
        
        if (this.operation.equals("deleteUser")) {
            String username = in.getHeader("username", String.class);
            new UserApiConstraint<UserApiValidationRequest>().checkConstraint(validator.validateValue(UserApiValidationRequest.class, "deleteUserusername", username));

        }
        
        if (this.operation.equals("getUserByName")) {
            String username = in.getHeader("username", String.class);
            new UserApiConstraint<UserApiValidationRequest>().checkConstraint(validator.validateValue(UserApiValidationRequest.class, "getUserByNameusername", username));

        }
        
        if (this.operation.equals("loginUser")) {
            String username = in.getHeader("username", String.class);
            new UserApiConstraint<UserApiValidationRequest>().checkConstraint(validator.validateValue(UserApiValidationRequest.class, "loginUserusername", username));
            String password = in.getHeader("password", String.class);
            new UserApiConstraint<UserApiValidationRequest>().checkConstraint(validator.validateValue(UserApiValidationRequest.class, "loginUserpassword", password));

        }
        
        if (this.operation.equals("logoutUser")) {

        }
        
        if (this.operation.equals("updateUser")) {
            String username = in.getHeader("username", String.class);
            new UserApiConstraint<UserApiValidationRequest>().checkConstraint(validator.validateValue(UserApiValidationRequest.class, "updateUserusername", username));
            User body = in.getBody(User.class);
            new UserApiConstraint<User>().checkConstraint(validator.validate(body));

        }
        
    }

    class UserApiValidationRequest {
        private  @NotNull String deleteUserusername;
        private  @NotNull String getUserByNameusername;
        private  @NotNull String loginUserusername;
        private  @NotNull String loginUserpassword;
        private  @NotNull String updateUserusername;

    }

    class UserApiConstraint<T> {
        private void checkConstraint(Set<ConstraintViolation<T>> violations) throws ConstraintViolationException {
            Iterator<ConstraintViolation<T>> iterator = violations.iterator();
            if (iterator.hasNext()) {
                ConstraintViolation<T> violation = iterator.next();
                throw new ConstraintViolationException(violation.getPropertyPath() + " " + violation.getMessage(), violations);
            }
        }
    }

}
