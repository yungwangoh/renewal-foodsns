import {Button, Card, CardBody, CardFooter, CardHeader, Image} from "@nextui-org/react";
import {HeartIcon} from "../icon/HeartIcon";
import {CommentIcon} from "../icon/CommentIcon";
import {ShareIcon} from "../icon/ShareIcon";
import {useEffect, useState} from "react";
import {PostPageResponse} from "../../axios/type/PostType";
import {findPostAll} from "../../axios/CallApi";

export function Home() {

    const [posts, setPosts] = useState<PostPageResponse | null>(null);

    useEffect(() => {
        findPostAll()
            .catch(error => console.error(error))
            .then((res: PostPageResponse) => setPosts(res));
    }, []);

    return (
        <Card>
            <CardHeader className="flex-col items-start">
                <p className="text-tiny uppercase font-bold">Daily Mix</p>
                <small className="text-default-500">12 Tracks</small>
                <h4 className="font-bold text-large">Frontend Radio</h4>
            </CardHeader>
            <CardBody className="overflow-visible p-0">
                <Image
                    alt="Card background"
                    className="w-full object-cover"
                    src="https://nextui.org/images/hero-card-complete.jpeg"
                    width={450}
                    height={300}
                />
            </CardBody>
            <CardFooter>
                <Button variant="light" size="sm">
                    <HeartIcon size={20}/>
                    {70}
                </Button>
                <Button variant="light" size="sm">
                    <CommentIcon/>
                    {80}
                </Button>
                <Button variant="light" size="sm">
                    <ShareIcon size={20}/>
                    {80}
                </Button>
            </CardFooter>
        </Card>
    )
}