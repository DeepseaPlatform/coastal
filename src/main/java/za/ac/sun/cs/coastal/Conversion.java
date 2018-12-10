package za.ac.sun.cs.coastal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;
import org.apache.commons.configuration2.tree.NodeCombiner;
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

	public static class ConfigCombiner extends NodeCombiner {

	    private final Map<String, String> joinNodes;

	    public ConfigCombiner() {
	    	joinNodes = new HashMap<>();
		}

	    public void addJoinNode(final String nodeName, final String key) {
	    	joinNodes.put(nodeName, key);
	    }

	    public String getJoinKey(final ImmutableNode node) {
	        return joinNodes.get(node.getNodeName());
	    }

	    @Override
		public ImmutableNode combine(final ImmutableNode node1, final ImmutableNode node2) {
			final ImmutableNode.Builder result = new ImmutableNode.Builder();
			result.name(node1.getNodeName());
			String joinKey = getJoinKey(node1);
			if (joinKey != null) {
				final Map<Object, ImmutableNode> children2 = new HashMap<>();
				for (final ImmutableNode child2 : node2.getChildren()) {
					Map<String, Object> attrs2 = child2.getAttributes();
					if (!attrs2.containsKey(joinKey)) {
						result.addChild(child2);
					} else {
						children2.put(attrs2.get(joinKey), child2);
					}
				}
				for (final ImmutableNode child1 : node1.getChildren()) {
					Map<String, Object> attrs1 = child1.getAttributes();
					if (!attrs1.containsKey(joinKey)) {
						result.addChild(child1);
					} else if (children2.containsKey(attrs1.get(joinKey))) {
						Object joinAttr = attrs1.get(joinKey);
						result.addChild(combine(child1, children2.get(joinAttr)));
						children2.remove(joinAttr);
					} else {
						result.addChild(child1);
					}
				}
				for (final ImmutableNode child2 : children2.values()) {
					result.addChild(child2);
				}
			} else {
				for (final ImmutableNode child : node1.getChildren()) {
					final ImmutableNode child2 = canCombine(node1, node2, child);
					if (child2 != null) {
						result.addChild(combine(child, child2));
					} else {
						result.addChild(child);
					}
				}
				for (final ImmutableNode child : node2.getChildren()) {
					if (HANDLER.getChildrenCount(node1, child.getNodeName()) < 1) {
						result.addChild(child);
					}
				}
			}
			addAttributes(result, node1, node2);
			result.value((node1.getValue() != null) ? node1.getValue() : node2.getValue());
			return result.create();
		}

		protected void addAttributes(final ImmutableNode.Builder result, final ImmutableNode node1,
				final ImmutableNode node2) {
			result.addAttributes(node1.getAttributes());
			for (final String attr : node2.getAttributes().keySet()) {
				if (!node1.getAttributes().containsKey(attr)) {
					result.addAttribute(attr, HANDLER.getAttributeValue(node2, attr));
				}
			}
		}

		protected ImmutableNode canCombine(final ImmutableNode node1, final ImmutableNode node2,
				final ImmutableNode child) {
			if (HANDLER.getChildrenCount(node2, child.getNodeName()) == 1
					&& HANDLER.getChildrenCount(node1, child.getNodeName()) == 1 && !isListNode(child)) {
				return HANDLER.getChildren(node2, child.getNodeName()).get(0);
			}
			return null;
		}

	}

}
