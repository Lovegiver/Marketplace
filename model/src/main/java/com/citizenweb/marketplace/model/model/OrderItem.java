package com.citizenweb.marketplace.model.model;

import com.citizenweb.marketplace.model.enums.OrderItemStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ORDERITEM")
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor (access = AccessLevel.PROTECTED)
@Builder
public class OrderItem implements Orderable, Serializable {

    private static final long serialVersionUID = 12L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lin_id")
    @Getter @Setter @ToString.Include
    private long id;
    @Column(name = "lin_status") @Enumerated(EnumType.STRING)
    @Getter @Setter @ToString.Include
    private OrderItemStatus status;
    @Column(name = "lin_create_date") @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter @ToString.Include
    private LocalDateTime creationDate;
    @Column(name = "lin_qty")
    @Getter @Setter @ToString.Include
    private int quantity;
    @Column(name = "lin_unit_price_no_Vat")
    @Getter @Setter @ToString.Include
    private BigDecimal unitPriceNoVAT;
    @Column(name = "lin_no_Vat_amount")
    @Setter @ToString.Include
    private BigDecimal totalAmountNoVAT;
    @Column(name = "lin_unit_price_with_Vat")
    @Getter @Setter @ToString.Include
    private BigDecimal unitPriceWithVAT;
    @Column(name = "lin_with_Vat_amount")
    @Setter @ToString.Include
    private BigDecimal totalAmountWithVAT;
    @Column(name = "lin_vat_value")
    @Getter @Setter @ToString.Include
    private BigDecimal applicableVAT;
    @Column(name = "lin_discount")
    @Getter @Setter @ToString.Include
    private BigDecimal applicableDiscount;
    @Column(name = "lin_is_active")
    @Getter @Setter @ToString.Include
    private boolean isActive;

    /* RELATIONSHIP */
    /** The Product */
    @ManyToOne @JoinColumn(name = "lin_pdt_id")
    @Getter @Setter @ToString.Include
    private Product product;
    /** The Order associated to this OrderItem */
    @ManyToOne @JoinColumn(name = "lin_ord_id")
    @Getter @Setter @ToString.Include
    private Order order;
    /** The Cart associated to this OrderItem */
    @ManyToOne @JoinColumn(name = "lin_crt_id")
    @Getter @Setter @ToString.Include
    private Cart cart;

    public OrderItem(Product product, int quantity, BigDecimal vatRate, BigDecimal discountRate) {
        this.product = product;
        this.quantity = quantity;
        this.applicableVAT = vatRate;
        this.applicableDiscount = discountRate;
        this.status = OrderItemStatus.DRAFT;
        this.creationDate = LocalDateTime.now();
        this.isActive = true;
    }

    /**
     * @return an amount
     */
    @Override
    public BigDecimal getTotalAmountNoVAT() {
        return this.totalAmountNoVAT;
    }

    /**
     * @return an amount
     */
    @Override
    public BigDecimal getTotalAmountWithVAT() {
        return this.totalAmountWithVAT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return id == orderItem.id && quantity == orderItem.quantity && isActive == orderItem.isActive
                && status == orderItem.status && Objects.equals(creationDate, orderItem.creationDate)
                && unitPriceNoVAT.equals(orderItem.unitPriceNoVAT) && totalAmountNoVAT.equals(orderItem.totalAmountNoVAT)
                && unitPriceWithVAT.equals(orderItem.unitPriceWithVAT) && totalAmountWithVAT.equals(orderItem.totalAmountWithVAT)
                && applicableVAT.equals(orderItem.applicableVAT) && Objects.equals(applicableDiscount, orderItem.applicableDiscount)
                && product.equals(orderItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, creationDate, quantity, unitPriceNoVAT, totalAmountNoVAT, unitPriceWithVAT,
                totalAmountWithVAT, applicableVAT, applicableDiscount, isActive, product);
    }
}
