package com.example.http;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by ms on 2017. 12. 20..
 */
@RestController
@RequestMapping("/test/http")
public class ServerController {
    private static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

    @RequestMapping(value = "/500/1", method = RequestMethod.POST, produces = APPLICATION_JSON_UTF8)
    public Integer test500_1(@RequestBody Parameter parameter) {
        /**
         * 500 ERROR - 클라이언트로부터 전달받은 파라미터를 가공하다가 발생할 수 있는 상황을 재현
         *
         * /test/http/500/1
         * {"name": "ms", "age": "28", "job": "front developer"}
         */
        Integer parsedName = Integer.parseInt(parameter.getName());

        return parsedName;
    }

    @RequestMapping(value = "/500/2", method = RequestMethod.POST, produces = APPLICATION_JSON_UTF8)
    public String test500_2(@RequestBody Map map) {
        /**
         * 500 ERROR - 클라이언트의 요청을 처리하다가 null 값을 만나 발생할 수 있는 상황을 재현
         * /test/http/500/1
         * {"name": "ms", "age": "28", "job": "front developer"}
         */
        String userName = (String) map.get("userName");
        if(userName.equals("ms")) {
            System.out.println("user name is ms!");
        }

        return userName;
    }

    @RequestMapping(value = "/503/1", method = RequestMethod.GET)
    public String test503_1(@RequestParam Integer sleepSeconds) throws InterruptedException {
        /**
         * 503 ERROR - 서버가 스레드를 가용할 수 없는 상황을 재현
         */
        Thread.sleep(sleepSeconds * 1000);

        return "success";
    }

    @RequestMapping(value = "/504/1", method = RequestMethod.GET)
    public String test504_1(@RequestParam Integer sleepSeconds) throws InterruptedException {
        /**
         * 504 ERROR - WAS까지 요청이 전달됐지만, 병목이나 잘못된 구현으로 적절한 시간내에 응답을 하지못하는 상황을 재현
         */
        Thread.sleep(sleepSeconds * 1000);

        return "success";
    }
}
