# caffeine 介绍

## 1. cache 类型

### 1.1 cache

####  

1) 最普通的一种缓存，无需指定加载方式，需要手动调用 **put()** 方法进行加载。需要注意的是，**put()**
   方法对于已经存在的key将进行覆盖，这点和Map保持一致。
2) 在获取缓存值时，如果想要在缓存值不存在时，原子性的将值写入缓存，则可以调用 `get(key, k -> value)` 方法，该方法避免写入竞争。
3) 调用 **invalidate()** 方法，将手动移除缓存。
4) 多线程情况下，当使用 `get(key, k -> value)`
   时，如果有另一个线程同时调用本方法进行竞争，则后一线程会被阻塞，直到前一线程更新缓存完成；而若另一线程调用 **
   getIfPresent()** 方法，则会立即返回null，不会被阻塞。

### 1.2 Loading cache

####  

1) LoadingCache 是一种自动加载的缓存。
2) 其和普通缓存不同的地方在于，当缓存不存在/缓存已过期时，若调用get()方法，则会自动调用`CacheLoader.load()`方法加载最新值。
3) 调用 **getAll()** 方法将遍历所有的 **key** 调用 **get()** ，除非实现了 **CacheLoader.load()** 方法。
4) 使用 **LoadingCache** 时，需要指定 **CacheLoader** ，并实现其中的 **load()** 方法供缓存丢失时自动加载。
5) 多线程情况下，当两个线程同时调用 **get()** 方法，则后一个线程将被阻塞，直至前一个线程更新缓存完成。

```
LoadingCache<String, String> cache=Caffeine.newBuilder()
        .build(new CacheLoader<String, String>(){
@Override
public String load(@NonNull String k)throws Exception{
        return"456";
        }

@Override
// 如果需要批量加载
public @NonNull Map<String, String> loadAll(@NonNull Iterable<?extends String> keys)throws Exception{
        return null;
        }
        });

        cache.getIfPresent("123"); // null
        cache.get("123");          // 456
        cache.getAll(keys);        // Map<String, String>
```

### 1.3 Async Cache

####
1) AsyncCache 是 Cache 的一个变体，其响应结果是`CompletableFuture`，通过这种方式，AsyncCache 对异步编程模式进行了适配。默认情况下，缓存计算使用ForkJoinPool.commonPool()作为线程池，如果想要指定线程池，可以覆盖并实现`Caffeine.executor(Executor)`方法
2) `Synchronus()` 提供了阻塞直至异步缓存生成完毕的能力，它将以cache进行返回。
3) 多线程情况下，当两个线程同时调用 `get(key, k -> value)`，它将会返回同一个 **CompletableFuture** 对象。由于返回结果本身不进行阻塞，可以根据业务设计自行选择阻塞等待或者非阻塞。
4) 
```
AsyncCache<String, String> cache = Caffeine.newBuilder().buildAsync();

CompletableFuture<String> completableFuture = cache.get(key, k -> "456");

completableFuture.get(); // 阻塞，直至缓存更新完成
```

### 1.4 Async Loading Cache

####
1) 显然是 Loading Cache 和 Async Cache 的功能组合。 AsyncLoadingCache 支持以异步的方式，对缓存进行自动加载。
2) 类似 Loading Cache， 同样需要指定 CacheLoader , 并实现其中的 `load()` 方法供缓存缺失时自动加载，该方法将在 `ForkJoinPool.commonPool()` 线程池中提交。同样的，如果想要指定线程池，可以覆盖实现 `Caffeine.executor(Executor)` 方法 
```
AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
        .buildAsync(new AsyncCacheLoader<String, String>() {
            @Override
            // 自定义线程池加载
            public @NonNull CompletableFuture<String> asyncLoad(@NonNull String key, @NonNull Executor executor) {
                return null;
            }
        })
        .buildAsync(new CacheLoader<String, String>() {
            @Override
            // OR，使用默认线程池加载（二者选其一）
            public String load(@NonNull String key) throws Exception {
                return "456";
            }
        });

CompletableFuture<String> completableFuture = cache.get(key); // CompletableFuture<String>
completableFuture.get(); // 阻塞，直至缓存更新完成
```

## 2. 驱逐策略

驱逐策略在创建缓存的时候进行指定。常用的有 **基于容量的驱逐** 和 **基于时间的驱逐** 。  
1) 基于容量的驱逐：需要指定缓存容量的最大值。当缓存容量达到最大时，Caffeine 将通过 `LRU策略` 对缓存进行淘汰
2) 基于时间的驱逐：可以设置 在最后访问/写入一个缓存经过一段时间 后，自动进行淘汰  

驱逐策略可以组合使用，任意驱逐策略生效后，该缓存条目将被驱逐。
```
Caffeine.newBuilder().maximumSize(1000).build(); // 创建一个最大容量为1000的缓存
Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.HOURS).build(); // 创建一个写入10h后过期的缓存
Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).build(); // 创建一个访问1h后过期的缓存
```

## 3. 刷新机制

&emsp;&emsp; 试想这么一种情况：当缓存运行过程中，有些缓存值需要我们定期进行刷新，以确保信息可以正确被同步到缓存中来。  
&emsp;&emsp; 我们当然可以使用基于时间的驱逐策略 `expireAfterWrite()`，但带来的问题是一旦缓存过期，下次重新生成缓存时将使得调用线程处于阻塞状态。  
&emsp;&emsp; 而使用刷新机制 `refreshAfterWrite()` ，Caffeine 将在 key 允许刷新后的首次访问，立即返回旧值，同时异步的对缓存值进行刷新，这使得调用方不至于因为缓存驱逐而被阻塞。  
&emsp;&emsp; 需要注意的是，刷新机制只支持 LoadingCache 和 AsyncLoadingCache  
通过`覆写CacheLoader.reload()`方法，将在刷新时使得旧缓存值参与其中。
```
Caffeine.newBuilder().refreshAfterWrite(10, TimeUnit.MINUTES).build(k -> create(k));
```

## 4. 统计

Caffeine 内置了数据收集功能，通过 `Caffeine.recordStats()` 方法，可以打开数据收集。 这样 `Cache.stats()` 方法将会返回当前缓存的一些统计指标。  
例如：
* `hitRate()`: 查询缓存命中率
* `evictionCount()`: 被驱逐的缓存数量
* `averageLoadPenalty()`: 新值被载入的平均耗时
```
Cache<String, String> cache = Caffeine.newBuilder().recordStats().build();
cache.stats(); // 获取统计指标
```
