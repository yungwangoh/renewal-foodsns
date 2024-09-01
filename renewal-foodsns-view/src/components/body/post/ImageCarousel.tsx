import {IoIosCloseCircleOutline} from "react-icons/io";
import {Button, Image} from "@nextui-org/react";
import Slider from "react-slick";
import {useCallback, useRef} from "react";

interface ImageFile {
    id: number;
    preview: string;
}

interface ImageCarouselProps {
    images: ImageFile[];
    onDeleteImage: (id: number) => void;
}

export function ImageCarousel({images, onDeleteImage}: ImageCarouselProps) {
    const sliderRef = useRef<Slider | null>(null);

    const sliderSettings = {
        dots: true,
        infinite: false,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        adaptiveHeight: true
    };

    const handleDeleteImage = useCallback((id: number) => {
        onDeleteImage(id);
        // 다음 틱에서 Slider를 강제로 업데이트합니다.
        setTimeout(() => {
            if (sliderRef.current) {
                sliderRef.current.slickGoTo(0);
            }
        }, 0);
    }, [onDeleteImage]);

    if (images.length === 0) return null;

    return (
        <Slider ref={sliderRef} {...sliderSettings} key={images.length}>
            {images.map((image) => (
                <div key={image.id} className="relative">
                    <Image
                        src={image.preview}
                        width={100}
                        height={100}
                        className="object-cover"
                    />
                    <Button
                        size="sm"
                        variant="light"
                        onPress={() => handleDeleteImage(image.id)}
                        className="absolute top-2 right-2"
                    >
                        <IoIosCloseCircleOutline size={20}/>
                    </Button>
                </div>
            ))}
        </Slider>
    );
}