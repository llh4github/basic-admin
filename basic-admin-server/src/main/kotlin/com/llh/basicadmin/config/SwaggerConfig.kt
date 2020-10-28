package com.llh.basicadmin.config

import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Parameter
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 *
 * Created At 2020/10/28 15:45
 *
 * @author llh
 */
@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Value("\${swagger2.enable}") // kotlin 语言设计导致只能这样注入基本类型数据
    private var enable: Boolean = false

    @Bean
    fun createRestApi(): Docket {
//        val tokenPar = ParameterBuilder()
//        val refreshTokenPar = ParameterBuilder()
//        tokenPar.name("authorization")
//            .description("swagger测试用(模拟authorization传入)非必填 header")
//            .modelRef(ModelRef("string")).parameterType("header")
//            .required(false)
//        refreshTokenPar.name("refresh_token")
//            .description("swagger测试用(模拟刷新token传入)非必填 header")
//            .modelRef(ModelRef("string")).parameterType("header")
//            .required(false)
//        val list = mutableListOf<Parameter>(tokenPar.build(), refreshTokenPar.build())

        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation::class.java))
            .paths(PathSelectors.any())
            .build()
//            .globalOperationParameters(list)
            .enable(enable)

    }

    private fun apiInfo(): ApiInfo? {
        return ApiInfoBuilder()
            .title("basic-admin")
            .description("简单的后台管理系统基础框架")
            .termsOfServiceUrl("")
            .version("0.1")
            .build()
    }
}
