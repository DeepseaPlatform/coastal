package za.ac.sun.cs.coastal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.logging.log4j.Logger;

public class Conversion {

	public static Object createInstance(COASTAL coastal, String className) {
		Logger log = coastal.getLog();
		Class<?> clas = loadClass(coastal, className);
		if (clas == null) {
			return null;
		}
		try {
			Constructor<?> constructor = null;
			try {
				constructor = clas.getConstructor(COASTAL.class);
				if (constructor == null) {
					return null;
				}
				return constructor.newInstance(coastal);
			} catch (NoSuchMethodException x) {
				log.info("CONSTRUCTOR NOT FOUND: " + className, x);
			}
		} catch (SecurityException x) {
			log.warn("CONSTRUCTOR SECURITY ERROR: " + className, x);
		} catch (IllegalArgumentException x) {
			log.warn("CONSTRUCTOR ARGUMENT ERROR: " + className, x);
		} catch (InstantiationException x) {
			log.warn("CONSTRUCTOR INSTANTIATION ERROR: " + className, x);
		} catch (IllegalAccessException x) {
			log.warn("CONSTRUCTOR ACCESS ERROR: " + className, x);
		} catch (InvocationTargetException x) {
			log.warn("CONSTRUCTOR INVOCATION ERROR: " + className, x);
		}
		return null;
	}

	public static Class<?> loadClass(COASTAL coastal, String className) {
		Logger log = coastal.getLog();
		Class<?> clas = null;
		if ((className != null) && (className.length() > 0)) {
			try {
				ClassLoader cl = COASTAL.class.getClassLoader();
				clas = cl.loadClass(className);
			} catch (ClassNotFoundException x) {
				log.warn("CLASS NOT FOUND: " + className, x);
			} catch (ExceptionInInitializerError x) {
				log.warn("CLASS NOT FOUND: " + className, x);
			}
		}
		return clas;
	}

	public static long minmax(long value, long min, long max) {
		return (value < min) ? min : (value > max) ? max : value;
	}

	public static long zero(long value, long zero) {
		return (value == 0) ? zero : value;
	}

	public static long limitLong(ImmutableConfiguration config, String key) {
		return 	Conversion.zero(Conversion.minmax(config.getLong(key, Long.MAX_VALUE), 0, Long.MAX_VALUE), Long.MAX_VALUE);
	}
	
	public static int minmax(int value, int min, int max) {
		return (value < min) ? min : (value > max) ? max : value;
	}
	
	public static int zero(int value, int zero) {
		return (value == 0) ? zero : value;
	}

	public static int limitInt(ImmutableConfiguration config, String key) {
		return Conversion.zero(Conversion.minmax(config.getInt(key, Integer.MAX_VALUE), 0, Integer.MAX_VALUE), Integer.MAX_VALUE);
	}
	
	/*
	public static Long getConfigLong(ImmutableConfiguration config, String key, long min, long max) {
		Long value = config.getLong(key, null);
		if (value == null) {
			return null;
		} else if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}

	public static long getConfigLong(ImmutableConfiguration config, String key, long min, long max, long defaultValue) {
		long value = config.getLong(key, defaultValue);
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}
	
	public static long getConfigLong(ImmutableConfiguration config, String key, long min, long max, long defaultValue, long zero) {
		long value = config.getLong(key, defaultValue);
		if (value == 0) {
			value = zero;
		}
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}
	
	public static long getLimitLong(ImmutableConfiguration config, String key) {
		return getConfigLong(config, key, 0, Long.MAX_VALUE, 0, Long.MAX_VALUE);
	}
	
	public static Long getConfigLong(COASTAL coastal, String key, long min, long max) {
		return getConfigLong(coastal.getConfig(), key, min, max);
	}
	
	public static long getConfigLong(COASTAL coastal, String key, long min, long max, long defaultValue) {
		return getConfigLong(coastal.getConfig(), key, min, max, defaultValue);
	}

	public static long getConfigLong(COASTAL coastal, String key, long min, long max, long defaultValue, long zero) {
		return getConfigLong(coastal.getConfig(), key, min, max, defaultValue, zero);
	}
	
	public static long getLimitLong(COASTAL coastal, String key) {
		return getLimitLong(coastal.getConfig(), key);
	}
	
	public static Integer getConfigInt(ImmutableConfiguration config, String key, int min, int max) {
		Integer value = config.getInteger(key, null);
		if (value == null) {
			return null;
		} else if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}
	
	public static int getConfigInt(ImmutableConfiguration config, String key, int min, int max, int defaultValue) {
		int value = config.getInt(key, defaultValue);
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}
	
	public static int getConfigInt(ImmutableConfiguration config, String key, int min, int max, int defaultValue, int zero) {
		int value = config.getInt(key, defaultValue);
		if (value == 0) {
			value = zero;
		}
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		} else {
			return value;
		}
	}
	
	public static int getLimitInt(ImmutableConfiguration config, String key) {
		return getConfigInt(config, key, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
	}
	
	public static Integer getConfigInt(COASTAL coastal, String key, int min, int max) {
		return getConfigInt(coastal.getConfig(), key, min, max);
	}
	
	public static int getConfigInt(COASTAL coastal, String key, int min, int max, int defaultValue) {
		return getConfigInt(coastal.getConfig(), key, min, max, defaultValue);
	}
	
	public static int getConfigInt(COASTAL coastal, String key, int min, int max, int defaultValue, int zero) {
		return getConfigInt(coastal.getConfig(), key, min, max, defaultValue, zero);
	}
	
	public static int getLimitInt(COASTAL coastal, String key) {
		return getLimitInt(coastal.getConfig(), key);
	}
	*/
	
}
