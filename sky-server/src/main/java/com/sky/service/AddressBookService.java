package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

/**
 * @Classname AddressBookService
 * @Description 地址簿的Service
 * @Date 2024/2/26 15:25
 * @Created by Hygge
 */
public interface AddressBookService {
    List<AddressBook> list(AddressBook addressBook);

    void save(AddressBook addressBook);

    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    void setDefault(AddressBook addressBook);

    void deleteById(Long id);
}
