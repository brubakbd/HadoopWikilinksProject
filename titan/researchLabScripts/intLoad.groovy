conf = new BaseConfiguration()
conf.setProperty("gremlin.graph", "com.thinkaurelius.titan.core.TitanFactory")
conf.setProperty("storage.backend", "hbase")
conf.setProperty("storage.hostname", "hadoop-16.csse.rose-hulman.edu")
conf.setProperty("storage.hbase.table", "intGraph")
conf.setProperty("storage.batch-loading", true)
conf.setProperty("storage.hbase.ext.zookeeper.znode.parent","/hbase-unsecure")
conf.setProperty("storage.hbase.ext.hbase.zookeeper.property.clientPort", 2181)
conf.setProperty("cache.db-cache",true)
conf.setProperty("cache.db-cache-clean-wait", 20)
conf.setProperty("cache.db-cache-time", 180000)
conf.setProperty("cache.db-cache-size", 0.5)

graph = TitanFactory.open(conf)

m = graph.openManagement()
m.makePropertyKey("num").dataType(Integer.class).make();
m.commit()

graph.tx().commit();

one = graph.addVertex("num", 1);
zero = graph.addVertex("num", 0);
two = graph.addVertex("num", -2);
three = graph.addVertex("num", 3);
four = graph.addVertex("num", -4);
five = graph.addVertex("num", 5);

graph.tx().commit();
