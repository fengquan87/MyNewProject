package cn.com.example.fengquan.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fengquan on 2019/5/15.
 * Description: View注解Annotation
 * @Taget(ElementType.FIELD) daia
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    // -->@ViewById(R.id.xx)
    int value();
}
