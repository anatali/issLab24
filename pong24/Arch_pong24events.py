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
with Diagram('pong24eventsArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxpongev', graph_attr=nodeattr):
          pongev=Custom('pongev','./qakicons/symActorWithobjSmall.png')
     with Cluster('ctxpingev', graph_attr=nodeattr):
          pingev=Custom('pingev(ext)','./qakicons/externalQActor.png')
     pongev >> Edge( label='ball', **eventedgeattr, decorate='true', fontcolor='red') >> sys
diag