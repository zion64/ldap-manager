package com.unionia.ldap.config;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
public class ThymeleafConfig {
	
	private static final String LAYOUT_LOCATION = "/WEB-INF/layout/";
	private static final String TEMPLATE_LOCATION = "/WEB-INF/views/";
	private static final String TEMPLATE_MODE = "HTML5";
	private static final String TEMPLATE_SUFFIX = ".html";
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		Set<IDialect> dialects = new HashSet<IDialect>();
		dialects.add(new SpringSecurityDialect());
		dialects.add(new LayoutDialect());
		engine.setAdditionalDialects(dialects);
		LinkedHashSet<ITemplateResolver> templateResolvers = new LinkedHashSet<ITemplateResolver>(2);
		templateResolvers.add(templateResolverServlet());
		templateResolvers.add(layoutTemplateResolverServlet());
		engine.setTemplateResolvers(templateResolvers);
		return engine;
	}

	@Bean
	public ServletContextTemplateResolver layoutTemplateResolverServlet() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix(LAYOUT_LOCATION);
		templateResolver.setSuffix("");
		templateResolver.setTemplateMode(TEMPLATE_MODE);
		templateResolver.setOrder(1);
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Bean
	public ServletContextTemplateResolver templateResolverServlet() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix(TEMPLATE_LOCATION);
		templateResolver.setSuffix(TEMPLATE_SUFFIX);
		templateResolver.setTemplateMode(TEMPLATE_MODE);
		templateResolver.setOrder(2);
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	@Bean
	public ViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setOrder(1);
		resolver.setCharacterEncoding("UTF-8");
		resolver.setCache(false);
		return resolver;
	}
}
