package com.vault.velocity.limits.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadFundsResDto {

    private String id;
    private String customer_id;
    private boolean accepted;
}
