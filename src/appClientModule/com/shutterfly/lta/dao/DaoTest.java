package com.shutterfly.lta.dao;

import java.sql.SQLException;

import org.junit.Test;

import com.shutterfly.lta.model.CustomerBean;

public class DaoTest {
	
	
	@Test
	public void test1() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		LTADao dao = LTADao.getInstance();
		dao.insertCustomerRecord(new CustomerBean());
	}
}
