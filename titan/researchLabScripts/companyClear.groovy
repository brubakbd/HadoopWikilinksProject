import com.thinkaurelius.titan.core.*
import com.thinkaurelius.titan.core.titan.*
import com.thinkaurelius.titan.core.util.TitanCleanup

graph = TitanFactory.open("./conf/companySample.properties")

graph.close()

TitanCleanup.clear(graph)
