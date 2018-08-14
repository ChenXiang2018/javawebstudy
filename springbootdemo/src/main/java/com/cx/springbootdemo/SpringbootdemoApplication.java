package com.cx.springbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.cx.springbootdemo.mapper")
@EnableCaching
public class SpringbootdemoApplication {

//	@Bean
//	public HttpMessageConverters fastJsonConfigure(){
//		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//		FastJsonConfig fastJsonConfig = new FastJsonConfig();
//		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//		//日期格式化
//		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
//		converter.setFastJsonConfig(fastJsonConfig);
//		return new HttpMessageConverters(converter);
//	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootdemoApplication.class, args);
	}
}
