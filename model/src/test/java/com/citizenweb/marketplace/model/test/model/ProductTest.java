package com.citizenweb.marketplace.model.test.model;

import com.citizenweb.marketplace.model.enums.UserType;
import com.citizenweb.marketplace.model.enums.WeightUnitType;
import com.citizenweb.marketplace.model.model.Product;
import com.citizenweb.marketplace.model.model.Seller;
import com.citizenweb.marketplace.model.model.Unit;
import com.citizenweb.marketplace.model.model.User;
import com.citizenweb.marketplace.model.test.model.utils.EntityBuilderService;
import com.citizenweb.marketplace.model.test.model.utils.ProductBuilder;
import com.citizenweb.marketplace.model.test.model.utils.UserBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private final static Logger LOG = LoggerFactory.getLogger("Product Testing");
    private final EntityBuilderService<? extends User> userBuilder = new UserBuilder();
    private final EntityBuilderService<? extends Product> productBuilder = new ProductBuilder();

    /**
     * Product generation with Builder.<br>
     * To create a {@link Product} we first have to create a {@link Seller}
     * because a Seller is a mandatory parameter for Product creation
     */
    @Test
    public void createProductWithBuilderTest() {

        long sellerId = 1L;
        long productId = 1L;
        Unit unit = WeightUnitType.GR;
        Seller seller = (Seller) userBuilder.getEntityWithBuilder(UserType.SELLER, sellerId);
        Product product = productBuilder.getEntityWithBuilder(unit, productId, seller);
        seller.addProduct(product);

        assertNotNull(seller);
        assertEquals(seller, product.getSeller());

        assertNotNull(product);
        assertEquals(productId, product.getId());
        assertEquals(ProductBuilder.name, product.getName());
        assertEquals(ProductBuilder.reference, product.getReference());
        assertEquals(ProductBuilder.productDescription, product.getDescription());
        assertEquals(unit, product.getUnit());
        assertEquals(ProductBuilder.quantity, product.getQuantity());
        assertEquals(seller, product.getSeller());
        assertEquals(1, seller.getProducts().size());
        assertTrue(seller.getProducts().contains(product));
        seller.getProducts().forEach(p -> assertEquals(product, p));

        seller.removeProduct(product);
        assertEquals(0, seller.getProducts().size());
        assertNull(product.getSeller());

        LOG.debug(product.toString());
        LOG.debug(seller.toString());

    }

    /**
     * Product generation with Constructor.<br>
     *
     */
    @Test
    public void createProductWithConstructorTest() {

        long sellerId1 = 1L;
        long productId1 = 1L;
        long sellerId2 = 2L;
        long productId2 = 2L;
        Unit unit = WeightUnitType.GR;
        Seller seller1 = (Seller) userBuilder.getEntityWithConstructor(UserType.SELLER, sellerId1);
        Seller seller2 = (Seller) userBuilder.getEntityWithConstructor(UserType.SELLER, sellerId2);
        Product product1 = productBuilder.getEntityWithConstructor(unit, productId1, seller1);
        Product product2 = productBuilder.getEntityWithConstructor(unit, productId2, seller2);
        seller1.addProduct(product1);
        seller2.addProduct(product2);

        assertNotNull(seller1);
        assertNotNull(seller2);
        assertNotEquals(seller1, seller2);
        assertNotEquals(seller1.hashCode(), seller2.hashCode());

        assertNotNull(product1);
        assertNotNull(product2);
        assertNotEquals(product1, product2);
        assertNotEquals(product1.hashCode(), product2.hashCode());

        LOG.debug(product1.toString());
        LOG.debug(seller1.toString());

    }

}
