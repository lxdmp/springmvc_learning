package com.lxdmp.springtest.entity.repository.impl;

import org.springframework.stereotype.Repository;
import com.lxdmp.springtest.entity.repository.CartRepository;

@Repository("mysqlCartRepo")
public class MysqlCartRepository extends InMemoryCartRepository
{
}

