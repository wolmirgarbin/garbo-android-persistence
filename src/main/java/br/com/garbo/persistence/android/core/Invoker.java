package br.com.garbo.persistence.android.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import br.com.garbo.persistence.android.GarboPersistenceException;
import br.com.garbo.persistence.android.resources.ResourceBundleFactory;


public final class Invoker {

	private final ResourceBundle bundle = ResourceBundleFactory.get();
	
	private final Object receiver;
	private final Method method;
	
	public Invoker(Object receiver, Method method) {
		this.receiver = receiver;
		this.method = method;
	}
	
	public Object withParams(Object... params) {
		method.setAccessible(true);
		try {
			return method.invoke(receiver, params);
		} catch(InvocationTargetException e) {
			throw new GarboPersistenceException(MessageFormat.format(bundle.getString("invocation_failed"), method.getName(), e.getMessage()));
		} catch(IllegalAccessException ie) {
			throw new GarboPersistenceException(MessageFormat.format(bundle.getString("invocation_failed"), method.getName(), ie.getMessage()));
		}
	}
	
	public Object withoutParams() {
		return withParams(new Object[] {});
	}
}