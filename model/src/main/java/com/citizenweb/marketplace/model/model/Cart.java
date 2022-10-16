package com.citizenweb.marketplace.model.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(name = "CART")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends ItemContainer {

    @Column(name = "crt_is_shared")
    @Getter @Setter @ToString.Include
    private boolean isSharedCart;
    /* RELATIONSHIP */
    /** The OrderItems contained in this Cart */
    @OneToMany(mappedBy = "cart")
    @Getter @Setter @Builder.Default
    private CopyOnWriteArrayList<OrderItem> orderItems = new CopyOnWriteArrayList<>();
    /** The Buyer, owner of this Cart */
    @ManyToMany @JoinTable(name = "USER_CART",
        joinColumns = { @JoinColumn(name = "crt_id") },
            inverseJoinColumns = { @JoinColumn(name = "usr_id") }
    )
    @Getter @Setter @Builder.Default
    private Set<Buyer> buyers = new LinkedHashSet<>();

     public Cart(boolean isShared) {
        super();
        this.isSharedCart = isShared;
        this.buyers = new LinkedHashSet<>();
         this.orderItems = new CopyOnWriteArrayList<>();
    }

    /**
     * @return an amount
     */
    @Override
    public BigDecimal getTotalAmountNoVAT() {
        var amount = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
        this.orderItems.forEach(line -> {
            amount.set(amount.get().add(line.getTotalAmountNoVAT()));
        });
        this.totalAmountNoVAT = amount.get();
        return amount.get();
    }

    /**
     * @return an amount
     */
    @Override
    public BigDecimal getTotalAmountWithVAT() {
        var amount = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
        this.orderItems.forEach(line -> {
            amount.set(amount.get().add(line.getTotalAmountWithVAT()));
        });
        this.totalAmountWithVAT = amount.get();
        return amount.get();
    }

    public void addBuyer(Buyer buyer) {
        this.buyers.add(buyer);
        buyer.addCart(this);
    }

    public void removeBuyer(Buyer buyer) {
        this.buyers.remove(buyer);
        buyer.removeCart(this);
    }

    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
        item.setCart(this);
    }

    public void removeOrderItem(OrderItem item) {
        this.orderItems.remove(item);
        item.setCart(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cart cart = (Cart) o;
        return isSharedCart == cart.isSharedCart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isSharedCart);
    }
}
