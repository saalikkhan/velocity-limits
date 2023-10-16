package com.vault.velocity.limits.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadFundsReqDto {

    @NotBlank(message = "Mandatory, Please provoide id")
    private String id;

    @NotBlank(message = "Mandatory, Please provoide customer_id")
    private String customer_id;

    @NotBlank(message = "Mandatory, Please provoide load_amount")
    private String load_amount;

    @NotNull(message = "Mandatory, Please provide time")
    private LocalDateTime time;
}
