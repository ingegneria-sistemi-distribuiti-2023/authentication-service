package com.isd.authentication.test;

import com.isd.authentication.controller.UserController;
import com.isd.authentication.domain.User;
import com.isd.authentication.dto.UserBalanceDTO;
import com.isd.authentication.mapper.UserMapperService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class) // FIXME: fa runnare un test che ritorna un errore
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test
public class UsersAppTest {
    @Autowired
    MockMvc mockMvc;

//    FIXME: return error
//    @Test
//    public void contextLoads() {
//        Assertions.assertThat(mockMvc).isNot(null);
//    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/user/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(get("/user/findbyid?userId=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // TODO:
}