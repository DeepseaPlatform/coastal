package za.ac.sun.cs.coastal.symbolic;

import java.util.Map;

public interface PayloadCarrier {

	/**
	 * Return the value of a payload field.
	 * 
	 * @param key key for the payload field
	 * @return value of the payload field 
	 */
	Object getPayload(String key);
	
	/**
	 * Return the value of an integer payload field.
	 * 
	 * @param key key for the payload field
	 * @param defaultValue value to use if field is not present
	 * @return value of the payload field 
	 */
	int getPayload(String key, int defaultValue);
	
	/**
	 * Set the value of a payload field.
	 * 
	 * @param key key for the payload field
	 * @param value new value for the payload field
	 */
	void setPayload(String key, Object value);

	/**
	 * Return the payload of a carrier.
	 * 
	 * @return payload mapping
	 */
	Map<String, Object> getPayload();

	/**
	 * Set several payload field by copying them from another carrier.
	 * 
	 * @param carrier source for payload
	 */
	void copyPayload(PayloadCarrier carrier);

}
