Dominion
========

The goal is to be able to write JUnit tests to validate the quality of
the JUnit tests.

This specific implementation counts the number of calls to:
Mockito.doCallRealMethod()

(Because partial mocking is a smell of "test after" practices)
