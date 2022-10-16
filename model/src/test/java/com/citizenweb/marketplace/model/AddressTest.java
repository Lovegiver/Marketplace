package com.citizenweb.marketplace.model;

import com.citizenweb.marketplace.model.enums.AddressType;
import com.citizenweb.marketplace.model.enums.UserType;
import com.citizenweb.marketplace.model.model.Address;
import com.citizenweb.marketplace.model.model.Buyer;
import com.citizenweb.marketplace.model.model.User;
import com.citizenweb.marketplace.model.utils.AddressBuilder;
import com.citizenweb.marketplace.model.utils.EntityBuilderService;
import com.citizenweb.marketplace.model.utils.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressTest {

    private final static Logger LOG = LoggerFactory.getLogger("Address Testing");
    private final EntityBuilderService<? extends User> userBuilder = new UserBuilder();
    private final EntityBuilderService<? extends Address> addressBuilder = new AddressBuilder();

    @Test
    public void associateAddressToUser() {

        final long userId = 1L;
        Buyer buyer = (Buyer) userBuilder.getEntityWithBuilder(UserType.BUYER, userId);
        Assertions.assertNotNull(buyer);
        LOG.debug(buyer.toString());

        final long addressId1 = 1L;
        Address address1 = addressBuilder.getEntityWithBuilder(AddressType.BILLING, addressId1, buyer);
        Assertions.assertNotNull(address1);
        LOG.debug(address1.toString());

        final long addressId2 = 2L;
        Address address2 = addressBuilder.getEntityWithConstructor(AddressType.DELIVERY, addressId2, buyer);
        Assertions.assertNotNull(address2);
        LOG.debug(address2.toString());

        Assertions.assertNotEquals(address1, address2);
        Assertions.assertNotEquals(address1.hashCode(), address2.hashCode());

        buyer.addAddress(address1);
        buyer.addAddress(address2);
        Assertions.assertEquals(2, buyer.getAddresses().size());

        Assertions.assertEquals(addressId1, address1.getId());
        Assertions.assertEquals(AddressType.BILLING, address1.getAddressType());
        Assertions.assertEquals(AddressBuilder.street1, address1.getStreet1());
        Assertions.assertEquals(AddressBuilder.street2, address1.getStreet2());
        Assertions.assertEquals(AddressBuilder.zipcode, address1.getZipcode());
        Assertions.assertEquals(AddressBuilder.city, address1.getCity());
        Assertions.assertEquals(AddressBuilder.country, address1.getCountry());
        Assertions.assertEquals(AddressBuilder.state, address1.getState());
        Assertions.assertEquals(AddressBuilder.isActive, address1.isActive());
        Assertions.assertEquals(buyer, address1.getUser());

        buyer.removeAddress(address1);
        buyer.removeAddress(address2);
        Assertions.assertEquals(0, buyer.getAddresses().size());

    }

}
