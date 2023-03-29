package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//@Slf4j
@RestController
public class LogTestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "spring";
        //로그로 찍기
        log.trace("trace log={}",name);
        log.debug("debug log={}",name);
        log.info(" info log={}, name");
        log.warn(" warn log={}, name");
        log.error("error log={}, name");


        //HTTP 메시지 바디에 바로 입력
        return "ok";
    }
}
