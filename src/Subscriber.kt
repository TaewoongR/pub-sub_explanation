interface Subscriber {
    val name: String
    fun receiveMessage(topic: String, message: String){}
}

class BlueTypeSubscriber(override val name: String): Subscriber{
    override fun receiveMessage(topic: String, message: String) {
        println("$name subscriber receive topic: $topic, message: $message")
    }
}

class PurpleSubscriber(override val name: String): Subscriber{
    override fun receiveMessage(topic: String, message: String) {
        println("$name subscriber receive topic: $topic, message: $message")
    }
}