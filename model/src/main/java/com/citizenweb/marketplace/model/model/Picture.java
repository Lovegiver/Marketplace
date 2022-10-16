package com.citizenweb.marketplace.model.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "PICTURE")
@DiscriminatorColumn(name = "pic_type")
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Picture implements Serializable {

    private static final long serialVersionUID = 13L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pic_id")
    @Getter @Setter @ToString.Include
    private long id;
    @Lob
    @Column(name = "pic_picture")
    @Getter @Setter
    private Blob picture;
    /* RELATIONSHIP */
    @OneToOne @JoinColumn(name = "pic_usr_id_owner")
    @Getter @Setter @ToString.Include
    private User owner;


    public Picture(@NonNull Blob picture, @NonNull User user) {
        this.picture = picture;
        this.owner = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture1 = (Picture) o;
        return id == picture1.id && picture.equals(picture1.picture) && owner.equals(picture1.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, picture, owner);
    }
}
