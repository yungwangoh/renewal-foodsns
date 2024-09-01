import {Avatar, Button, Card, CardBody, CardFooter, CardHeader, Link, Tab, Tabs} from "@nextui-org/react";

export function MyPage() {
    return (
        <Card className="w-[50%]">
            <CardHeader className="px-6 py-4">
                <div className="flex flex-row justify-between items-center w-full">
                    <div className="flex flex-col gap-1">
                        <strong className="text-2xl">윤광오</strong>
                        <p className="mb-5">ㅇㅇ</p>
                        <Link color="foreground" href="">팔로워 {1}명</Link>
                    </div>
                    <Avatar className="w-20 h-20 text-large"/>
                </div>
            </CardHeader>
            <CardHeader>
                <Button className="w-full mb-5" variant="bordered">
                    프로필 수정
                </Button>
            </CardHeader>
            <CardBody className="overflow-visible p-0">
                <div className="flex w-full flex-col items-center">
                    <Tabs
                        fullWidth
                        aria-label="Options"
                        variant="underlined"
                    >
                        <Tab
                            key="photos"
                            title={
                                <div className="flex items-center space-x-2">
                                    <span>게시물</span>
                                </div>
                            }
                        />
                        <Tab
                            key="music"
                            title={
                                <div className="flex items-center space-x-2">
                                    <span>답글</span>
                                </div>
                            }
                        />
                        <Tab
                            key="videos"
                            title={
                                <div className="flex items-center space-x-2">
                                    <span>리포스트</span>
                                </div>
                            }
                        />
                    </Tabs>
                </div>
            </CardBody>
            <CardFooter>

            </CardFooter>
        </Card>
    )
}