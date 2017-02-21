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
conf.setProperty("storage.hbase.table", "wikiGraph")

graph = TitanFactory.open(conf)

g = graph.traversal()

g.V(""+args[0]).as("node").both().as("adjacent").dedup("node","adjacent").
  select("node","adjacent").as("na").
  select("na").by(__.as("na").select("adjacent").as("a").select("na").select("node").outE().where(inV().where(eq("a"))).fold()).as("outEdges").
  select("na").by(__.as("na").select("adjacent").as("a").select("na").select("node").inE().where(outV().where(eq("a"))).fold()).as("inEdges").
  select("node","adjacent","outEdges","inEdges")
