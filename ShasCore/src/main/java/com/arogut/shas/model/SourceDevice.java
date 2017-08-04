package com.arogut.shas.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Map;

@Entity
@Data
public class SourceDevice extends Device {
    @Transient
    private Map<String, Object> measurements;
}
