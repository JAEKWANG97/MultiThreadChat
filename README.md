# MultiTreadChat
멀티쓰레드 기반의 채팅 시스템

### 멀티쓰레드란?
- 하나의 프로세스를 다수의 실행 단위로 구분하여 자원을 공유하고 자원의 생성과 관리의 중복성을 최소화하여 수행 능력을 향상시키는 것을 멀티쓰레딩이라고 한다.
- 하나의 프로그램에 동시에 여러개의 일을 수행할수 있도록 해주는 것이다.

##### 프로세스와 쓰레드

프로세스
- 프로세스는 실행 중인 프로그램의 인스턴스입니다.
- 운영 체제에서 프로그램이 실행될 때, 프로그램 코드는 프로세스로 로드되며, 이는 필요한 자원(메모리, 파일 핸들 등)을 할당받아 작업을 수행합니다.

- **특징**:
  - **독립성**: 각 프로세스는 별도의 메모리 공간을 가지며, 다른 프로세스와 메모리를 공유하지 않습니다.
  - **자원 할당 단위**: 프로세스는 CPU 시간, 메모리, 파일 핸들 등 자원을 할당받습니다.
  - **고립성**: 한 프로세스에서 발생한 문제가 다른 프로세스에 영향을 미치지 않습니다.

- **예**: 웹 브라우저, 텍스트 편집기, 터미널 세션 등 각각의 실행 중인 애플리케이션은 독립적인 프로세스입니다.

스레드 (Thread)
스레드는 프로세스 내에서 실행되는 작은 실행 단위입니다. 하나의 프로세스는 여러 개의 스레드를 가질 수 있으며, 이들 스레드는 프로세스 내의 자원을 공유하면서 동시에 실행됩니다.

- **특징**:
  - **경량성**: 스레드는 프로세스보다 훨씬 가볍고 적은 자원으로 생성 및 관리됩니다.
  - **자원 공유**: 같은 프로세스 내의 스레드들은 메모리 공간과 자원을 공유합니다.
  - **병행 실행**: 여러 스레드는 병행하여 실행될 수 있으며, CPU의 멀티코어 기능을 활용하여 성능을 향상시킬 수 있습니다.
  - **협력적 작업**: 여러 스레드가 협력하여 하나의 작업을 보다 효율적으로 수행할 수 있습니다.

- **예**: 웹 브라우저의 탭, 워드 프로세서의 자동 저장 기능, 게임의 그래픽 렌더링과 물리 엔진 등이 각각 별도의 스레드로 실행될 수 있습니다.

> 크롬 브라우저의 경우 탭 하나 하나하나가 하나의 프로세스임

```mermaid
graph TD
    subgraph 클라이언트
    A[클라이언트 1] -->|메시지 전송| B
    C[클라이언트 2] -->|메시지 전송| B
    D[클라이언트 3] -->|메시지 전송| B
    end

    subgraph 서버
    B((서버)) -->|연결 수락| E[클라이언트 핸들러 1]
    B -->|연결 수락| F[클라이언트 핸들러 2]
    B -->|연결 수락| G[클라이언트 핸들러 3]

    E -->|메시지 수신| H{메시지 브로드캐스트}
    F -->|메시지 수신| H
    G -->|메시지 수신| H

    H -->|메시지 전송| I[클라이언트 1]
    H -->|메시지 전송| J[클라이언트 2]
    H -->|메시지 전송| K[클라이언트 3]
    end

    I -->|메시지 수신| A
    J -->|메시지 수신| C
    K -->|메시지 수신| D

    classDef 클라이언트 fill:#f9f,stroke:#333,stroke-width:2px, color:black;
    classDef 서버 fill:#ccf,stroke:#333,stroke-width:2px, color:black;
    class A,C,D 클라이언트;
    class B,E,F,G,H,I,J,K 서버;

```
