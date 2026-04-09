package com.myapp.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String name;
    private String description;
    private String date;
    private UUID userId;
}