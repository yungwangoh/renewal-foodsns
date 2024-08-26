import axios, {AxiosResponse} from "axios";

import {
    PostDocument,
    PostPageResponse,
    PostParam,
    PostResponse,
    SearchHits,
    Tag,
    UpdatePostParam
} from "../type/PostType";

const baseUrl = "http://localhost:8080/api/v1/posts";

export const postApi = {
    create: async (postParam: PostParam, tags: Set<Tag>, images: File[]): Promise<PostResponse> => {
        const formData = new FormData();
        formData.append('post', JSON.stringify(postParam));
        formData.append('tag', JSON.stringify(Array.from(tags)));
        images.forEach((image) => {
            formData.append(`image`, image);
        });

        const config = {
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            withCredentials: true // 쿠키를 포함하여 요청을 보냅니다
        };

        const response: AxiosResponse<PostResponse> = await axios.post(baseUrl, formData, config);
        return response.data;
    },

    update: async (updatePostParam: UpdatePostParam, tags: Set<Tag>): Promise<PostResponse> => {
        const config = {
            params: {
                tags: Array.from(tags)
            },
            withCredentials: true // 쿠키를 포함하여 요청을 보냅니다
        };

        const response: AxiosResponse<PostResponse> = await axios.put(baseUrl, updatePostParam, config);
        return response.data;
    },

    increaseHeart: async (postId: number): Promise<PostResponse> => {
        const config = {
            params: {
                postId
            },
            withCredentials: true // 쿠키를 포함하여 요청을 보냅니다
        };

        const response: AxiosResponse<PostResponse> = await axios.patch(`${baseUrl}/heart`, null, config);
        return response.data;
    },

    increaseReport: async (postId: number): Promise<PostResponse> => {
        const config = {
            params: {
                postId
            },
            withCredentials: true // 쿠키를 포함하여 요청을 보냅니다
        };

        const response: AxiosResponse<PostResponse> = await axios.patch(`${baseUrl}/report`, null, config);
        return response.data;
    },

    find: async (postId: number): Promise<PostResponse> => {
        const config = {
            withCredentials: true // 쿠키를 포함하여 요청을 보냅니다
        };

        const response: AxiosResponse<PostResponse> = await axios.get(`${baseUrl}/${postId}`, config);
        return response.data;
    },

    findTitlePage: async (title: string, page: number = 0, size: number = 20, sort: string = 'createdAt,DESC'): Promise<PostPageResponse> => {
        const config = {
            params: {
                title,
                page,
                size,
                sort
            },
            withCredentials: true // 쿠키를 포함하여 요청을 보냅니다
        };

        const response: AxiosResponse<PostPageResponse> = await axios.get(`${baseUrl}/title`, config);
        return response.data;
    },

    findPageByNickName: async (nickName: string, page: number = 0, size: number = 20, sort: string = 'createdAt,DESC'): Promise<PostPageResponse> => {
        const config = {
            params: {
                nickName,
                page,
                size,
                sort
            },
            withCredentials: true // 쿠키를 포함하여 요청을 보냅니다
        };

        const response: AxiosResponse<PostPageResponse> = await axios.get(`${baseUrl}/nickName`, config);
        return response.data;
    },

    findAll: async (page: number = 0, size: number = 20, sort: string = 'createdAt,DESC'): Promise<PostPageResponse> => {
        const config = {
            params: {
                page,
                size,
                sort
            },
            withCredentials: true // 쿠키를 포함하여 요청을 보냅니다
        };

        const response: AxiosResponse<PostPageResponse> = await axios.get(baseUrl, config);
        return response.data;
    },

    searchFullText: async (searchText: string, page: number = 0, size: number = 20, sort: string = 'heart,DESC'): Promise<SearchHits<PostDocument>> => {
        const config = {
            params: {
                searchText,
                page,
                size,
                sort
            },
            withCredentials: true // 쿠키를 포함하여 요청을 보냅니다
        };

        const response: AxiosResponse<SearchHits<PostDocument>> = await axios.get(`${baseUrl}/search`, config);
        return response.data;
    }
};