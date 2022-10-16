package com.citizenweb.marketplace.model.model;

import com.citizenweb.marketplace.model.enums.AddressType;
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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ADDRESS")
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address implements Serializable {

    private static final long serialVersionUID = 10L;

    @Id @GeneratedValue @Column(name = "add_id")
    @Getter @Setter @ToString.Include
    private long id;
    @Column(name = "add_type") @Enumerated(EnumType.STRING)
    @Getter @Setter @ToString.Include
    private AddressType addressType;
    @Column(name = "add_street1")
    @Getter @Setter @ToString.Include
    private String street1;
    @Column(name = "add_street2")
    @Getter @Setter @ToString.Include
    private String street2;
    @Column(name = "add_zipcode")
    @Getter @Setter @ToString.Include
    private String zipcode;
    @Column(name = "add_city")
    @Getter @Setter @ToString.Include
    private String city;
    @Column(name = "add_country")
    @Getter @Setter @ToString.Include
    private String country;
    @Column(name = "add_state")
    @Getter @Setter @ToString.Include
    private String state;
    @Column(name = "add_active")
    @Getter @Setter @ToString.Include
    private boolean isActive;
    /* RELATIONSHIP */
    @ManyToOne @JoinColumn(name = "add_usr_id")
    @Getter @Setter @ToString.Include
    private User user;

    public Address(@NonNull AddressType type, @NonNull User user, @NonNull String street1, String street2,
                   @NonNull String zipcode, @NonNull String city, @NonNull String country, String state,
                   boolean isActive) {
        this.addressType = type;
        this.user = user;
        this.street1 = street1;
        this.street2 = street2;
        this.zipcode = zipcode;
        this.city = city;
        this.country = country;
        this.state = state;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return id == address.id && isActive == address.isActive && addressType == address.addressType
                && street1.equals(address.street1) && zipcode.equals(address.zipcode) && city.equals(address.city)
                && country.equals(address.country) && user.equals(address.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addressType, street1, zipcode, city, country, isActive, user);
    }
}
