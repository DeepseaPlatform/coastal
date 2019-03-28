package za.ac.sun.cs.coastal.symbolic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the {@link PayloadCarrier} interface. Many of the classes
 * that implement it do not have superclasses, and can therefore extend this
 * implementation.
 */
public abstract class PayloadCarrierImpl implements PayloadCarrier {

	/**
	 * Additional payload information.
	 */
	protected final Map<String, Object> payload = new HashMap<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * za.ac.sun.cs.coastal.symbolic.PayloadCarrier#getPayload(java.lang.String)
	 */
	@Override
	public Object getPayload(String key) {
		return payload.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * za.ac.sun.cs.coastal.symbolic.PayloadCarrier#getPayload(java.lang.String,
	 * int)
	 */
	@Override
	public int getPayload(String key, int defaultValue) {
		Integer value = (Integer) getPayload(key);
		return (value == null) ? defaultValue : value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * za.ac.sun.cs.coastal.symbolic.PayloadCarrier#setPayload(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setPayload(String key, Object value) {
		payload.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.ac.sun.cs.coastal.symbolic.PayloadCarrier#getPayload()
	 */
	@Override
	public Map<String, Object> getPayload() {
		return Collections.unmodifiableMap(payload);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * za.ac.sun.cs.coastal.symbolic.PayloadCarrier#copyPayload(za.ac.sun.cs.coastal
	 * .symbolic.PayloadCarrier)
	 */
	@Override
	public void copyPayload(PayloadCarrier carrier) {
		payload.putAll(carrier.getPayload());
	}

}
