package com.restcontrol.restcontrol_tc2.helper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;

public class UserTypeTestHelper {

    public static final String VALID_USER_TYPE_ID = "507f1f77bcf86cd799439011";
    public static final String VALID_NAME = "Cliente";
    public static final String VALID_CODE = UserType.CUSTOMER_CODE;
    public static final String RESTAURANT_OWNER_NAME = "Dono de Restaurante";
    public static final String RESTAURANT_OWNER_CODE = UserType.RESTAURANT_OWNER_CODE;

    public static UserType validUserType() {
        return new UserType(VALID_USER_TYPE_ID, VALID_NAME, VALID_CODE);
    }

    public static UserType restaurantOwnerUserType() {
        return new UserType(VALID_USER_TYPE_ID, RESTAURANT_OWNER_NAME, RESTAURANT_OWNER_CODE);
    }

    public static CreateUserTypeInputDTO createUserTypeInput() {
        return new CreateUserTypeInputDTO(null, VALID_NAME, VALID_CODE);
    }

    public static UpdateUserTypeInputDTO updateUserTypeInput() {
        return new UpdateUserTypeInputDTO("Tipo Atualizado", "UPDATED_TYPE");
    }
}
