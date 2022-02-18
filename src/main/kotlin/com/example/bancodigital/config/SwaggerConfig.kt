package com.example.bancodigital.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityReference
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    private val BEARER_AUTH = "Bearer"

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.example.bancodigital.controller"))
            .paths(PathSelectors.any()).build().apiInfo(apiInfo()).groupName("public")
            .securitySchemes(securitySchemes()).securityContexts(addSecurityPaths())
    }

    @Bean
    fun adminApi(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .groupName("admin")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.bancodigital.admin"))
            .paths(PathSelectors.regex("/internal.*"))
            .build()

    fun apiInfo(): ApiInfo {
        return ApiInfo("DIGITAL BANK WEB REST API", "Product API to perform CRUD operations",
            "1.0", "Terms of service",
            Contact("Luiz Paulo Aureliano", "", "lpaureliano74@gmail.com"),
            "License of API", "API license URL", emptyList())
    }

    private fun securitySchemes(): List<SecurityScheme> {
        return listOf<SecurityScheme>(ApiKey(BEARER_AUTH,"Authorization", "header"))
    }

    private fun addSecurityPaths(): List<SecurityContext> {
        val securityOne = SecurityContext.builder().securityReferences(listOf(bearerAuthReference()))
            .forPaths(PathSelectors.ant("/holders/**")).build()
        val securityTwo = SecurityContext.builder().securityReferences(listOf(bearerAuthReference()))
            .forPaths(PathSelectors.ant("/accounts/**")).build()
        val securityThree = SecurityContext.builder().securityReferences(listOf(bearerAuthReference()))
            .forPaths(PathSelectors.ant("/address/**")).build()
        val securityFour = SecurityContext.builder().securityReferences(listOf(bearerAuthReference()))
            .forPaths(PathSelectors.ant("/transactions/**")).build()
        return listOf(securityOne, securityTwo, securityThree, securityFour)
    }

    private fun bearerAuthReference(): SecurityReference {
        val authorizationScopes: Array<AuthorizationScope?> = arrayOfNulls(0)
        return SecurityReference(BEARER_AUTH, authorizationScopes)
    }

}