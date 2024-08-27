import {Input} from "@nextui-org/react";
import {SearchInputIcon} from "../icon/SearchInputIcon";

export function Search() {
    return (
        <Input
            className="max-w-xl"
            isClearable
            radius="lg"
            placeholder="검색"
            startContent={
                <SearchInputIcon
                    className="text-black/50 mb-0.5 dark:text-white/90 text-slate-400 pointer-events-none flex-shrink-0"/>
            }
            size="lg"
        />
    )
}