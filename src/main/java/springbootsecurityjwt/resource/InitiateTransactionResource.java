package springbootsecurityjwt.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springbootsecurityjwt.jwt.JwtUtil;
import springbootsecurityjwt.pojo.TokenRequest;
import springbootsecurityjwt.pojo.TokenResponse;

@RestController
@RequestMapping("/initiateTransaction")
@Slf4j
public class InitiateTransactionResource {

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<TokenResponse> generateToken(@RequestBody TokenRequest tokenRequest) {
        String token = jwtUtil.generateToken(tokenRequest);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setOrderId(tokenRequest.getPayload().getOrderId());
        log.info("Token generated " + token);
        tokenResponse.setTokenValue(token);
        return ResponseEntity.status(200).body(tokenResponse);
    }

}
