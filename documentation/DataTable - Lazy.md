# DataTable - Lazy

```java
import org.primefaces.model.LazyDataModel;

class ManagedBean {
	private LazyDataModel<User> lazyDataModel;
	
	public LazyDataModel<User> getLazyDataModel() {
		return lazyDataModel;
	}
}
```

## Basic usage

The base class implementation need only the **Service** and the **target type** of objects.

```java
class UsersManagedBean {
	// attribute
	// getter
	
	@Autowired private UserService userService;
	
	@PostConstruct
	private void init() {
		lazyDataModel = new QuerydslLazyDataModel<User>(userService, QUser.user);
	}
}
```

## Overload

The base implementation can be overload.

```java
class UserLazyDataModel extends QuerydslLazyDataModel<User> {
	public UserLazyDataModel(UserService service) {
		super(service);
	}
}
```

Only the **service** must be set, the **target type** can be set dynamically.

```java
class UsersManagedBean {
	// attribute
	// getter
	
	@Autowired private UserService userService;
	
	@PostConstruct
	private void init() {
		lazyDataModel = new UserLazyDataModel(userService);
	}
}
```