package org.kowlintech.utils.command.objects;

import org.kowlintech.utils.command.objects.enums.Category;
import org.kowlintech.utils.command.objects.enums.PermissionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String name();
    Category category();
    String description();
    PermissionType permission() default PermissionType.NONE;
    String args() default "";
}