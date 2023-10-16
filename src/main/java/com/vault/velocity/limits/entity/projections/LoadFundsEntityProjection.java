package com.vault.velocity.limits.entity.projections;

import com.vault.velocity.limits.entity.LoadFundsEntity;

import java.math.BigDecimal;

/**
 * Projection for {@link LoadFundsEntity}
 */
//Can use this too but it doesnt give any performance boost.
public interface LoadFundsEntityProjection {
    BigDecimal getLoad_amount();
    Long getCount();
}