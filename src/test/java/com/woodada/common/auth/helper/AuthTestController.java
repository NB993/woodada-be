package com.woodada.common.auth.helper;

import com.woodada.common.auth.argument_resolver.WddMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Profile("test")
@RestController
public class AuthTestController {

    @GetMapping("/api/auth")
    void api_using_WddMember(WddMember wddMember) {
    }
}
