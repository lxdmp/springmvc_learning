package com.lxdmp.springtest.domain.repository.impl;

import org.springframework.stereotype.Repository;
import com.lxdmp.springtest.domain.repository.CartRepository;

@Repository("mysqlCartRepo")
public class MysqlCartRepository extends InMemoryCartRepository
{
}

