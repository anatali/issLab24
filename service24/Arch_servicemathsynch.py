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
with Diagram('servicemathsynchArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxservice', graph_attr=nodeattr):
          display=Custom('display','./qakicons/symActorWithobjSmall.png')
          displayweb=Custom('displayweb','./qakicons/symActorSmall.png')
          servicemath=Custom('servicemath','./qakicons/symActorWithobjSmall.png')
     servicedev=Custom('servicedev','./qakicons/server.png')
     displayweb >> Edge(color='blue', style='solid', decorate='true', label='<out &nbsp; show>',  fontcolor='blue') >> servicedev
     servicemath >> Edge(color='blue', style='solid',  decorate='true', label='<show &nbsp; >',  fontcolor='blue') >> displayweb
diag
