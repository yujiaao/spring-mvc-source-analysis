package annotation.repeatable;

import java.lang.annotation.Repeatable;

//@Repeatable 注解了 annotation.repeatable.Person。而 @Repeatable 后面括号中的类相当于一个容器注解。
//什么是容器注解呢？就是用来存放其它注解的地方。它本身也是一个注解。
@interface Persons {
    Person[]  value();
}

@Repeatable(Persons.class)
@interface Person{
    String role() default "";
}


@Person(role="artist")
@Person(role="coder")
@Person(role="PM")
public class SuperMan{
}

