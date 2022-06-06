package com.stevenson.whitelist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WhitelistStorageModel {
    private String ip;
    private String environment;
    private String app;
}
