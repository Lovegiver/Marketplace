package com.citizenweb.marketplace.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.citizenweb.marketplace.dao.repos")
public class DaoConfig {
}
