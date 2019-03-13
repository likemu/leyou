package com.leyou.order.client;

import com.leyou.order.dto.AddressDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：lidatu
 * @Date： 2019/02/08 星期五 23:02
 * @Description： 模拟地址
 */

public abstract class AddressClient {
    public static final List<AddressDTO> addressList = new ArrayList<AddressDTO>(){
        {
            AddressDTO address = new AddressDTO();
            address.setId(2L);
            address.setAddress("广东广州");
            address.setCity("广州");
            address.setDistrict("天河区");
            address.setName("若黎");
            address.setPhone("17300000123");
            address.setState("广州");
            address.setZipCode("1657542");
            address.setIsDefault(true);
            add(address);

            AddressDTO address2 = new AddressDTO();
            address.setId(1L);
            address.setAddress("北京北京路");
            address.setCity("北京");
            address.setDistrict("北京");
            address.setName("若_黎2");
            address.setPhone("17540001234");
            address.setState("北京");
            address.setZipCode("120000");
            address.setIsDefault(false);
            add(address2);
        }
    };

    public static AddressDTO findById(Long id){
        for(AddressDTO addressDTO : addressList){
            if(addressDTO.getId() == id)
                return addressDTO;
        }
        return null;
    }

}

















