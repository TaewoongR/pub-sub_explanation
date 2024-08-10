fun main() {

    val broker = Broker()

    val publisher = Publisher(broker)

    val subscriber1 = BlueTypeSubscriber("First")
    val subscriber2 = PurpleSubscriber("Second")
    val subscriber3 = BlueTypeSubscriber("Third")
    val subscriber4 = BlueTypeSubscriber("Fourth")

    broker.subscribe("fun topic", subscriber1)
    broker.subscribe("fun topic", subscriber2)
    broker.subscribe("sad topic", subscriber3)

    publisher.publish("fun topic", "fun story")
    publisher.publish("sad topic", "sad story")
}