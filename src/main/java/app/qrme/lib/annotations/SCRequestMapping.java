package app.qrme.lib.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping
public @interface SCRequestMapping {

    @AliasFor(annotation = RequestMapping.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = RequestMapping.class, attribute = "method")
    RequestMethod method() default RequestMethod.POST;

    @AliasFor(annotation = RequestMapping.class, attribute = "headers")
    String[] headers() default {
            "ssousername=a&%Vq*&gf3QpK0zd5&OV*Ha&%*&%",
            "ssopassword=3158969EFC3CC187481C42C9429FA9ED37F610372589148771A26C911747AFA58DD966982BA87664D5561E45B1E244DB8F33A98D29F42A73F258CA37AF17487B"
    };

}