package org.kowlintech.utils.command.objects;

import org.kowlintech.utils.command.objects.enums.Category;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String name();
    Category getCategory();
    String description();
    String args() default "";
}