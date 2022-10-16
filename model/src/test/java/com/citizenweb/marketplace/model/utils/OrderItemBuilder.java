package com.citizenweb.marketplace.model.utils;

import com.citizenweb.marketplace.model.enums.OrderItemStatus;
import com.citizenweb.marketplace.model.model.OrderItem;
import com.citizenweb.marketplace.model.model.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

public class OrderItemBuilder implements EntityBuilderService<OrderItem> {

    public final static int quantity = 10;
    public final static BigDecimal discountRate = new BigDecimal("0.15");
    public final static BigDecimal vatRate = new BigDecimal("0.206");
    public final static LocalDateTime creationDate = LocalDateTime.of(1971, Month.JULY,8,14,0);

    /**
     * Uses a {@link Builder} to build an {@link OrderItem}
     *
     * @param params Product, ItemContainer, id
     * @return an ItemContainer
     */
    @Override
    public OrderItem getEntityWithBuilder(Object... params) {
        Product product = (Product) params[0];
        long id = (long) params[1];
        return OrderItem.builder()
                .id(id)
                .status(OrderItemStatus.DRAFT)
                .creationDate(creationDate)
                .isActive(true)
                .product(product)
                .quantity(quantity)
                .unitPriceNoVAT(product.getUnitPriceNoVAT())
                .unitPriceWithVAT(product.getUnitPriceWithVAT())
                .totalAmountNoVAT(product.getUnitPriceNoVAT().multiply(new BigDecimal(quantity)))
                .totalAmountWithVAT(product.getUnitPriceWithVAT().multiply(new BigDecimal(quantity)))
                .applicableDiscount(discountRate)
                .applicableVAT(vatRate)
                .build();
    }

    /**
     * Uses a constructor to build an {@link OrderItem}
     *
     * @param params Product, ItemContainer, id
     * @return an ItemContainer
     */
    @Override
    public OrderItem getEntityWithConstructor(Object... params) {
        Product product = (Product) params[0];
        long id = (long) params[1];
        OrderItem item = new OrderItem(product, quantity, vatRate, discountRate);
        item.setId(id);
        item.setStatus(OrderItemStatus.DRAFT);
        item.setCreationDate(creationDate);
        item.setActive(true);
        item.setUnitPriceNoVAT(product.getUnitPriceNoVAT());
        item.setUnitPriceWithVAT(product.getUnitPriceWithVAT());
        item.setTotalAmountNoVAT(product.getUnitPriceNoVAT().multiply(new BigDecimal(quantity)));
        item.setTotalAmountWithVAT(product.getUnitPriceWithVAT().multiply(new BigDecimal(quantity)));
        return item;
    }

}
