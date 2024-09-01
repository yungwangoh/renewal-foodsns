import React, {FormEvent, useState} from 'react';
import {Button, Card, CardBody, CardFooter, CardHeader, Input} from "@nextui-org/react";
import {createMember} from "../../../axios/CallApi";

export function SignUp() {
    const [email, setEmail] = useState("");
    const [nickName, setNickName] = useState("");
    const [password, setPassword] = useState("");
    const [profileImage, setProfileImage] = useState<File | null>(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setIsLoading(true);
        setError("");

        try {

            return await createMember(email, nickName, password, profileImage);
            // 여기서 회원가입 성공 후 처리를 합니다. 예: 로그인 페이지로 리다이렉트
        } catch (err) {
            console.error('회원가입 실패:', err);
            setError("회원가입에 실패했습니다. 다시 시도해주세요.");
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <Card>
            <CardHeader className="flex-col items-start">
                <h3 className="font-bold text-large">회원 가입</h3>
            </CardHeader>
            <form onSubmit={handleSubmit}>
                <CardBody className="overflow-visible py-2">
                    <div className="w-full flex flex-col gap-4">
                        <Input
                            type="email"
                            name="email"
                            placeholder="이메일"
                            label="이메일"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            isClearable
                            isRequired
                        />
                        <Input
                            type="text"
                            name="nick-name"
                            placeholder="닉네임"
                            label="닉네임"
                            value={nickName}
                            onChange={(e) => setNickName(e.target.value)}
                            isClearable
                            isRequired
                        />
                        <Input
                            type="password"
                            name="password"
                            placeholder="비밀번호"
                            label="비밀번호"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            isClearable
                            isRequired
                        />
                    </div>
                </CardBody>
                <CardFooter className="py-4">
                    <div className="w-full flex flex-col gap-4">
                        <h3 className="font-bold text-large">프로필 이미지</h3>
                        <Input
                            type="file"
                            name="image"
                            accept="image/*"
                            onChange={(e) => setProfileImage(e.target.files ? e.target.files[0] : null)}
                        />
                        {error && <p className="text-red-500 mt-2">{error}</p>}
                    </div>
                </CardFooter>
                <CardFooter>
                    <Button type="submit" variant="bordered" disabled={isLoading}>
                        {isLoading ? '처리 중...' : '회원 가입'}
                    </Button>
                </CardFooter>
            </form>
        </Card>
    )
}