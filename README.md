Learned how to use `Condition` class from https://www.byteslounge.com/tutorials/lock-conditions-in-java
Learned how to use `ReentrantLock` from https://jakob.jenkov.com/tutorials/java-util-concurrent/lock.html

Learned about the BlockingQueue from https://www.baeldung.com/java-blocking-queue

This thread serves the planes. Once the planes have finished their tasks. The thread will end. Which is why it is set as a daemon thread. Source: https://www.baeldung.com/java-daemon-thread

# Unused
Semaphore general definition - https://www.baeldung.com/cs/semaphore
Semaphore in Java - https://www.baeldung.com/java-semaphore

# Important
Solves the unable to land 4th plane problem.
Context: There are 4 planes, and 3 gates. 3 planes occupy 3 gates. 1 plane is done and leaves, leaving 1 empty gate. But, the 4th plane doesn't land.

```java
can_depart.signalAll(); // tells other planes that are waiting to depart that they can.
can_land.signalAll(); // tells planes waiting to land that they can land as at this point
                     // a gate and a runway is free.
```

---

1. Look into whether an ATC is required.
2. Ask ChatGPT h