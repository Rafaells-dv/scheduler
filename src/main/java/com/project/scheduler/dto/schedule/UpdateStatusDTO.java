package com.project.scheduler.dto.schedule;

import com.project.scheduler.enums.EnSchedStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStatusDTO {
    public EnSchedStatus status;
}
