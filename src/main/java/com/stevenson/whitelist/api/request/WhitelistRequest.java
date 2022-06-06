package com.stevenson.whitelist.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WhitelistRequest {

    @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$",
            message = "ip must be a valid ip address"
    )
    @NotBlank(message = "ip string is required")
    private String ip;
    @NotBlank(message = "environment string is required")
    private String environment;
    @NotBlank(message = "app string is required")
    private String app;

}
