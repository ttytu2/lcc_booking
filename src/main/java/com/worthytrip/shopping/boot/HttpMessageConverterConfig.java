package com.worthytrip.shopping.boot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuzhigang on 3/6/2018 9:46 PM.
 * @version 1.0
 * Description:
 */
@Configuration
public class HttpMessageConverterConfig {

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new MyFastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        SerializerFeature[] serializerFeatures = new SerializerFeature[]{
                //    输出key是包含双引号
//                SerializerFeature.QuoteFieldNames,
                //    是否输出为null的字段,若为null 则显示该字段
//                SerializerFeature.WriteMapNullValue,
                //    数值字段如果为null，则输出为0
                SerializerFeature.WriteNullNumberAsZero,
                //     List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty,
                //    字符类型字段如果为null,输出为"",而非null
                SerializerFeature.WriteNullStringAsEmpty,
                //    Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteNullBooleanAsFalse,
                //    Date的日期转换器
                SerializerFeature.WriteDateUseDateFormat,
                //    循环引用
                SerializerFeature.DisableCircularReferenceDetect,
        };

        fastJsonConfig.setSerializerFeatures(serializerFeatures);
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastConverter.setFastJsonConfig(fastJsonConfig);

        List<MediaType> fastjsonSupportedMediaTypes = new ArrayList<>();
        fastjsonSupportedMediaTypes.add(MediaType.TEXT_PLAIN);
        fastjsonSupportedMediaTypes.add(MediaType.APPLICATION_JSON);
        fastjsonSupportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastjsonSupportedMediaTypes);

        HttpMessageConverter<?> converter = fastConverter;

        return new HttpMessageConverters(converter);
    }

    private static class MyFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
        private static final Logger log = LoggerFactory.getLogger(MyFastJsonHttpMessageConverter.class);

        @Override
        public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException,
                HttpMessageNotReadableException {
            Object obj = super.read(type, contextClass, inputMessage);

            log.info("UHE-REQ-[{}]:{}", type.getTypeName(), JSON.toJSONString(obj));

            return obj;
        }

        @Override
        public void write(Object t, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException,
                HttpMessageNotWritableException {

            log.info("UHE-RESP-[{}]:{}", type.getTypeName(), JSON.toJSONString(t));

            super.write(t, type, contentType, outputMessage);
        }
    }


}

