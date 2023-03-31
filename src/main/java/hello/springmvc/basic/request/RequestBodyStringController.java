package hello.springmvc.basic.request;

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
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

//HTTP 요청메시지가 단순 텍스트일때
@Slf4j
@Controller
public class RequestBodyStringController {
    //InputStream 직접 사용
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        response.getWriter().write("ok");
    }
    //Input, Output 스트림, Reader
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}",messageBody);

        responseWriter.write("ok");
    }
    //Stream 사용X -> HttpEntity 사용
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}",messageBody);

        return new HttpEntity<>("ok");
    }
    //애노테이션 제공 -> @RequestBody
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}",messageBody);

        return "ok";
    }
}
