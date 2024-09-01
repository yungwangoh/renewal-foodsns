import axios from "axios";

const BASE_URL = '/api/v1';

export interface CommentParam {
    text: string;
}

export interface CommentResponse {
    postId: number;
    id: number;
    nickName: string;
    text: string;
    heart: number;
    report: number;
}

export interface PageResponse<T> {
    content: T[];
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

export interface SliceResponse<T> {
    content: T[];
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

export const commentApi = {

    create: async (postId: number, commentParam: CommentParam): Promise<CommentResponse> => {
        const response = await axios.post(`${BASE_URL}/${postId}/comments`, commentParam);
        return response.data;
    },

    update: async (postId: number, commentId: number, commentParam: CommentParam): Promise<CommentResponse> => {
        const response = await axios.put(`${BASE_URL}/${postId}/comments/${commentId}`, commentParam);
        return response.data;
    },

    findByPostId: async (postId: number, page: number = 0, size: number = 20, sort: string = 'createAt,DESC'): Promise<PageResponse<CommentResponse>> => {
        const config = {
            params: {
                sort: sort,
                page: page,
                size: size,
            }
        }

        const response = await axios.get(`${BASE_URL}/${postId}/comments/page`, config);
        return response.data;
    },

    findSliceByPostId: async (postId: number, page: number = 0, size: number = 20, sort: string = 'createAt,DESC'): Promise<SliceResponse<CommentResponse>> => {
        const config = {
            params: {
                sort: sort,
                page: page,
                size: size,
            }
        }
        const response = await axios.get(`${BASE_URL}/${postId}/comments/slice`, config);
        return response.data;
    },

    delete: async (postId: number, commentId: number): Promise<void> => {
        await axios.delete(`${BASE_URL}/${postId}/comments/${commentId}`);
    },

    increaseHeart: async (postId: number, commentId: number): Promise<CommentResponse> => {
        const response = await axios.patch(`${BASE_URL}/${postId}/comments/heart/${commentId}`);
        return response.data;
    },

    increaseReport: async (postId: number, commentId: number): Promise<CommentResponse> => {
        const response = await axios.patch(`${BASE_URL}/${postId}/comments/report/${commentId}`);
        return response.data;
    }
}