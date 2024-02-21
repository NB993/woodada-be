package com.woodada.test.common.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodada.common.auth.adapter.out.persistence.MemberRepository;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.JwtProperties;
import com.woodada.common.config.CorsProperties;
import com.woodada.common.exception.WddException;
import com.woodada.test.common.exception.ExceptionTestController.TestRequestBody;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

//todo TestController에 목 객체 주입해서 예외 발생 메서드를 stubbing하는 방법도 고려.
@DisplayName("[unit test] GlobalExceptionHandler 단위 테스트")
@ActiveProfiles("test")
@WebMvcTest(value = {ExceptionTestController.class, JwtHandler.class, JwtProperties.class, CorsProperties.class})
public class GlobalExceptionHandlerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private MemberRepository memberRepository;

    @DisplayName("IllegalArgumentException 핸들러 테스트")
    @Test
    void illegal_argument_exception_response() throws Exception{
        //when
        final ResultActions perform = mockMvc.perform(post("/exception/illegal-argument-exception"));

        //then
        perform
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(IllegalArgumentException.class))
            .andExpect(jsonPath("status").value(false))
            .andExpect(jsonPath("error.code").value("400"))
            .andExpect(jsonPath("error.message").exists())
            .andExpect(jsonPath("error.validations").isEmpty())
            .andDo(print());
    }

    @DisplayName("HttpRequestMethodNotSupportedException 핸들러 테스트")
    @Test
    void http_request_method_not_supported_exception_response() throws Exception {
        //when
        final ResultActions perform = mockMvc.perform(
            delete("/exception/http-request-method-not-supported-exception"));

        //then
        perform
            .andExpect(status().isMethodNotAllowed())
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(HttpRequestMethodNotSupportedException.class))
            .andExpect(jsonPath("status").value(false))
            .andExpect(jsonPath("error.code").value("405"))
            .andExpect(jsonPath("error.validations").isEmpty())
            .andDo(print());
    }

    @DisplayName("HttpMessageNotReadableException 핸들러 테스트")
    @Test
    void http_message_not_readable_exception_response() throws Exception {
        //when
        ResultActions perform = mockMvc.perform(post("/exception/http-message-not-readable-exception")
            .contentType(MediaType.APPLICATION_JSON));
            //body 입력 X

        //then
        perform
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(HttpMessageNotReadableException.class))
            .andExpect(jsonPath("status").value(false))
            .andExpect(jsonPath("error.code").value("400"))
            .andExpect(jsonPath("error.message").exists())
            .andExpect(jsonPath("error.validations").isEmpty())
            .andDo(print());
    }

    @DisplayName("MethodArgumentNotValidException 핸들러 테스트")
    @Test
    void method_argument_not_valid_exception_response() throws Exception {
        //given
        final TestRequestBody invalidBody = new TestRequestBody("1234", -1, List.of());

        //when
        ResultActions perform = mockMvc.perform(post("/exception/method-argument-not-valid-exception")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidBody)));

        //then
        perform
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
            .andExpect(jsonPath("status").value(false))
            .andExpect(jsonPath("error.code").value("400"))
            .andExpect(jsonPath("error.message").value("입력 조건을 위반하였습니다."))
            .andExpect(jsonPath("error.validations").exists())
            .andDo(print());
    }

    @DisplayName("WddException 핸들러 테스트")
    @Test
    void wdd_exception_response() throws Exception {
        //when
        ResultActions perform = mockMvc.perform(get("/exception/business-exception"));

        //then
        perform
            .andExpect(status().isNotAcceptable())
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(WddException.class))
            .andExpect(jsonPath("status").value(false))
            .andExpect(jsonPath("error.code").value(TestException.TEST.getCode()))
            .andExpect(jsonPath("error.message").value(TestException.TEST.getMessage()))
            .andExpect(jsonPath("error.validations").isEmpty())
            .andDo(print());
    }

    @DisplayName("Exception 핸들러 테스트")
    @Test
    void server_error_response() throws Exception {
        //when
        ResultActions perform = mockMvc.perform(get("/exception/internal-server-error"));

        //then
        perform
            .andExpect(status().isInternalServerError())
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(Exception.class))
            .andExpect(jsonPath("status").value(false))
            .andExpect(jsonPath("error.code").value("500"))
            .andExpect(jsonPath("error.message").value("서버 에러"))
            .andExpect(jsonPath("error.validations").isEmpty())
            .andDo(print());
    }

}
