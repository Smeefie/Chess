
package client;

public interface ICommunicator {


    public void start();

    public void stop();
    
    /**
     * Register a property.
     * @param username
     */
    public void register(String username);
    
//    /**
//     * Unregister a property.
//     * @param property
//     */
//    public void unregister(String property);
//
//    /**
//     * Subscribe to a property.
//     * @param property
//     */
//    public void subscribe(String property);
//
//    /**
//     * Unsubscribe from a property.
//     * @param property
//     */
//    public void unsubscribe(String property);
//
//    /**
//     * Update a property by sending a message to all clients
//     * that are subscribed to the property of the message.
//     * @param message the message to be sent
//     */
//    public void update(CommunicatorMessage message);
}
