# What is Publisher-Subscriber Pattern?

  * Publisher-Subscriber 패턴은 Messaging Pattern 중 하나로, Publisher 가 메시지를 Message Broker (또는 Middleware) 를 통해 Subscriber 에게 전송하는 방식이다.

  * Broker 는  Publisher 와 Subscriber 중간에서 중개자 역할을 하며, 이 둘은 서로 인지하지 않고 소통할 수 있게 한다.

  * 디커플링(결합도 감소) 특성으로 인해 Publisher 는 메시지를 수신자가 누구인지 알지 못해도 메시지를 보낼 수 있고, Subscriber 는 메시지를 보낸 사람이 누구인지 알지 못해도 메시지를 받는다.

  * Publisher 는 메시지를 생성하고 이를 메시지 Broker 에게 전송하며, Broker 는 특정 주제에 대해 등록된 모든 Subscriber 에게 메시지를 전달한다.

  * Message Broker 는 Subscriber 와 그들이 구독한 주제에 대한 레지스트리를 유지하며, 해당 주제에 관심을 표현한 모든 Publisher 에게 메시지를 전달한다.

    * 레지스트리(Registry): Message Broker 가 유지하는 데이터 구조 또는 데이터베이스로, 구독자 정보와 구독자들이 구독한 주제(topic)에 대한 정보를 기록하고 관리하는 곳이다.

    * 구독(Subscription): Subscriber 가 특정 주제(Topic)에 관심이 있음을 표현하고, 해당 주제와 관련된 메시지를 수신하기 위해 Message Broker 에 요청하는 행위다.

#### 특징

  1. Scalability(확장성): Publisher 는 Subscriber 의 정체나 수를 알지 못해도 여러 구독자에게 메시지를 보낼 수 있다.

  2. Decoupling(디커플링): Publisher 와 Subscriber 는 서로를 알 필요가 없기에 메세지를 보내고 받는데 송신자/수신자가 누구인지 모르며, 이 특성 때문에 시스템에 새로운 구성 요소를 추가하거나 제거하기가 더 쉬워진다.

  3. Flexibility(유연성): 구독자는 구독할 주제를 선택할 수 있으며, 퍼블리셔는 메시지를 보낼 주제를 선택할 수 있다.

