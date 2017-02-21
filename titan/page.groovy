def parse(line, factory){
  def (id, name) = line.split('\t').toList()
  def label = "page"
  id = "${label}:${id}".toString()
  def v1 = factory.vertex(id, label)
  v1.property("name", name)
  return v1
}
