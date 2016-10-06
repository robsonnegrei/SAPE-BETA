package com.quixada.sme.model;

import com.quixada.sme.dao.ConfiguracaoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Created by Isaias on 10/4/2016.
 */
@Component
public class Configuracao {

    private static Logger logger = LoggerFactory.getLogger(Configuracao.class);

    public static int ANO_AVALIACAO_ATUAL;
    public static int PERIODO_AVALIACAO_ATUAL;
    public static int PERIODOS_POR_ANO;
    public static int LIMITE_DIAS_REAVALIACAO;

    private static ConfiguracaoDAO cfgDAO;

    private static boolean invalidConfig = false;

    @Autowired
    public Configuracao(ConfiguracaoDAO cfgDAO) throws SQLException {
        this.cfgDAO = cfgDAO;
        load();
    }

    public static void load() throws SQLException {
        logger.info("CONFIG: Inicializando configuracoes do sistema");
        int resultado;
        try {
            resultado = (int) cfgDAO.getConfigField("AnoAvaliacaoAtual");
            logger.info("CONFIG: Ano de Avaliacao:" + resultado);
            ANO_AVALIACAO_ATUAL = resultado;

            resultado = (int) cfgDAO.getConfigField("PeriodoAvaliacaoAtual");
            logger.info("CONFIG: Periodo avaliacao:" + resultado);
            PERIODO_AVALIACAO_ATUAL = resultado;

            resultado = (int) cfgDAO.getConfigField("PeriodosPorAno");
            logger.info("CONFIG: [Leitura] Periodos por ano:" + resultado);
            PERIODOS_POR_ANO = resultado;

            resultado = (int) cfgDAO.getConfigField("LimiteDiasReavaliacao");
            logger.info("CONFIG: Limite dias:" + resultado);
            LIMITE_DIAS_REAVALIACAO = resultado;

            logger.info("CONFIG: Configuracoes carregadas");
        }
        catch (SQLException e) {
            logger.error("Erro ao carregar configurações. Abortando. : " + e.getMessage());
            throw e;
        }
    }

    /*
   Persiste as configuracoes no banco de dados
    */
    public static void save(){
        logger.info("CONFIG: Salvando configuracao de sistema");
        int resultado;
        try {
            cfgDAO.setConfigField("AnoAvaliacaoAtual", ANO_AVALIACAO_ATUAL);
            cfgDAO.setConfigField("PeriodoAvaliacaoAtual", PERIODO_AVALIACAO_ATUAL);
            cfgDAO.setConfigField("LimiteDiasReavaliacao", LIMITE_DIAS_REAVALIACAO);
            cfgDAO.setConfigField("primeiraConfiguracao", false);
            logger.info("CONFIG: Configuracoes salvas");
        }
        catch (SQLException e) {
            logger.error("Erro ao guardar configurações. Abortando. : " + e.getMessage());
        }
    }

    public static boolean isValidConfiguration(){
        return !invalidConfig;
    }
}
