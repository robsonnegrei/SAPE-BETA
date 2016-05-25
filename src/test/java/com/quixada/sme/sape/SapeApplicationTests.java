package com.quixada.sme.sape;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.quixada.sme.dao.UsuarioDAO;
import com.quixada.sme.model.Usuario;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SapeApplication.class)
@WebAppConfiguration
public class SapeApplicationTests {

	@Test
	public void contextLoads() {
	}
	

}
