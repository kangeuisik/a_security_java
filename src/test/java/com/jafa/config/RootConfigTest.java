package com.jafa.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jafa.AppTest;

public class RootConfigTest extends AppTest{
	@Autowired
	DataSource dataSource;
	@Test
	public void dataSource() throws SQLException {
		assertNotNull(dataSource);
		dataSource.getConnection();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String now = jdbcTemplate.queryForObject(
				"select sysdate from dual", String.class);
		System.out.println(now);
	}

}
