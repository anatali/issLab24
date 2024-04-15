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
evattr = {
    'color': 'darkgreen',
    'style': 'dotted'
}
with Diagram('ledusageArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxledusage', graph_attr=nodeattr):
          ledusage=Custom('ledusage','./qakicons/symActorSmall.png')
     with Cluster('ctxledalone', graph_attr=nodeattr):
          led=Custom('led(ext)','./qakicons/externalQActor.png')
     ledusage >> Edge(color='blue', style='solid',  decorate='true', label='<turnOn &nbsp; turnOff &nbsp; >',  fontcolor='blue') >> led
diag
