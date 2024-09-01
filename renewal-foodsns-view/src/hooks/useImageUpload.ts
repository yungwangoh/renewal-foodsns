import React, {useCallback, useRef, useState} from "react";

interface ImageFile {
    id: number;
    file: File;
    preview: string;
}

const MAX_IMAGES = 10;

export function useImageUpload() {
    const [images, setImages] = useState<ImageFile[]>([]);
    const [error, setError] = useState<string | null>(null);
    const nextIdRef = useRef(0);
    const fileInputRef = useRef<HTMLInputElement>(null);

    const getNextId = useCallback(() => {
        const id = nextIdRef.current;
        nextIdRef.current += 1;
        return id;
    }, []);

    const handleFileChange = useCallback((event: React.ChangeEvent<HTMLInputElement>) => {
        const files = event.target.files;
        if (files && files.length > 0) {
            setImages(prevImages => {
                const remainingSlots = MAX_IMAGES - prevImages.length;
                const filesToAdd = Array.from(files).slice(0, remainingSlots);

                if (filesToAdd.length < files.length) {
                    setError(`${files.length - filesToAdd.length}개의 이미지가 최대 개수를 초과하여 추가되지 않았습니다.`);
                } else {
                    setError(null);
                }

                const newImages = filesToAdd.map(file => ({
                    id: getNextId(),
                    file,
                    preview: URL.createObjectURL(file)
                }));

                return [...prevImages, ...newImages];
            });
        }
        // 파일 입력을 리셋합니다.
        if (event.target) {
            event.target.value = '';
        }
    }, [getNextId]);

    const handleDeleteImage = useCallback((id: number) => {
        setImages(prevImages => {
            const imageToDelete = prevImages.find(img => img.id === id);
            if (imageToDelete && imageToDelete.preview) {
                URL.revokeObjectURL(imageToDelete.preview);
            }
            return prevImages.filter(img => img.id !== id);
        });
        setError(null);
    }, []);

    const resetImages = useCallback(() => {
        setImages(prevImages => {
            prevImages.forEach(image => {
                if (image.preview) {
                    URL.revokeObjectURL(image.preview);
                }
            });
            return [];
        });
        setError(null);
        nextIdRef.current = 0;
        if (fileInputRef.current) {
            fileInputRef.current.value = '';
        }
    }, []);

    return {
        images,
        error,
        fileInputRef,
        handleFileChange,
        handleDeleteImage,
        resetImages,
        isMaxImagesReached: images.length >= MAX_IMAGES
    };
}