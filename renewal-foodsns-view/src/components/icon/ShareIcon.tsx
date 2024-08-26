import {FaRegShareFromSquare} from "react-icons/fa6";

interface ShareIconProps {
    size?: number
}

export function ShareIcon({size = 30}: ShareIconProps) {
    return (
        <FaRegShareFromSquare size={size}/>
    )
}