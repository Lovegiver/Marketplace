package com.citizenweb.marketplace.model.model;

import java.math.BigDecimal;

/**
 * Methods to compute the total amount of an {@link OrderItem} or an {@link ItemContainer}
 */
public interface Orderable {
    /**
     * Sums all its {@link OrderItem#getTotalAmountNoVAT()}
     * @return an amount
     */
    BigDecimal getTotalAmountNoVAT();
    /**
     * Sums all its {@link OrderItem#getTotalAmountWithVAT()}
     * @return an amount
     */
    BigDecimal getTotalAmountWithVAT();
}
