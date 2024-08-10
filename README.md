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

#### 코틀린 Pub-Sub 예시

  * ##### Broker 클래스

    * 주제(Topic)와 주제를 구독한 구독자들(Subscribers) 를 저장하는 레지스트리(Registry) 를 구현한다. 
    
      * 맵 형식으로 키(key)는 String 타입인 주제를 저장한다.

      * 맵의 값(value)는 List 타입인 구독자들을 저장한다.
      
      * 신규 구독자들은 계속 늘어나며, 주제 또한 추가되기에 레지스트리의 맵 타입과 맵의 값 타입은 mutable 로 한다.

    * Broker 에서 Publisher 와 Subscriber 가 수행하는 함수를 정의한다.
    
    * Subscriber 는 Publisher 에게 직접적으로 구독하는(subscribe) 것이 아닌 Broker 를 통해 구독하므로, 이 클래스에 `subscribe` 함수가 존재한다.
    
      * 어떤 주제로 어떤 구독자가 구독할건지 알아야 하기에 이 함수의 파라미터에는 주제와 구독자 타입 (String, Subscriber) 가 필요하다.
      
      * 파라미터의 주제와 구독자에 따라 레지스트리에 추가 저장해준다.
      
      * 맵 객체는 `computeIfAbsent` 함수를 사용하여 파라미터에는 맵의 키(key)를 입력하여 맵에 현재 이 키가 존재하는지 판단하고, 
      <br>존재하지 않는다면 대괄호 내부에 정의한 값(value)를 만든다. 그 후 존재 유무를 떠나 공통적으로 value에 대한 작업을 작성한다.
      <br>예제에서는 맵의 값이 mutableList 이기에 subscribe 함수의 파라미터인 Subscriber 를 맵의 값에 추가했다.
    
    * Publisher 는 Subscriber 에게 직접적으로 배포하는(publish) 것이 아닌 Broker 를 통해 배포하므로, 이 클래스에 `publish` 함수가 존재한다.
    
      * 어느 주제(topic)의 메세지를 보낼지 정하기 위해 파라미터로 주제(topic: String)와 메세지(message: String)를 파라미터로 한다.
      
      * 레지스트리인 맵에서 파라미터 topic 을 통해 그 주제가 있는지 확인하고 있다면 주제를 구독한 구독자들에 접근하여 Subscriber 클래스에 정의된
      <br>함수를 호출하여 그 함수에 메세지를 전달한다.

* ##### Publisher 클래스

  * constructor 에 broker 를 받도록 하였다. 이는 publisher 가 어느 broker 를 통해 subscriber 에게 주제에 따른 메세지를 보낼지 정한다.
  
  * 멤버 함수로는 publish 가 존재하며 subscriber 에게 직접적으로 배포하는 것이 아니기 때문에 constructor 에서 감지한 broker 를 통해 broker 의 멤버함수인 publish 를 호출하여 간접적으로 메세지를 보낸다.

* ##### Subscriber 인터페이스와 이를 상속받는 클래스

  * 인터페이스로 선언한 이유는 pub-sub 패턴의 정의대로 다양한 subscriber 들이 존재하는 특성을 따라 인터페이스로 선언한 후 다른 여러 클래스가 이 인터페이스를 상속받로고 한다.
  
  * 인터페이스 내부에는 subscriber 의 행위인 receiveMessage 함수가 존재한다.
  
    * 이는 subscriber 가 직접적으로 행하는 것이 아닌 broker 가 메세지를 전달 함으로서 행해지는 것이다.
    
    * broker 의 publish 함수를 다시보면 레지스트리를 통해 subscriber 가 가진 receiveMessage 를 호출하여 broker 가 보내는 방식이다.
    
    * subscriber 는 메세지를 받으면 메세지를 출력받는다.

* ##### Main 함수

  * broker, publisher, subscriber`s` 객체들을 생성하였다.

  * broker 는 publisher 와 subscriber 들을 전부알고 있다.
  
    * publisher 인스턴스 생성시 broker 를 생성자(constructor)에서 요구한다.
    
    * broker 내부에 존재하는 레지스트리는 subscriber 들의 정보를 안다.
  
  * subscriber 는 broker 를 통해 구독이 진행되는 증거로 broker 객체를 통해 subscribe 함수를 호출한다.
  
  * publisher 는 broker 를 통해 배포가 진행되는 증거는 메인에서는 확인이 어렵지만 이미 Publisher 클래스 내부의 멤버함수 publish 는 broker 객체를 통해 publish 함수를 호출한다.
