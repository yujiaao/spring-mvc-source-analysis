import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController(value="mark")
public class AnnotationTest {
    public static void main(String[] args) throws ClassNotFoundException {

        showAnnotation(AnnotationTest.class);
        showAnnotation(B.class);

        //System.out.println(a.getClass().toGenericString());
        //输出 public final class com.sun.proxy.$Proxy1
        //https://stackoverflow.com/questions/19633534/what-is-com-sun-proxy-proxy

        //System.out.println(a.annotationType());
        //B: interface java.lang.Deprecated
        //A: interface org.springframework.web.bind.annotation.RestController

    }


    private static String dump( Annotation[] A){
        return Arrays.asList(A).stream().map(a -> a.toString()).collect(Collectors.joining("\n"));
    }
    private static void showAnnotation(Class<?> cls) {
        System.out.println(cls.getCanonicalName());
        System.out.println("is Controller annotation present = "+cls.isAnnotationPresent(Controller.class));
        System.out.println("is controller annotation associated ="+dump(cls.getAnnotationsByType(Controller.class)));

        // 注意： AnnotationUtils.findAnnotation 能找到注解的注解，但不能传递 Value值
        System.out.println("is controller annotation in super annotation="+ AnnotationUtils.findAnnotation(cls, Controller.class));
        System.out.println("is component annotation in super annotation="+ AnnotationUtils.findAnnotation(cls, Component.class));
        System.out.println("is Service annotation in super annotation="+ AnnotationUtils.findAnnotation(cls, Service.class));

        Annotation[] list = cls.getDeclaredAnnotations(); // get all annotation

        for (Annotation a: list
             ) {

            System.out.println(a.annotationType());
            System.out.println("Controller is assignable from = "+(Controller.class.isAssignableFrom(a.annotationType())));


        }
    }
}

@Deprecated //RetentionPolicy.RUNTIME
@SuppressWarnings(value = "unchecked") //RetentionPolicy.SOURCE 不会看见
class B {

}