package com.sumal.mapper;


import com.sumal.dto.item.ItemResponse;
import com.sumal.entity.ItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

  ItemResponse toResponse(ItemEntity itemEntity);
}
