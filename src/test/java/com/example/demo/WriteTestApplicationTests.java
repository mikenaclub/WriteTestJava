package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WriteTestApplicationTests {

    //Test from function
    @Autowired
    private Test1 controller;

    @Test
    public void fromfunction() throws Exception {
        assertThat(controller.testfunction1()).isEqualTo("testfunction1");
    }

    //Test from url
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void fromurl() throws Exception {
        assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("testfunction1");
    }

    //Test from url2
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fromurl2() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("testfunction1")));
    }

    //test from url3
    @Test
    public void fromurl3() throws Exception {
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity("/", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("testfunction1");
    }

    //test from url3
    @Test
    public void fromurl4() throws Exception {
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity("/test", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).contains("testfunction1");
    }
}
