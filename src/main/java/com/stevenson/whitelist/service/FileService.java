package com.stevenson.whitelist.service;

import com.stevenson.whitelist.api.request.WhitelistRequest;
import com.stevenson.whitelist.model.WhitelistStorageModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface FileService {
    WhitelistStorageModel saveToFile(WhitelistRequest request) throws IOException, URISyntaxException;
    List<WhitelistStorageModel> retrieveAll() throws IOException, URISyntaxException;
}
