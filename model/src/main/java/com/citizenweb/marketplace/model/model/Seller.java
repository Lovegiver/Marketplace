package com.citizenweb.marketplace.model.model;

import com.citizenweb.marketplace.model.enums.GenderType;
import com.citizenweb.marketplace.model.enums.UserType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "SELLER")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Seller extends User {

    @Column(name = "usr_company_name")
    @Getter @Setter @ToString.Include
    private String companyName;
    @Column(name = "usr_rcs")
    @Getter @Setter @ToString.Include
    private String rcs;
    @Column(name = "usr_siren")
    @Getter @Setter @ToString.Include
    private String siren;
    /* RELATIONSHIP */
    @OneToMany(mappedBy = "seller")
    @Getter @Setter @Builder.Default
    private Set<Product> products = new LinkedHashSet<>();
    @OneToMany(mappedBy = "seller")
    @Getter @Setter  @Builder.Default
    private Set<Order> orders = new LinkedHashSet<>();

    public Seller(@NonNull String email, @NonNull String password, @NonNull String pseudo,
                  @NonNull String firstname, @NonNull String lastname, @NonNull GenderType genderType,
                  LocalDateTime lastConnected, @NonNull String companyName, String rcs, String siren) {
        super(UserType.SELLER, email, password, pseudo, firstname, lastname, genderType, lastConnected);
        this.companyName = companyName;
        this.rcs = rcs;
        this.siren = siren;
        this.products = new LinkedHashSet<>();
        this.orders = new LinkedHashSet<>();
    }

    void addOrder(Order order) {
        this.orders.add(order);
    }

    void removeOrder(Order order) {
        this.orders.remove(order);
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setSeller(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setSeller(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Seller seller = (Seller) o;
        return companyName.equals(seller.companyName) && Objects.equals(rcs, seller.rcs) && Objects.equals(siren, seller.siren);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName, rcs, siren);
    }
}
