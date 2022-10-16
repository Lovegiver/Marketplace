package com.citizenweb.marketplace.model.test.model.utils;

public interface EntityBuilderService<T> {

    /**
     * Uses a {@link lombok.Builder} to build a T
     * @param params
     * @return a T
     */
    T getEntityWithBuilder(Object... params);

    /**
     * Uses a constructor to build a T
     * @param params
     * @return a T
     */
    T getEntityWithConstructor(Object... params);

}
