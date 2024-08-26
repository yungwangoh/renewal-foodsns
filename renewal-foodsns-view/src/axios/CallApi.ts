import {postApi} from "./post/PostApi";
import {PostParam, Tag} from "./type/PostType";

export function createPost(postParam: PostParam, tags: Set<Tag>, images: File[]) {
    return postApi.create(postParam, tags, images);
}

export function findPostAll() {
    return postApi.findAll();
}

export function searchPostByFullText(searchText: string, page: number = 0, size: number = 20, sort: string = 'heart,DESC') {
    return postApi.searchFullText(searchText, page, size, sort);
}