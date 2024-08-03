package mubex.renewal_foodsns.presentation;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mubex.renewal_foodsns.application.PostService;
import mubex.renewal_foodsns.domain.dto.request.PostParam;
import mubex.renewal_foodsns.domain.dto.request.update.UpdatePostParam;
import mubex.renewal_foodsns.domain.dto.response.PostPageResponse;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.domain.type.Tag;
import mubex.renewal_foodsns.presentation.annotation.Login;
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
    public ResponseEntity<PostResponse> create(@RequestPart("post") @Valid final PostParam postParam,
                                               @RequestParam("tag") final Set<Tag> tags,
                                               @RequestPart(value = "image", required = false) final List<MultipartFile> multipartFiles,
                                               @Login final Long memberId) {

        final PostResponse postResponse = postService.create(
                postParam.title(),
                postParam.text(),
                memberId,
                tags,
                multipartFiles
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @PutMapping
    public ResponseEntity<PostResponse> update(@RequestBody @Valid final UpdatePostParam updatePostParam,
                                               @RequestParam("tags") final Set<Tag> tags,
                                               @Login final Long memberId) {

        final PostResponse postResponse = postService.update(
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
    public ResponseEntity<PostResponse> increaseHeart(@RequestParam("postId") final Long postId,
                                                      @Login final Long memberId) {

        final PostResponse postResponse = postService.increaseHeart(memberId, postId, 1);

        return ResponseEntity.ok(postResponse);
    }

    @PatchMapping("/report")
    public ResponseEntity<PostResponse> increaseReport(@RequestParam("postId") final Long postId,
                                                       @Login final Long memberId) {

        final PostResponse postResponse = postService.increaseReport(memberId, postId, 1);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> find(@PathVariable("postId") final Long postId) {

        final PostResponse postResponse = postService.find(postId);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/title/page")
    public ResponseEntity<Page<PostPageResponse>> findPage(@RequestParam("title") final String title,
                                                           @PageableDefault(
                                                                   sort = "createdAt",
                                                                   direction = Sort.Direction.DESC
                                                           ) final Pageable pageable) {

        final Page<PostPageResponse> postsByTitle = postService.findPostsByTitle(title, pageable);

        return ResponseEntity.ok(postsByTitle);
    }

    @GetMapping("/nickName/page")
    public ResponseEntity<Page<PostPageResponse>> findPageByNickName(@RequestParam("nickName") final String nickName,
                                                                     @PageableDefault(
                                                                             sort = "createdAt",
                                                                             direction = Sort.Direction.DESC
                                                                     ) final Pageable pageable) {

        final Page<PostPageResponse> postsByNickName = postService.findPostsByNickName(nickName, pageable);

        return ResponseEntity.ok(postsByNickName);
    }

    @GetMapping
    public ResponseEntity<Slice<PostPageResponse>> findAll(
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) final Pageable pageable) {

        final Slice<PostPageResponse> all = postService.findAll(pageable);

        return ResponseEntity.ok(all);
    }
}
