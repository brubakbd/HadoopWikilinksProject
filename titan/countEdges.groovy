godsGraph = TitanFactory.open('./conf/godsSample.properties');
t = godsGraph.traversal();
t.V().as('vertex', 'in edges', 'out edges').select('vertex','in edges','out edges').by('name').by(__.in().count()).by(__.out().count());
godsGraph.tx().commit();
