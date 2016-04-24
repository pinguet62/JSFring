# Data Access Object

## Interfaces

```java
interface FooDao extends CommonRepository, FooDaoCustom {
	// Spring Batch methods
	List<T> findByField1(...);
	T findByField1AndField2(...);
}
```

```java
interface FooDaoCustom {
	// Application methods
	void doSomething(...);
}
```

```java
class FooDaoImpl implements FooDaoCustom {
	// Application methods implementations
	@Override
	void doSomething(...) { ... }
}
```