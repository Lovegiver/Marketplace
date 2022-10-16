package com.citizenweb.marketplace.model.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Blob;
import java.util.Objects;

@Entity
@DiscriminatorValue("USER")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserPicture extends Picture {

    /* RELATIONSHIP */
    @OneToOne @JoinColumn(name = "pic_usr_id")
    @Getter @Setter @ToString.Include
    private User user;

    public UserPicture(@NonNull Blob picture, @NonNull User user) {
        super(picture, user);
    }

    /**
     * Associates a User to this Picture
     * @param holder the User to be associated
     */
    public void associatePictureHolder(@NonNull PictureHolder holder) {
        this.user = (User) holder;
        holder.associatePicture(this);
    }

    /**
     * Dissociates a user from this Picture
     * @param holder the User to be dissociated
     */
    public void removeAssociatedPictureHolder(@NonNull PictureHolder holder) {
        this.user = null;
        holder.dissociatePicture(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPicture)) return false;
        if (!super.equals(o)) return false;
        UserPicture that = (UserPicture) o;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user);
    }
}
