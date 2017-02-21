intGraph = TitanFactory.open('./conf/intSample.properties');
t = intGraph.traversal();
t.V().values('num');
t.V().sideEffect{it.get().property('num', -1 * it.get().value('num'))};
t.V().values('num');
intGraph.tx().commit()
