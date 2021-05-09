package springbootsecurityjwt.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenResponse {
    String tokenValue;
    String orderId;
}
