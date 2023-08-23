# Gatling Test Plan for Flask API Emulator

This repository contains Gatling test scenarios designed to test the [Flask API emulator](https://github.com/stsolovey/app_plug_for_jmeter20230811).

## Overview

These tests are written to be used with a pure Gatling setup, without the overhead of tools like SBT or Maven. The focus here is to keep things simple and straightforward.

### Configuration

Before running the test scenarios, ensure you've configured the target IP (host) correctly in the project.

## Test Plan Descriptions

Each test plan represents a different approach or methodology in load testing. Below are brief descriptions of each test plan and its intended purpose, with examples of Register Request method:

- **RampUp Load**: Gradually increases the number of users over a set duration. It's useful for understanding how the system copes with growing numbers of users over time.

```scala
//=====RampUp Load=====
rampUsersPerSec(1)
.to(500)
.during(1.hour)
```
  
- **Stepping Load**: Incrementally increases the number of users at set intervals. This is great for understanding how the system performs under progressively increasing load.

```scala
// =====Stepping Load=====
incrementUsersPerSec(15)
.times(4)
.eachLevelLasting(15.minutes)
.startingFrom(15)
//=====Stepping Load======
constantUsersPerSec(5) during (20 minutes),
constantUsersPerSec(30) during (20 minutes),
constantUsersPerSec(60) during (20 minutes)
//=====Stepping Load======
incrementConcurrentUsers(50)
.times(3)
.eachLevelLasting(20.minutes)
.startingFrom(1)
```
- **Spike Testing**: Introduces a sudden and large number of users to the system to see how it reacts to sudden spikes in demand.
```scala
//=====Spike Testing======
atOnceUsers(3000)
```
- **Constant Load**: A fixed number of users are active for a set duration. Useful for testing how the system behaves under a constant demand.
```scala
//=====Constant Load======
constantUsersPerSec(100).during(1.hour)
```
- **Ramp Down**: Gradually reduces the number of users over a set duration. It can help identify how the system manages decreasing numbers of users and potentially freeing up resources.
```scala
//=====Ramp Down=======
incrementConcurrentUsers(-300)
.times(5)
.eachLevelLasting(1.hour)
.startingFrom(1505)
```

## Conclusion

This Gatling project is designed to test the Flask API emulator. By leveraging different test strategies, we aim to understand the performance, reliability, and potential bottlenecks in the system.

---

You can use this as a base and adjust or extend it further based on any additional details you might want to add.