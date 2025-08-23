package com.sumal.entity;

import com.sumal.common.Role;
import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@Setter
@Getter
@Table(name = "app_user")
public class UserEntity {

  @Id
  @UuidGenerator
  private String id;

  @Column(unique = true)
  private String username;

  private String passwordHash;
  private String firstName;
  private String lastName;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
          name = "user_entity_roles",
          joinColumns = @JoinColumn(
                  name = "user_entity_id",       // column in join table
                  referencedColumnName = "id",   // refers to app_user.id
                  foreignKey = @ForeignKey(name = "fk_user_roles_user") // optional, but clearer
          )
  )
  @Column(name = "role")
  private Set<Role> roles;


  private Boolean active;
  private ZonedDateTime createdDate;
  private ZonedDateTime updatedDate;

  @PrePersist
  public void onPrePersist() {
    createdDate = ZonedDateTime.now();
    updatedDate = ZonedDateTime.now();
  }

  @PreUpdate
  public void onPreUpdate() {
    updatedDate = ZonedDateTime.now();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
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
}
