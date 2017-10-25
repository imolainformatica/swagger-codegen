package io.swagger.api;

import io.swagger.model.Order;
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

public class StoreApiValidator implements Processor {

    private final String operation;

    public StoreApiValidator (String operation) {
        this.operation = operation;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final Message in = exchange.getIn();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        if (this.operation.equals("deleteOrder")) {
            String orderId = in.getHeader("orderId", String.class);
            new StoreApiConstraint<StoreApiValidationRequest>().checkConstraint(validator.validateValue(StoreApiValidationRequest.class, "deleteOrderorderId", orderId));

        }
        
        if (this.operation.equals("getInventory")) {

        }
        
        if (this.operation.equals("getOrderById")) {
            Long orderId = in.getHeader("orderId", Long.class);
            new StoreApiConstraint<StoreApiValidationRequest>().checkConstraint(validator.validateValue(StoreApiValidationRequest.class, "getOrderByIdorderId", orderId));

        }
        
        if (this.operation.equals("placeOrder")) {
            Order body = in.getBody(Order.class);
            new StoreApiConstraint<Order>().checkConstraint(validator.validate(body));

        }
        
    }

    class StoreApiValidationRequest {
        private  @NotNull String deleteOrderorderId;
        private  @NotNull @Min(1) @Max(5) Long getOrderByIdorderId;

    }

    class StoreApiConstraint<T> {
        private void checkConstraint(Set<ConstraintViolation<T>> violations) throws ConstraintViolationException {
            Iterator<ConstraintViolation<T>> iterator = violations.iterator();
            if (iterator.hasNext()) {
                ConstraintViolation<T> violation = iterator.next();
                throw new ConstraintViolationException(violation.getPropertyPath() + " " + violation.getMessage(), violations);
            }
        }
    }

}
