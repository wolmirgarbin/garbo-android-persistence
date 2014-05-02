package br.com.garbo.persistence.android.anotations;

@java.lang.annotation.Documented
@java.lang.annotation.Target(value={java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Entity {
	
	public abstract java.lang.String name() default "";
	
}
