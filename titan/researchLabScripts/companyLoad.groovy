conf = new BaseConfiguration()
conf.setProperty("gremlin.graph", "com.thinkaurelius.titan.core.TitanFactory")
conf.setProperty("storage.backend", "hbase")
conf.setProperty("storage.hostname", "hadoop-16.csse.rose-hulman.edu, hadoop-63.csse.rose-hulman.edu, hadoop-64.csse.rose-hulman.edu")
conf.setProperty("storage.batch-loading", true)
conf.setProperty("storage.hbase.ext.zookeeper.znode.parent","/hbase-unsecure")
conf.setProperty("storage.hbase.ext.hbase.zookeeper.property.clientPort", 2181)
conf.setProperty("cache.db-cache",true)
conf.setProperty("cache.db-cache-clean-wait", 20)
conf.setProperty("cache.db-cache-time", 180000)
conf.setProperty("cache.db-cache-size", 0.9)
conf.setProperty("storage.hbase.table", "companyGraph")

graph = TitanFactory.open(conf)
graph.tx().rollback()

m = graph.openManagement()
m.makeEdgeLabel("friendsWith").make()
m.makePropertyKey("name").dataType(String.class).make()
m.commit()
graph.tx().commit()

batchSize = 500000 
counter = new java.util.concurrent.atomic.AtomicLong()
cache = [:]

mutate = { ->
  if (0 == counter.incrementAndGet() % batchSize) {
    graph.tx().commit()
  }
}


addVertex = { def username ->
  def v = graph.addVertex()
  v.property("name",username)
  mutate()
  return v
}

vertex = { def name ->
  cache.computeIfAbsent(name, addVertex)
}

edge = {def name, def other ->
  vertex(name).addEdge("friendsWith", vertex(other))
}

t = new Date(); 
new File("./bin/friendsList.txt").eachLine { def line ->
  def (user, allfriends) = line.split("\t", 2)
  friends = allfriends.split(",")
  friends.each {
    edge(user, it)
    println "Added an edge " + user + ", " + it
  }
};
graph.tx().commit(); use (groovy.time.TimeCategory) { "DONE in ${new Date() - t}" }
