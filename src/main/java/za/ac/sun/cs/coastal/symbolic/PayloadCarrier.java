/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019-2020, Computer Science, Stellenbosch University.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
