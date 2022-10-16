package com.citizenweb.marketplace.model.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT")
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product implements PictureHolder, Serializable {

    private static final long serialVersionUID = 14L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pdt_id")
    @Getter @Setter @ToString.Include
    private long id;
    @Column(name = "pdt_name")
    @Getter @Setter @ToString.Include
    private String name;
    @Column(name = "pdt_reference")
    @Getter @Setter @ToString.Include
    private String reference;
    @Column(name = "pdt_description")
    @Getter @Setter @ToString.Include
    private String description;
    @Column(name = "pdt_unit") @Enumerated(EnumType.STRING)
    @Getter @Setter @ToString.Include
    private Unit unit;
    @Column(name = "pdt_quantity")
    @Getter @Setter @ToString.Include
    private int quantity;
    @Column(name = "pdt_unit_price_no_Vat")
    @Getter @Setter @ToString.Include
    private BigDecimal unitPriceNoVAT;
    @Column(name = "pdt_unit_price_with_Vat")
    @Getter @Setter @ToString.Include
    private BigDecimal unitPriceWithVAT;

    /* RELATIONSHIP */
    /** The product's seller */
    @ManyToOne @JoinColumn(name = "pdt_usr_id_seller")
    @Getter @Setter @ToString.Include
    private Seller seller;
    /** The product's picture */
    @ManyToOne @JoinColumn(name = "pdt_pic_id")
    @Getter @Setter
    private Picture associatedPicture;
    /** The order lines where this product may be found */
    @OneToMany(mappedBy = "product")
    @Getter @Setter @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    public Product(@NonNull String name, @NonNull String reference, String description, @NonNull Unit unit,
                   int quantity, @NonNull BigDecimal priceNoVat, @NonNull BigDecimal priceWithVat, @NonNull Seller seller) {
        this.name = name;
        this.reference = reference;
        this.description = description;
        this.unit = unit;
        this.quantity = quantity;
        this.unitPriceNoVAT = priceNoVat;
        this.unitPriceWithVAT = priceWithVat;
        this.seller = seller;
        this.orderItems = new ArrayList<>();
    }

    @Override
    public void associatePicture(Picture picture) {
        this.associatedPicture = picture;
    }

    @Override
    public void dissociatePicture(Picture picture) {
        this.associatedPicture = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && quantity == product.quantity && name.equals(product.name) && reference.equals(product.reference) && unit.equals(product.unit) && seller.equals(product.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, reference, unit, quantity, seller);
    }
}
