package com.lxdmp.springtest.entity.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class BaseRepository
{
	@Autowired
	protected NamedParameterJdbcTemplate jdbcTemplate;
}

