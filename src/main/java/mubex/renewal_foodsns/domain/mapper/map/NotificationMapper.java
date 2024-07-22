package mubex.renewal_foodsns.domain.mapper.map;

import mubex.renewal_foodsns.domain.dto.response.NotificationResponse;
import mubex.renewal_foodsns.domain.entity.Notification;
import mubex.renewal_foodsns.domain.mapper.Mappable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationMapper extends Mappable<NotificationResponse, Notification> {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Override
    @Mapping(source = "inDeleted", target = "visible")
    NotificationResponse toResponse(Notification notification);
}
