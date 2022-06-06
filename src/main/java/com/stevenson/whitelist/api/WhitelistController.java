package com.stevenson.whitelist.api;

import com.stevenson.whitelist.api.request.WhitelistRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;

public interface WhitelistController {
    ResponseEntity<?> addIpToWhitelist(WhitelistRequest Request) throws IOException, URISyntaxException;
    ResponseEntity<?> getAllWhitelist() throws IOException, URISyntaxException;
}
