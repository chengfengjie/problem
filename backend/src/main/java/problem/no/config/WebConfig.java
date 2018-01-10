package problem.no.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import problem.no.filter.AllFilter;
import problem.no.filter.LogFilter;
import problem.no.interceptor.AuthorizationInterceptor;
import problem.no.manager.UserCacheManager;

@EnableCaching
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Bean
    public UserCacheManager userCacheManager() {
        return new UserCacheManager();
    }

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
    }
}
