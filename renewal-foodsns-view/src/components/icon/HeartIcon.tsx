import {FaRegHeart} from "react-icons/fa";

interface HeartIconProps {
    size?: number
}

export function HeartIcon({size = 30}: HeartIconProps) {
    return (
        <FaRegHeart size={size}/>
    )
}