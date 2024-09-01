import {Button, Card, CardBody, CardFooter, CardHeader, Image, Textarea, User} from "@nextui-org/react";
import {HeartIcon} from "../../icon/HeartIcon";
import {CommentIcon} from "../../icon/CommentIcon";
import {ShareIcon} from "../../icon/ShareIcon";

export function View({postId}: number) {

    
    return (
        <Card isPressable onPress>
            <CardHeader className="flex-col items-start">
                <User name="yun" description="developer"/>
            </CardHeader>
            <CardBody>
                <Textarea isReadOnly>
                    {"test"}
                </Textarea>
            </CardBody>
            <CardBody className="overflow-visible p-0">
                <Image
                    alt="Card background"
                    className="w-full object-cover"
                    src="https://nextui.org/images/hero-card-complete.jpeg"
                    width={450}
                    height={300}
                />
            </CardBody>
            <h1>답글</h1>
            <CardBody>
                <div className="flex flex-col gap-3">

                </div>
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