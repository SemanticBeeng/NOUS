package gov.pnnl.aristotle.utils

import scala.io.Source
import java.io.PrintWriter
import java.io.File

object CreateGraphVizDotFile {
  
  
  def drawTTLFile(inputPath:String,outputPath:String)
  {
    var output_dot = new PrintWriter(new File(outputPath))
    output_dot.println("digraph data {")
    output_dot.println("rankdir=LR;")
    for (line <- Source.fromFile(inputPath).getLines()) {
      if (line.startsWith("@") || line.startsWith("#") || line.isEmpty()) { ; }
      else {
        appendQuadrupleToDotFile(line,output_dot)
      }
    }
    output_dot.println("}")
    output_dot.flush()
  }
  

  def main(args: Array[String]): Unit = {
    
//   drawTTLFile("/sumitData/work/myprojects/AIM/aristotle-dev/knowledge_graph/triple_drone_sample.txt",
//       "/sumitData/work/myprojects/AIM/aristotle-dev/knowledge_graph/triple_drone_sample.dot")
    drawTTLFile("GraphMineInputTime5.txt", "GraphMineInputTime5.dot")
  }

  def drawGraphMiningOutputFile()
  {
    val path : String = "/sumitData/work/myprojects/AIM/aristotle-dev/knowledge_graph/traphMiningOutput.txt"
    var entity_Map :Map[String,Long] = Map.empty
    var all_patterns_dot = new PrintWriter(new File("all_pattern.dot"))
    var frequent_patterns = new PrintWriter(new File("frequent_pattern.dot"))
    //label="Graph";
    //labelloc=top;
    //labeljust=left;
    all_patterns_dot.println("digraph data {")
    all_patterns_dot.println("rankdir=LR;")
    frequent_patterns.println("digraph data {")
    frequent_patterns.println("rankdir=LR;")
    var all_pattern_counter = 0
    var frequent_pattern_counter = 0
    
    
  for(line <- Source.fromFile(path).getLines()) {
  	if (line.startsWith("@") || line.startsWith("#") || line.isEmpty() ) { ; }
  	else
  	{
  	  if(line.startsWith("All:"))
  	  {
//  	    all_patterns_dot = new PrintWriter(new File(s"all_$all_pattern_counter.dot"))
//  	    
//  	    all_patterns_dot.println("digraph data {")
//  	    all_patterns_dot.println('"'+"label" + '"' + "=Support_"
//  	        +line.trim().split("\\t")(line.trim().split("\\t").length -1 ) )
//  	    all_patterns_dot.println("rankdir=LR;")
      	appendQuadrupleToDotFile(line.replaceAll("All:", "").
  	  	    replaceAll("\t+", "\t"),all_patterns_dot)
  	    all_patterns_dot.println("}")
  	    all_patterns_dot.flush()
  	    all_pattern_counter += 1
  	  }
  	  if(line.startsWith("Frq:"))
  	  {
  	    frequent_patterns.println(s"subgraph clusterstep$frequent_pattern_counter {")
  	    appendQuadrupleToDotFile(line.replaceAll("Frq:", "").
  	  	    replaceAll("\t+", "\t"),frequent_patterns)
  	  	frequent_patterns.println("}")  
  	  	frequent_pattern_counter += 1

  	  }
  	}
  }
    
    all_patterns_dot.println("}")
    frequent_patterns.println("}")
    
    all_patterns_dot.flush()
    frequent_patterns.flush()

  }
  
  def appendQuadrupleToDotFile(quadruple : String, file : PrintWriter) =
  {
    val quadruple_array = quadruple.trim().replaceAll("\"", "").split("\\t")
    
    val number_of_edge = quadruple_array.length/3
    var i = 0
    // A Pattern can be of any length so take set of 3 and draw as one line
    for(i <- 0 to  number_of_edge-1)
    {
        var base_index = i * 3
        file.println('"' + quadruple_array(base_index) + '"' + " -> " + '"' + quadruple_array(base_index + 2) +
          '"' + " [label=" + '"' + quadruple_array(base_index +1) + '"' + "]")
      }
    
  }
}