package br.com.garbo.persistence.android.anotations;

//Compiled from Column.java (version 1.6 : 50.0, no super bit)
@java.lang.annotation.Target(value={java.lang.annotation.ElementType.METHOD,java.lang.annotation.ElementType.FIELD})
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public abstract @interface Column {

	// Method descriptor #5 ()Ljava/lang/String;
	public abstract java.lang.String name() default "";
	
	// Method descriptor #9 ()Z
	public abstract boolean unique() default false;
	
	// Method descriptor #9 ()Z
	public abstract boolean nullable() default true;
	
	// Method descriptor #9 ()Z
	public abstract boolean insertable() default true;
	
	// Method descriptor #9 ()Z
	public abstract boolean updatable() default true;
	
	// Method descriptor #5 ()Ljava/lang/String;
	public abstract java.lang.String columnDefinition() default "";
	
	// Method descriptor #5 ()Ljava/lang/String;
	public abstract java.lang.String table() default "";
	
	// Method descriptor #18 ()I
	public abstract int length() default (int) 255;
	
	// Method descriptor #18 ()I
	public abstract int precision() default (int) 0;
	
	// Method descriptor #18 ()I
	public abstract int scale() default (int) 0;

}