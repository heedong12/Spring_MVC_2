package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */

//HTTP 요청 메시지가 Json 형식일 때
@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();

    //HTTPServletRequest를 사용해서 직접 읽어옴
    @PostMapping("/request-body-json-v1")
    public void RequestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }
    //@RequestBody 사용
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String RequestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}",messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }
    //HelloData 객체 사용
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String RequestBodyJsonV3(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }
    //HttpEntity 사용
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String RequestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData helloData=httpEntity.getBody();
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }
    //응답에 객체 넣어줄 수 있음(HttpMessageConverter)
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData RequestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return data;
    }
}
