package com.fang.fangshop.core.common;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.collections.functors.ExceptionClosure;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

 

public class EntityUtils {
	static {
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new ByteConverter(null), Byte.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new ShortConverter(null), Short.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new DateConverter() , Date.class);
    }
 
    public static Map<String, String> objectToHash(Object obj) {
        try {
            Map<String, String> map = Maps.newHashMap();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                if (!property.getName().equals("class")) {
                    if (property.getReadMethod().invoke(obj) != null) {
                        map.put(property.getName(), "" + property.getReadMethod().invoke(obj));
                    }
                }
            }
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    @SuppressWarnings("unchecked")
    private static void hashToObject(Map<?, ?> map, Object obj) {
        try {
        	BeanUtils.populate(obj, (Map)map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    @SuppressWarnings("unchecked")
    public static <T> T hashToObject(Map<?, ?> map, Class c) {
        try {
            Object obj = c.newInstance();
            hashToObject(map, obj);
            return (T)obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
