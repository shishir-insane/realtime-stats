# RealTime Statistics API

The crux of this realtime stats app is the implementation of a concurrent access linked queue which serves as the data store for the transaction data. It stores data only for the defined period which is actually the refresh interval of the dashboard and gets rid of the data which is no longer required by the dashboard. 

It uses an bounded thread-safe queue and derives properties from  ConcurrentLinkedQueue which employs Simple, Fast, and Practical Non-Blocking and Blocking Concurrent Queue Algorithms by Maged M. Michael and Michael L. Scott.

The type of elements of this class is defined in AggregatedSalesData.

**Prerequisite:**
- Java 1.8+
- Maven 3.3+

**Build:**

	mvn clean install
	
**Run:**

	mvn spring-boot:run
