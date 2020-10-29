package com.llh.basicadmin.common.config

import org.ktorm.database.Database
import org.ktorm.jackson.KtormModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource
import com.fasterxml.jackson.databind.Module

/**
 *
 * Created At 2020/10/27 16:01
 *
 * @author llh
 */
@Configuration
class KtormConfig {
    @Autowired
    lateinit var dataSource: DataSource

    @Bean
    fun database(): Database {
        return Database.connectWithSpringSupport(dataSource)
    }

    /**
     * 序列化ktorm对象
     */
    @Bean
    fun ktormModule(): Module {
        return KtormModule()
    }
}
