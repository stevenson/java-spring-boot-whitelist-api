package com.stevenson.whitelist.service;

import com.stevenson.whitelist.api.exception.ApiStorageException;
import com.stevenson.whitelist.api.request.WhitelistRequest;
import com.stevenson.whitelist.model.WhitelistStorageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class DefaultFileService implements FileService{

    @Value("${storage.filepath}")
    private String defaultFilepath;

    @Override
    public WhitelistStorageModel saveToFile(WhitelistRequest request) throws IOException {
        if(!isDuplicate(request)) {
            File f = new File(defaultFilepath);
            if(!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(defaultFilepath, true));
            PrintWriter out = new PrintWriter(writer);
            out.println(request.getIp()+","+request.getEnvironment()+","+request.getApp());
            writer.close();
            WhitelistStorageModel model = WhitelistStorageModel.builder()
                    .ip(request.getIp())
                    .environment(request.getEnvironment())
                    .app(request.getApp())
                    .build();
            return model;
        }else{
            throw new ApiStorageException("the entry already exists");
        }
    }

    @Override
    public List<WhitelistStorageModel> retrieveAll() throws IOException {
        ArrayList<WhitelistStorageModel> result = new ArrayList<>();
        File f = new File(defaultFilepath);
        if(f.exists() && !f.isDirectory()) {
            try (BufferedReader br = new BufferedReader(new FileReader(defaultFilepath))) {
                while (br.ready()) {
                    String str = br.readLine();
                    result.add(convertToDto(Arrays.asList(str.split(","))));
                }
            }
        }
        return result;
    }

    private WhitelistStorageModel convertToDto(List<String> lineData){
        return WhitelistStorageModel.builder()
                .ip(lineData.get(0))
                .environment(lineData.get(1))
                .app(lineData.get(2))
                .build();
    }

    private boolean isDuplicate(WhitelistRequest request) throws IOException {
        WhitelistStorageModel newStorage = WhitelistStorageModel.builder()
                .ip(request.getIp())
                .environment(request.getEnvironment())
                .app(request.getApp())
                .build();
        List<WhitelistStorageModel> whitelists = retrieveAll();
        for (WhitelistStorageModel whitelist: whitelists) {
            if(whitelist.equals(newStorage)){
                System.out.println("it is a duplicate");
                return true;
            }
        }
        System.out.println("it is a not a duplicate");
        return false;
    }

}
