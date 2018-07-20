package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.product.distributor.dto.UserDTO;
import org.product.distributor.model.auth.User;

/**
 * Created by vikram on 20/07/18.
 */
@Mapper
public interface UserMapper {

    UserDTO map(User user);
    User map(UserDTO userDTO);

}
