package com.citizenweb.marketplace.model.model;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(name = "ORDER")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends ItemContainer {

    @Column(name = "ord_is_shared")
    @Getter @Setter @ToString.Include
    private boolean isSharedOrder;
    /* RELATIONSHIP */
    /** The OrderItem contained in this Order */
    @OneToMany(mappedBy = "order")
    @Getter @Setter @Builder.Default
    private CopyOnWriteArrayList<OrderItem> orderItems = new CopyOnWriteArrayList<>();
    /** The Buyers involved in this Order or just one */
    @ManyToMany @JoinTable(name = "USER_ORDER",
        joinColumns = { @JoinColumn(name = "ord_id") },
            inverseJoinColumns = { @JoinColumn(name = "usr_id") }
    )
    @Getter @Setter @Builder.Default
    private Set<Buyer> buyers = new LinkedHashSet<>();
    /** The Seller for the Products in this Order */
    @ManyToOne @JoinColumn(name = "ord_usr_id_seller")
    @Getter @Setter @ToString.Include
    private Seller seller;

    public Order(@NonNull List<OrderItem> items, boolean isShared) {
        super();
        this.isSharedOrder = isShared;
        this.orderItems = new CopyOnWriteArrayList<>();
        this.buyers = new LinkedHashSet<>();
        this.orderItems.addAll(items);
    }

    public void addBuyer(Buyer buyer) {
        this.buyers.add(buyer);
        buyer.addOrder(this);
    }

    public void removeBuyer(Buyer buyer) {
        this.buyers.remove(buyer);
        buyer.removeOrder(this);
    }

    public void addSeller(Seller seller) {
        seller.addOrder(this);
        this.seller = seller;
    }

    public void removeSeller(Seller seller) {
        seller.removeOrder(this);
        this.seller = null;
    }

    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
        item.setOrder(this);
    }

    public void removeOrderItem(OrderItem item) {
        this.orderItems.remove(item);
        item.setOrder(null);
    }

    /**
     * @return an amount
     */
    @Override
    public BigDecimal getTotalAmountNoVAT() {
        var amount = new AtomicReference<>(BigDecimal.ZERO);
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
        var amount = new AtomicReference<>(BigDecimal.ZERO);
        this.orderItems.forEach(line -> {
            amount.set(amount.get().add(line.getTotalAmountWithVAT()));
        });
        this.totalAmountWithVAT = amount.get();
        return amount.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return isSharedOrder == order.isSharedOrder && seller.equals(order.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isSharedOrder, seller);
    }
}
