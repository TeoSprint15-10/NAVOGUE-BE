package teo.sprint.navogue.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teo.sprint.navogue.common.security.jwt.JwtTokenProvider;
import teo.sprint.navogue.domain.user.data.response.UserLoginRes;
import teo.sprint.navogue.domain.user.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping ("/login")
    public ResponseEntity<UserLoginRes> login(@RequestParam("code") String code) throws Exception {
        //return  ResponseEntity.ok(new UserLoginRes(1L, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIwMjI2ODZAbmF2ZXIuY29tIiwiaWF0IjoxNjg3NjgwODQyLCJleHAiOjE5MDM2ODA4NDJ9.HdoXLX2KR5IArs1q2rbYvJrF0fSLGhdgtNlBgSFax1U"));
        return ResponseEntity.ok(userService.login(code));
    }
}