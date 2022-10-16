package com.citizenweb.marketplace.model.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@SuperBuilder
public abstract class ItemContainer implements Orderable, Serializable {

    private static final long serialVersionUID = 11L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oic_id")
    @Getter @Setter @ToString.Include
    private long id;
    @Column(name = "oic_no_Vat_amount")
    @Getter @Setter @ToString.Include
    BigDecimal totalAmountNoVAT;
    @Column(name = "oic_with_Vat_amount")
    @Getter @Setter @ToString.Include
    BigDecimal totalAmountWithVAT;

    public abstract void addBuyer(Buyer buyer);

    public abstract void removeBuyer(Buyer buyer);

    public abstract void addOrderItem(OrderItem item);

    public abstract void removeOrderItem(OrderItem item);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemContainer that = (ItemContainer) o;
        return id == that.id && Objects.equals(totalAmountNoVAT, that.totalAmountNoVAT)
                && Objects.equals(totalAmountWithVAT, that.totalAmountWithVAT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalAmountNoVAT, totalAmountWithVAT);
    }
}
