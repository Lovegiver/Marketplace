package com.citizenweb.marketplace.model.test.model;

import com.citizenweb.marketplace.model.enums.UserType;
import com.citizenweb.marketplace.model.enums.VolumeUnitType;
import com.citizenweb.marketplace.model.enums.WeightUnitType;
import com.citizenweb.marketplace.model.model.Buyer;
import com.citizenweb.marketplace.model.model.Picture;
import com.citizenweb.marketplace.model.model.PictureHolder;
import com.citizenweb.marketplace.model.model.Product;
import com.citizenweb.marketplace.model.model.ProductPicture;
import com.citizenweb.marketplace.model.model.Seller;
import com.citizenweb.marketplace.model.model.User;
import com.citizenweb.marketplace.model.model.UserPicture;
import com.citizenweb.marketplace.model.test.model.utils.EntityBuilderService;
import com.citizenweb.marketplace.model.test.model.utils.ProductBuilder;
import com.citizenweb.marketplace.model.test.model.utils.UserBuilder;
import org.hibernate.engine.jdbc.BlobProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class PictureTest {

    private final static Logger LOG = LoggerFactory.getLogger("Picture Testing");
    private final EntityBuilderService<? extends User> userBuilder = new UserBuilder();
    private final EntityBuilderService<? extends Product> productBuilder = new ProductBuilder();

    @Test
    public void convertUserPictureToBlob() {

        String picturePath1 = "src/test/resources/honey_picture.jpg";
        String picturePath2 = "src/test/resources/abeille.png";

        Buyer buyer1 = (Buyer) userBuilder.getEntityWithBuilder(UserType.BUYER, 1L);
        Buyer buyer2 = (Buyer) userBuilder.getEntityWithBuilder(UserType.BUYER, 2L);

        Picture picture1 = convertPictureToBlob(buyer1, buyer1, picturePath1);
        Picture picture2 = convertPictureToBlob(buyer2, buyer2, picturePath2);

        Assertions.assertNotEquals(picture1, picture2);
        Assertions.assertNotEquals(picture1.hashCode(), picture2.hashCode());

        ((UserPicture) picture1).removeAssociatedPictureHolder(buyer1);
        ((UserPicture) picture2).removeAssociatedPictureHolder(buyer2);

        Assertions.assertNull(((UserPicture) picture1).getUser());
        Assertions.assertNull(((UserPicture) picture2).getUser());

    }

    @Test
    public void convertProductPictureToBlob() {

        String picturePath1 = "src/test/resources/honey_picture.jpg";
        String picturePath2 = "src/test/resources/abeille.png";

        Seller seller = (Seller) userBuilder.getEntityWithBuilder(UserType.SELLER, 1L);
        Assertions.assertNotNull(seller);

        Product product1 = productBuilder.getEntityWithBuilder(WeightUnitType.MG, 1L, seller);
        Product product2 = productBuilder.getEntityWithBuilder(VolumeUnitType.ML, 2L, seller);

        Assertions.assertNotNull(product1);
        Assertions.assertNotNull(product2);

        ProductPicture picture1 = (ProductPicture) convertPictureToBlob(product1, seller, picturePath1);
        ProductPicture picture2 = (ProductPicture) convertPictureToBlob(product2, seller, picturePath2);
        Assertions.assertNotNull(picture1);
        Assertions.assertNotNull(picture2);
        Assertions.assertNotEquals(picture1, picture2);
        Assertions.assertNotEquals(picture1.hashCode(), picture2.hashCode());

        picture1.removeAssociatedPictureHolder(product1);
        picture2.removeAssociatedPictureHolder(product2);

        Assertions.assertEquals(0, picture1.getProducts().size());

    }

    <T extends PictureHolder> Picture convertPictureToBlob(T object, final User owner, String path) {
        Class<?> clazz = object.getClass();
        File file = new File(path);
        Picture picture;
        try (
                FileInputStream fis = new FileInputStream(file)
        ) {
            byte[] bytes = fis.readAllBytes();
            Blob encodedPicture = BlobProxy.generateProxy(bytes);
            LOG.debug(String.format("Receiving picture for %s", clazz));
            if (User.class.equals(clazz) || Buyer.class.equals(clazz) || Seller.class.equals(clazz)) {
                picture = new UserPicture(encodedPicture, owner);
                ((UserPicture) picture).associatePictureHolder(object);
            } else {
                picture = new ProductPicture(encodedPicture, owner);
                ((ProductPicture) picture).associatePictureHolder(object);
            }
            LOG.debug(String.valueOf(picture.getPicture().length()));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        return picture;
    }

}
