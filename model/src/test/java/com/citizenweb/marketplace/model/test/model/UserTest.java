package com.citizenweb.marketplace.model.test.model;

import com.citizenweb.marketplace.model.enums.GenderType;
import com.citizenweb.marketplace.model.enums.ItemContainerType;
import com.citizenweb.marketplace.model.enums.UserType;
import com.citizenweb.marketplace.model.model.Buyer;
import com.citizenweb.marketplace.model.model.Cart;
import com.citizenweb.marketplace.model.model.ItemContainer;
import com.citizenweb.marketplace.model.model.Order;
import com.citizenweb.marketplace.model.model.Seller;
import com.citizenweb.marketplace.model.model.User;
import com.citizenweb.marketplace.model.test.model.utils.CartOrderBuilder;
import com.citizenweb.marketplace.model.test.model.utils.EntityBuilderService;
import com.citizenweb.marketplace.model.test.model.utils.UserBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private final static Logger LOG = LoggerFactory.getLogger("Product Testing");
    private final EntityBuilderService<? extends User> userBuilder = new UserBuilder();
    private final EntityBuilderService<? extends ItemContainer> containerBuilder = new CartOrderBuilder();

    @Test
    public void createBuyerWithCartTest() {

        long buyerId1 = 1L;
        long buyerId2 = 2L;
        long cartId1 = 1L;
        long cartId2 = 2L;

        Buyer buyer1 = (Buyer) userBuilder.getEntityWithBuilder(UserType.BUYER, buyerId1);
        Buyer buyer2 = (Buyer) userBuilder.getEntityWithConstructor(UserType.BUYER, buyerId2);
        Cart cart1 = (Cart) containerBuilder.getEntityWithBuilder(ItemContainerType.CART, cartId1, true);
        Cart cart2 = (Cart) containerBuilder.getEntityWithConstructor(ItemContainerType.CART, cartId2, true);

        assertNotNull(buyer1);
        assertNotNull(buyer2);
        assertNotNull(cart1);
        assertNotNull(cart2);

        cart1.addBuyer(buyer1);
        cart2.addBuyer(buyer2);

        assertNotEquals(buyer1, buyer2);
        assertNotEquals(cart1, cart2);
        assertNotEquals(buyer1.hashCode(), buyer2.hashCode());
        assertNotEquals(cart1.hashCode(), cart2.hashCode());

        LOG.debug(buyer1.toString());
        LOG.debug(buyer2.toString());
        LOG.debug(cart1.toString());
        LOG.debug(cart2.toString());

        cart1.removeBuyer(buyer1);
        cart2.removeBuyer(buyer2);

        assertEquals(0, cart1.getBuyers().size());
        assertEquals(0, cart2.getBuyers().size());
        assertEquals(0, buyer1.getCarts().size());
        assertEquals(0, buyer2.getCarts().size());

    }

    @Test
    public void createBuyerWithOrderTest() {

        long buyerId1 = 1L;
        long buyerId2 = 2L;
        long orderId1 = 1L;
        long orderId2 = 2L;
        long sellerId = 3L;

        Buyer buyer1 = (Buyer) userBuilder.getEntityWithBuilder(UserType.BUYER, buyerId1);
        Buyer buyer2 = (Buyer) userBuilder.getEntityWithConstructor(UserType.BUYER, buyerId2);
        Seller seller1 = (Seller) userBuilder.getEntityWithBuilder(UserType.SELLER, sellerId);
        Order order1 = (Order) containerBuilder.getEntityWithBuilder(ItemContainerType.ORDER, orderId1, true, null, seller1);
        Order order2 = (Order) containerBuilder.getEntityWithConstructor(ItemContainerType.ORDER, orderId2, true, null, seller1);

        assertNotNull(buyer1);
        assertNotNull(buyer2);
        assertNotNull(order1);
        assertNotNull(order2);

        order1.addBuyer(buyer1);
        order1.addSeller(seller1);
        assertEquals(1, order1.getBuyers().size());
        assertEquals(1, buyer1.getOrders().size());
        order2.addBuyer(buyer2);
        order2.addSeller(seller1);
        assertEquals(1, order2.getBuyers().size());
        assertEquals(1, buyer2.getOrders().size());

        assertEquals(2, seller1.getOrders().size());

        assertNotEquals(buyer1, buyer2);
        assertNotEquals(order1, order2);
        assertNotEquals(buyer1.hashCode(), buyer2.hashCode());
        assertNotEquals(order1.hashCode(), order2.hashCode());

        LOG.debug(buyer1.toString());
        LOG.debug(buyer2.toString());
        LOG.debug(order1.toString());
        LOG.debug(order2.toString());

        assertEquals(UserBuilder.company, seller1.getCompanyName());
        assertEquals(UserBuilder.rcs, seller1.getRcs());
        assertEquals(UserBuilder.siren, seller1.getSiren());
        assertEquals(sellerId, seller1.getId());
        assertEquals(UserType.SELLER, seller1.getUserType());
        assertEquals(UserBuilder.pseudo, seller1.getPseudo());
        assertEquals(UserBuilder.firstname, seller1.getFirstname());
        assertEquals(UserBuilder.lastname, seller1.getLastname());
        assertEquals(UserBuilder.userDescription, seller1.getDescription());
        assertEquals(UserBuilder.email, seller1.getEmail());
        assertEquals(UserBuilder.password.concat(String.valueOf(sellerId)), seller1.getPassword());
        assertEquals(GenderType.WOMAN, seller1.getGenderType());
        assertEquals(UserBuilder.lastConnected, seller1.getLastConnection());

        order1.removeBuyer(buyer1);
        order2.removeBuyer(buyer2);
        assertEquals(0, order1.getBuyers().size());
        assertEquals(0, order2.getBuyers().size());
        order1.removeSeller(seller1);
        order2.removeSeller(seller1);
        assertNull(order1.getSeller());
        assertNull(order2.getSeller());
        assertEquals(0, seller1.getOrders().size());

    }
}
