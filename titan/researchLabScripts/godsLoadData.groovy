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

saturn = graph.addVertex(T.label, "titan", "name", "saturn", "age", 10000);
sky = graph.addVertex(T.label, "location", "name", "sky");
sea = graph.addVertex(T.label, "location", "name", "sea");
jupiter = graph.addVertex(T.label, "god", "name", "jupiter", "age", 5000);
neptune = graph.addVertex(T.label, "god", "name", "neptune", "age", 4500);
hercules = graph.addVertex(T.label, "demigod", "name", "hercules", "age", 30);
alcmene = graph.addVertex(T.label, "human", "name", "alcmene", "age", 45);
pluto = graph.addVertex(T.label, "god", "name", "pluto", "age", 4000);
nemean = graph.addVertex(T.label, "monster", "name", "nemean");
hydra = graph.addVertex(T.label, "monster", "name", "hydra");
cerberus = graph.addVertex(T.label, "monster", "name", "cerberus");
tartarus = graph.addVertex(T.label, "monster", "name", "tartarus");

jupiter.addEdge("father", saturn);
jupiter.addEdge("lives", sky);
jupiter.addEdge("brother", neptune);
jupiter.addEdge("brother", pluto);

neptune.addEdge("lives", sea);
neptune.addEdge("brother", jupiter);
neptune.addEdge("brother", pluto);

hercules.addEdge("father", jupiter);
hercules.addEdge("mother", alcmene);
hercules.addEdge("battled", nemean);
hercules.addEdge("battled", hydra);
hercules.addEdge("battled", cerberus);
pluto.addEdge("brother", jupiter);
pluto.addEdge("brother", neptune);
pluto.addEdge("lives", tartarus);
pluto.addEdge("pet", cerberus);
cerberus.addEdge("lives", tartarus);

graph.tx().commit();
