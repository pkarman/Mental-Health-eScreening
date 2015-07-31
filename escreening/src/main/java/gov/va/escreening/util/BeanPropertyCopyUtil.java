package gov.va.escreening.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by pouncilt on 8/1/14.
 */
public class BeanPropertyCopyUtil {
    public static void copyBeanProperties(
            final Object source,
            final Object target,
            final Iterable<String> properties){

        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        for(final String propertyName : properties){
            trg.setPropertyValue(
                    propertyName,
                    src.getPropertyValue(propertyName)
            );
        }
    }

    public static void copyBeanProperties(
            final Object source,
            final Object target,
            final Collection<String> includes){

        final Collection<String> excludes = new ArrayList<String>();
        final PropertyDescriptor[] propertyDescriptors =
                BeanUtils.getPropertyDescriptors(source.getClass());
        for(final PropertyDescriptor propertyDescriptor : propertyDescriptors){
            String propName = propertyDescriptor.getName();
            if(!includes.contains(propName)){
                excludes.add(propName);
            }
        }
        BeanUtils.copyProperties(source, target, excludes.toArray(new String[excludes.size()]));
    }
}
