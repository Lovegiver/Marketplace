package com.citizenweb.marketplace.model;

import com.citizenweb.marketplace.model.enums.ItemContainerType;
import com.citizenweb.marketplace.model.enums.OrderItemStatus;
import com.citizenweb.marketplace.model.enums.UserType;
import com.citizenweb.marketplace.model.enums.VolumeUnitType;
import com.citizenweb.marketplace.model.model.Buyer;
import com.citizenweb.marketplace.model.model.Cart;
import com.citizenweb.marketplace.model.model.ItemContainer;
import com.citizenweb.marketplace.model.model.Order;
import com.citizenweb.marketplace.model.model.OrderItem;
import com.citizenweb.marketplace.model.model.Product;
import com.citizenweb.marketplace.model.model.Seller;
import com.citizenweb.marketplace.model.model.User;
import com.citizenweb.marketplace.model.utils.CartOrderBuilder;
import com.citizenweb.marketplace.model.utils.EntityBuilderService;
import com.citizenweb.marketplace.model.utils.OrderItemBuilder;
import com.citizenweb.marketplace.model.utils.ProductBuilder;
import com.citizenweb.marketplace.model.utils.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class ItemContainerTest {

    private final static Logger LOG = LoggerFactory.getLogger("Product Testing");
    private final EntityBuilderService<? extends User> userBuilder = new UserBuilder();
    private final EntityBuilderService<? extends ItemContainer> containerBuilder = new CartOrderBuilder();
    private final EntityBuilderService<? extends Product> productBuilder = new ProductBuilder();
    private final EntityBuilderService<? extends OrderItem> itemBuilder = new OrderItemBuilder();

    @Test
    public void fillContainerWithProducts() {

        long sellerId1 = 1L;
        Seller seller1 = (Seller) userBuilder.getEntityWithBuilder(UserType.SELLER, sellerId1);
        Assertions.assertNotNull(seller1);

        long sellerId2 = 2L;
        Seller seller2 = (Seller) userBuilder.getEntityWithConstructor(UserType.SELLER, sellerId2);
        Assertions.assertNotNull(seller2);

        long productId1 = 1L;
        Product product1 = productBuilder.getEntityWithBuilder(VolumeUnitType.L, productId1, seller1);
        Assertions.assertNotNull(product1);

        long productId2 = 2L;
        Product product2 = productBuilder.getEntityWithConstructor(VolumeUnitType.CL, productId2, seller2);
        Assertions.assertNotNull(product2);

        long buyerId3 = 3L;
        Buyer buyer3 = (Buyer) userBuilder.getEntityWithBuilder(UserType.BUYER, buyerId3);
        Assertions.assertNotNull(buyer3);

        long cartId1 = 1L;
        Cart cart1 = (Cart) containerBuilder.getEntityWithBuilder(ItemContainerType.CART, cartId1, false);
        Assertions.assertNotNull(cart1);
        Assertions.assertFalse(cart1.isSharedCart());

        long itemId1 = 1L;
        OrderItem item1 = itemBuilder.getEntityWithBuilder(product1, itemId1);
        Assertions.assertNotNull(item1);
        product1.getOrderItems().add(item1);
        Assertions.assertTrue(product1.getOrderItems().contains(item1));

        Assertions.assertEquals(product1, item1.getProduct());
        Assertions.assertEquals(itemId1, item1.getId());
        Assertions.assertEquals(OrderItemStatus.DRAFT, item1.getStatus());
        Assertions.assertEquals(OrderItemBuilder.creationDate, item1.getCreationDate());
        Assertions.assertEquals(OrderItemBuilder.quantity, item1.getQuantity());
        Assertions.assertEquals(OrderItemBuilder.quantity, item1.getQuantity());
        Assertions.assertEquals(product1.getUnitPriceNoVAT(), item1.getUnitPriceNoVAT());
        Assertions.assertEquals(product1.getUnitPriceWithVAT(), item1.getUnitPriceWithVAT());
        Assertions.assertEquals(product1.getUnitPriceNoVAT().multiply(BigDecimal.valueOf(OrderItemBuilder.quantity)), item1.getTotalAmountNoVAT());
        Assertions.assertEquals(product1.getUnitPriceWithVAT().multiply(BigDecimal.valueOf(OrderItemBuilder.quantity)), item1.getTotalAmountWithVAT());
        Assertions.assertEquals(OrderItemBuilder.vatRate, item1.getApplicableVAT());
        Assertions.assertEquals(OrderItemBuilder.discountRate, item1.getApplicableDiscount());
        Assertions.assertTrue(item1.isActive());

        long itemId2 = 2L;
        OrderItem item2 = itemBuilder.getEntityWithConstructor(product2, itemId2);
        Assertions.assertNotNull(item2);
        product2.getOrderItems().add(item2);
        Assertions.assertTrue(product2.getOrderItems().contains(item2));

        Assertions.assertNotEquals(item1, item2);
        Assertions.assertNotEquals(item1.hashCode(), item2.hashCode());

        cart1.addOrderItem(item1);
        cart1.addOrderItem(item2);
        Assertions.assertEquals(2, cart1.getOrderItems().size());
        Assertions.assertTrue(cart1.getTotalAmountNoVAT().doubleValue() > 0);
        Assertions.assertTrue(cart1.getTotalAmountWithVAT().doubleValue() > 0);

        long orderId2 = 2L;
        Order order2 = (Order) containerBuilder.getEntityWithConstructor(ItemContainerType.ORDER, orderId2, false, cart1.getOrderItems(), seller1);
        Assertions.assertNotNull(order2);
        Assertions.assertFalse(order2.isSharedOrder());
        Assertions.assertEquals(2, order2.getOrderItems().size());
        Assertions.assertTrue(order2.getTotalAmountNoVAT().doubleValue() > 0);
        Assertions.assertTrue(order2.getTotalAmountWithVAT().doubleValue() > 0);
        order2.getOrderItems().forEach(cart1::removeOrderItem);
        Assertions.assertEquals(0, cart1.getOrderItems().size());
        order2.removeOrderItem(item1);
        order2.removeOrderItem(item2);
        Assertions.assertEquals(0, order2.getOrderItems().size());

    }

}
