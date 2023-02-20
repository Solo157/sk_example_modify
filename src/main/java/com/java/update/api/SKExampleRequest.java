package com.java.update.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SKExampleRequest {
    private Integer id;
    @JsonProperty("add")
    private Integer value;
}
