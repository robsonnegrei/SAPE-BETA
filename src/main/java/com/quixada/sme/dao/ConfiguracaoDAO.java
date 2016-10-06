package com.quixada.sme.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.validation.constraints.Null;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * Created by Isaias on 10/4/2016.
 */
@ComponentScan(value={"com.quixada.sme"})
@Component
public class ConfiguracaoDAO {

    private static Logger logger = LoggerFactory.getLogger(ConfiguracaoDAO.class);

    @Autowired
    DataSource ds;

    private Connection con;

    /*
    A tipagem fica por conta da classe de configuração
     */
    public Object getConfigField(String field) throws SQLException, NullPointerException{
        try {
            con = ds.getConnection();
            String sql = "SELECT "+field+" FROM configuracao";
            logger.debug("Config field query : " + sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getObject(1);
            }
            throw new NullPointerException(MessageFormat.format("Campo nao encontrado: {0} Query: {1}",field,sql));
        }
        finally {
            if (con != null) con.close();
        }
    }
    /*
    A tipagem fica por conta da classe de configuração
     */
    public void setConfigField(String field, Object value) throws SQLException, NullPointerException{
        try {
            con = ds.getConnection();
            //TEM QUE SER UPDATE ANIMAL
            String sql = "";//"INSERT INTO configuracao("+field+") VALUES (?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setObject(1,value);
            if (!stmt.execute()){
                logger.error(MessageFormat.format("Erro ao inserir campo: {0} Query: {1}",field,sql));
            }
        }
        finally {
            if (con != null) con.close();
        }
    }

}
