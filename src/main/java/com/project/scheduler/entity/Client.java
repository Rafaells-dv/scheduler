package com.project.scheduler.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String phone;
    private Boolean active;

    private Client(String name, String email, String phone, Boolean active) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.active = active;
    }

    public static Client create(String name, String email, String phone) {
        return new Client(name, email, phone, Boolean.TRUE);
    }

    public void update(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void activate() {
        this.active = true;
    }

    public void inactivate() {
        this.active = false;
    }
}
