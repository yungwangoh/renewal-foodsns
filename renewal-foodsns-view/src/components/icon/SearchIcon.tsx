import React from "react";
import {IoSearch} from "react-icons/io5";

interface SearchIconProps {
    size?: number
}

export function SearchIcon({size = 30}: SearchIconProps) {
    return (
        <IoSearch size={30}/>
    )
}