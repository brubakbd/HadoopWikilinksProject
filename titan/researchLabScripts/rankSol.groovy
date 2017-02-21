intGraph = TitanFactory.open('./conf/intSample.properties');
t = intGraph.traversal();
t.V().as("node").union(inE().as("edge")).select("node").sideEffect( t -> {;
intGraph.tx().commit()
