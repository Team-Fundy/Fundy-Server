package com.fundy.domain.user;

import com.fundy.domain.user.vos.Address;
import com.fundy.domain.user.vos.DeliveryAddressId;
import com.fundy.domain.user.vos.Phone;

public class DeliveryAddress {
    private DeliveryAddressId id;
    private Address address;
    private String receiver;
    private boolean isMain;
    private Phone phone;
    private String information;
}
