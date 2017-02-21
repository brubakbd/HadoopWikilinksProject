companyGraph = TitanFactory.open('./conf/companySample.properties');
t = companyGraph.traversal();
def avg = t.E().count().next().div(t.V().count().next());
