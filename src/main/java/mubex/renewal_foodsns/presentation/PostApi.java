package mubex.renewal_foodsns.presentation;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.PostService;
import mubex.renewal_foodsns.common.annotation.Login;
import mubex.renewal_foodsns.domain.dto.request.PostParam;
import mubex.renewal_foodsns.domain.dto.request.update.UpdatePostParam;
import mubex.renewal_foodsns.domain.dto.response.PostPageResponse;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.domain.type.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Validated
public class PostApi {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestPart("post") @Valid PostParam postParam,
                                               @RequestParam("tag") Set<Tag> tags,
                                               @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles,
                                               @Login Long memberId) {

        PostResponse postResponse = postService.create(
                postParam.title(),
                postParam.text(),
                memberId,
                tags,
                multipartFiles
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @PutMapping
    public ResponseEntity<PostResponse> update(@RequestBody @Valid UpdatePostParam updatePostParam,
                                               @RequestParam("tags") Set<Tag> tags,
                                               @Login Long memberId) {

        PostResponse postResponse = postService.update(
                updatePostParam.postId(),
                updatePostParam.title(),
                updatePostParam.text(),
                memberId,
                tags,
                updatePostParam.multipartFiles()
        );

        return ResponseEntity.ok(postResponse);
    }

    @PatchMapping("/heart")
    public ResponseEntity<PostResponse> increaseHeart(@RequestParam("postId") Long postId,
                                                      @Login Long memberId) {

        PostResponse postResponse = postService.increaseHeart(memberId, postId);

        return ResponseEntity.ok(postResponse);
    }

    @PatchMapping("/report")
    public ResponseEntity<PostResponse> increaseReport(@RequestParam("postId") Long postId,
                                                       @Login Long memberId) {

        PostResponse postResponse = postService.increaseReport(memberId, postId);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> find(@PathVariable("postId") Long postId) {

        PostResponse postResponse = postService.find(postId);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{title}/page")
    public ResponseEntity<Page<PostPageResponse>> findPage(@PathVariable("title") String title,
                                                           @PageableDefault(
                                                                   sort = "createdAt",
                                                                   direction = Sort.Direction.DESC
                                                           ) Pageable pageable) {

        Page<PostPageResponse> postsByTitle = postService.findPostsByTitle(title, pageable);

        return ResponseEntity.ok(postsByTitle);
    }

    @GetMapping("/{nickName}/page")
    public ResponseEntity<Page<PostPageResponse>> findPageByNickName(@PathVariable("nickName") String nickName,
                                                                     @PageableDefault(
                                                                             sort = "createdAt",
                                                                             direction = Sort.Direction.DESC
                                                                     ) Pageable pageable) {

        Page<PostPageResponse> postsByNickName = postService.findPostsByNickName(nickName, pageable);

        return ResponseEntity.ok(postsByNickName);
    }

    @GetMapping
    public ResponseEntity<Slice<PostPageResponse>> findAll(
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable) {

        Slice<PostPageResponse> all = postService.findAll(pageable);

        return ResponseEntity.ok(all);
    }
}
