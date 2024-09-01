import {Button, Card, CardBody, CardFooter, CardHeader, Input, Link} from "@nextui-org/react";

export function SignIn() {
    return (
        <Card>
            <CardHeader className="flex-col items-start">
                <h3 className="font-bold text-large">로그인</h3>
            </CardHeader>
            <form>
                <CardBody className="overflow-visible py-2">
                    <div className="w-full flex flex-row flex-wrap gap-4">
                        <Input type="email" name="email" placeholder="이메일" label="이메일" isClearable isRequired/>
                        <Input type="password" name="password" placeholder="비밀번호" label="비밀번호" isClearable isRequired/>
                        <Button type="submit" variant="bordered">
                            로그인
                        </Button>
                    </div>
                </CardBody>
            </form>
            <CardFooter className="flex-col items-center">
                <Link href="/sign-up">
                    아직 회원이 아니세요?
                </Link>
            </CardFooter>
        </Card>
    )
}