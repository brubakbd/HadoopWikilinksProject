conf = new BaseConfiguration()
conf.setProperty("gremlin.graph", "com.thinkaurelius.titan.core.TitanFactory")
conf.setProperty("storage.backend", "hbase")
conf.setProperty("storage.hostname", "hadoop-16.csse.rose-hulman.edu")
conf.setProperty("storage.batch-loading", true)
conf.setProperty("storage.hbase.ext.zookeeper.znode.parent","/hbase-unsecure")
conf.setProperty("storage.hbase.ext.hbase.zookeeper.property.clientPort", 2181)
conf.setProperty("cache.db-cache",true)
conf.setProperty("cache.db-cache-clean-wait", 20)
conf.setProperty("cache.db-cache-time", 180000)
conf.setProperty("cache.db-cache-size", 0.5)
conf.setProperty("storage.hbase.table", "wikilinksGraph")

graph = TitanFactory.open(conf)

batchSize = 1000000
counter = new java.util.concurrent.atomic.AtomicLong()
cache = [:]

mutate = { ->
  if (0 == counter.incrementAndGet() % batchSize) {
    println "Committing..."
    graph.tx().commit()
  }
}


t= new Date();
for(i=1; i<=5716808; i++){
  v = graph.addVertex()
  v.property("pageID",i)
  mutate()
  println "Added vertex " + i
}

graph.tx().commit(); use (groovy.time.TimeCategory) { "DONE in ${new Date() - t}" }

