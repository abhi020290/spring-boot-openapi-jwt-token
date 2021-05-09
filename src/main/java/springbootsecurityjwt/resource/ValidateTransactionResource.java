package springbootsecurityjwt.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springbootsecurityjwt.pojo.Payload;

@RestController
@RequestMapping("/api")
public class ValidateTransactionResource {

    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/validateTransaction", method = RequestMethod.POST)
    ResponseEntity<String> handleTransaction(@RequestBody Payload payload) {
        return ResponseEntity.status(200).body("Validate Transaction Completed for Id " + payload.getOrderId());
    }
}
