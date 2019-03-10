package com.qantas.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class UnitTestApplicationConfig extends AbstractMongoConfiguration {
    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return "admin";
    }

    public Mongo mongo() throws Exception {
        return new Fongo(getDatabaseName()).getMongo();
    }

	@Override
	public MongoClient mongoClient() {
		// TODO Auto-generated method stub
		System.out.println("---------->");
		return new Fongo("user1").getMongo();
	}
}
