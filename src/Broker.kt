class Broker{
    private val registry: MutableMap<String, MutableList<Subscriber>> = mutableMapOf()

    fun subscribe(topic: String, subscriber: Subscriber){
        registry.computeIfAbsent(topic) { mutableListOf() }.add(subscriber)
    }

    fun publish(topic: String, message: String){
        registry[topic]?.forEach { it.receiveMessage(topic, message) }
    }
}