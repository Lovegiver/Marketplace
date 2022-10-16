package com.citizenweb.marketplace.model.model;

import com.citizenweb.marketplace.model.enums.GenderType;
import com.citizenweb.marketplace.model.enums.UserType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USER")
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public
class User implements PictureHolder, Serializable {

    private static final long serialVersionUID = 15L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    @Getter @Setter @ToString.Include
    private long id;
    @Column(name = "usr_type") @Enumerated(EnumType.STRING)
    @Getter @Setter @ToString.Include
    private UserType userType;
    @Column(name = "usr_pseudo")
    @Getter @Setter @ToString.Include
    private String pseudo;
    @Column(name = "usr_firstname")
    @Getter @Setter @ToString.Include
    private String firstname;
    @Column(name = "usr_lastname")
    @Getter @Setter @ToString.Include
    private String lastname;
    @Column(name = "usr_description")
    @Getter @Setter
    private String description;
    @Column(name = "usr_email")
    @Getter @Setter @ToString.Include
    private String email;
    @Column(name = "usr_pwd")
    @Getter @Setter @ToString.Include
    private String password;
    @Column(name = "usr_last_co") @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter @ToString.Include
    private LocalDateTime lastConnection;
    @Column(name = "usr_gender") @Enumerated(EnumType.STRING)
    @Getter @Setter @ToString.Include
    private GenderType genderType;

    /* RELATIONSHIP */
    /** User's addresses */
    @OneToMany(mappedBy = "user")
    @Getter @Setter @Builder.Default
    private Set<Address> addresses = new LinkedHashSet<>();
    /** User's profile picture */
    @OneToOne(mappedBy = "user")
    @Getter @Setter
    private Picture associatedPicture;

    protected User(@NonNull UserType type, @NonNull String email, @NonNull String password, @NonNull String pseudo,
                   @NonNull String firstname, @NonNull String lastname, @NonNull GenderType genderType, LocalDateTime lastConnected) {
        this.userType = type;
        this.email = email;
        this.password = password;
        this.pseudo = pseudo;
        this.firstname = firstname;
        this.lastname = lastname;
        this.genderType = genderType;
        this.lastConnection = lastConnected;
        this.addresses = new LinkedHashSet<>();
    }

    /** Adds an {@link Address} to the {@link User}'s collection */
    public void addAddress(@NonNull Address address) { this.addresses.add(address); address.setUser(this); }

    /** Removes an {@link Address} from the {@link User}'s collection */
    public void removeAddress(@NonNull Address address) { this.addresses.remove(address); address.setUser(null); }

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
        User user = (User) o;
        return id == user.id && userType == user.userType && email.equals(user.email) && password.equals(user.password) && Objects.equals(lastConnection, user.lastConnection) && genderType == user.genderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userType, email, password, lastConnection, genderType);
    }
}
