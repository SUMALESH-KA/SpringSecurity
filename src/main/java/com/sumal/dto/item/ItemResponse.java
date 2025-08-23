package com.sumal.dto.item;


import com.sumal.common.ItemState;

public record ItemResponse(String id, String data, String userId, ItemState itemState) {}
