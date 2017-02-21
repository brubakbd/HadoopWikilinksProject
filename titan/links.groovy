def parse(line, factory){
  def (from, to) = line.split('\t')
  def label = "page"
  def v1 = factory.vertex("${label}:${from}".toString(), label)
  def v2 = factory.vertex("${label}:${to}".toString(), label)
  factory.edge(v1, v2, "linksTo"
  return v1
}
