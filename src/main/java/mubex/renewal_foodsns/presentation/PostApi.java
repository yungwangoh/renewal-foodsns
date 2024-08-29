package mubex.renewal_foodsns.presentation;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.application.PostService;
import mubex.renewal_foodsns.domain.document.PostDocument;
import mubex.renewal_foodsns.domain.dto.request.PostParam;
import mubex.renewal_foodsns.domain.dto.request.update.UpdatePostParam;
import mubex.renewal_foodsns.domain.dto.response.PostPageResponse;
import mubex.renewal_foodsns.domain.dto.response.PostResponse;
import mubex.renewal_foodsns.presentation.annotation.Login;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Validated
@Slf4j
public class PostApi {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@RequestPart("post") @Valid final PostParam postParam,
                               @RequestParam("tag") final Set<String> tags,
                               @RequestPart(value = "image", required = false) final List<MultipartFile> multipartFiles,
                               @Login final Long memberId) {

        return postService.create(
                postParam.title(),
                postParam.text(),
                memberId,
                tags,
                multipartFiles
        );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PostResponse update(@RequestBody @Valid final UpdatePostParam updatePostParam,
                               @RequestParam("tags") final Set<String> tags,
                               @Login final Long memberId) {

        return postService.update(
                updatePostParam.postId(),
                updatePostParam.title(),
                updatePostParam.text(),
                memberId,
                tags,
                updatePostParam.multipartFiles()
        );
    }

    @PatchMapping("/heart")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse increaseHeart(@RequestParam("postId") final Long postId,
                                      @Login final Long memberId) {

        return postService.increaseHeart(memberId, postId, 1);
    }

    @PatchMapping("/report")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse increaseReport(@RequestParam("postId") final Long postId,
                                       @Login final Long memberId) {

        return postService.increaseReport(memberId, postId, 1);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse find(@PathVariable("postId") final Long postId) {

        return postService.find(postId);
    }

    @GetMapping("/title")
    @ResponseStatus(HttpStatus.OK)
    public Slice<PostPageResponse> findTitlePage(@RequestParam("title") final String title,
                                                 @PageableDefault(
                                                         sort = "createdAt",
                                                         direction = Sort.Direction.DESC
                                                 ) final Pageable pageable) {

        return postService.findPostsByTitle(title, pageable);
    }

    @GetMapping("/nickName")
    @ResponseStatus(HttpStatus.OK)
    public Page<PostPageResponse> findPageByNickName(@RequestParam("nickName") final String nickName,
                                                     @PageableDefault(
                                                             sort = "createdAt",
                                                             direction = Sort.Direction.DESC
                                                     ) final Pageable pageable) {

        return postService.findPostsByNickName(nickName, pageable);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Slice<PostPageResponse> findAll(
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) final Pageable pageable) {

        return postService.findAll(pageable);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public SearchHits<PostDocument> searchFullText(@RequestParam("searchText") final String searchText,
                                                   @PageableDefault(
                                                           sort = "heart",
                                                           direction = Sort.Direction.DESC
                                                   ) final Pageable pageable) {

        return postService.findAllByText(searchText, pageable);
    }
}
