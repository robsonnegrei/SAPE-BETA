package com.quixada.sme.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

    private static boolean invalidConfig = false;

    public Configuracao(){
        load();
    }

    public static void load(){
        logger.info("CONFIG: Inicializando configuracoes do sistema");
        ANO_AVALIACAO_ATUAL = 2016;
        PERIODO_AVALIACAO_ATUAL = 1;
        PERIODOS_POR_ANO = 5;
        LIMITE_DIAS_REAVALIACAO = 15;
        logger.info("CONFIG: Configuracoes carregadas");
    }

    public static boolean isValidConfiguration(){
        return !invalidConfig;
    }
}
