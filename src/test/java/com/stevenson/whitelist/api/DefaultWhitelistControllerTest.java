package com.stevenson.whitelist.api;

import com.stevenson.whitelist.api.request.WhitelistRequest;
import com.stevenson.whitelist.model.WhitelistStorageModel;
import com.stevenson.whitelist.service.DefaultFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DefaultWhitelistControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private DefaultFileService mockService;

    @Test
    void addIpToWhitelist() throws IOException {
        WhitelistRequest request = WhitelistRequest.builder()
                .ip("192.168.0.1")
                .environment("DEV")
                .app("app1")
                .build();
        WhitelistStorageModel sm = WhitelistStorageModel.builder()
                .ip("192.168.0.1")
                .environment("DEV")
                .app("app1")
                .build();
        given(this.mockService.saveToFile(any())).willReturn(sm);

        ResponseEntity<WhitelistStorageModel> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/whitelists",
                request,
                WhitelistStorageModel.class);
        assertThat(response, is(notNullValue()));
        assertThat(response.getBody().getIp(), is(sm.getIp()));
        assertThat(response.getStatusCodeValue(), is(201));
    }

    @Test
    void getAllWhitelist() throws IOException {
        WhitelistStorageModel sm = WhitelistStorageModel.builder()
                .ip("192.168.0.1")
                .environment("DEV")
                .app("app1")
                .build();
        List<WhitelistStorageModel> list = new ArrayList<>();
        list.add(sm);
        given(this.mockService.retrieveAll()).willReturn(list);

        ResponseEntity<WhitelistStorageModel[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/whitelists",
                WhitelistStorageModel[].class);
        assertThat(response, is(notNullValue()));
        assertThat(response.getBody()[0].getIp(), is(sm.getIp()));
        assertThat(response.getStatusCodeValue(), is(200));
    }
}