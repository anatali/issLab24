### conda install diagrams
from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('servicemath24synchArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env  ' ):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxservice|localhost:8011', graph_attr=nodeattr):
          servicemath=Custom('servicemath','./qakicons/symActorWithobjSmall.png')
     f=Custom('f','./qakicons/server.png')
     servicemath >> Edge( label='out', **eventedgeattr, decorate='true', fontcolor='red') >> sys
     f >> Edge(color='blue', style='solid', decorate='true', label='< &harr; >',  fontcolor='blue') >> servicemath
diag
