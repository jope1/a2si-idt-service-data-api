package com.bjss.nhsd.a2si.idt.servicedataapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class IdtServiceDataConfiguration {

    @Autowired
    private DataSource dataSource;

}
