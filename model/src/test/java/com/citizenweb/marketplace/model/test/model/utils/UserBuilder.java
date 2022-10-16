package com.citizenweb.marketplace.model.test.model.utils;

import com.citizenweb.marketplace.model.enums.GenderType;
import com.citizenweb.marketplace.model.enums.UserType;
import com.citizenweb.marketplace.model.model.Buyer;
import com.citizenweb.marketplace.model.model.Seller;
import com.citizenweb.marketplace.model.model.User;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Month;

@NoArgsConstructor
public class UserBuilder implements EntityBuilderService<User> {

    /* USER */
    public static String email = "user@honey.com";
    public static String password = "password";
    public static String pseudo = "pseudo";
    public static String firstname = "firstname";
    public static String lastname = "lastname";
    public static String company = "company";
    public static String rcs = "rcs";
    public static String siren = "siren";
    public static GenderType man = GenderType.MAN;
    public static GenderType woman = GenderType.WOMAN;
    public static LocalDateTime lastConnected = LocalDateTime.of(1971, Month.JULY.getValue(), 8, 14, 0);
    public static String userDescription = "Producteur de miel";

    /**
     * Uses a {@link lombok.Builder} to build a {@link User}, either a {@link Buyer} or a {@link Seller}
     * @param params UserType, id
     * @return a {@link User}
     */
    @Override
    public User getEntityWithBuilder(Object... params) {
        UserType type = (UserType) params[0];
        long id = (long) params[1];
        User user;
        if (UserType.BUYER.equals(type)) {
            user = Buyer.builder()
                    .id(id)
                    .userType(type)
                    .email(email)
                    .password(password.concat(String.valueOf(id)))
                    .pseudo(pseudo)
                    .firstname(firstname)
                    .lastname(lastname)
                    .genderType(man)
                    .lastConnection(lastConnected)
                    .description(userDescription)
                    .build();
        } else {
            user = Seller.builder()
                    .id(id)
                    .userType(type)
                    .email(email)
                    .password(password.concat(String.valueOf(id)))
                    .pseudo(pseudo)
                    .firstname(firstname)
                    .lastname(lastname)
                    .genderType(woman)
                    .lastConnection(lastConnected)
                    .description(userDescription)
                    .companyName(company)
                    .rcs(rcs)
                    .siren(siren)
                    .build();
        }
        return user;
    }

    /**
     * Uses a constructor to build a {@link User}, either a {@link Buyer} or a {@link Seller}
     * @param params UserType, id
     * @return a {@link User}
     */
    @Override
    public User getEntityWithConstructor(Object... params) {
        UserType type = (UserType) params[0];
        long id = (long) params[1];
        User user;
        if (UserType.BUYER.equals(type)) {
            user = new Buyer(email, password.concat(String.valueOf(id)), pseudo, firstname, lastname, man, lastConnected);
            user.setId(id);
        } else {
            user = new Seller(email, password.concat(String.valueOf(id)), pseudo, firstname, lastname, woman, lastConnected, company, rcs, siren);
            user.setId(id);
        }
        return user;
    }

}
