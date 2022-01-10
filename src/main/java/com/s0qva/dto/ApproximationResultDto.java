package com.s0qva.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApproximationResultDto {
    List<String> argumentValues;
    List<String> functionValues;
    double result;
    double absoluteFault;
}
