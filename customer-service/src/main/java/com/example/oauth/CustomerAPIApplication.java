package com.example.oauth;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class CustomerAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerAPIApplication.class, args);
	}

	@Bean
	public OpenAPI openAPI() {
		//be sure to set the redirect url as allowed in IDP.  http://localhost:8000/login/oauth2/code/cognito
		return new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("spring_oauth", new SecurityScheme()
								.type(SecurityScheme.Type.OAUTH2)
								.description("Oauth2 flow")
								.flows(new OAuthFlows()
										.authorizationCode(new OAuthFlow()
												.authorizationUrl("https://ellin.auth.us-east-1.amazoncognito.com/login")
												.refreshUrl("https://ellin.auth.us-east-1.amazoncognito.com/oauth2" + "/token")
												.tokenUrl("https://ellin.auth.us-east-1.amazoncognito.com/oauth2" + "/token")
												.scopes(new Scopes().addString("openid","openid").addString("email","email"))
										)))
						.addSecuritySchemes("bearer-token", new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.description("Bearer Token")
								.scheme("bearer")
								.bearerFormat("JWT")))

				.security(Arrays.asList(
						new SecurityRequirement().addList("spring_oauth"),new SecurityRequirement().addList("bearer-token")))

				.info(new Info()
						.title("Your API")
						.description("Your API")
						.license(new License().name("Don't use it"))
						.version("8")
						.contact(new Contact()
								.name("Example Inc")
								.url("https://www.example.com/")
								.email("developer@example.com")));
	}


}
