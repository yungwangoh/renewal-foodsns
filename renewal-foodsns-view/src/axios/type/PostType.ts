// MemberResponse 인터페이스 (실제 구조에 맞게 조정 필요)
export interface MemberResponse {
    id: number;
    nickname: string;
    // 추가 필드가 있다면 여기에 정의
}

// PostImageResponse 인터페이스 (실제 구조에 맞게 조정 필요)
export interface PostImageResponse {
    id: number;
    url: string;
    // 추가 필드가 있다면 여기에 정의
}

// PostResponse 인터페이스
export interface PostResponse {
    id: number;
    title: string;
    text: string;
    heart: number;
    report: number;
    views: number;
    visible: boolean;
    memberResponse: MemberResponse;
    postImageResponses: PostImageResponse[];
}

// PostParam 인터페이스
export interface PostParam {
    title: string;
    text: string;
}

// UpdatePostParam 인터페이스
export interface UpdatePostParam {
    postId: number;
    title: string;
    text: string;
    multipartFiles: File[]; // 브라우저 환경에서는 File 객체 사용
}

// Tag 타입 (이전에 언급된 Set<Tag>를 위해)
export type Tag = string; // 또는 Tag에 대한 더 구체적인 인터페이스가 있다면 그것을 사용

// PostPageResponse 인터페이스 (페이징된 결과를 위해)
export interface PostPageResponse {
    content: PostResponse[];
    pageable: {
        pageNumber: number;
        pageSize: number;
        sort: {
            empty: boolean;
            sorted: boolean;
            unsorted: boolean;
        };
        offset: number;
        paged: boolean;
        unpaged: boolean;
    };
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    sort: {
        empty: boolean;
        sorted: boolean;
        unsorted: boolean;
    };
    first: boolean;
    numberOfElements: number;
    empty: boolean;
}

// SearchHits 인터페이스 (검색 결과를 위해)
export interface SearchHits<T> {
    hits: {
        content: T;
        id: string;
        score: number;
    }[];
    maxScore: number;
    totalHits: number;
}

// PostDocument 인터페이스 (검색 결과 문서를 위해)
export interface PostDocument {
    id: number;
    title: string;
    text: string;
    // 추가 필드가 있다면 여기에 정의
} // Assuming Tag is a string, adjust if it's a more complex type
