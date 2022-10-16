package com.citizenweb.marketplace.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.citizenweb.marketplace.dao.repos")
public class DaoConfig {
}
