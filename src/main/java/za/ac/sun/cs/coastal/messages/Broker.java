package za.ac.sun.cs.coastal.messages;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

/**
 * Message broker for COASTAL publish-subscribe implementation.
 */
public class Broker {

	/**
	 * Map from topics (strings) to lists of subscribers.
	 */
	private final Map<String, SubscriberList> subscribers = new HashMap<>();

	/**
	 * A lock to update the map of topics to subscriber lists.
	 */
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);

	/**
	 * Publish a message on a certain topic.
	 * 
	 * @param topic
	 *            the topic to publish
	 * @param message
	 *            the message as a generic object
	 */
	public void publish(String topic, Object message) {
		SubscriberList subscriberList = subscribers.get(topic);
		if (subscriberList != null) {
			subscriberList.publish(message);
		}
	}

	/**
	 * Publish a message on a certain topic that is local to the current thread.
	 * 
	 * @param topic
	 *            the topic to publish
	 * @param message
	 *            the message as a generic object
	 */
	public void publishThread(String topic, Object message) {
		topic = topic + Thread.currentThread().getId();
		SubscriberList subscriberList = subscribers.get(topic);
		if (subscriberList != null) {
			subscriberList.publish(message);
		}
	}
	
	/**
	 * Subscribe to a topic.
	 * 
	 * @param topic
	 *            the topic to subscribe to
	 * @param subscriber
	 *            a callback that will be invoked for all new message on the
	 *            topic
	 */
	public void subscribe(String topic, Consumer<Object> subscriber) {
		SubscriberList subscriberList = subscribers.get(topic);
		if (subscriberList == null) {
			lock.writeLock().lock();
			subscriberList = new SubscriberList();
			subscribers.put(topic, subscriberList);
			lock.writeLock().unlock();
		}
		subscriberList.subscribe(subscriber);
	}
	
	/**
	 * Subscribe to a topic that is local to the current thread.
	 * 
	 * @param topic
	 *            the topic to subscribe to
	 * @param subscriber
	 *            a callback that will be invoked for all new message on the
	 *            topic
	 */
	public void subscribeThread(String topic, Consumer<Object> subscriber) {
		topic = topic + Thread.currentThread().getId();
		SubscriberList subscriberList = subscribers.get(topic);
		if (subscriberList == null) {
			lock.writeLock().lock();
			subscriberList = new SubscriberList();
			subscribers.put(topic, subscriberList);
			lock.writeLock().unlock();
		}
		subscriberList.subscribe(subscriber);
	}

	/**
	 * An encapsulation of a list of subscribers. This sits in an inner class
	 * mainly to simplify the code that locks the access to the list of
	 * subscribers.
	 */
	private static class SubscriberList {

		/**
		 * The list of subscribers.
		 */
		private final List<Consumer<Object>> subscribers = new LinkedList<>();

		/**
		 * A lock to protect the list of subscribers when publishing messages or
		 * updating the list.
		 */
		private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);

		/**
		 * Add a new subscriber to this list.
		 * 
		 * @param subscriber
		 *            the new subscriber
		 */
		public void subscribe(Consumer<Object> subscriber) {
			lock.writeLock().lock();
			subscribers.add(subscriber);
			lock.writeLock().unlock();
		}

		/**
		 * Publish a message to all subscribers on this list.
		 * 
		 * @param message
		 *            the message to publish
		 */
		public void publish(Object message) {
			lock.readLock().lock();
			subscribers.forEach(s -> s.accept(message));
			lock.readLock().unlock();
		}

	}

}
