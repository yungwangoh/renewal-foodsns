package mubex.renewal_foodsns.common.mapper.map;

import mubex.renewal_foodsns.common.mapper.Mappable;
import mubex.renewal_foodsns.domain.dto.response.NotificationResponse;
import mubex.renewal_foodsns.domain.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface NotificationMapper extends Mappable<NotificationResponse, Notification> {

    @Override
    @Mapping(source = "inDeleted", target = "visible")
    NotificationResponse toResponse(Notification notification);
}
