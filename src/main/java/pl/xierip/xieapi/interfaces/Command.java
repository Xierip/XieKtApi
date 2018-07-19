package pl.xierip.xieapi.interfaces;

import pl.xierip.xieapi.enums.SenderType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Xierip
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String[] aliases() default {""};

    String description() default "";

    String name();

    String permission() default "";

    String permissionMessage() default "&cNie posiadasz uprawnien do tej komendy! &f({PERMISSION})";

    SenderType senderType() default SenderType.ALL;
}
