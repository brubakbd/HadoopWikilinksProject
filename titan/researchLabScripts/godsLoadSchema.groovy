conf = new BaseConfiguration()
conf.setProperty("gremlin.graph", "com.thinkaurelius.titan.core.TitanFactory")
conf.setProperty("storage.backend", "hbase")
conf.setProperty("storage.hostname", "hadoop-16.csse.rose-hulman.edu")
conf.setProperty("storage.hbase.table", "godsGraph")
conf.setProperty("storage.batch-loading", true)
conf.setProperty("storage.hbase.ext.zookeeper.znode.parent","/hbase-unsecure")
conf.setProperty("storage.hbase.ext.hbase.zookeeper.property.clientPort", 2181)
conf.setProperty("cache.db-cache",true)
conf.setProperty("cache.db-cache-clean-wait", 20)
conf.setProperty("cache.db-cache-time", 180000)
conf.setProperty("cache.db-cache-size", 0.5)

graph = TitanFactory.open(conf)

m = graph.openManagement()
m.makePropertyKey("name").dataType(String.class).make();
m.makePropertyKey("age").dataType(Integer.class).make();
m.makeEdgeLabel("father").multiplicity(Multiplicity.MANY2ONE).make()
m.makeEdgeLabel("mother").multiplicity(Multiplicity.MANY2ONE).make()
m.makeEdgeLabel("battled").make()
m.makeEdgeLabel("pet").make()
m.makeEdgeLabel("brother").make()
m.makeEdgeLabel("lives").make()
m.makeVertexLabel("titan").make()
m.makeVertexLabel("location").make()
m.makeVertexLabel("god").make()
m.makeVertexLabel("demigod").make()
m.makeVertexLabel("human").make()
m.makeVertexLabel("monster").make()
m.commit()

graph.tx().commit();
