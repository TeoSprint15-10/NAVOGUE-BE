package teo.sprint.navogue.domain.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teo.sprint.navogue.common.security.jwt.JwtTokenProvider;
import teo.sprint.navogue.domain.memo.data.entity.Memo;
import teo.sprint.navogue.domain.memo.data.req.MemoAddReq;
import teo.sprint.navogue.domain.memo.data.req.MemoUpdateReq;
import teo.sprint.navogue.domain.memo.data.res.*;
import teo.sprint.navogue.domain.memo.service.MemoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/memo")
public class MemoController {
    @Autowired
    private MemoService memoService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/token")
    public ResponseEntity<String> testToken(@RequestHeader("Access-Token") String accessToken) throws Exception {
        String email = jwtTokenProvider.getEmail(accessToken);
        return ResponseEntity.ok("토큰 이메일 : " + email);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() throws Exception {
        return ResponseEntity.ok("CORS 극복 성공맨");
    }

    @PostMapping
    public ResponseEntity<MemoAddRes> addMemo(@RequestBody MemoAddReq memoAddReq,
                                              @RequestHeader("Access-Token") String accessToken) throws Exception {
        String email = jwtTokenProvider.getEmail(accessToken);
        return ResponseEntity.ok(memoService.addMemo(memoAddReq, email));
    }

    @GetMapping
    public ResponseEntity<Slice<MemoListRes>> getMemoList(@RequestParam("page") int page,
                                                          @RequestParam("type") Optional<String> typeParam,
                                                          @RequestParam("tag") Optional<String> tagParam,
                                                          @RequestParam("keyword") Optional<String> keywordParam,
                                                          @RequestHeader("Access-Token") String accessToken) throws Exception {
        String type = typeParam.orElse("");
        String tag = tagParam.orElse("");
        String keyword = keywordParam.orElse("");
        String email = jwtTokenProvider.getEmail(accessToken);

        return ResponseEntity.ok(memoService.getList(page, type, tag, keyword, email));
    }

    @DeleteMapping("/{memoId}")
    public ResponseEntity<MemoDeleteRes> delete(@PathVariable("memoId") int memoId) throws Exception {
        return ResponseEntity.ok(new MemoDeleteRes(memoService.delete(memoId)));
    }

    @PutMapping
    public ResponseEntity<MemoUpdateRes> update(@RequestBody MemoUpdateReq memoUpdateReq) throws Exception {
        return ResponseEntity.ok(new MemoUpdateRes(memoService.update(memoUpdateReq)));
    }

    @PatchMapping("/pin/{memoId}")
    public ResponseEntity<MemoPinRes> pin(@PathVariable("memoId") int memoId, @RequestHeader("Access-Token") String accessToken) throws Exception {
        return ResponseEntity.ok(new MemoPinRes(memoService.pin(memoId)));
    }
}