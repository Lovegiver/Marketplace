package com.citizenweb.marketplace.model.test.model.utils;

import com.citizenweb.marketplace.model.enums.ItemContainerType;
import com.citizenweb.marketplace.model.model.Cart;
import com.citizenweb.marketplace.model.model.ItemContainer;
import com.citizenweb.marketplace.model.model.Order;
import com.citizenweb.marketplace.model.model.OrderItem;
import com.citizenweb.marketplace.model.model.Seller;
import lombok.Builder;

import java.util.concurrent.CopyOnWriteArrayList;

public class CartOrderBuilder implements EntityBuilderService<ItemContainer> {

    /**
     * Uses a {@link Builder} to build a {@link Cart} or an {@link Order}
     *
     * @param params ItemContainerType, Id, isShared, Seller
     * @return a {@link ItemContainer}
     */
    @SuppressWarnings("unchecked")
    @Override
    public ItemContainer getEntityWithBuilder(Object... params) {
        ItemContainerType type = (ItemContainerType) params[0];
        long id = (long) params[1];
        boolean isShared = (boolean) params[2];
        ItemContainer container;
        if (ItemContainerType.CART.equals(type)) {
            container = Cart.builder()
                    .isSharedCart(isShared)
                    .build();
            container.setId(id);
        } else {
            CopyOnWriteArrayList<OrderItem> items = (params[3] != null) ? new CopyOnWriteArrayList<>((CopyOnWriteArrayList<OrderItem>) params[3]) : new CopyOnWriteArrayList<>();
            Seller seller = (Seller) params[4];
            container = Order.builder()
                    .orderItems(items)
                    .isSharedOrder(isShared)
                    .build();
            container.setId(id);
            ((Order) container).setSeller(seller);
        }
        return container;
    }

    /**
     * Uses a constructor to build a {@link Cart} or an {@link Order}
     *
     * @param params ItemContainerType, Id, isShared, Seller
     * @return a {@link ItemContainer}
     */
    @SuppressWarnings("unchecked")
    @Override
    public ItemContainer getEntityWithConstructor(Object... params) {
        ItemContainerType type = (ItemContainerType) params[0];
        long id = (long) params[1];
        boolean isShared = (boolean) params[2];
        ItemContainer container;
        if (ItemContainerType.CART.equals(type)) {
            container = new Cart(isShared);
            container.setId(id);
        } else {
            CopyOnWriteArrayList<OrderItem> items = params[3] != null ? new CopyOnWriteArrayList<>((CopyOnWriteArrayList<OrderItem>) params[3]) : new CopyOnWriteArrayList<>();
            Seller seller = (Seller) params[4];
            container = new Order(items, isShared);
            container.setId(id);
            ((Order) container).setSeller(seller);
        }
        return container;
    }

}
