package cn.iocoder.yudao.module.yw.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class MultipartConfig {

    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    public StandardServletMultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new DoNotCleanupMultipartResolver();
        multipartResolver.setResolveLazily(false);
        return multipartResolver;
    }

    public static class DoNotCleanupMultipartResolver extends StandardServletMultipartResolver {
        @Override
        public void cleanupMultipart(MultipartHttpServletRequest request) {
            try{
                if(request instanceof StandardMultipartHttpServletRequest){
                    ((StandardMultipartHttpServletRequest) request).getRequest().getParts().clear();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}

