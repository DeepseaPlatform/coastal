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
