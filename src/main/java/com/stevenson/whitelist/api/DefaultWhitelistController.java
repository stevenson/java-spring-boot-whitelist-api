package com.stevenson.whitelist.api;

import com.stevenson.whitelist.api.request.WhitelistRequest;
import com.stevenson.whitelist.model.WhitelistStorageModel;
import com.stevenson.whitelist.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RequestMapping("api/v1/whitelists")
@RestController
public class DefaultWhitelistController implements WhitelistController{
    @Autowired
    private final FileService service;

    public DefaultWhitelistController(FileService service){
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> addIpToWhitelist(@Valid @RequestBody WhitelistRequest request){
        System.out.println(request);
        try {
            service.saveToFile(request);
        }catch (IOException | URISyntaxException e){
            e.printStackTrace();    //prints exception if any
        }
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<?> getAllWhitelist(){
        try {
            List<WhitelistStorageModel> data = service.retrieveAll();
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (URISyntaxException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
