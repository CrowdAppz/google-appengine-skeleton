package com.crowdappz.demo.servlet;

import com.crowdappz.demo.handler.QueryHandler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextListener implements ServletContextListener {

    // ================ Constants ============================================== //

    // ================ Members ================================================ //

    // ================ Constructors & Main ==================================== //

    // ================ Methods for/from SuperClass / Interfaces =============== //
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        QueryHandler.registerClazzez();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    // ================ Methods ================================================ //

    // ================ Getter & Setter ======================================== //

    // ================ Inner & Anonymous Classes ============================== //
}
