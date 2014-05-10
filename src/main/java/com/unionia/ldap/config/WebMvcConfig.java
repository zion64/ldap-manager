package com.unionia.ldap.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@ComponentScan(basePackages = "com.unionia.ldap", useDefaultFilters = true)
public class WebMvcConfig extends WebMvcConfigurationSupport {

	private static final String MESSAGE_SOURCE_BASE = "messages";

	private static final String RESOURCES_HANDLER = "/resources/";
	private static final String RESOURCES_LOCATION = RESOURCES_HANDLER + "**";

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(MESSAGE_SOURCE_BASE);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setCacheSeconds(5);
		return messageSource;
	}

	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();
		requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
		requestMappingHandlerMapping.setUseTrailingSlashMatch(false);
		return requestMappingHandlerMapping;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION);
	}
	
	@Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}