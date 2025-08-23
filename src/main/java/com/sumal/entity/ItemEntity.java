package com.sumal.entity;


import com.sumal.common.ItemState;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Table(name = "item")
public class ItemEntity {

  @Id @UuidGenerator private String id;

  private String data;

  private String userId;

  private ItemState itemState;

  private ZonedDateTime createdDate;

  private ZonedDateTime updatedDate;

  @PrePersist
  public void onPrePersist() {

    createdDate = ZonedDateTime.now();
    updatedDate = ZonedDateTime.now();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public ItemState getItemState() {
    return itemState;
  }

  public void setItemState(ItemState itemState) {
    this.itemState = itemState;
  }

  public ZonedDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(ZonedDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public ZonedDateTime getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(ZonedDateTime updatedDate) {
    this.updatedDate = updatedDate;
  }

  @PreUpdate
  public void onPreUpdate() {

    updatedDate = ZonedDateTime.now();
  }
}
