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
conf.setProperty("storage.hbase.table", "linksGraph2")

graph = TitanFactory.open(conf)

batchSize = 500000 
counter = new java.util.concurrent.atomic.AtomicLong()
cache = [:]

mutate = { ->
  if (0 == counter.incrementAndGet() % batchSize) {
    graph.tx().commit()
  }
}


addVertex = { def vid ->
  def v = graph.addVertex()
  v.property("pageID",vid)
  mutate()
  return v
}

vertex = { def vid ->
  cache.computeIfAbsent(vid, addVertex)
}

edge = {def vid1, def vid2 ->
  vertex(vid1).addEdge("linksTo", vertex(vid2))
}

t = new Date(); 
new File("./bin/wikilinksData/part-m-00000").eachLine { def line ->
  def (id1, id2) = line.split("\t", 2)
  edge(id1, id2)
  println "Added an edge " + id1 + ", " + id2
};
graph.tx().commit(); use (groovy.time.TimeCategory) { "DONE in ${new Date() - t}" }
