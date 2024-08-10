class Publisher(private val broker: Broker) {

    fun publish(topic: String, message: String){
        println("Publisher send topic: $topic, message: $message")
        broker.publish(topic, message)
    }
}