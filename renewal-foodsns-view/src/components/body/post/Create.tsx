import {
    Button,
    Image,
    Input,
    Modal,
    ModalBody,
    ModalContent,
    ModalFooter,
    ModalHeader,
    Textarea,
    User
} from "@nextui-org/react";
import {ImageIcon} from "../../icon/ImageIcon";
import React, {useState} from "react";
import {IoIosCloseCircleOutline} from "react-icons/io";
import {useImageUpload} from "../../../hooks/useImageUpload";

interface CreateProps {
    isOpen: boolean;
    onOpenChange: (isOpen: boolean) => void;
}

export function Create({isOpen, onOpenChange}: CreateProps) {

    const [text, setText] = useState("");
    const {
        images,
        error,
        fileInputRef,
        handleFileChange,
        handleDeleteImage,
        resetImages,
        isMaxImagesReached
    } = useImageUpload();

    const handleOpenChange = (open: boolean) => {
        if (!open) {
            resetImages();
            setText("");
        }
        onOpenChange(open);
    };

    const handleTextChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setText(event.target.value);
    };

    const handleButtonClick = () => {
        if (fileInputRef.current) {
            fileInputRef.current.click();
        }
    };

    return (
        <Modal
            size="2xl"
            backdrop="opaque"
            isOpen={isOpen}
            onOpenChange={handleOpenChange}
        >
            <ModalContent>
                {(onClose) => (
                    <>
                        <ModalHeader className="flex justify-center">
                            새로운 게시물
                        </ModalHeader>
                        <ModalBody>
                            <User
                                className="flex justify-start"
                                name="윤광오"
                            />
                        </ModalBody>
                        <ModalBody>
                            <Textarea
                                isRequired
                                label="글쓰기"
                                labelPlacement="outside"
                                placeholder="게시물을 작성해주세요..."
                                className="w-full"
                                variant="faded"
                                value={text}
                                onChange={handleTextChange}
                            />
                        </ModalBody>
                        <ModalBody>
                            <div className="flex flex-row gap-1.5">
                                {images.length > 0 &&

                                    (images.map((image) => (
                                        <div key={image.id}>
                                            {image.preview && (
                                                <Image
                                                    src={image.preview}
                                                    alt={`Preview ${image.id}`}
                                                    width={200}
                                                    height={200}
                                                />
                                            )}
                                            <Button
                                                size="sm"
                                                variant="light"
                                                onPress={() => handleDeleteImage(image.id)}
                                            >
                                                <IoIosCloseCircleOutline size={20}/>
                                            </Button>
                                        </div>
                                    )))

                                }
                            </div>
                            <div className="flex flex-col gap-1">
                                <Input
                                    className="hidden"
                                    type="file"
                                    ref={fileInputRef}
                                    onChange={handleFileChange}
                                    accept="image/*"
                                    style={{display: 'none'}}
                                />
                                {error && <p color="error">{error}</p>}
                                <Button
                                    isIconOnly
                                    variant="light"
                                    onPress={handleButtonClick}
                                    disabled={images.length >= isMaxImagesReached}
                                >
                                    <ImageIcon/>
                                </Button>
                            </div>
                        </ModalBody>
                        <ModalFooter>
                            <Button color="default" variant="bordered" onPress={onClose}>
                                게시
                            </Button>
                        </ModalFooter>
                    </>
                )}
            </ModalContent>
        </Modal>
    )
}