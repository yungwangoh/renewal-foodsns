import {Button, Link, Navbar, NavbarBrand, NavbarContent, NavbarItem} from '@nextui-org/react';
import {AcmeLogo} from "../icon/AcmeLogo";
import {HomeIcon} from "../icon/HomeIcon";
import {SearchIcon} from "../icon/SearchIcon";
import {ShareIcon} from "../icon/ShareIcon";
import {HeartIcon} from "../icon/HeartIcon";
import {HumanIcon} from "../icon/HumanIcon";

function NavbarComponent() {

    return (
        <Navbar isBordered>

            <NavbarContent justify="start">
                <NavbarBrand className="mr-4">
                    <AcmeLogo/>
                </NavbarBrand>
            </NavbarContent>

            <NavbarContent justify="center">
                <NavbarItem>
                    <Button isIconOnly variant="light"
                            href="/"
                            as={Link}
                            className="px-6 min-w-24 h-12 text-medium gap-3 rounded-large"
                            aria-label="home">
                        <HomeIcon/>
                    </Button>
                </NavbarItem>
                <NavbarItem>
                    <Button isIconOnly variant="light"
                            href="/search"
                            as={Link}
                            className="px-6 min-w-24 h-12 text-medium gap-3 rounded-large"
                            aria-label="search">
                        <SearchIcon/>
                    </Button>
                </NavbarItem>
                <NavbarItem>
                    <Button isIconOnly variant="light" className="px-6 min-w-24 h-12 text-medium gap-3 rounded-large"
                            aria-label="share">
                        <ShareIcon/>
                    </Button>
                </NavbarItem>
                <NavbarItem>
                    <Button isIconOnly variant="light" className="px-6 min-w-24 h-12 text-medium gap-3 rounded-large"
                            aria-label="heart">
                        <HeartIcon/>
                    </Button>
                </NavbarItem>
                <NavbarItem>
                    <Button isIconOnly
                            href="/my-page"
                            variant="light"
                            className="px-6 min-w-24 h-12 text-medium gap-3 rounded-large"
                            as={Link}
                            aria-label="human">
                        <HumanIcon/>
                    </Button>
                </NavbarItem>
            </NavbarContent>

            <NavbarContent justify="end">
                <NavbarItem className="lg:flex">
                    <Button color="default" href="/sign-in" variant="bordered" as={Link}>
                        로그인
                    </Button>
                </NavbarItem>
                <NavbarItem className="hidden">
                    <Button color="primary" href="#" variant="bordered">
                        회원 가입
                    </Button>
                </NavbarItem>
            </NavbarContent>
        </Navbar>
    );
}

export default NavbarComponent;