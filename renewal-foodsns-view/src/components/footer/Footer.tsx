import {Button, useDisclosure} from "@nextui-org/react";
import {PostAddIcon} from "../icon/PostAddIcon";
import {Create} from "../body/post/Create";

export function Footer() {

    const {isOpen, onOpen, onOpenChange} = useDisclosure();

    return (
        <Button
            onPress={onOpen}
            className="fixed right-6 bottom-6 z-50 shadow-lg w-20 h-20"
            isIconOnly
            size="lg"
            variant="bordered"
        >
            <PostAddIcon/>
            <Create isOpen={isOpen} onOpenChange={onOpenChange}/>
        </Button>
    )
}