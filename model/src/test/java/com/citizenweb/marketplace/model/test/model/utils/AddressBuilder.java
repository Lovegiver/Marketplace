package com.citizenweb.marketplace.model.test.model.utils;

import com.citizenweb.marketplace.model.enums.AddressType;
import com.citizenweb.marketplace.model.model.Address;
import com.citizenweb.marketplace.model.model.User;
import lombok.Builder;

public class AddressBuilder implements EntityBuilderService<Address> {

    public static final String street1 = "Oxford Street";
    public static final String street2 = "Building B";
    public static final String zipcode = "01234";
    public static final String city = "London";
    public static final String country = "England";
    public static final String state = "London metropolitan";
    public static final boolean isActive = true;

    /**
     * Uses a {@link Builder} to build a T
     *
     * @param params
     * @return a T
     */
    @Override
    public Address getEntityWithBuilder(Object... params) {
        final AddressType type = (AddressType) params[0];
        final long id = (long) params[1];
        final User user = (User) params[2];
        return Address.builder()
                .id(id)
                .addressType(type)
                .street1(street1)
                .street2(street2)
                .zipcode(zipcode)
                .city(city)
                .country(country)
                .state(state)
                .isActive(isActive)
                .user(user)
                .build();
    }

    /**
     * Uses a constructor to build a T
     *
     * @param params
     * @return a T
     */
    @Override
    public Address getEntityWithConstructor(Object... params) {
        final AddressType type = (AddressType) params[0];
        final long id = (long) params[1];
        final User user = (User) params[2];
        Address address = new Address(type, user, street1, street2, zipcode, city, country, state, isActive);
        address.setId(id);
        return address;
    }
}
