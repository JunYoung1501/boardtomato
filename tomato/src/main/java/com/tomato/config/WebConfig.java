package com.tomato.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		if (!registry.hasMappingForPattern("/webjars/**")) {

			registry.addResourceHandler("/webjars/**")
					.addResourceLocations("classpath:/META-INF/resources/webjars/")
					.setCachePeriod(20);
		}

		registry.addResourceHandler("/bootstrap/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/");
		
		registry.addResourceHandler("/bootstrap_icons/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap-icons/");

		
		registry.addResourceHandler("/jquery/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/jquery/");
		
		registry.addResourceHandler("/summernote/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/summernote/");
		
		registry.addResourceHandler("/images/**")
				.addResourceLocations("classpath:/static/images/");
		
		registry.addResourceHandler("/board/**")
			    .addResourceLocations("classpath:/board/**");
		
		registry.addResourceHandler("/ckeditor4/**")
			    .addResourceLocations("classpath:/static/ckeditor4/");
		
		//D:\filestate
		registry.addResourceHandler("/upload/**")
				.addResourceLocations("file:///D:/filestate/");

		registry.addResourceHandler("/upload_image/**")
				.addResourceLocations("file:///D:/filestate/image/");
		
	}
}
