package springbootsecurityjwt.pojo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenRequest {

    private String clientId;
    private String clientSecret;
    private String clientName;
    private Payload payload;
}
