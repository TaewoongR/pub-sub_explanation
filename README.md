# What is the Publisher-Subscriber Pattern?

* The Publisher-Subscriber pattern is one of the Messaging Patterns, where a Publisher sends messages to Subscribers via a Message Broker (or Middleware).

* The Broker acts as an intermediary between the Publisher and Subscriber, allowing them to communicate without being aware of each other.

* Due to the decoupling characteristic, the Publisher can send messages without knowing who the recipients are, and the Subscriber can receive messages without knowing who sent them.

* The Publisher generates a message and sends it to the Message Broker, which then delivers the message to all Subscribers registered for a specific topic.

* The Message Broker maintains a registry of Subscribers and the topics they have subscribed to, and delivers messages to all Publishers who have expressed interest in those topics.

    * **Registry**: A data structure or database maintained by the Message Broker, where information about Subscribers and the topics they are subscribed to is recorded and managed.

    * **Subscription**: The act where a Subscriber expresses interest in a specific topic and requests the Message Broker to receive messages related to that topic.

#### Characteristics

1. **Scalability**: The Publisher can send messages to multiple Subscribers without knowing their identities or the number of Subscribers.

2. **Decoupling**: Since the Publisher and Subscriber do not need to know each other, they can send and receive messages without knowing the identity of the sender/receiver. This characteristic makes it easier to add or remove components in the system.

3. **Flexibility**: The Subscriber can choose which topics to subscribe to, and the Publisher can choose which topics to send messages on.

#### Understand Pub-Sub Pattern with Kotlin code Example

* ##### Broker Class

    * Implements a registry that stores topics and the Subscribers who are subscribed to those topics.

        * A map is used where the key (of type `String`) stores the topic.

        * The map’s value stores the list of Subscribers.

        * Since the number of Subscribers and topics can increase, both the map and its values are mutable.

    * Defines functions that the Publisher and Subscriber will execute.

    * Since Subscribers subscribe not directly to the Publisher but through the Broker, the `subscribe` function is part of this class.

        * To know which topic a Subscriber will subscribe to, the parameters of this function include the topic and the Subscriber (types: `String` and `Subscriber`).

        * Adds the topic and Subscriber to the registry based on the provided parameters.

        * The map uses the `computeIfAbsent` function, where the map key is provided as a parameter to check if the key already exists. If it does not exist, the value defined within the brackets is created. Regardless of the existence, the value is modified. In this example, since the map value is a `mutableList`, the `Subscriber` parameter is added to the map’s value.

    * The Publisher doesn’t directly distribute messages to Subscribers but does so via the Broker, so the `publish` function is part of this class.

        * To determine which topic to send a message to, the function parameters include the topic (`topic: String`) and the message (`message: String`).

        * Checks if the topic exists in the registry map, and if it does, accesses the Subscribers registered for that topic and calls the function defined in the Subscriber class, passing the message to that function.

* ##### Publisher Class

    * The constructor accepts a `broker`, which allows the Publisher to decide through which Broker the message should be sent to Subscribers based on the topic.

    * Contains a member function called `publish`, which does not directly distribute messages to Subscribers but calls the `publish` function of the Broker detected in the constructor, thus sending messages indirectly.

* ##### Subscriber Interface and Inheriting Classes

    * The interface is declared to follow the characteristic of the pub-sub pattern that there can be various types of Subscribers. After declaring the interface, other classes inherit from it.

    * Within the interface, the `receiveMessage` function exists, representing the action of a Subscriber.

        * This is not directly performed by the Subscriber but is triggered when the Broker sends a message.

        * Looking back at the Broker’s `publish` function, it calls the `receiveMessage` function owned by the Subscriber through the registry, allowing the Broker to send messages.

        * When the Subscriber receives a message, it prints the message.

* ##### Main Function

    * Creates objects for the `broker`, `publisher`, and `subscribers`.

    * The Broker is aware of all Publishers and Subscribers.

        * When the Publisher instance is created, it requires the `broker` in the constructor.

        * The registry within the Broker is aware of the Subscribers’ information.

    * The fact that the subscription is processed through the Broker is evidenced by the call to the `subscribe` function through the Broker object.

    * Although it’s not directly evident in the main function, the Publisher distributes messages through the Broker, as the `publish` function in the Publisher class internally calls the `publish` function of the Broker object.