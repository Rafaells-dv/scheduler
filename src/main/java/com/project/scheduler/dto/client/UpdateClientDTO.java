package com.project.scheduler.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateClientDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
}
